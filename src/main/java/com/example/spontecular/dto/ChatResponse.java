package com.example.spontecular.dto;

import java.util.List;

public class ChatResponse {

    private List<Choice> choices;

    // constructors, getters and setters
    
    public static class Choice {

        private int index;
        private Message message;

        // constructors, getters and setters
    }
}
