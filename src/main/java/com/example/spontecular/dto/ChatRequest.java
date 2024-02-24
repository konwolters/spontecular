package com.example.spontecular.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class ChatRequest {

    private String model;
    private List<Message> messages;
    private int n;
    private double temperature;
    private String response_format;


    //https://www.baeldung.com/spring-boot-chatgpt-api-openai
}