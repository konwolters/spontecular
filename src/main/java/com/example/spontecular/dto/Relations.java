package com.example.spontecular.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Relations {
    private List<List<String>> relations;

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Convert the object to a JSON string
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "Error converting to JSON";
        }
    }
}
