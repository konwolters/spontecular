package com.example.spontecular.controller;

import com.example.spontecular.dto.Classes;
import com.example.spontecular.dto.Constraints;
import com.example.spontecular.dto.Hierarchy;
import com.example.spontecular.dto.Relations;
import com.example.spontecular.service.JenaService;
import com.example.spontecular.service.SpecificationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AppController {
    private final JenaService jenaService;
    private final SpecificationService specificationService;


    @GetMapping({"/", "/restart"})
    public String index() {
        return "index";
    }

    @PostMapping("/loadSpecification")
    public String loadSpecification(Model model,
                                    @RequestParam(name = "specification_type") String specificationType,
                                    @RequestParam(required = false, name = "pdfFile") MultipartFile pdfFile,
                                    @RequestParam(required = false, name = "wordFile") MultipartFile wordFile) {

        String specification = switch (specificationType) {
            case "example" -> specificationService.loadExampleSpecification();
            case "pdf" -> specificationService.loadPdfSpecification(pdfFile);
            case "word" -> specificationService.loadWordSpecification(wordFile);
            default -> "";
        };
        model.addAttribute("specification", specification);

        return "fragments :: specificationFragment";
    }


    @PostMapping("/export")
    public String export(Model model,
                         @RequestParam String classesText,
                         @RequestParam String hierarchyText,
                         @RequestParam(required = false) String relationsText,
                         @RequestParam(required = false) String constraintsText) {
        Classes classes = new Classes(classesText);

        System.out.println("Hierarchy: " + hierarchyText);
        Hierarchy hierarchy = hierarchyText != null ? new Hierarchy(hierarchyText) : null;
        Relations relations = relationsText != null ? new Relations(relationsText) : null;
        Constraints constraints = constraintsText != null ? new Constraints(constraintsText) : null;

        JenaService.Response response = jenaService.createOntology(classes, hierarchy, relations, constraints);
        String content = response.getModelAsString();
        List<String> errorMessages = response.getErrorMessages();

        model.addAttribute("content", content);
        model.addAttribute("errorMessages", errorMessages);
        return "export";
    }

    @GetMapping("favicon.ico")
    void favicon(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
