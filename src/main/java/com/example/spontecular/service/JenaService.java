package com.example.spontecular.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Service class that provides methods for creating ontology classes, class hierarchies, subclasses, and non-taxonomic relations.
 */
public class JenaService {
    public void createOntClasses(String jsonString) {
        String SOURCE = "http://www.semanticweb.org/ontologies/2021/0/untitled-ontology-1";
        String NAMESPACE = SOURCE + "#";

        OntModel model = ModelFactory.createOntologyModel();

        ObjectMapper mapper = new ObjectMapper();
        ArrayList<String> classes = new ArrayList<>();

        try {
            Map<String, Object> map = mapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {
            });
            classes = (ArrayList<String>) map.get("classes");
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String className : classes) {
            OntClass ontClass = model.createClass(NAMESPACE + className);
        }
    }

    public static void createNonTaxonomicRelations(OntModel model, String namespace, String jsonString) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode rootNode = mapper.readTree(jsonString);
            JsonNode relationsNode = rootNode.path("relations");

            for (JsonNode relation : relationsNode) {
                if (relation.size() == 3) { // Ensure each relation array has exactly 3 elements
                    String sourceClassName = relation.get(0).asText();
                    String propertyName = relation.get(1).asText();
                    String targetClassName = relation.get(2).asText();

                    // Retrieve or create the object property
                    String propertyUri = namespace + propertyName;
                    ObjectProperty property = model.getObjectProperty(propertyUri);
                    if (property == null) {
                        property = model.createObjectProperty(propertyUri);
                    }

                    // Retrieve existing source and target classes
                    OntClass sourceClass = model.getOntClass(namespace + sourceClassName);
                    OntClass targetClass = model.getOntClass(namespace + targetClassName);

                    // Check if both classes exist
                    if (sourceClass != null && targetClass != null) {
                        // Add the property assertion
                        sourceClass.addProperty(property, targetClass);
                    } else {
                        System.err.println("One or both classes not found for relation: " + relation);
                    }
                } else {
                    System.err.println("Invalid relation format, expected 3 elements per relation: " + relation);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


