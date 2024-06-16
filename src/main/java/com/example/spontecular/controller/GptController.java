package com.example.spontecular.controller;

import com.example.spontecular.dto.*;
import com.example.spontecular.dto.formDtos.ClassItem;
import com.example.spontecular.service.FeatureFactory;
import com.example.spontecular.service.GptService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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

    @PostMapping("/classes")
    public String addClass(@RequestParam String newClass, Model model) {
        model.addAttribute("classItem", new ClassItem(newClass, false));

        return "classes-fragments :: classesItem";
    }

    @PostMapping("/hierarchy")
    public String addHierarchy(@RequestParam String newParentClass,
                               @RequestParam String newChildClass,
                               Model model) {

        model.addAttribute(
                "hierarchyItem",
                new HierarchyItem(newParentClass, newChildClass, false)
        );

        return "hierarchy-fragments :: hierarchyItem";
    }

    @PutMapping("/classes")
    public String updateClasses(@ModelAttribute Classes classes, Model model, HttpSession session) {

        //Remove all deleted classes
        classes.setClasses(
                classes.getClasses().stream()
                        .filter(classItem -> classItem.getValue() != null)
                        .toList()
        );

        session.setAttribute("classes", classes);
        model.addAllAttributes(classes.getResponseMap());
        return "fragments :: featureFragment";
    }

    @PutMapping("/hierarchy")
    public String updateHierarchy(@ModelAttribute Hierarchy hierarchy, Model model, HttpSession session) {

        //Remove all deleted hierarchy elements
        hierarchy.setHierarchy(
                hierarchy.getHierarchy().stream()
                        .filter(hierarchyItem -> hierarchyItem.getParent() != null)
                        .toList()
        );

        session.setAttribute("hierarchy", hierarchy);
        model.addAllAttributes(hierarchy.getResponseMap());
        return "fragments :: featureFragment";
    }
}

