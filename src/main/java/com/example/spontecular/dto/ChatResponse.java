package com.example.spontecular.dto;

import java.util.List;

public class ChatResponse {

    private List<Choice> choices;

    public static class Choice {

        private int index;
        private Message message;
    }
}
