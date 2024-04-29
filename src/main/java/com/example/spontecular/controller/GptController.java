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

@Controller
@RequiredArgsConstructor
public class GptController {
    private final GptService gptService;

    String gptResponseMessage = """
            {
              "class": "LivingThing",
              "subclasses": [
                {
                  "class": "Animal",
                  "subclasses": [
                    {
                      "class": "Mammal",
                      "subclasses": [
                        {
                          "class": "Dog",
                          "subclasses": []
                        },
                        {
                          "class": "Cat",
                          "subclasses": [
                            {
                              "class": "PersianCat",
                              "subclasses": []
                            },
                            {
                              "class": "SiameseCat",
                              "subclasses": []
                            }
                          ]
                        }
                      ]
                    },
                    {
                      "class": "Bird",
                      "subclasses": [
                        {
                          "class": "Sparrow",
                          "subclasses": []
                        },
                        {
                          "class": "Eagle",
                          "subclasses": []
                        }
                      ]
                    }
                  ]
                },
                {
                  "class": "Plant",
                  "subclasses": [
                    {
                      "class": "FloweringPlant",
                      "subclasses": []
                    }
                  ]
                }
              ]
            }""";

    @PostMapping("/getClasses")
    public String getClasses(@RequestParam String inputText, Model model, HttpSession session) {
        //Classes classes = gptService.getClasses(inputText);

        String dummyClasses = """
                         Satellite,
                         Chassis,
                         Framework,
                         Rail,
                         Sidewall,
                         Circuit board,
                         Solar cell,
                         Sensor wire,
                         Magnetic coil,
                         Groove,
                         Attitude Determination and Control System,
                         Connector,
                         Module,
                         Bus connector,
                         Cable
                """;

        model.addAttribute("gptResponseMessage", dummyClasses);
        model.addAttribute("fieldTitle", "Classes:");
        model.addAttribute("modalTitle", "Edit Classes:");
        model.addAttribute("endpointUrl", "/getHierarchy");
        model.addAttribute("targetElementId", "hierarchyDiv");
        session.setAttribute("classes", dummyClasses);

        return "fragments :: featureFragment";
    }

    @PostMapping("/getHierarchy")
    public String getHierarchy(Model model, @RequestParam String inputText, HttpSession session) {
        String classes = (String) session.getAttribute("classes");
        //Hierarchy hierarchy = gptService.getHierarchy(inputText, classes);

        String dummyHierarchy = """
                 ["Component", "Chassis"],
                    ["Component", "Rail"],
                    ["Component", "Framework"],
                    ["Component", "Sidewall"],
                    ["Component", "Circuit board"],
                    ["Component", "Elastic blushing"],
                    ["Circuit board", "Double-sided circuit board"],
                    ["Circuit board", "FR-4"],
                    ["Circuit board", "Printed Circuit Board"],
                    ["Component", "Connector"],
                    ["Connector", "Bus connector"]
                """;

        model.addAttribute("gptResponseMessage", dummyHierarchy);
        model.addAttribute("fieldTitle", "Hierarchy:");
        model.addAttribute("modalTitle", "Edit Hierarchy:");
        model.addAttribute("endpointUrl", "/getRelations");
        model.addAttribute("targetElementId", "relationsDiv");
        session.setAttribute("hierarchy", dummyHierarchy);

        return "fragments :: featureFragment";
    }

    @PostMapping("/getRelations")
    public String getRelations(Model model, @RequestParam String inputText, HttpSession session) {
        String classes = (String) session.getAttribute("classes");
        //Relations relations = gptService.getRelations(inputText, classes);

        String dummyRelations = """
                ["Chassis", "consistsOf", "Framework"],
                    ["Sidewall", "isMadeFrom", "Circuit board"],
                    ["Sidewall", "servesAs", "Circuit board"],
                    ["Double-sided circuit board", "mayServeAs", "Circuit board"],
                    ["Solar cell", "isMountedOn", "Printed circuit board"],
                    ["Satellite", "needs", "Connector"],
                    ["Internal module", "consistOf", "FR-4"],
                    ["Internal module", "consistOf", "Circuit board"],
                    ["Module", "isStackedInside", "Satellite"],
                    ["Elastic bushing", "isPlacedIn", "Groove"]
                """;

        model.addAttribute("gptResponseMessage", dummyRelations);
        model.addAttribute("fieldTitle", "Non-taxonomic Relations:");
        model.addAttribute("modalTitle", "Edit non-taxonomic Relations:");
        model.addAttribute("endpointUrl", "/getConstraints");
        model.addAttribute("targetElementId", "constraintsDiv");
        session.setAttribute("relations", dummyRelations);
        return "fragments :: featureFragment";
    }

    @PostMapping("/getConstraints")
    public String getConstraints(Model model, @RequestParam String inputText, HttpSession session) {
        String relations = (String) session.getAttribute("relations");

        Constraints constraints = gptService.getConstraints(inputText, relations);

        model.addAttribute("gptResponseMessage", constraints.toString());
        model.addAttribute("fieldTitle", "Constraints:");
        model.addAttribute("modalTitle", "Edit Constraints:");
        session.setAttribute("constraints", constraints.toString());

        return "fragments :: featureFragment";
    }
}

