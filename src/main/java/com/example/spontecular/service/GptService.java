package com.example.spontecular.service;

import com.example.spontecular.client.OpenAiClient;
import com.example.spontecular.dto.GptMessage;
import com.example.spontecular.dto.GptRequest;
import com.example.spontecular.dto.GptResponse;
import com.example.spontecular.dto.ResponseFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class GptService {
    private final OpenAiClient openAiClient;

    @Value("${openai.api.model}")
    private String apiModel;

    ResponseFormat responseFormat = new ResponseFormat("json_object");

    public String getGptResponseMessage(String inputText, String prompt) {
        GptMessage systemMessage= GptMessage.builder()
                .role("system")
                .content("")
                .build();

        GptMessage inputMessage = GptMessage.builder()
                .role("user")
                .content(inputText)
                .build();

        GptMessage promptMessage = GptMessage.builder()
                .role("user")
                .content(prompt)
                .build();

        GptRequest gptRequest = GptRequest.builder()
                .model(apiModel)
                .temperature(0)
                .response_format(responseFormat)
                .messages(Arrays.asList(systemMessage, inputMessage, promptMessage))
                .build();

        ResponseEntity<GptResponse> apiResponse = openAiClient.getResponse(gptRequest);

        return apiResponse
                .getBody()
                .getChoices()
                .get(0)
                .getMessage()
                .getContent();
    }
}
