package com.example.spontecular.service;

import com.example.spontecular.service.utility.TextExtractor;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class SpecificationService {

    private final Resource exampleSpecification;
    private final TextExtractor textExtractor;

    public SpecificationService(@Value("classpath:/specification/satellite.txt") Resource exampleSpecification,
                                TextExtractor textExtractor) {
        this.exampleSpecification = exampleSpecification;
        this.textExtractor = textExtractor;
    }

    public String loadExampleSpecification() {
        String specification;

        try {
            specification = exampleSpecification.getContentAsString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return specification;
    }

    public String loadPdfSpecification(MultipartFile pdfFile) {
        return textExtractor.extractTextFromPdf(pdfFile);
    }


    public String loadWordSpecification(MultipartFile wordFile) {
        return textExtractor.extractTextFromWord(wordFile);
    }
}
