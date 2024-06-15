package com.example.spontecular.controller;

import com.example.spontecular.dto.*;
import com.example.spontecular.service.GptService;
import com.example.spontecular.service.utility.DummyUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
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

    SettingsForm settings;
    Classes mockClasses;

    @BeforeEach
    void setUp() {
        settings = SettingsForm.builder().build();
        mockClasses = new Classes();
        mockClasses.setClasses(DummyUtil.getClassesDummyData());
    }

    @Test
    void shouldGetClassesAndReturnFragment() throws Exception {
        when(gptService.getClasses(anyString(), any(SettingsForm.class))).thenReturn(mockClasses);

        MvcResult result = mockMvc.perform(post("/featureRequest")
                        .param("inputText", "input data")
                        .param("featureType", "classes")
                        .sessionAttr("settings", settings))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();

        assertThat(modelAndView).isNotNull();
        Map<String, Object> model = modelAndView.getModel();

        assertThat(model.get("featureType")).isEqualTo("classes");
        assertThat(model.get("nextFeatureType")).isEqualTo("hierarchy");
        assertThat(model.get("itemList")).isEqualTo(mockClasses.getClasses());
        assertThat(modelAndView.getViewName()).isEqualTo("fragments :: featureFragment");
    }
}