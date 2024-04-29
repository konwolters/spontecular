package com.example.spontecular.service;

import com.example.spontecular.dto.Classes;
import com.example.spontecular.dto.Constraints;
import com.example.spontecular.dto.Hierarchy;
import com.example.spontecular.dto.Relations;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ResponseObjectMapper {
    private final ObjectMapper objectMapper;

    private final Map<String, Class<?>> ontologyFeatureClassMap = Map.of(
            "classes", Classes.class,
            "hierarchy", Hierarchy.class,
            "relations", Relations.class,
            "constraints", Constraints.class
    );

    public Object mapResponse(String response, String ontologyFeature) {

        try {
            // Use the ontologyFeature to get the appropriate class type for deserialization
            Class<?> responseClass = ontologyFeatureClassMap.get(ontologyFeature);
            if (responseClass == null) {
                throw new IllegalArgumentException("Unsupported ontology feature: " + ontologyFeature);
            }
            return objectMapper.readValue(response, responseClass);
        } catch (IOException e) {
            throw new RuntimeException("Failed to map response to object", e);
        }
    }

    public Classes mapToClasses(String response) {
        try {
            return objectMapper.readValue(response, Classes.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to map response to object", e);
        }
    }

    public Hierarchy mapToHierarchy(String response) {
        try {
            return objectMapper.readValue(response, Hierarchy.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to map response to object", e);
        }
    }

    public Relations mapToRelations(String response) {
        try {
            return objectMapper.readValue(response, Relations.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to map response to object", e);
        }
    }

    public Constraints mapToConstraints(String response) {
        try {
            return objectMapper.readValue(response, Constraints.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to map response to object", e);
        }
    }
}
