package com.example.spontecular.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class GptService {
    private final OpenAiChatClient chatClient;
    private final ResponseObjectMapper responseObjectMapper;

    @Value("${SYSTEM.MESSAGE}")
    private String systemMessage;

    private String call(String inputText, String prompt) {
        ChatResponse chatResponse = chatClient.call(
                new Prompt(
                        Arrays.asList(
                                new SystemMessage(systemMessage),
                                new UserMessage(inputText),
                                new UserMessage(prompt)
                        )
                )
        );
        return chatResponse.getResult().getOutput().getContent();
    }

    public Object getResponse(String inputText, String ontologyFeature, String prompt){
        String response = call(inputText, prompt);
        return responseObjectMapper.mapResponse(response, ontologyFeature);
    }
}
