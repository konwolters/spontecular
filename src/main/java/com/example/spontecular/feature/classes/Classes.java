package com.example.spontecular.feature.classes;

import com.example.spontecular.feature.Feature;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class Classes implements Feature {
    private List<ClassItem> classes = new ArrayList<>();

    @JsonIgnore
    @Override
    public Map<String, Object> getResponseMap() {

        //Only show non blacklisted items
        classes = classes.stream()
                .filter(item -> !item.isBlacklisted())
                .collect(Collectors.toCollection(ArrayList::new));

        return new HashMap<>() {{
            put("featureType", "classes");
            put("nextFeatureType", "hierarchy");
            put("itemList", classes);
        }};
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
