package com.example.spontecular.controller;

import com.example.spontecular.dto.Classes;
import com.example.spontecular.dto.Constraints;
import com.example.spontecular.dto.Hierarchy;
import com.example.spontecular.dto.Relations;
import com.example.spontecular.service.JenaService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AppController {
    private final JenaService jenaService;

    //Import sample Specification
    @Value("classpath:/specification/satellite.txt")
    private Resource specification;

    @GetMapping("/")
    public String index(Model model) {
        try {
            String content = specification.getContentAsString(StandardCharsets.UTF_8);
            model.addAttribute("specification", content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "index";
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
