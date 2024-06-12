package com.example.spontecular.service;

import com.example.spontecular.dto.Classes;
import com.example.spontecular.dto.Constraints;
import com.example.spontecular.dto.Hierarchy;
import com.example.spontecular.dto.Relations;
import com.example.spontecular.service.utility.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JenaServiceTest {

    @Mock
    private StringUtils stringUtils;

    @InjectMocks
    private JenaService jenaService;

    private Classes classes;
    private Hierarchy hierarchy;
    private Relations relations;
    private Constraints constraints;

    @BeforeEach
    void setUp() {
        classes = new Classes();
        classes.setClassStrings(List.of("Class1", "Class2"));

        hierarchy = new Hierarchy();
        hierarchy.setHierarchy(List.of(List.of("Class1", "Class2")));

        relations = new Relations();
        relations.setRelations(List.of(List.of("Class1", "Relation1", "Class2")));

        constraints = new Constraints();
        constraints.setConstraints(List.of(List.of("Class1", "Relation1", "Class2", "1", "1")));
    }

    @Test
    void shouldCreateOntology() {

        when(stringUtils.toUpperCamelCase("Class1")).thenReturn("Class1");
        when(stringUtils.toUpperCamelCase("Class2")).thenReturn("Class2");
        when(stringUtils.toLowerCamelCase("Relation1")).thenReturn("relation1");

        JenaService.Response response = jenaService.createOntology(classes, hierarchy, relations, constraints);

        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/M"));

        assertThat(response.getModelAsString()).contains("https://www.example.org/ontologies/" + currentDate + "/untitled-ontology/Class1");
        assertThat(response.getModelAsString()).contains("https://www.example.org/ontologies/" + currentDate + "/untitled-ontology/Class2");
        assertThat(response.getModelAsString()).contains("https://www.example.org/ontologies/" + currentDate + "/untitled-ontology/relation1");
        assertThat(response.getModelAsString()).containsIgnoringWhitespaces("""
                <owl:Restriction>
                <rdfs:subClassOf rdf:resource="https://www.example.org/ontologies/2024/6/untitled-ontology/Class1"/>
                <owl:minCardinality rdf:datatype="http://www.w3.org/2001/XMLSchema#int"
                >1</owl:minCardinality>
                <owl:onProperty rdf:resource="https://www.example.org/ontologies/2024/6/untitled-ontology/relation1"/>
                </owl:Restriction>""");
        assertThat(response.getErrorMessages()).isEmpty();
    }

    @Test
    void shouldCreateOntologyWithHierarchyError() {

            hierarchy.setHierarchy(List.of(List.of("Class1", "Class3")));

            when(stringUtils.toUpperCamelCase("Class1")).thenReturn("Class1");
            when(stringUtils.toUpperCamelCase("Class2")).thenReturn("Class2");

            JenaService.Response response = jenaService.createOntology(classes, hierarchy, relations, constraints);

            assertThat(response.getErrorMessages())
                    .hasSize(1)
                    .contains("One or more classes in the hierarchy ([Class1, Class3]) do not exist.");
    }

    @Test
    void shouldCreateOntologyWithRelationsError() {

        relations.setRelations(List.of(List.of("Class1", "Relation1", "Class3")));

        when(stringUtils.toUpperCamelCase("Class1")).thenReturn("Class1");
        when(stringUtils.toUpperCamelCase("Class2")).thenReturn("Class2");
        when(stringUtils.toLowerCamelCase("Relation1")).thenReturn("relation1");

        JenaService.Response response = jenaService.createOntology(classes, hierarchy, relations, constraints);

        assertThat(response.getErrorMessages())
                .hasSize(2)
                .contains("One or more classes in the relation ([Class1, Relation1, Class3]) do not exist.");
    }

    @Test
    void shouldCreateOntologyWithConstraintsError() {

        constraints.setConstraints(List.of(List.of("Class1", "Relation1", "Class2", "1")));

        when(stringUtils.toUpperCamelCase("Class1")).thenReturn("Class1");
        when(stringUtils.toUpperCamelCase("Class2")).thenReturn("Class2");
        when(stringUtils.toLowerCamelCase("Relation1")).thenReturn("relation1");

        JenaService.Response response = jenaService.createOntology(classes, hierarchy, relations, constraints);

        assertThat(response.getErrorMessages())
                .hasSize(1)
                .contains("Constraint element [Class1, Relation1, Class2, 1] does not contain correct information format.");
    }
}
