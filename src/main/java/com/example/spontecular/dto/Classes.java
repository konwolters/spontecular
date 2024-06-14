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
    private List<ClassItem> classes = new ArrayList<>();

    @Override
    public Map<String, Object> getResponseMap() {
        return Map.of(
                "featureType", "classes",
                "nextFeatureType", "hierarchy",
                "itemList", classes
        );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ClassItem classItem : classes) {
            sb.append(classItem.getValue()).append(",\n");
        }
        return sb.toString();
    }
}
