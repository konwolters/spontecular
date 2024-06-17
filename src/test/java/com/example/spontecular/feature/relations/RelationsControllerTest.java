package com.example.spontecular.feature.relations;

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

@WebMvcTest(RelationsController.class)
class RelationsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    FeatureService featureService;

    @Test
    void shouldAddNewRelation() throws Exception {
        MvcResult result = mockMvc.perform(post("/relations")
                        .param("newSubject", "testSubject")
                        .param("newPredicate", "testPredicate")
                        .param("newObject", "testObject")
                )
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();

        assertThat(modelAndView).isNotNull();
        Map<String, Object> model = modelAndView.getModel();

        assertThat(model.get("relationsItem"))
                .isEqualTo(new RelationItem("testSubject", "testPredicate", "testObject", false));

        assertThat(modelAndView.getViewName()).isEqualTo("relations-fragments :: relationsItem");
    }

    @Test
    void shouldUpdateRelations() throws Exception {
        Relations testRelations = new Relations();
        testRelations.setRelations(DummyUtil.getRelationsDummyData());

        MockHttpSession testSession = new MockHttpSession();

        MvcResult result = mockMvc.perform(put("/relations")
                        .session(testSession)
                        .flashAttr("relations", testRelations))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();

        assertThat(modelAndView).isNotNull();
        Map<String, Object> model = modelAndView.getModel();
        assertThat(testSession.getAttribute("relations")).isEqualTo(testRelations);
        assertThat(modelAndView.getViewName()).isEqualTo("fragments :: featureFragment");
        assertThat(model).containsAllEntriesOf(testRelations.getResponseMap());
    }
}