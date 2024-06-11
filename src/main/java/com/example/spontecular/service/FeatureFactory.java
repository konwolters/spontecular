package com.example.spontecular.service;

import com.example.spontecular.dto.*;
import com.example.spontecular.model.FeatureType;
import jakarta.servlet.http.HttpSession;

public class FeatureFactory {
    public static Feature createFeature(String featureType, String inputText, HttpSession session, GptService gptService) {
        SettingsForm settings = (SettingsForm) session.getAttribute("settings");
        switch (FeatureType.fromString(featureType)) {
            case CLASSES:
                Classes classes = gptService.getClasses(inputText, settings);
                session.setAttribute("classes", classes);
                return classes;
            case HIERARCHY:
                Classes existingClasses = (Classes) session.getAttribute("classes");
                Hierarchy hierarchy = gptService.getHierarchy(inputText, existingClasses.toString(), settings);
                session.setAttribute("hierarchy", hierarchy);
                return hierarchy;
            case RELATIONS:
                existingClasses = (Classes) session.getAttribute("classes");
                Relations relations = gptService.getRelations(inputText, existingClasses.toString(), settings);
                session.setAttribute("relations", relations);
                return relations;
            case CONSTRAINTS:
                Relations existingRelations = (Relations) session.getAttribute("relations");
                Constraints constraints = gptService.getConstraints(inputText, existingRelations.toString(), settings);
                session.setAttribute("constraints", constraints);
                return constraints;
            default:
                throw new IllegalArgumentException("Invalid feature type: " + featureType);
        }
    }
}
