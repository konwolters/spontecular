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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AppController {
    private final JenaService jenaService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/export")
    public String export(Model model, HttpSession session) {
        Classes classes = (Classes) session.getAttribute("classes");
        Hierarchy hierarchy = (Hierarchy) session.getAttribute("hierarchy");
        Relations relations = (Relations) session.getAttribute("relations");
        Constraints constraints = (Constraints) session.getAttribute("constraints");

        String content = jenaService.createOntology(classes, hierarchy, relations, constraints);
        model.addAttribute("content", content);
        return "export";
    }

    @GetMapping("favicon.ico")
    void favicon(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204 No Content
    }
}
