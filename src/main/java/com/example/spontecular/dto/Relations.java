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
        StringBuilder sb = new StringBuilder();
        for (List<String> relationElement : relations) {
            sb.append(relationElement.toString()).append(",\n");
        }
        return sb.toString();
    }
}
