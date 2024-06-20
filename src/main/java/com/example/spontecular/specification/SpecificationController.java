package com.example.spontecular.specification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/specification")
@RequiredArgsConstructor
public class SpecificationController {
    private final SpecificationService specificationService;

    @PostMapping
    public String loadSpecification(Model model,
                                    @RequestParam(name = "specification_type") String specificationType,
                                    @RequestParam(required = false, name = "pdfFile") MultipartFile pdfFile,
                                    @RequestParam(required = false, name = "wordFile") MultipartFile wordFile) {

        String specification = switch (specificationType) {
            case "example" -> specificationService.loadExampleSpecification();
            case "pdf" -> specificationService.loadPdfSpecification(pdfFile);
            case "word" -> specificationService.loadWordSpecification(wordFile);
            default -> "";
        };
        model.addAttribute("specification", specification);

        return "fragments :: specificationFragment";
    }
}
