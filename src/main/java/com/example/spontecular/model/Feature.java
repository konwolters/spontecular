package com.example.spontecular.model;

import java.util.Map;

public enum Feature {
    CLASSES("Classes:", "/getHierarchy", "hierarchyDiv"),
    HIERARCHY("Hierarchy:", "/getRelations", "relationsDiv"),
    RELATIONS("Non-taxonomic Relations:", "/getConstraints", "constraintsDiv"),
    CONSTRAINTS("Constraints:", null, null);

    private final String fieldTitle;
    private final String endpointUrl;
    private final String targetElementId;

    Feature(String fieldTitle, String endpointUrl, String targetElementId) {
        this.fieldTitle = fieldTitle;
        this.endpointUrl = endpointUrl;
        this.targetElementId = targetElementId;
    }

    public Map<String, String> getResponseMap(String response) {
        return Map.of(
                "gptResponseMessage", response,
                "feature", this.name().toLowerCase(),
                "fieldTitle", this.fieldTitle,
                "endpointUrl", this.endpointUrl == null ? "" : this.endpointUrl,
                "targetElementId", this.targetElementId == null ? "" : this.targetElementId
        );
    }
}