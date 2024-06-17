package com.example.spontecular.feature.classes;

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
public class ClassController {

    @PostMapping("/classes")
    public String addClass(@RequestParam String newClass, Model model) {
        model.addAttribute("classItem", new ClassItem(newClass, false));

        return "classes-fragments :: classesItem";
    }

    @PutMapping("/classes")
    public String updateClasses(@ModelAttribute Classes classes, Model model, HttpSession session) {

        // Remove all deleted classes
        classes.setClasses(
                classes.getClasses().stream()
                        .filter(classItem -> classItem.getValue() != null)
                        .toList()
        );

        session.setAttribute("classes", classes);
        model.addAllAttributes(classes.getResponseMap());
        return "fragments :: featureFragment";
    }
}
