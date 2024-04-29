package com.example.spontecular.controller;

import com.example.spontecular.dto.Classes;
import com.example.spontecular.dto.Constraints;
import com.example.spontecular.dto.Hierarchy;
import com.example.spontecular.dto.Relations;
import com.example.spontecular.service.GptService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GptController {
    private final GptService gptService;

    @PostMapping("/getClasses")
    public String getClasses(@RequestParam String inputText, Model model, HttpSession session) {
        //Classes classes = gptService.getClasses(inputText);

        //Dummy classes
        Classes classes = new Classes();
        classes.setClasses(List.of("Satellite", "Chassis", "Framework", "Rail", "Sidewall", "Circuit board", "Solar cell", "Sensor wire", "Magnetic coil", "Groove", "Attitude Determination and Control System", "Connector", "Module", "Bus connector", "Cable"));

        model.addAttribute("gptResponseMessage", classes.toString());
        model.addAttribute("fieldTitle", "Classes:");
        model.addAttribute("modalTitle", "Edit Classes:");
        model.addAttribute("endpointUrl", "/getHierarchy");
        model.addAttribute("targetElementId", "hierarchyDiv");
        session.setAttribute("classes", classes);

        return "fragments :: featureFragment";
    }

    @PostMapping("/getHierarchy")
    public String getHierarchy(Model model, @RequestParam String inputText, HttpSession session) {
        Classes classes = (Classes) session.getAttribute("classes");
        //Hierarchy hierarchy = gptService.getHierarchy(inputText, classes);

        //Dummy hierarchy
        Hierarchy hierarchy = new Hierarchy();
        hierarchy.setHierarchy(List.of(
                List.of("Component", "Chassis"),
                List.of("Component", "Rail"),
                List.of("Component", "Framework"),
                List.of("Component", "Sidewall"),
                List.of("Component", "Circuit board"),
                List.of("Component", "Elastic blushing"),
                List.of("Circuit board", "Double-sided circuit board"),
                List.of("Circuit board", "FR-4"),
                List.of("Circuit board", "Printed Circuit Board"),
                List.of("Component", "Connector"),
                List.of("Connector", "Bus connector")
        ));

        model.addAttribute("gptResponseMessage", hierarchy.toString());
        model.addAttribute("fieldTitle", "Hierarchy:");
        model.addAttribute("modalTitle", "Edit Hierarchy:");
        model.addAttribute("endpointUrl", "/getRelations");
        model.addAttribute("targetElementId", "relationsDiv");
        session.setAttribute("hierarchy", hierarchy);

        return "fragments :: featureFragment";
    }

    @PostMapping("/getRelations")
    public String getRelations(Model model, @RequestParam String inputText, HttpSession session) {
        Classes classes = (Classes) session.getAttribute("classes");
        //Relations relations = gptService.getRelations(inputText, classes);

        //Dummy relations
        Relations relations = new Relations();
        relations.setRelations(List.of(
                List.of("Chassis", "consistsOf", "Framework"),
                List.of("Sidewall", "isMadeFrom", "Circuit board"),
                List.of("Sidewall", "servesAs", "Circuit board"),
                List.of("Double-sided circuit board", "mayServeAs", "Circuit board"),
                List.of("Solar cell", "isMountedOn", "Printed circuit board"),
                List.of("Satellite", "needs", "Connector"),
                List.of("Internal module", "consistOf", "FR-4"),
                List.of("Internal module", "consistOf", "Circuit board"),
                List.of("Module", "isStackedInside", "Satellite"),
                List.of("Elastic bushing", "isPlacedIn", "Groove")
        ));

        model.addAttribute("gptResponseMessage", relations.toString());
        model.addAttribute("fieldTitle", "Non-taxonomic Relations:");
        model.addAttribute("modalTitle", "Edit non-taxonomic Relations:");
        model.addAttribute("endpointUrl", "/getConstraints");
        model.addAttribute("targetElementId", "constraintsDiv");
        session.setAttribute("relations", relations);
        return "fragments :: featureFragment";
    }

    @PostMapping("/getConstraints")
    public String getConstraints(Model model, @RequestParam String inputText, HttpSession session) {
        Relations relations = (Relations) session.getAttribute("relations");
        //Constraints constraints = gptService.getConstraints(inputText, relations);

        //Dummy constraints
        Constraints constraints = new Constraints();
        constraints.setConstraints(List.of(
                List.of("Chassis", "consistsOf", "Framework", "1", "1"),
                List.of("Sidewall", "isMadeFrom", "Circuit board", "1", "1"),
                List.of("Sidewall", "servesAs", "Circuit board", "1", "1"),
                List.of("Double-sided circuit board", "mayServeAs", "Circuit board", "1", "1"),
                List.of("Solar cell", "isMountedOn", "Printed circuit board", "1", "1"),
                List.of("Satellite", "needs", "Connector", "1", "1"),
                List.of("Internal module", "consistOf", "FR-4", "1", "1"),
                List.of("Internal module", "consistOf", "Circuit board", "1", "1"),
                List.of("Module", "isStackedInside", "Satellite", "1", "1"),
                List.of("Elastic bushing", "isPlacedIn", "Groove", "1", "1")
        ));

        model.addAttribute("gptResponseMessage", constraints.toString());
        model.addAttribute("fieldTitle", "Constraints:");
        model.addAttribute("modalTitle", "Edit Constraints:");
        session.setAttribute("constraints", constraints);

        return "fragments :: featureFragment";
    }
}

