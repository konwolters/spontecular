package com.example.spontecular.model;

import java.util.Map;
//TODO: Remove the map and find other ways to adjust the fragments to the specific feature type
public enum Feature {
    CLASSES( "hierarchy"),
    HIERARCHY("relations"),
    RELATIONS( "constraints"),
    CONSTRAINTS(null);

    private final String nextFeature;

    Feature(String nextFeature) {
        this.nextFeature = nextFeature;
    }

    public Map<String, String> getResponseMap(String response) {
        return Map.of(
                "gptResponseMessage", response,
                "feature", this.name().toLowerCase(),
                "nextFeature", this.nextFeature == null ? "" : this.nextFeature
        );
    }
}