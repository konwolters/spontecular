package com.example.spontecular.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GptMessage {
    private String role;
    private String content;
}