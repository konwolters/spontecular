package com.example.spontecular.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Constraints {
    List<List<String>> constraints = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (List<String> constraintsElement : constraints) {
            sb.append(constraintsElement.toString()).append(",\n");
        }
        return sb.toString();
    }
}
