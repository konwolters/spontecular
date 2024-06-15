package com.example.spontecular.service;

import com.example.spontecular.dto.*;
import com.example.spontecular.service.utility.DummyUtil;
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
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GptServiceTest {

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
    private GptService gptService;

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

    private final List<HierarchyItem> testHierarchy = DummyUtil.getHierarchyDummyData();

    private final List<List<String>> testRelations = List.of(
            List.of("Chassis", "consistsOf", "Framework"),
            List.of("Sidewall", "isMadeFrom", "Circuit board"),
            List.of("Sidewall", "servesAs", "Circuit board"),
            List.of("Double-sided circuit board", "mayServeAs", "Circuit board"),
            List.of("Solar cell", "isMountedOn", "Printed circuit board"),
            List.of("Satellite", "needs", "Connector"),
            List.of("Internal module", "consistOf", "FR-4"),
            List.of("Internal module", "consistOf", "Circuit board"),
            List.of("Module", "isStackedInside", "Satellite"),
            List.of("Elastic bushing", "isPlacedIn", "Groove"));

    private final List<List<String>> testConstraints = List.of(
            List.of("Chassis", "consistsOf", "Framework", "1", "1"),
            List.of("Sidewall", "isMadeFrom", "Circuit board", "1", "1"),
            List.of("Sidewall", "servesAs", "Circuit board", "1", "1"),
            List.of("Double-sided circuit board", "mayServeAs", "Circuit board", "1", "1"),
            List.of("Solar cell", "isMountedOn", "Printed circuit board", "1", "1"),
            List.of("Satellite", "needs", "Connector", "1", "1"),
            List.of("Internal module", "consistOf", "FR-4", "1", "1"),
            List.of("Internal module", "consistOf", "Circuit board", "1", "1"),
            List.of("Module", "isStackedInside", "Satellite", "1", "1"),
            List.of("Elastic bushing", "isPlacedIn", "Groove", "1", "1"));

    @BeforeEach
    void setup() {
        testClasses.setClasses(DummyUtil.getClassesDummyData());
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
        when(assistantMessage.getContent()).thenReturn(objectMapper.writeValueAsString(Map.of("classes", DummyUtil.getClassesDummyData())));

        Classes result = gptService.getClasses("testString", settings);

        assertThat(result.getClasses()).containsExactlyElementsOf(testClasses.getClasses());
    }

    @Test
    public void shouldReturnDummyHierarchy() {
        gptService.useDummyData = true;

        Hierarchy result = gptService.getHierarchy("testString", testClasses, settings);

        assertThat(result.getHierarchy()).containsExactlyInAnyOrderElementsOf(testHierarchy);
    }

    @Test
    public void shouldReturnHierarchyFromChatClient() throws IOException {
        gptService.useDummyData = false;

        setupCommonMocks();
        when(assistantMessage.getContent()).thenReturn(objectMapper.writeValueAsString(Map.of("hierarchy", testHierarchy)));

        Hierarchy expectedHierarchy = new Hierarchy();
        expectedHierarchy.setHierarchy(testHierarchy);

        Hierarchy result = gptService.getHierarchy("testString", testClasses, settings);

        assertThat(result.getHierarchy()).containsExactlyElementsOf(expectedHierarchy.getHierarchy());
    }

    @Test
    public void shouldReturnDummyRelations() {
        gptService.useDummyData = true;

        Relations result = gptService.getRelations("testString", "testString2", settings);

        assertThat(result.getRelations()).containsExactlyInAnyOrderElementsOf(testRelations);
    }

    @Test
    public void shouldReturnRelationsFromChatClient() throws IOException {
        gptService.useDummyData = false;

        setupCommonMocks();
        when(assistantMessage.getContent()).thenReturn(objectMapper.writeValueAsString(Map.of("relations", testRelations)));

        Relations expectedRelations = new Relations();
        expectedRelations.setRelations(testRelations);

        Relations result = gptService.getRelations("testString", "testString2", settings);

        assertThat(result.getRelations()).containsExactlyElementsOf(expectedRelations.getRelations());
    }

    @Test
    public void shouldReturnDummyConstraints() {
        gptService.useDummyData = true;

        Constraints result = gptService.getConstraints("testString", "testString2", settings);

        assertThat(result.getConstraints()).containsExactlyInAnyOrderElementsOf(testConstraints);
    }

    @Test
    public void shouldReturnConstraintsFromChatClient() throws IOException {
        gptService.useDummyData = false;

        setupCommonMocks();
        when(assistantMessage.getContent()).thenReturn(objectMapper.writeValueAsString(Map.of("constraints", testConstraints)));

        Constraints expectedConstraints = new Constraints();
        expectedConstraints.setConstraints(testConstraints);

        Constraints result = gptService.getConstraints("testString", "testString2", settings);

        assertThat(result.getConstraints()).containsExactlyElementsOf(expectedConstraints.getConstraints());
    }

    private void setupCommonMocks() throws IOException {
        when(resource.getInputStream()).thenReturn(new ByteArrayInputStream("testString".getBytes()));
        when(chatClient.call(any(Prompt.class))).thenReturn(chatResponse);
        when(chatResponse.getResult()).thenReturn(generation);
        when(generation.getOutput()).thenReturn(assistantMessage);
    }
}