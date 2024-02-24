package com.example.spontecular.service;

import com.example.spontecular.dto.ChatRequest;
import com.example.spontecular.dto.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class OpenAiClient {

    @Value("${openai.api.uri}")
    private String uri;

    RestClient restClient = RestClient.create();
    public ResponseEntity<ChatResponse> getResponse(ChatRequest chatRequest) {
        return restClient.post()
                .uri(uri)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(chatRequest)
                .retrieve()
                .toEntity(ChatResponse.class);
    }
}
