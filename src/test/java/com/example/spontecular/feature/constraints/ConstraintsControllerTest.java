package com.example.spontecular.feature.constraints;

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

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConstraintsController.class)
class ConstraintsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    FeatureService featureService;

    @Test
    void shouldAddNewConstraints() throws Exception {
        MvcResult result = mockMvc.perform(post("/constraints")
                        .param("newSubject", "testSubject")
                        .param("newPredicate", "testPredicate")
                        .param("newObject", "testObject")
                        .param("minCardinality", "1")
                        .param("maxCardinality", "N")
                )
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();

        assertThat(modelAndView).isNotNull();
        Map<String, Object> model = modelAndView.getModel();

        assertThat(model.get("constraintsItem"))
                .isEqualTo(new ConstraintsItem("testSubject", "testPredicate", "testObject", "1", "N", false));

        assertThat(modelAndView.getViewName()).isEqualTo("constraints-fragments :: constraintsItem");
    }

    @Test
    void shouldUpdateConstraints() throws Exception {
        Constraints testConstraints = new Constraints();
        testConstraints.setConstraints(DummyUtil.getConstraintsDummyData());

        MockHttpSession testSession = new MockHttpSession();

        MvcResult result = mockMvc.perform(put("/constraints")
                        .session(testSession)
                        .flashAttr("constraints", testConstraints))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();

        assertThat(modelAndView).isNotNull();
        Map<String, Object> model = modelAndView.getModel();
        assertThat(testSession.getAttribute("constraints")).isEqualTo(testConstraints);
        assertThat(modelAndView.getViewName()).isEqualTo("fragments :: featureFragment");
        assertThat(model).containsAllEntriesOf(testConstraints.getResponseMap());
    }
}