package com.example.spontecular.feature;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class FeatureController {
    private final FeatureService featureService;

    @PostMapping("/featureRequest")
    public String getFeatureResponse(@RequestParam String featureType,
                                     @RequestParam String inputText,
                                     Model model,
                                     HttpSession session) {

        Feature featureResponse = FeatureFactory.createFeature(featureType, inputText, session, featureService);
        model.addAllAttributes(featureResponse.getResponseMap());

        return "fragments :: featureFragment";
    }
}

