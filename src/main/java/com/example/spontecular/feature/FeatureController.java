package com.example.spontecular.feature;

import com.example.spontecular.feature.classes.ClassItem;
import com.example.spontecular.feature.classes.Classes;
import com.example.spontecular.feature.constraints.Constraints;
import com.example.spontecular.feature.constraints.ConstraintsItem;
import com.example.spontecular.feature.hierarchy.Hierarchy;
import com.example.spontecular.feature.hierarchy.HierarchyItem;
import com.example.spontecular.feature.relations.RelationItem;
import com.example.spontecular.feature.relations.Relations;
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
public class FeatureController {
    private final FeatureService gptService;

    @PostMapping("/featureRequest")
    public String getFeatureResponse(@RequestParam String featureType,
                                     @RequestParam String inputText,
                                     Model model,
                                     HttpSession session) {

        Feature featureResponse = FeatureFactory.createFeature(featureType, inputText, session, gptService);
        model.addAllAttributes(featureResponse.getResponseMap());

        return "fragments :: featureFragment";
    }
//
//    @PostMapping("/classes")
//    public String addClass(@RequestParam String newClass, Model model) {
//        model.addAttribute("classItem", new ClassItem(newClass, false));
//
//        return "classes-fragments :: classesItem";
//    }
//
//    @PutMapping("/classes")
//    public String updateClasses(@ModelAttribute Classes classes, Model model, HttpSession session) {
//
//        //Remove all deleted classes
//        classes.setClasses(
//                classes.getClasses().stream()
//                        .filter(classItem -> classItem.getValue() != null)
//                        .toList()
//        );
//
//        session.setAttribute("classes", classes);
//        model.addAllAttributes(classes.getResponseMap());
//        return "fragments :: featureFragment";
//    }
//
//
//    @PostMapping("/hierarchy")
//    public String addHierarchy(@RequestParam String newParentClass,
//                               @RequestParam String newChildClass,
//                               Model model) {
//
//        model.addAttribute(
//                "hierarchyItem",
//                new HierarchyItem(newParentClass, newChildClass, false)
//        );
//
//        return "hierarchy-fragments :: hierarchyItem";
//    }
//
//    @PutMapping("/hierarchy")
//    public String updateHierarchy(@ModelAttribute Hierarchy hierarchy, Model model, HttpSession session) {
//
//        //Remove all deleted hierarchy elements
//        hierarchy.setHierarchy(
//                hierarchy.getHierarchy().stream()
//                        .filter(hierarchyItem -> hierarchyItem.getParent() != null)
//                        .toList()
//        );
//
//        session.setAttribute("hierarchy", hierarchy);
//        model.addAllAttributes(hierarchy.getResponseMap());
//        return "fragments :: featureFragment";
//    }
//
//
//    @PostMapping("/relations")
//    public String addRelation(@RequestParam String newSubject,
//                              @RequestParam String newPredicate,
//                              @RequestParam String newObject,
//                              Model model) {
//
//        model.addAttribute(
//                "relationsItem",
//                new RelationItem(newSubject, newPredicate, newObject, false)
//        );
//
//        return "relations-fragments :: relationsItem";
//    }
//
//    @PutMapping("/relations")
//    public String updateRelations(@ModelAttribute Relations relations, Model model, HttpSession session) {
//
//        //Remove all deleted relations elements
//        relations.setRelations(
//                relations.getRelations().stream()
//                        .filter(hierarchyItem -> hierarchyItem.getSubject() != null)
//                        .toList()
//        );
//
//        session.setAttribute("relations", relations);
//        model.addAllAttributes(relations.getResponseMap());
//        return "fragments :: featureFragment";
//    }
//
//
//
//    @PostMapping("/constraints")
//    public String addConstraint(@RequestParam String newSubject,
//                              @RequestParam String newPredicate,
//                              @RequestParam String newObject,
//                              @RequestParam String minCardinality,
//                              @RequestParam String maxCardinality,
//                              Model model) {
//
//        model.addAttribute(
//                "constraintsItem",
//                new ConstraintsItem(newSubject, newPredicate, newObject, minCardinality, maxCardinality, false)
//        );
//
//        return "constraints-fragments :: constraintsItem";
//    }
//
//    @PutMapping("/constraints")
//    public String updateConstraints(@ModelAttribute Constraints constraints, Model model, HttpSession session) {
//
//        //Remove all deleted constraints elements
//        constraints.setConstraints(
//                constraints.getConstraints().stream()
//                        .filter(hierarchyItem -> hierarchyItem.getSubject() != null)
//                        .toList()
//        );
//
//        session.setAttribute("constraints", constraints);
//        model.addAllAttributes(constraints.getResponseMap());
//        return "fragments :: featureFragment";
//    }
}

