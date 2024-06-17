package com.example.spontecular.feature.hierarchy;

import com.example.spontecular.feature.DummyUtil;
import com.example.spontecular.feature.FeatureService;
import com.example.spontecular.feature.relations.RelationItem;
import com.example.spontecular.feature.relations.Relations;
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

@WebMvcTest(HierarchyController.class)
class HierarchyControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    FeatureService featureService;

    @Test
    void shouldAddNewHierarchy() throws Exception {
        MvcResult result = mockMvc.perform(post("/hierarchy")
                        .param("newParentClass", "testParent")
                        .param("newChildClass", "testChild")
                )
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();

        assertThat(modelAndView).isNotNull();
        Map<String, Object> model = modelAndView.getModel();

        assertThat(model.get("hierarchyItem"))
                .isEqualTo(new HierarchyItem("testParent", "testChild", false));

        assertThat(modelAndView.getViewName()).isEqualTo("hierarchy-fragments :: hierarchyItem");
    }

    @Test
    void shouldUpdateHierarchy() throws Exception {
        Hierarchy testHierarchy = new Hierarchy();
        testHierarchy.setHierarchy(DummyUtil.getHierarchyDummyData());

        MockHttpSession testSession = new MockHttpSession();

        MvcResult result = mockMvc.perform(put("/hierarchy")
                        .session(testSession)
                        .flashAttr("hierarchy", testHierarchy))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();

        assertThat(modelAndView).isNotNull();
        Map<String, Object> model = modelAndView.getModel();
        assertThat(testSession.getAttribute("hierarchy")).isEqualTo(testHierarchy);
        assertThat(modelAndView.getViewName()).isEqualTo("fragments :: featureFragment");
        assertThat(model).containsAllEntriesOf(testHierarchy.getResponseMap());
    }
}