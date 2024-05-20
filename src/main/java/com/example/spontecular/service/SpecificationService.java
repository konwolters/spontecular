package com.example.spontecular.service;

import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class SpecificationService {

    @Value("classpath:/specification/satellite.txt")
    private Resource exampleSpecification;

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
        String text;

        System.out.println("loadPdfSpecification called");
        try (InputStream inputStream = pdfFile.getInputStream()) {
            byte[] pdfBytes = toByteArray(inputStream);
            try (PDDocument document = Loader.loadPDF(pdfBytes)) {
                PDFTextStripper stripper = new PDFTextStripper();
                text = stripper.getText(document);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return text;
    }

    private byte[] toByteArray(InputStream inputStream) throws IOException {
        try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            int nRead;
            byte[] data = new byte[16384];
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            return buffer.toByteArray();
        }
    }

    public String loadWordSpecification() {
        return "Word specification";
    }
}
