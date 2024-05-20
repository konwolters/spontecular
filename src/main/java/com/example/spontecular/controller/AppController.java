package com.example.spontecular.controller;

import com.example.spontecular.dto.Classes;
import com.example.spontecular.dto.Constraints;
import com.example.spontecular.dto.Hierarchy;
import com.example.spontecular.dto.Relations;
import com.example.spontecular.service.JenaService;
import com.example.spontecular.service.SpecificationService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AppController {
    private final JenaService jenaService;
    private final SpecificationService specificationService;



    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @PostMapping("/loadSpecification")
    public String loadSpecification(Model model,
                                    @RequestParam(name = "specification_type") String specificationType,
                                    @RequestParam(required = false, name = "pdfFile") MultipartFile pdfFile) {

        String specification = switch (specificationType) {
            case "example" -> specificationService.loadExampleSpecification();
            case "pdf" -> specificationService.loadPdfSpecification(pdfFile);
            case "word" -> specificationService.loadWordSpecification();
            default -> "";
        };

        model.addAttribute("specification", specification);

        return "fragments :: specificationFragment";
    }

    @PostMapping("/export")
    public String export(Model model, HttpSession session) {
        Classes classes = (Classes) session.getAttribute("classes");
        Hierarchy hierarchy = (Hierarchy) session.getAttribute("hierarchy");
        Relations relations = (Relations) session.getAttribute("relations");
        Constraints constraints = (Constraints) session.getAttribute("constraints");

        JenaService.Response response = jenaService.createOntology(classes, hierarchy, relations, constraints);
        String content = response.getModelAsString();
        List<String> errorMessages = response.getErrorMessages();

        model.addAttribute("content", content);
        model.addAttribute("errorMessages", errorMessages);
        return "export";
    }

    @GetMapping("favicon.ico")
    void favicon(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
