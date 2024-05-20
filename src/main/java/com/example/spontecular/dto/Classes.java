package com.example.spontecular.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class Classes {
    private List<String> classes = new ArrayList<>();

    public Classes() {
    }

    public Classes(String classesString) {
        if (classesString != null && !classesString.isEmpty()) {
            this.classes = new ArrayList<>(Arrays.asList(classesString.split(",")));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String className : classes) {
            sb.append(className).append(",\n");
        }
        return sb.toString();
    }
}
