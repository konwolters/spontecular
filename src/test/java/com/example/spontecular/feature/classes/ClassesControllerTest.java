package com.example.spontecular.feature.classes;

import com.example.spontecular.feature.DummyUtil;
import com.example.spontecular.feature.FeatureService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClassesController.class)
class ClassesControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    FeatureService featureService;

    @Test
    void shouldAddNewClass() throws Exception {
        MvcResult result = mockMvc.perform(post("/classes")
                        .param("newClass", "testClass"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();

        assertThat(modelAndView).isNotNull();
        Map<String, Object> model = modelAndView.getModel();

        assertThat(model.get("classItem")).isEqualTo(new ClassItem("testClass", false));
        assertThat(modelAndView.getViewName()).isEqualTo("classes-fragments :: classesItem");
    }

    @Test
    void shouldUpdateClasses() throws Exception {
        Classes testClasses = new Classes();
        testClasses.setClasses(DummyUtil.getClassesDummyData());

        MockHttpSession testSession = new MockHttpSession();

        MvcResult result = mockMvc.perform(put("/classes")
                        .session(testSession)
                        .flashAttr("classes", testClasses))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();

        assertThat(modelAndView).isNotNull();
        Map<String, Object> model = modelAndView.getModel();
        assertThat(testSession.getAttribute("classes")).isEqualTo(testClasses);
        assertThat(modelAndView.getViewName()).isEqualTo("fragments :: featureFragment");
        assertThat(model).containsAllEntriesOf(testClasses.getResponseMap());
    }
}