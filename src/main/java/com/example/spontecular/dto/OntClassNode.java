package com.example.spontecular.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OntClassNode {
    @JsonProperty("class")
    private String className;

    @JsonProperty("subclasses")
    private List<OntClassNode> subclasses;
}