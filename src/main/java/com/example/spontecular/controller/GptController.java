package com.example.spontecular.controller;

import com.example.spontecular.dto.Classes;
import com.example.spontecular.dto.Constraints;
import com.example.spontecular.dto.Hierarchy;
import com.example.spontecular.dto.Relations;
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
        Classes classes = gptService.getClasses(inputText);

        model.addAllAttributes(getFeatureResponse("classes", classes.toString()));
        session.setAttribute("classes", classes);

        return "fragments :: featureFragment";
    }

    @PostMapping("/getHierarchy")
    public String getHierarchy(Model model, @RequestParam String inputText, HttpSession session) {
        Classes classes = (Classes) session.getAttribute("classes");
        Hierarchy hierarchy = gptService.getHierarchy(inputText, classes.toString());

        model.addAllAttributes(getFeatureResponse("hierarchy", hierarchy.toString()));
        session.setAttribute("hierarchy", hierarchy);

        return "fragments :: featureFragment";
    }

    @PostMapping("/getRelations")
    public String getRelations(Model model, @RequestParam String inputText, HttpSession session) {
        Classes classes = (Classes) session.getAttribute("classes");
        Relations relations = gptService.getRelations(inputText, classes.toString());

        model.addAllAttributes(getFeatureResponse("relations", relations.toString()));
        session.setAttribute("relations", relations);

        return "fragments :: featureFragment";
    }

    @PostMapping("/getConstraints")
    public String getConstraints(Model model, @RequestParam String inputText, HttpSession session) {
        Relations relations = (Relations) session.getAttribute("relations");

        Constraints constraints = gptService.getConstraints(inputText, relations.toString());

        model.addAllAttributes(getFeatureResponse("constraints", constraints.toString()));
        session.setAttribute("constraints", constraints);

        return "fragments :: featureFragment";
    }

    @PostMapping("/reloadFeature")
    public String reloadFeature(Model model,
                                @RequestParam String inputText,
                                @RequestParam String feature,
                                @RequestParam(required = false) String classesText,
                                @RequestParam(required = false) String relationsText) {

        switch (feature) {
            case "classes" -> model.addAllAttributes(
                    getFeatureResponse(feature, gptService.getClasses(inputText).toString())
            );
            case "hierarchy" -> model.addAllAttributes(
                    getFeatureResponse(feature, gptService.getHierarchy(inputText, classesText).toString())
            );
            case "relations" -> model.addAllAttributes(
                    getFeatureResponse(feature, gptService.getRelations(inputText, classesText).toString())
            );
            case "constraints" -> model.addAllAttributes(
                    getFeatureResponse(feature, gptService.getConstraints(inputText, relationsText).toString())
            );
        }

        return "fragments :: featureFragment";
    }

    private Map<String, String> getFeatureResponse(String feature, String response) {
        Map<String, String> responseMap;

        switch (feature) {
            case "classes" -> responseMap = Map.of(
                    "gptResponseMessage", response,
                    "feature", "classes",
                    "fieldTitle", "Classes:",
                    "endpointUrl", "/getHierarchy",
                    "targetElementId", "hierarchyDiv");
            case "hierarchy" -> responseMap = Map.of(
                    "gptResponseMessage", response,
                    "feature", "hierarchy",
                    "fieldTitle", "Hierarchy:",
                    "endpointUrl", "/getRelations",
                    "targetElementId", "relationsDiv");
            case "relations" -> responseMap = Map.of(
                    "gptResponseMessage", response,
                    "feature", "relations",
                    "fieldTitle", "Non-taxonomic Relations:",
                    "endpointUrl", "/getConstraints",
                    "targetElementId", "constraintsDiv");
            case "constraints" -> responseMap = Map.of(
                    "gptResponseMessage", response,
                    "feature", "constraints",
                    "fieldTitle", "Constraints:");
            default -> responseMap = Map.of();
        }
        return responseMap;
    }
}

