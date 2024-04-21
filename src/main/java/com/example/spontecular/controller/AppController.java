package com.example.spontecular.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AppController {
    private final ChatClient chatClient;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/ai/generate")
    public String generate(Model model, @RequestParam(value = "message", defaultValue = "Tell me about your day in a short assay in json format.") String message) {
        SystemMessage systemMessage = new SystemMessage("You are a pirate and talk accordingly.");
        UserMessage userMessage = new UserMessage(message);
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        model.addAttribute("gptResponse", chatClient.call(prompt).getResult().getOutput().getContent());
        return "index";
    }

    @GetMapping("favicon.ico")
    void favicon(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204 No Content
    }
}
