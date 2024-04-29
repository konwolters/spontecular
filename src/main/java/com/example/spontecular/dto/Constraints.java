package com.example.spontecular.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Constraints {
    List<List<String>> constraints;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (List<String> constraintsElement : constraints) {
            sb.append(constraintsElement.toString()).append(",\n");
        }
        return sb.toString();
    }
}
