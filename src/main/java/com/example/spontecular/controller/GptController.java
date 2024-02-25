package com.example.spontecular.controller;

import com.example.spontecular.service.GptService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class GptController {
    private final GptService gptService;

    @Value("${PROMPT.CLASSES}")
    private String promptClasses;

    @Value("${PROMPT.HIERARCHY}")
    private String promptHierarchy;

    @Value("${PROMPT.RELATIONS}")
    private String promptRelations;

    @Value("${PROMPT.AXIOMS}")
    private String promptAxioms;

    @PostMapping("/getClasses")
    public String getResponse(Model model, @RequestParam String inputText) {
        String gptResponseMessage = gptService.getGptResponseMessage(inputText, promptClasses);

        model.addAttribute("gptResponse", gptResponseMessage);
        return "index";
    }

    @PostMapping("/getHierarchy")
    public String getHierarchy(Model model, @RequestParam String inputText) {
        String gptResponseMessage = gptService.getGptResponseMessage(inputText, promptHierarchy);

        model.addAttribute("gptResponse", gptResponseMessage);
        return "index";
    }

    @PostMapping("/getRelations")
    public String getRelations(Model model, @RequestParam String inputText) {
        String gptResponseMessage = gptService.getGptResponseMessage(inputText, promptRelations);

        model.addAttribute("gptResponse", gptResponseMessage);
        return "index";
    }

    @PostMapping("/getAxioms")
    public String getAxioms(Model model, @RequestParam String inputText) {
        String gptResponseMessage = gptService.getGptResponseMessage(inputText, promptAxioms);

        model.addAttribute("gptResponse", gptResponseMessage);
        return "index";
    }
}

