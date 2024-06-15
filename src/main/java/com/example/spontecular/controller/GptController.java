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

        System.out.println("newParentClass" + newParentClass);
        System.out.println("newChildClass" + newChildClass);
        model.addAttribute(
                "hierarchyItem",
                new HierarchyItem(newParentClass, newChildClass, false)
        );

        return "hierarchy-fragments :: hierarchyItem";
    }

    @PutMapping("/feature")
    public String updateFeature(@ModelAttribute Classes classes, Model model, HttpSession session) {

        List<ClassItem> itemList = classes.getClasses().stream()
                .filter(classItem -> classItem.getValue() != null)
                .toList();
        session.setAttribute("classes", classes);

        model.addAttribute("featureType", "classes");
        model.addAttribute("nextFeatureType", "hierarchy");
        model.addAttribute("itemList", itemList);
        return "fragments :: featureFragment";
    }
}

