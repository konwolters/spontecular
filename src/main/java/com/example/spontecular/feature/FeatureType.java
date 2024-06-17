package com.example.spontecular.feature;

public enum FeatureType {
    CLASSES,
    HIERARCHY,
    RELATIONS,
    CONSTRAINTS;

    public static FeatureType fromString(String feature) {
        return switch (feature.toLowerCase()) {
            case "classes" -> CLASSES;
            case "hierarchy" -> HIERARCHY;
            case "relations" -> RELATIONS;
            case "constraints" -> CONSTRAINTS;
            default -> throw new IllegalArgumentException("Unknown feature: " + feature);
        };
    }
}