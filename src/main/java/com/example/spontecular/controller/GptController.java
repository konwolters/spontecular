package com.example.spontecular.controller;

import com.example.spontecular.dto.*;
import com.example.spontecular.model.Feature;
import com.example.spontecular.service.GptService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class GptController {
    private final GptService gptService;

    @PostMapping("/getClasses")
    public String getClasses(@RequestParam String inputText, Model model, HttpSession session) {
        SettingsForm settings = (SettingsForm) session.getAttribute("settings");
        Classes classes = gptService.getClasses(inputText, settings);

        model.addAllAttributes(getFeatureResponse("classes", classes.toString()));
        session.setAttribute("classes", classes);

        return "fragments :: featureFragment";
    }

    @PostMapping("/getHierarchy")
    public String getHierarchy(Model model, @RequestParam String inputText, HttpSession session) {
        SettingsForm settings = (SettingsForm) session.getAttribute("settings");
        Classes classes = (Classes) session.getAttribute("classes");
        Hierarchy hierarchy = gptService.getHierarchy(inputText, classes.toString(), settings);

        model.addAllAttributes(getFeatureResponse("hierarchy", hierarchy.toString()));
        session.setAttribute("hierarchy", hierarchy);

        return "fragments :: featureFragment";
    }

    @PostMapping("/getRelations")
    public String getRelations(Model model, @RequestParam String inputText, HttpSession session) {
        SettingsForm settings = (SettingsForm) session.getAttribute("settings");
        Classes classes = (Classes) session.getAttribute("classes");
        Relations relations = gptService.getRelations(inputText, classes.toString(), settings);

        model.addAllAttributes(getFeatureResponse("relations", relations.toString()));
        session.setAttribute("relations", relations);

        return "fragments :: featureFragment";
    }

    @PostMapping("/getConstraints")
    public String getConstraints(Model model, @RequestParam String inputText, HttpSession session) {
        SettingsForm settings = (SettingsForm) session.getAttribute("settings");
        Relations relations = (Relations) session.getAttribute("relations");

        Constraints constraints = gptService.getConstraints(inputText, relations.toString(), settings);

        model.addAllAttributes(getFeatureResponse("constraints", constraints.toString()));
        session.setAttribute("constraints", constraints);

        return "fragments :: featureFragment";
    }

    @PostMapping("/reloadFeature")
    public String reloadFeature(Model model,
                                @RequestParam String inputText,
                                @RequestParam String feature,
                                @RequestParam(required = false) String classesText,
                                @RequestParam(required = false) String relationsText,
                                HttpSession session) {
        SettingsForm settings = (SettingsForm) session.getAttribute("settings");

        switch (feature) {
            case "classes" -> model.addAllAttributes(
                    getFeatureResponse(feature, gptService.getClasses(inputText, settings).toString())
            );
            case "hierarchy" -> model.addAllAttributes(
                    getFeatureResponse(feature, gptService.getHierarchy(inputText, classesText, settings).toString())
            );
            case "relations" -> model.addAllAttributes(
                    getFeatureResponse(feature, gptService.getRelations(inputText, classesText, settings).toString())
            );
            case "constraints" -> model.addAllAttributes(
                    getFeatureResponse(feature, gptService.getConstraints(inputText, relationsText, settings).toString())
            );
        }

        return "fragments :: featureFragment";
    }

    private Map<String, String> getFeatureResponse(String featureString, String response) {
        Feature feature = Feature.valueOf(featureString.toUpperCase());

        return feature.getResponseMap(response);
    }
}

