package com.example.spontecular.service;

import com.example.spontecular.dto.*;
import com.example.spontecular.service.utility.DummyUtil;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.ai.parser.OutputParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class GptService {
    private final OpenAiChatClient chatClient;
    private final Resource classesPrompt;
    private final Resource hierarchyPrompt;
    private final Resource relationsPrompt;
    private final Resource constraintsPrompt;

    public GptService(OpenAiChatClient chatClient,
                      @Value("classpath:/prompts/classes-prompt.st") Resource classesPrompt,
                      @Value("classpath:/prompts/hierarchy-prompt.st") Resource hierarchyPrompt,
                      @Value("classpath:/prompts/relations-prompt.st") Resource relationsPrompt,
                      @Value("classpath:/prompts/constraints-prompt.st") Resource constraintsPrompt) {
        this.chatClient = chatClient;
        this.classesPrompt = classesPrompt;
        this.hierarchyPrompt = hierarchyPrompt;
        this.relationsPrompt = relationsPrompt;
        this.constraintsPrompt = constraintsPrompt;
    }

    @Value("${USE_DUMMY_DATA}")
    boolean useDummyData; // for development purposes to avoid API calls

    public Classes getClasses(String inputText, SettingsForm settings) {
        Classes classes;

        if (useDummyData) {
            classes = new Classes();
            classes.setClasses(DummyUtil.getClassesDummyData());
        } else {
            OutputParser<Classes> outputParser = new BeanOutputParser<>(Classes.class);

            PromptTemplate promptTemplate = new PromptTemplate(
                    classesPrompt,
                    new HashMap<String, Object>() {{
                        put("inputText", inputText);
                        put("definition", settings.getClassesDefinition());
                        put("examples", settings.getClassesExamples());
                        put("blacklist", settings.getClassesBlacklist());
                        put("format", outputParser.getFormat());
                    }}
            );

            ChatResponse response = chatClient.call(promptTemplate.create());
            classes = outputParser.parse(response.getResult().getOutput().getContent());
        }
        return classes;
    }

    public Hierarchy getHierarchy(String inputText, Classes classes, SettingsForm settings) {
        Hierarchy hierarchy;

        if (useDummyData) {
            hierarchy = new Hierarchy();
            hierarchy.setHierarchy(DummyUtil.getHierarchyDummyData());
        } else {
            OutputParser<Hierarchy> outputParser = new BeanOutputParser<>(Hierarchy.class);

            PromptTemplate promptTemplate = new PromptTemplate(
                    hierarchyPrompt,
                    new HashMap<String, Object>() {{
                        put("inputText", inputText);
                        put("classes", classes.toString());
                        put("definition", settings.getHierarchyDefinition());
                        put("examples", settings.getHierarchyExamples());
                        put("blacklist", settings.getHierarchyBlacklist());
                        put("format", outputParser.getFormat());
                    }}
            );

            ChatResponse response = chatClient.call(promptTemplate.create());
            hierarchy = outputParser.parse(response.getResult().getOutput().getContent());
        }
        return hierarchy;
    }

    public Relations getRelations(String inputText, Classes classes, SettingsForm settings) {
        Relations relations;

        if (useDummyData) {
            relations = new Relations();
            relations.setRelations(DummyUtil.getRelationsDummyData());
        } else {
            OutputParser<Relations> outputParser = new BeanOutputParser<>(Relations.class);

            PromptTemplate promptTemplate = new PromptTemplate(
                    relationsPrompt,
                    new HashMap<String, Object>() {
                        {
                            put("inputText", inputText);
                            put("classes", classes.toString());
                            put("definition", settings.getRelationsDefinition());
                            put("examples", settings.getRelationsExamples());
                            put("blacklist", settings.getRelationsBlacklist());
                            put("format", outputParser.getFormat());
                        }
                    }
            );

            ChatResponse response = chatClient.call(promptTemplate.create());
            relations = outputParser.parse(response.getResult().getOutput().getContent());
        }
        return relations;
    }

    public Constraints getConstraints(String inputText, Relations relations, SettingsForm settings) {
        Constraints constraints;

        if (useDummyData) {
            constraints = new Constraints();
            constraints.setConstraints(DummyUtil.getConstraintsDummyData());
        } else {
            OutputParser<Constraints> outputParser = new BeanOutputParser<>(Constraints.class);

            PromptTemplate promptTemplate = new PromptTemplate(
                    constraintsPrompt,
                    new HashMap<String, Object>() {{
                        put("inputText", inputText);
                        put("relations", relations);
                        put("definition", settings.getConstraintsDefinition());
                        put("examples", settings.getConstraintsExamples());
                        put("blacklist", settings.getConstraintsBlacklist());
                        put("format", outputParser.getFormat());
                    }}
            );

            ChatResponse response = chatClient.call(promptTemplate.create());
            constraints = outputParser.parse(response.getResult().getOutput().getContent());
        }
        return constraints;
    }
}
