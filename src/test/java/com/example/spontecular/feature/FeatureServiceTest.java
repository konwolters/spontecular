package com.example.spontecular.feature;

import com.example.spontecular.dto.*;
import com.example.spontecular.feature.classes.Classes;
import com.example.spontecular.feature.constraints.Constraints;
import com.example.spontecular.feature.hierarchy.Hierarchy;
import com.example.spontecular.feature.relations.Relations;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeatureServiceTest {

    @Mock
    private OpenAiChatClient chatClient;

    @Mock
    private Resource resource;

    @Mock
    private ChatResponse chatResponse;

    @Mock
    private Generation generation;

    @Mock
    private AssistantMessage assistantMessage;

    @InjectMocks
    private FeatureService gptService;

    ObjectMapper objectMapper = new ObjectMapper();

    SettingsForm settings = SettingsForm.builder()
            .classesDefinition("testString")
            .classesExamples("testString")
            .classesBlacklist("testString")
            .hierarchyDefinition("testString")
            .hierarchyExamples("testString")
            .hierarchyBlacklist("testString")
            .relationsDefinition("testString")
            .relationsExamples("testString")
            .relationsBlacklist("testString")
            .constraintsDefinition("testString")
            .constraintsExamples("testString")
            .constraintsBlacklist("testString")
            .build();

    private Classes testClasses;
    private Hierarchy testHierarchy;
    private Relations testRelations;
    private Constraints testConstraints;


    @BeforeEach
    void setup() {
        testClasses = new Classes();
        testHierarchy = new Hierarchy();
        testRelations = new Relations();
        testConstraints = new Constraints();

        testClasses.setClasses(DummyUtil.getClassesDummyData());
        testHierarchy.setHierarchy(DummyUtil.getHierarchyDummyData());
        testRelations.setRelations(DummyUtil.getRelationsDummyData());
        testConstraints.setConstraints(DummyUtil.getConstraintsDummyData());
    }


    @Test
    public void shouldReturnDummyClasses() {
        gptService.useDummyData = true;

        Classes result = gptService.getClasses("testString", settings);

        assertThat(result.getClasses()).containsExactlyInAnyOrderElementsOf(DummyUtil.getClassesDummyData());
    }

    @Test
    public void shouldReturnClassesFromChatClient() throws IOException {
        gptService.useDummyData = false;

        setupCommonMocks();
        when(assistantMessage.getContent()).thenReturn(objectMapper.writeValueAsString(testClasses));

        Classes result = gptService.getClasses("testString", settings);

        assertThat(result.getClasses()).containsExactlyInAnyOrderElementsOf(testClasses.getClasses());
    }

    @Test
    public void shouldReturnDummyHierarchy() {
        gptService.useDummyData = true;

        Hierarchy result = gptService.getHierarchy("testString", testClasses, settings);

        assertThat(result.getHierarchy()).containsExactlyInAnyOrderElementsOf(testHierarchy.getHierarchy());
    }

    @Test
    public void shouldReturnHierarchyFromChatClient() throws IOException {
        gptService.useDummyData = false;

        setupCommonMocks();
        when(assistantMessage.getContent()).thenReturn(objectMapper.writeValueAsString(testHierarchy));

        Hierarchy result = gptService.getHierarchy("testString", testClasses, settings);

        assertThat(result.getHierarchy()).containsExactlyInAnyOrderElementsOf(testHierarchy.getHierarchy());
    }

    @Test
    public void shouldReturnDummyRelations() {
        gptService.useDummyData = true;

        Relations result = gptService.getRelations("testString", testClasses, settings);

        assertThat(result.getRelations()).containsExactlyInAnyOrderElementsOf(testRelations.getRelations());
    }

    @Test
    public void shouldReturnRelationsFromChatClient() throws IOException {
        gptService.useDummyData = false;

        setupCommonMocks();
        when(assistantMessage.getContent()).thenReturn(objectMapper.writeValueAsString(testRelations));

        Relations result = gptService.getRelations("testString", testClasses, settings);

        assertThat(result.getRelations()).containsExactlyInAnyOrderElementsOf(testRelations.getRelations());
    }

    @Test
    public void shouldReturnDummyConstraints() {
        gptService.useDummyData = true;

        Constraints result = gptService.getConstraints("testString", testRelations, settings);

        assertThat(result.getConstraints()).containsExactlyInAnyOrderElementsOf(testConstraints.getConstraints());
    }

    @Test
    public void shouldReturnConstraintsFromChatClient() throws IOException {
        gptService.useDummyData = false;

        setupCommonMocks();
        when(assistantMessage.getContent()).thenReturn(objectMapper.writeValueAsString(testConstraints));

        Constraints result = gptService.getConstraints("testString", testRelations, settings);

        assertThat(result.getConstraints()).containsExactlyInAnyOrderElementsOf(testConstraints.getConstraints());
    }


    //Mock Spring AI dependencies
    private void setupCommonMocks() throws IOException {
        when(resource.getInputStream()).thenReturn(new ByteArrayInputStream("testString".getBytes()));
        when(chatClient.call(any(Prompt.class))).thenReturn(chatResponse);
        when(chatResponse.getResult()).thenReturn(generation);
        when(generation.getOutput()).thenReturn(assistantMessage);
    }
}