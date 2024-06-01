package com.example.spontecular.service;

import com.example.spontecular.dto.Classes;
import com.example.spontecular.dto.Constraints;
import com.example.spontecular.dto.Hierarchy;
import com.example.spontecular.dto.Relations;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.ai.parser.OutputParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    public Classes getClasses(String inputText) {
        Classes classes;

        if (useDummyData) {
            classes = new Classes();
            classes.setClasses(List.of("Satellite", "Chassis", "Framework", "Rail", "Sidewall",
                    "Circuit board", "Solar cell", "Sensor wire", "Magnetic coil", "Groove",
                    "Attitude Determination and Control System", "Connector", "Module", "Bus connector", "Cable"));
        } else {
            OutputParser<Classes> outputParser = new BeanOutputParser<>(Classes.class);

            PromptTemplate promptTemplate = new PromptTemplate(
                    classesPrompt,
                    Map.of("inputText", inputText)
            );
            ChatResponse response = chatClient.call(promptTemplate.create());
            classes = outputParser.parse(response.getResult().getOutput().getContent());
        }
        return classes;
    }

    public Hierarchy getHierarchy(String inputText, String classes) {
        Hierarchy hierarchy;
        if (useDummyData) {
            hierarchy = new Hierarchy();
            hierarchy.setHierarchy(List.of(
                    List.of("Framework", "Chassis"),
                    List.of("Sidewall", "Rail"),
                    List.of("Component", "Framework"),
                    List.of("Component", "Sidewall"),
                    List.of("Component", "Circuit board"),
                    List.of("Component", "Elastic blushing"),
                    List.of("Circuit board", "Double-sided circuit board"),
                    List.of("Circuit board", "FR-4"),
                    List.of("Circuit board", "Printed Circuit Board"),
                    List.of("Component", "Connector"),
                    List.of("Connector", "Bus connector")
            ));
        } else {
            OutputParser<Hierarchy> outputParser = new BeanOutputParser<>(Hierarchy.class);

            PromptTemplate promptTemplate = new PromptTemplate(
                    hierarchyPrompt,
                    Map.of("inputText", inputText, "classes", classes)
            );
            ChatResponse response = chatClient.call(promptTemplate.create());
            hierarchy = outputParser.parse(response.getResult().getOutput().getContent());
        }
        return hierarchy;
    }

    public Relations getRelations(String inputText, String classes) {
        Relations relations;

        if (useDummyData) {
            relations = new Relations();
            relations.setRelations(List.of(
                    List.of("Chassis", "consistsOf", "Framework"),
                    List.of("Sidewall", "isMadeFrom", "Circuit board"),
                    List.of("Sidewall", "servesAs", "Circuit board"),
                    List.of("Double-sided circuit board", "mayServeAs", "Circuit board"),
                    List.of("Solar cell", "isMountedOn", "Printed circuit board"),
                    List.of("Satellite", "needs", "Connector"),
                    List.of("Internal module", "consistOf", "FR-4"),
                    List.of("Internal module", "consistOf", "Circuit board"),
                    List.of("Module", "isStackedInside", "Satellite"),
                    List.of("Elastic bushing", "isPlacedIn", "Groove")
            ));
        } else {
            OutputParser<Relations> outputParser = new BeanOutputParser<>(Relations.class);

            PromptTemplate promptTemplate = new PromptTemplate(
                    relationsPrompt,
                    Map.of("inputText", inputText, "classes", classes)
            );

            ChatResponse response = chatClient.call(promptTemplate.create());

            relations = outputParser.parse(response.getResult().getOutput().getContent());
        }
        return relations;
    }

    public Constraints getConstraints(String inputText, String relations) {
        Constraints constraints;

        if (useDummyData) {
            constraints = new Constraints();
            constraints.setConstraints(List.of(
                    List.of("Chassis", "consistsOf", "Framework", "1", "1"),
                    List.of("Sidewall", "isMadeFrom", "Circuit board", "1", "1"),
                    List.of("Sidewall", "servesAs", "Circuit board", "1", "1"),
                    List.of("Double-sided circuit board", "mayServeAs", "Circuit board", "1", "1"),
                    List.of("Solar cell", "isMountedOn", "Printed circuit board", "1", "1"),
                    List.of("Satellite", "needs", "Connector", "1", "1"),
                    List.of("Internal module", "consistOf", "FR-4", "1", "1"),
                    List.of("Internal module", "consistOf", "Circuit board", "1", "1"),
                    List.of("Module", "isStackedInside", "Satellite", "1", "1"),
                    List.of("Elastic bushing", "isPlacedIn", "Groove", "1", "1")
            ));
        } else {
            OutputParser<Constraints> outputParser = new BeanOutputParser<>(Constraints.class);

            PromptTemplate promptTemplate = new PromptTemplate(
                    constraintsPrompt,
                    Map.of("inputText", inputText, "relations", relations)
            );

            ChatResponse response = chatClient.call(promptTemplate.create());

            constraints = outputParser.parse(response.getResult().getOutput().getContent());
        }
        return constraints;
    }
}
