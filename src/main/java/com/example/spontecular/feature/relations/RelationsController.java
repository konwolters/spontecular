package com.example.spontecular.feature.relations;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/relations")
@RequiredArgsConstructor
public class RelationsController {

    @PostMapping
    public String addRelation(@RequestParam String newSubject,
                              @RequestParam String newPredicate,
                              @RequestParam String newObject,
                              Model model) {

        model.addAttribute(
                "relationsItem",
                new RelationItem(newSubject, newPredicate, newObject, false)
        );

        return "relations-fragments :: relationsItem";
    }

    @PutMapping
    public String updateRelations(@ModelAttribute Relations relations, Model model, HttpSession session) {

        // Remove all deleted relations elements
        relations.setRelations(
                relations.getRelations().stream()
                        .filter(hierarchyItem -> hierarchyItem.getSubject() != null)
                        .toList()
        );

        session.setAttribute("relations", relations);
        model.addAllAttributes(relations.getResponseMap());
        return "fragments :: featureFragment";
    }
}
