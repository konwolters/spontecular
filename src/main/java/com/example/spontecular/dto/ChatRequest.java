package com.example.spontecular.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChatRequest {

    private String model;
    private List<Message> messages;
    private int n;
    private double temperature;

    public ChatRequest(String model, String prompt) {
        this.model = model;
        
        this.messages = new ArrayList<>();
        this.messages.add(new Message("user", prompt));
    }

    //https://www.baeldung.com/spring-boot-chatgpt-api-openai
}