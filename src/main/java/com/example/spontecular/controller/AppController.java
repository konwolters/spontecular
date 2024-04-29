package com.example.spontecular.controller;

import com.example.spontecular.dto.Classes;
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
public class AppController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/export")
    public String export(Model model, HttpSession session) {
        Classes classes = (Classes) session.getAttribute("classes");
        model.addAttribute("content", classes.toString());
        return "export";
    }

    @GetMapping("favicon.ico")
    void favicon(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204 No Content
    }
}
