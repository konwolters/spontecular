package com.example.spontecular.specification;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SpecificationController.class)
class SpecificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpecificationService specificationService;

    @Test
    void shouldLoadExampleSpecification() throws Exception {
        when(specificationService.loadExampleSpecification()).thenReturn("example specification");

        MvcResult mvcResult = mockMvc.perform(post("/loadSpecification")
                        .param("specification_type", "example"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mvcResult.getModelAndView().getViewName())
                .isEqualTo("fragments :: specificationFragment");

        assertThat(mvcResult.getModelAndView().getModel().get("specification"))
                .isEqualTo("example specification");
    }

    @Test
    void shouldLoadPdfSpecification() throws Exception {
        MockMultipartFile pdfFile = new MockMultipartFile("pdfFile", "test.pdf", "application/pdf", "PDF content".getBytes());
        when(specificationService.loadPdfSpecification(any())).thenReturn("PDF Spec");
        MvcResult result = mockMvc.perform(multipart("/loadSpecification")
                        .file(pdfFile)
                        .param("specification_type", "pdf"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getModelAndView().getViewName()).isEqualTo("fragments :: specificationFragment");
        assertThat(result.getModelAndView().getModel().get("specification")).isEqualTo("PDF Spec");
    }

    @Test
    void shouldLoadWordSpecification() throws Exception {
        MockMultipartFile wordFile = new MockMultipartFile("wordFile", "test.docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "Word content".getBytes());
        when(specificationService.loadWordSpecification(any())).thenReturn("Word Spec");
        MvcResult result = mockMvc.perform(multipart("/loadSpecification")
                        .file(wordFile)
                        .param("specification_type", "word"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getModelAndView().getViewName()).isEqualTo("fragments :: specificationFragment");
        assertThat(result.getModelAndView().getModel().get("specification")).isEqualTo("Word Spec");
    }
}