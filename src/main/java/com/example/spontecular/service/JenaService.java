package com.example.spontecular.service;

import com.example.spontecular.dto.Classes;
import com.example.spontecular.dto.Constraints;
import com.example.spontecular.dto.Hierarchy;
import com.example.spontecular.dto.Relations;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.Restriction;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Service class that provides methods for creating ontology classes, class hierarchies, subclasses, and non-taxonomic relations.
 */
@Service
public class JenaService {
    private final OntModel model;
    private final StringUtils stringUtils;
    private final String NAMESPACE = "http://www.semanticweb.org/ontologies/2021/0/untitled-ontology-1#";

    public JenaService(StringUtils stringUtils) {
        this.stringUtils = stringUtils;
        this.model = ModelFactory.createOntologyModel();
    }

    public String createOntology(Classes classes, Hierarchy hierarchy, Relations relations, Constraints constraints) {
        createOntologyClasses(classes);
        createClassHierarchy(hierarchy);
        createRelationships(relations);
        applyCardinalityConstraints(constraints);

        return modelToString();
    }

    private String modelToString() {
        StringWriter writer = new StringWriter();
        model.write(writer, "RDF/XML-ABBREV");
        return writer.toString();
    }

    private void createOntologyClasses(Classes classObj) {
        // Iterate through each class name and add it to the ontology model
        for (String className : classObj.getClasses()) {
            className = stringUtils.toUpperCamelCase(className);
            if (model.getOntClass(NAMESPACE + className) == null) { // Check if class already exists to prevent duplicates
                OntClass ontClass = model.createClass(NAMESPACE + className);
                System.out.println("Created class: " + ontClass.getURI());
            } else {
                System.out.println("Class already exists: " + NAMESPACE + className);
            }
        }
    }

    private void createClassHierarchy(Hierarchy hierarchy) {
        for (List<String> hierarchyElement : hierarchy.getHierarchy()) {
            if (hierarchyElement.size() < 2) {
                System.out.println("Hierarchy element " + hierarchyElement + " does not contain enough information.");
                continue;
            }
            String parentName = stringUtils.toUpperCamelCase(hierarchyElement.get(0));
            String childName = stringUtils.toUpperCamelCase(hierarchyElement.get(1));

            OntClass parentClass = model.getOntClass(NAMESPACE + parentName);
            OntClass childClass = model.getOntClass(NAMESPACE + childName);
            if (parentClass == null || childClass == null) {
                System.out.println("One or more classes in the hierarchy (" + hierarchyElement + ") do not exist.");
                continue;
            }
            childClass.addSuperClass(parentClass);
            System.out.println("Added " + childName + " as a subclass of " + parentName);
        }
    }

    private void createRelationships(Relations relations) {
        for (List<String> relation : relations.getRelations()) {
            if (relation.size() != 3) {
                System.out.println("Relation element " + relation + " does not contain correct information format.");
                continue;
            }
            String subjectName = stringUtils.toUpperCamelCase(relation.get(0));
            String predicateName = stringUtils.toLowerCamelCase(relation.get(1));
            String objectName = stringUtils.toUpperCamelCase(relation.get(2));

            OntClass subjectClass = model.getOntClass(NAMESPACE + subjectName);
            OntClass objectClass = model.getOntClass(NAMESPACE + objectName);
            if (subjectClass == null || objectClass == null) {
                System.out.println("One or more classes in the relation (" + relation + ") do not exist.");
                continue;
            }

            ObjectProperty property = model.createObjectProperty(NAMESPACE + predicateName);
            model.add(subjectClass, property, objectClass);
            System.out.println("Added relationship: " + subjectName + " " + predicateName + " " + objectName);
        }
    }

    private void applyCardinalityConstraints(Constraints constraints) {
        for (List<String> constraint : constraints.getConstraints()) {
            if (constraint.size() != 5) {
                System.out.println("Constraint element does not contain correct information format.");
                continue;
            }
            String subjectName = stringUtils.toUpperCamelCase(constraint.get(0));
            String predicateName = stringUtils.toLowerCamelCase(constraint.get(1));
            String objectName = stringUtils.toUpperCamelCase(constraint.get(2));
            int minCardinality;
            Integer maxCardinality = null;

            try {
                minCardinality = Integer.parseInt(constraint.get(3));
                String maxCard = constraint.get(4);
                if (!"N".equals(maxCard)) {
                    maxCardinality = Integer.parseInt(maxCard);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid cardinality values: " + e.getMessage());
                continue;
            }

            OntClass subjectClass = model.getOntClass(NAMESPACE + subjectName);
            OntClass objectClass = model.getOntClass(NAMESPACE + objectName);
            ObjectProperty property = model.getObjectProperty(NAMESPACE + predicateName);

            if (subjectClass == null || objectClass == null || property == null) {
                System.out.println("Invalid class or property in the constraint: " + constraint);
                continue;
            }

            if (!relationExists(subjectClass, property, objectClass)) {
                System.out.println("No existing relation found for the constraint: " + constraint);
                continue;
            }
            applyCardinality(subjectClass, property, objectClass, minCardinality, maxCardinality);
        }
    }

    private boolean relationExists(OntClass subjectClass, Property property, OntClass objectClass) {
        ExtendedIterator<Statement> statements = model.listStatements(subjectClass, property, objectClass);
        return statements.hasNext();
    }

    private void applyCardinality(OntClass subjectClass, ObjectProperty property, OntClass objectClass,
                                  int minCardinality, Integer maxCardinality) {
        if (minCardinality >= 0) {
            Restriction minCardinalityRestriction = model.createMinCardinalityRestriction(null, property, minCardinality);
            minCardinalityRestriction.addSubClass(model.createSomeValuesFromRestriction(null, property, objectClass));
            subjectClass.addSubClass(minCardinalityRestriction);
        }

        if (maxCardinality != null) {
            Restriction maxCardinalityRestriction = model.createMaxCardinalityRestriction(null, property, maxCardinality);
            maxCardinalityRestriction.addSubClass(model.createSomeValuesFromRestriction(null, property, objectClass));
            subjectClass.addSubClass(maxCardinalityRestriction);
        }
    }
}


