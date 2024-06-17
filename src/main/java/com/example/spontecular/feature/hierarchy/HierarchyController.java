package com.example.spontecular.feature.hierarchy;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HierarchyController {

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

    @PutMapping("/hierarchy")
    public String updateHierarchy(@ModelAttribute Hierarchy hierarchy, Model model, HttpSession session) {

        // Remove all deleted hierarchy elements
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
