package com.example.spontecular.controller;

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

    @Value("${PROMPT.CLASSES}")
    private String promptClasses;

    @Value("${PROMPT.HIERARCHY}")
    private String promptHierarchy;

    @Value("${PROMPT.RELATIONS}")
    private String promptRelations;

    @Value("${PROMPT.AXIOMS}")
    private String promptConstraints;

    @PostMapping("/getClasses")
    public String getResponse(@RequestParam String inputText, Model model, HttpSession session) {
        //String gptResponseMessage = gptService.getGptResponseMessage(inputText, promptClasses);
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

        model.addAttribute("gptResponseMessage", gptResponseMessage);
        model.addAttribute("fieldTitle", "Classes:");
        model.addAttribute("modelTitle", "Edit Classes:");
        session.setAttribute("classes", gptResponseMessage);

        return "fragments :: extractionField";
    }

    @PostMapping("/getHierarchy")
    public String getHierarchy(Model model, @RequestParam String inputText, HttpSession session) {
        String classes = (String) session.getAttribute("classes");

        String gptResponseMessage = gptService.getGptResponseMessage(inputText, promptHierarchy + classes);

        model.addAttribute("gptResponse", gptResponseMessage);
        model.addAttribute("fieldTitle", "Hierarchy:");
        model.addAttribute("modelTitle", "Edit Hierarchy:");
        session.setAttribute("hierarchy", gptResponseMessage);

        return "fragments :: extractionField";
    }

    @PostMapping("/getRelations")
    public String getRelations(Model model, @RequestParam String inputText, HttpSession session) {
        String classes = (String) session.getAttribute("classes");
        String gptResponseMessage = gptService.getGptResponseMessage(inputText, promptRelations + classes);

        model.addAttribute("gptResponse", gptResponseMessage);
        model.addAttribute("fieldTitle", "Non-taxonomic Relations:");
        model.addAttribute("modelTitle", "Edit non-taxonomic Relations:");
        session.setAttribute("relations", gptResponseMessage);
        return "fragments :: extractionField";
    }

    @PostMapping("/getAxioms")
    public String getConstraints(Model model, @RequestParam String inputText, HttpSession session) {
        String relations = (String) session.getAttribute("relations");
        String gptResponseMessage = gptService.getGptResponseMessage(inputText, promptConstraints + relations);

        model.addAttribute("gptResponse", gptResponseMessage);
        model.addAttribute("fieldTitle", "Constraints:");
        model.addAttribute("modelTitle", "Edit Constraints:");
        session.setAttribute("constraints", gptResponseMessage);

        return "fragments :: extractionField";
    }
}

