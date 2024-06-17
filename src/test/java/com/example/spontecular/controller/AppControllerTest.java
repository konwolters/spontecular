package com.example.spontecular.controller;

import com.example.spontecular.feature.DummyUtil;
import com.example.spontecular.feature.classes.Classes;
import com.example.spontecular.feature.constraints.Constraints;
import com.example.spontecular.feature.hierarchy.Hierarchy;
import com.example.spontecular.feature.relations.Relations;
import com.example.spontecular.jena.JenaService;
import com.example.spontecular.specification.SpecificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AppController.class)
class AppControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JenaService jenaService;

    @MockBean
    private SpecificationService specificationService;

    @Test
    void shouldLoadIndex() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mvcResult.getModelAndView().getViewName()).isEqualTo("index");
    }

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

    @Test
    void shouldExportWithAllFeatures() throws Exception {
        Classes testClasses = new Classes();
        testClasses.setClasses(DummyUtil.getClassesDummyData());

        Hierarchy testHierarchy = new Hierarchy();
        testHierarchy.setHierarchy(DummyUtil.getHierarchyDummyData());

        Relations testRelations = new Relations();
        testRelations.setRelations(DummyUtil.getRelationsDummyData());

        Constraints testConstraints = new Constraints();
        testConstraints.setConstraints(DummyUtil.getConstraintsDummyData());

        when(jenaService.createOntology(any(Classes.class), any(Hierarchy.class), any(Relations.class), any(Constraints.class)))
                .thenReturn(new JenaService.Response("Model as String", List.of("Error1", "Error2")));

        MockHttpSession testSession = new MockHttpSession();
        testSession.setAttribute("classes", testClasses);
        testSession.setAttribute("hierarchy", testHierarchy);
        testSession.setAttribute("relations", testRelations);
        testSession.setAttribute("constraints", testConstraints);

        MvcResult result = mockMvc.perform(get("/export")
                        .session(testSession))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getModelAndView().getViewName()).isEqualTo("export");
        assertThat(result.getModelAndView().getModel().get("content")).isEqualTo("Model as String");
        assertThat(result.getModelAndView().getModel().get("errorMessages")).asInstanceOf(LIST).containsExactly("Error1", "Error2");
    }
}