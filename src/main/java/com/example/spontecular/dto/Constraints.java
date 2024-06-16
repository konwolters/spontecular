package com.example.spontecular.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
public class Constraints implements Feature {
    List<ConstraintsItem> constraints = new ArrayList<>();

    @Override
    public Map<String, Object> getResponseMap() {

        //Only show non blacklisted items
        constraints = constraints.stream()
                .filter(item -> !item.isBlacklisted())
                .toList();

        return Map.of(
                "featureType", "constraints",
                "nextFeatureType", "",
                "itemList", getConstraints()
        );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ConstraintsItem constraintsItem : constraints) {
            sb.append(constraintsItem.toString()).append(",\n");
        }
        return sb.toString();
    }
}
