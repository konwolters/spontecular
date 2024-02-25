package com.example.spontecular.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class GptRequest {

    private String model;
    private List<GptMessage> messages;
    private double temperature;
    private ResponseFormat response_format;


    //https://www.baeldung.com/spring-boot-chatgpt-api-openai
}