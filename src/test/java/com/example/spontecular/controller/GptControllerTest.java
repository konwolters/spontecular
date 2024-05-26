package com.example.spontecular.controller;

import com.example.spontecular.dto.Classes;
import com.example.spontecular.dto.Constraints;
import com.example.spontecular.dto.Hierarchy;
import com.example.spontecular.dto.Relations;
import com.example.spontecular.service.GptService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GptController.class)
class GptControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    GptService gptService;

    @Test
    void shouldGetClassesAndReturnFragment() throws Exception {
        Classes mockClasses = new Classes("test classes data");

        when(gptService.getClasses(anyString())).thenReturn(mockClasses);

        MvcResult result = mockMvc.perform(post("/getClasses")
                        .param("inputText", "input data"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();

        assertThat(modelAndView).isNotNull();
        Map<String, Object> model = modelAndView.getModel();

        assertThat(model.get("feature")).isEqualTo("classes");
        assertThat(model.get("targetElementId")).isEqualTo("hierarchyDiv");
        assertThat(model.get("endpointUrl")).isEqualTo("/getHierarchy");
        assertThat(model.get("gptResponseMessage")).isEqualTo(mockClasses.toString());
        assertThat(model.get("fieldTitle")).isEqualTo("Classes:");
        assertThat(modelAndView.getViewName()).isEqualTo("fragments :: featureFragment");
    }

    @Test
    void shouldGetHierarchyAndReturnFragment() throws Exception {
        Classes mockClasses = new Classes("class1,class2,class3");
        Hierarchy mockHierarchy = new Hierarchy("test hierarchy data");

        when(gptService.getClasses(anyString())).thenReturn(mockClasses);
        when(gptService.getHierarchy(anyString(), anyString())).thenReturn(mockHierarchy);

        MvcResult result = mockMvc.perform(post("/getHierarchy")
                        .param("inputText", "input data")
                        .sessionAttr("classes", mockClasses))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();

        assertThat(modelAndView).isNotNull();
        Map<String, Object> model = modelAndView.getModel();

        assertThat(model.get("feature")).isEqualTo("hierarchy");
        assertThat(model.get("targetElementId")).isEqualTo("relationsDiv");
        assertThat(model.get("endpointUrl")).isEqualTo("/getRelations");
        assertThat(model.get("gptResponseMessage")).isEqualTo(mockHierarchy.toString());
        assertThat(model.get("fieldTitle")).isEqualTo("Hierarchy:");
        assertThat(modelAndView.getViewName()).isEqualTo("fragments :: featureFragment");
    }

    @Test
    void shouldGetRelationsAndReturnFragment() throws Exception {
        Classes mockClasses = new Classes("class1,class2,class3");
        Relations mockRelations = new Relations("test relation data");

        when(gptService.getClasses(anyString())).thenReturn(mockClasses);
        when(gptService.getRelations(anyString(), anyString())).thenReturn(mockRelations);

        MvcResult result = mockMvc.perform(post("/getRelations")
                        .param("inputText", "input data")
                        .sessionAttr("classes", mockClasses))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();

        assertThat(modelAndView).isNotNull();
        Map<String, Object> model = modelAndView.getModel();

        assertThat(model.get("feature")).isEqualTo("relations");
        assertThat(model.get("targetElementId")).isEqualTo("constraintsDiv");
        assertThat(model.get("endpointUrl")).isEqualTo("/getConstraints");
        assertThat(model.get("gptResponseMessage")).isEqualTo(mockRelations.toString());
        assertThat(model.get("fieldTitle")).isEqualTo("Non-taxonomic Relations:");
        assertThat(modelAndView.getViewName()).isEqualTo("fragments :: featureFragment");
    }

    @Test
    void shouldGetConstraintsAndReturnFragment() throws Exception {
        Relations mockRelations = new Relations("test relation data");
        Constraints mockConstraints = new Constraints("test constraint data");

        when(gptService.getConstraints(anyString(), anyString())).thenReturn(mockConstraints);

        MvcResult result = mockMvc.perform(post("/getConstraints")
                        .param("inputText", "input data")
                        .sessionAttr("relations", mockRelations))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();

        assertThat(modelAndView).isNotNull();
        Map<String, Object> model = modelAndView.getModel();

        assertThat(model.get("feature")).isEqualTo("constraints");
        assertThat(model.get("gptResponseMessage")).isEqualTo(mockConstraints.toString());
        assertThat(model.get("fieldTitle")).isEqualTo("Constraints:");
        assertThat(modelAndView.getViewName()).isEqualTo("fragments :: featureFragment");
    }
}