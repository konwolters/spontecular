package com.example.spontecular.service;

import com.example.spontecular.dto.*;
import com.example.spontecular.service.utility.StringUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.Restriction;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class that provides methods for creating ontology classes, class hierarchies, subclasses, and non-taxonomic relations.
 */
@Service
public class JenaService {
    private final StringUtils stringUtils;
    private final String NAMESPACE;

    public JenaService(StringUtils stringUtils) {
        this.stringUtils = stringUtils;
        LocalDate now = LocalDate.now();
        this.NAMESPACE = "https://www.example.org/ontologies/"
                + now.getYear() + "/"
                + now.getMonthValue() + "/untitled-ontology/";
    }

    public Response createOntology(Classes classes, Hierarchy hierarchy, Relations relations, Constraints constraints) {
        List<String> errorMessages = new ArrayList<>();
        OntModel model = ModelFactory.createOntologyModel();

        createOntologyClasses(classes, model, errorMessages);

        if(hierarchy != null)
            createClassHierarchy(hierarchy, model, errorMessages);

        if(relations != null)
            createRelationships(relations, model, errorMessages);

        if(constraints != null)
            applyCardinalityConstraints(constraints, model, errorMessages);

        return new Response(modelToString(model), errorMessages);
    }

    private String modelToString(OntModel model) {
        StringWriter writer = new StringWriter();
        model.write(writer, "RDF/XML-ABBREV");
        return writer.toString();
    }

    private void createOntologyClasses(Classes classObj, OntModel model, List<String> errorMessages) {
        for (ClassItem classItem : classObj.getClasses()) {
            String className = stringUtils.toUpperCamelCase(classItem.getValue());
            if (model.getOntClass(NAMESPACE + className) == null) {
                OntClass ontClass = model.createClass(NAMESPACE + className);
                System.out.println("Created class: " + ontClass.getURI());
            } else {
                errorMessages.add("Class already exists: " + NAMESPACE + className);
            }
        }
    }

    private void createClassHierarchy(Hierarchy hierarchy, OntModel model, List<String> errorMessages) {
        for (HierarchyItem hierarchyItem : hierarchy.getHierarchy()) {
            String parentName = stringUtils.toUpperCamelCase(hierarchyItem.getParent());
            String childName = stringUtils.toUpperCamelCase(hierarchyItem.getChild());

            OntClass parentClass = model.getOntClass(NAMESPACE + parentName);
            OntClass childClass = model.getOntClass(NAMESPACE + childName);
            if (parentClass == null || childClass == null) {
                errorMessages.add("One or more classes in the hierarchy (" + hierarchyItem.toString() + ") do not exist.");
                continue;
            }
            childClass.addSuperClass(parentClass);
            System.out.println("Added " + childName + " as a subclass of " + parentName);
        }
    }

    private void createRelationships(Relations relations, OntModel model, List<String> errorMessages) {
        for (RelationItem relationItem : relations.getRelations()) {

            String subjectName = stringUtils.toUpperCamelCase(relationItem.getSubject());
            String predicateName = stringUtils.toLowerCamelCase(relationItem.getPredicate());
            String objectName = stringUtils.toUpperCamelCase(relationItem.getObject());

            OntClass subjectClass = model.getOntClass(NAMESPACE + subjectName);
            OntClass objectClass = model.getOntClass(NAMESPACE + objectName);

            if (subjectClass == null || objectClass == null) {
                errorMessages.add("One or more classes in the relation (" + relationItem.toString() + ") do not exist.");
                continue;
            }

            ObjectProperty property = model.createObjectProperty(NAMESPACE + predicateName);
            model.add(subjectClass, property, objectClass);
            System.out.println("Added relationship: " + subjectName + " " + predicateName + " " + objectName);
        }
    }

    private void applyCardinalityConstraints(Constraints constraints, OntModel model, List<String> errorMessages) {
        for (ConstraintsItem constraint : constraints.getConstraints()) {

            String subjectName = stringUtils.toUpperCamelCase(constraint.getSubject());
            String predicateName = stringUtils.toLowerCamelCase(constraint.getPredicate());
            String objectName = stringUtils.toUpperCamelCase(constraint.getObject());
            int minCardinality;
            Integer maxCardinality = null;

            try {
                minCardinality = Integer.parseInt(constraint.getMinCardinality());
                String maxCard = constraint.getMaxCardinality();
                if (!"N".equals(maxCard)) {
                    maxCardinality = Integer.parseInt(maxCard);
                }
            } catch (NumberFormatException e) {
                errorMessages.add("Invalid cardinality values: " + e.getMessage());
                continue;
            }

            OntClass subjectClass = model.getOntClass(NAMESPACE + subjectName);
            OntClass objectClass = model.getOntClass(NAMESPACE + objectName);
            ObjectProperty property = model.getObjectProperty(NAMESPACE + predicateName);

            if (subjectClass == null || objectClass == null) {
                errorMessages.add("Invalid class in the constraint: " + constraint);
                continue;
            }

            if(property == null){
                errorMessages.add("Property does not exist: " + predicateName);
                continue;
            }

            if (!relationExists(subjectClass, property, objectClass, model)) {
                errorMessages.add("No existing relation found for the constraint: " + constraint);
                continue;
            }
            applyCardinality(subjectClass, property, objectClass, minCardinality, maxCardinality, model);
        }
    }

    private boolean relationExists(OntClass subjectClass, Property property, OntClass objectClass, OntModel model) {
        ExtendedIterator<Statement> statements = model.listStatements(subjectClass, property, objectClass);
        return statements.hasNext();
    }

    private void applyCardinality(OntClass subjectClass, ObjectProperty property, OntClass objectClass,
                                  int minCardinality, Integer maxCardinality, OntModel model) {
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

    @Getter
    @Setter
    public static class Response {
        private String modelAsString;
        private List<String> errorMessages;

        public Response(String modelAsString, List<String> errorMessages) {
            this.modelAsString = modelAsString;
            this.errorMessages = errorMessages;
        }
    }
}


