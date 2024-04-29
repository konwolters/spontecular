package com.example.spontecular.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Hierarchy {
    private List<List<String>> hierarchy;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (List<String> hierarchyElement : hierarchy) {
            sb.append(hierarchyElement.toString()).append(",\n");
        }
        return sb.toString();
    }
}
