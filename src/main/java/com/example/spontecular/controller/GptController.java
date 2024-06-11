package com.example.spontecular.controller;

import com.example.spontecular.dto.*;
import com.example.spontecular.model.FeatureType;
import com.example.spontecular.service.FeatureFactory;
import com.example.spontecular.service.GptService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class GptController {
    private final GptService gptService;

    @PostMapping("/featureRequest")
    public String getFeatureResponse(@RequestParam String featureType,
                                     @RequestParam String inputText,
                                     Model model,
                                     HttpSession session) {

        Feature featureResponse = FeatureFactory.createFeature(featureType, inputText, session, gptService);
        model.addAllAttributes(featureResponse.getResponseMap());

        return "fragments :: featureFragment";
    }
}

