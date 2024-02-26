package com.example.spontecular.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class GptService {
    private final ChatClient chatClient;

    @Value("${SYSTEM.MESSAGE}")
    private String systemMessage;

    public String getGptResponseMessage(String inputText, String prompt) {
        SystemMessage systemMessage = new SystemMessage(this.systemMessage);
        UserMessage inputMessage = new UserMessage(inputText);
        UserMessage promptMessage = new UserMessage(prompt);

        ChatResponse chatResponse = chatClient.call(
                new Prompt(Arrays.asList(systemMessage, inputMessage, promptMessage))
        );
        return chatResponse.getResult().getOutput().getContent();
    }
}
