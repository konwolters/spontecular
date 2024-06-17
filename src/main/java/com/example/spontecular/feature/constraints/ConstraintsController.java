package com.example.spontecular.feature.constraints;

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
public class ConstraintsController {

    @PostMapping("/constraints")
    public String addConstraint(@RequestParam String newSubject,
                                @RequestParam String newPredicate,
                                @RequestParam String newObject,
                                @RequestParam String minCardinality,
                                @RequestParam String maxCardinality,
                                Model model) {

        model.addAttribute(
                "constraintsItem",
                new ConstraintsItem(newSubject, newPredicate, newObject, minCardinality, maxCardinality, false)
        );

        return "constraints-fragments :: constraintsItem";
    }

    @PutMapping("/constraints")
    public String updateConstraints(@ModelAttribute Constraints constraints, Model model, HttpSession session) {

        // Remove all deleted constraints elements
        constraints.setConstraints(
                constraints.getConstraints().stream()
                        .filter(hierarchyItem -> hierarchyItem.getSubject() != null)
                        .toList()
        );

        session.setAttribute("constraints", constraints);
        model.addAllAttributes(constraints.getResponseMap());
        return "fragments :: featureFragment";
    }
}
