package com.example.spontecular.service;

import com.example.spontecular.dto.Classes;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.ai.parser.OutputParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GptService {
    private final OpenAiChatClient chatClient;
    private final ResponseObjectMapper responseObjectMapper;

    @Value("classpath:/prompts/classes-prompt.st")
    private Resource classesPrompt;

//    private String call(String inputText, String prompt) {
//        ChatResponse chatResponse = chatClient.call(
//                new Prompt(
//                        Arrays.asList(
//                                new SystemMessage(systemMessage),
//                                new UserMessage(inputText),
//                                new UserMessage(prompt)
//                        )
//                )
//        );
//        return chatResponse.getResult().getOutput().getContent();
//
//    }

//    public Object getResponse(String inputText, String ontologyFeature, String prompt){
//        String response = call(inputText, prompt);
//        return responseObjectMapper.mapResponse(response, ontologyFeature);
//    }

    public Classes getClasses(String inputText){
        OutputParser<Classes> outputParser = new BeanOutputParser<>(Classes.class);

        PromptTemplate promptTemplate = new PromptTemplate(
                classesPrompt,
                Map.of("inputText", inputText)
        );

        ChatResponse response = chatClient.call(promptTemplate.create());

        return outputParser.parse(response.getResult().getOutput().getContent());
    }
}
