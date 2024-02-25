package com.example.spontecular.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class GptResponse {
    String system_fingerprint;
    private List<Choice> choices;

    @Getter
    public static class Choice {
        private int index;
        private GptMessage message;
    }
}
