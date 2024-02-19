package com.example.spontecular.service;

import com.example.spontecular.dto.OntClassNode;
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
 * JenaService is a service class that provides methods for creating ontology classes, class hierarchies, subclasses, and non-taxonomic relations.
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

    /**
     * This method is used to create a class hierarchy in an ontology model.
     * It accepts a JSON string that represents the class hierarchy to be created.
     * The JSON string is deserialized into an OntClassNode object that represents the root of the class hierarchy.
     * The method creates an OntClass for the root class and calls the createSubclasses method to create its subclasses.
     * If an IOException is thrown during the deserialization of the JSON string, it is caught and printed to the console.
     *
     * @param model      The ontology model to which the class hierarchy is to be added.
     * @param namespace  The namespace of the ontology model.
     * @param jsonString The JSON string that represents the class hierarchy to be created.
     */
    public void createClassHierarchy(OntModel model, String namespace, String jsonString) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            OntClassNode root = mapper.readValue(jsonString, OntClassNode.class);
            OntClass rootClass = model.createClass(namespace + root.getClassName());
            createSubclasses(model, namespace, rootClass, root.getSubclasses());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to create subclasses in an ontology model.
     * It accepts a parent OntClass and a list of OntClassNode objects representing the subclasses to be created.
     * Each OntClassNode object contains the name of the subclass and a list of its own subclasses.
     * The method iterates over the list of OntClassNode objects, creates an OntClass for each one, and adds it as a subclass to the parent OntClass.
     * The method then recursively calls itself with the new OntClass and the list of its subclasses.
     *
     * @param model      The ontology model to which the subclasses are to be added.
     * @param nameSpace  The namespace of the ontology model.
     * @param parent     The parent OntClass to which the subclasses are to be added.
     * @param subclasses The list of OntClassNode objects representing the subclasses to be created.
     */
    private void createSubclasses(OntModel model, String nameSpace, OntClass parent, List<OntClassNode> subclasses) {
        for (OntClassNode subclassNode : subclasses) {
            OntClass subclass = model.createClass(nameSpace + subclassNode.getClassName());
            parent.addSubClass(subclass);
            createSubclasses(model, nameSpace, subclass, subclassNode.getSubclasses());
        }
    }


    /**
     * This method is used to create non-taxonomic relations between classes in an ontology model.
     * It accepts a JSON string that specifies the relations to be created.
     * Each relation is specified as a three-element array: [source class name, property name, target class name].
     * The method retrieves the source class, target class, and property from the model.
     * If the property does not exist, it is created.
     * The method then establishes the relation by adding the property to the source class with the target class as its value.
     * If either the source class or the target class does not exist, an error message is printed to the console.
     *
     * @param model      The ontology model to which the relations are to be added.
     * @param namespace  The namespace of the ontology model.
     * @param jsonString The JSON string that specifies the relations to be created.
     */
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


