package com.example.spontecular.export;

import com.example.spontecular.dto.SettingsForm;
import com.example.spontecular.feature.classes.Classes;
import com.example.spontecular.feature.constraints.Constraints;
import com.example.spontecular.feature.hierarchy.Hierarchy;
import com.example.spontecular.feature.relations.Relations;
import com.example.spontecular.jena.JenaService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ExportController {
    private final JenaService jenaService;

    @GetMapping("/export")
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


    @PostMapping("/download-owl")
    public ResponseEntity<ByteArrayResource> downloadOwl(@RequestParam("owlContent") String owlContent) {
        byte[] contentBytes = owlContent.getBytes();
        ByteArrayResource resource = new ByteArrayResource(contentBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.owl");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/owl+xml");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(contentBytes.length)
                .body(resource);
    }
}
