package com.example.spontecular.dto;

import java.util.List;

public class ChatResponse {
    String system_fingerprint;

    private List<Choice> choices;

    public static class Choice {

        private int index;
        private Message message;
    }
}
