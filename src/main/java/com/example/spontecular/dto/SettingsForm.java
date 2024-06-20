package com.example.spontecular.dto;

import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
public class SettingsForm {
    private String classesDefinition;
    private String classesExamples;
    private String classesBlacklist;
    private String hierarchyDefinition;
    private String hierarchyExamples;
    private String hierarchyBlacklist;
    private String relationsDefinition;
    private String relationsExamples;
    private String relationsBlacklist;
    private String constraintsDefinition;
    private String constraintsExamples;
    private String constraintsBlacklist;

    public String getClassesDefinition() {
        return classesDefinition;
    }

    public String getClassesExamples() {
        return classesExamples == null ? null : "Examples: " + classesExamples;
    }

    public String getClassesBlacklist() {
        return classesBlacklist == null ? null : "Exclude the followings items from the results: " + classesExamples;
    }

    public String getHierarchyDefinition() {
        return hierarchyDefinition;
    }

    public String getHierarchyExamples() {
        return hierarchyExamples == null ? null : "Examples: " + hierarchyExamples;
    }

    public String getHierarchyBlacklist() {
        return hierarchyBlacklist == null ? null : "Exclude the followings items from the results: " + hierarchyBlacklist;
    }

    public String getRelationsDefinition() {
        return relationsDefinition;
    }

    public String getRelationsExamples() {
        return relationsExamples == null ? null : "Examples: " + relationsExamples;
    }

    public String getRelationsBlacklist() {
        return relationsBlacklist == null ? null : "Exclude the followings items from the results: " + relationsBlacklist;
    }

    public String getConstraintsDefinition() {
        return constraintsDefinition;
    }

    public String getConstraintsExamples() {
        return constraintsExamples == null ? null : "Examples: " + constraintsExamples;
    }

    public String getConstraintsBlacklist() {
        return constraintsBlacklist == null ? null : "Exclude the followings items from the results: " + constraintsBlacklist;
    }
}
