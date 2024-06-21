package com.example.spontecular.export;

import com.example.spontecular.feature.DummyUtil;
import com.example.spontecular.feature.classes.Classes;
import com.example.spontecular.feature.constraints.Constraints;
import com.example.spontecular.feature.hierarchy.Hierarchy;
import com.example.spontecular.feature.relations.Relations;
import com.example.spontecular.jena.JenaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExportController.class)
class ExportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JenaService jenaService;

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


    @Test
    public void shouldDownloadOwl() throws Exception {
        String owlContent = "<owl>Sample OWL Content</owl>";

        MvcResult result = mockMvc.perform(post("/download-owl")
                        .param("owlContent", owlContent))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.owl"))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, "application/owl+xml"))
                .andExpect(content().bytes(owlContent.getBytes()))
                .andReturn();

        byte[] responseBytes = result.getResponse().getContentAsByteArray();
        assertThat(responseBytes).isEqualTo(owlContent.getBytes());
    }
}