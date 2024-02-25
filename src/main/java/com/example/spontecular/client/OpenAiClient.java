package com.example.spontecular.client;

import com.example.spontecular.dto.GptRequest;
import com.example.spontecular.dto.GptResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
public class OpenAiClient {

    @Value("${openai.api.uri}")
    private String uri;

    @Value("${openai.api.key}")
    private String apiKey;

    RestClient restClient = RestClient.create();

    public ResponseEntity<GptResponse> getResponse(GptRequest gptRequest) {
        return restClient.post()
                .uri(uri)
                .header("Authorization", "Bearer " + apiKey)
                .contentType(APPLICATION_JSON)
                .body(gptRequest)
                .accept(APPLICATION_JSON)
                .retrieve()
                .toEntity(GptResponse.class);
    }
}
