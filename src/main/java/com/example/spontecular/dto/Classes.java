package com.example.spontecular.dto;

import com.example.spontecular.dto.formDtos.ClassItem;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Classes implements Feature {
    private List<String> classStrings = new ArrayList<>();
    private List<ClassItem> classes = new ArrayList<>();

    public Classes() {
    }

    public Classes(String classesString) {
        if (classesString != null && !classesString.isEmpty()) {
            this.classStrings = new ArrayList<>(Arrays.asList(classesString.split(",")));
        }
    }

    @Override
    public Map<String, Object> getResponseMap() {
        return Map.of(
                "featureType", "classes",
                "nextFeatureType", "hierarchy",
                "itemList", getClasses2()
        );
    }

    public List<ClassItem> getClasses2() {
        return classStrings.stream().map(item -> new ClassItem(item, false)).toList();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String className : classStrings) {
            sb.append(className).append(",\n");
        }
        return sb.toString();
    }
}
