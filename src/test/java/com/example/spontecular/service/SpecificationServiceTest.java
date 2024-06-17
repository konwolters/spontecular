package com.example.spontecular.service;

import com.example.spontecular.utility.TextExtractor;
import com.example.spontecular.specification.SpecificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SpecificationServiceTest {

    @Mock
    private Resource exampleSpecification;

    @Mock
    private TextExtractor textExtractor;

    @InjectMocks
    private SpecificationService specificationService;

    @Mock
    private MultipartFile file;


    @Test
    public void shouldLoadExampleSpecificationSuccess() throws IOException {
        String expectedContent = "Satellite specification data...";

        when(exampleSpecification.getContentAsString(StandardCharsets.UTF_8)).thenReturn(expectedContent);

        String actualContent = specificationService.loadExampleSpecification();

        assertThat(actualContent).isEqualTo(expectedContent);
    }

    @Test
    public void shouldLoadExampleSpecificationThrowsException() throws IOException {
        when(exampleSpecification.getContentAsString(StandardCharsets.UTF_8)).thenThrow(new IOException("Failed to read file"));

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> specificationService.loadExampleSpecification())
                .withCauseInstanceOf(IOException.class)
                .withMessageContaining("Failed to read file");
    }

    @Test
    public void shouldLoadPdfSpecification() {
        String expectedText = "PDF specification data...";

        when(textExtractor.extractTextFromPdf(file)).thenReturn(expectedText);

        String actualText = specificationService.loadPdfSpecification(file);

        assertThat(actualText).isEqualTo(expectedText);
    }

    @Test
    public void shouldLoadWordSpecification() {
        String expectedText = "Word specification data...";

        when(textExtractor.extractTextFromWord(file)).thenReturn(expectedText);

        String actualText = specificationService.loadWordSpecification(file);

        assertThat(actualText).isEqualTo(expectedText);
    }
}