package com.example.spontecular.jena;

import com.example.spontecular.feature.classes.ClassItem;
import com.example.spontecular.feature.classes.Classes;
import com.example.spontecular.feature.constraints.Constraints;
import com.example.spontecular.feature.constraints.ConstraintsItem;
import com.example.spontecular.feature.hierarchy.Hierarchy;
import com.example.spontecular.feature.hierarchy.HierarchyItem;
import com.example.spontecular.feature.relations.RelationItem;
import com.example.spontecular.feature.relations.Relations;
import com.example.spontecular.utility.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

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
        classes.setClasses(
                Arrays.asList(
                        new ClassItem("Class1", false),
                        new ClassItem("Class2", false)
                )
        );

        hierarchy = new Hierarchy();
        hierarchy.setHierarchy(
                Arrays.asList(
                        new HierarchyItem("Class1", "Class2", false)
                )
        );

        relations = new Relations();
        relations.setRelations(
                Arrays.asList(
                        new RelationItem("Class1", "Relation1", "Class2", false)
                )
        );

        constraints = new Constraints();
        constraints.setConstraints(
                Arrays.asList(
                        new ConstraintsItem("Class1", "Relation1", "Class2", "1", "N", false)
                )
        );
    }

    @Disabled
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
        hierarchy.setHierarchy(
                Arrays.asList(
                        new HierarchyItem("Class1", "Class3", false)
                )
        );

            when(stringUtils.toUpperCamelCase("Class1")).thenReturn("Class1");
            when(stringUtils.toUpperCamelCase("Class2")).thenReturn("Class2");

            JenaService.Response response = jenaService.createOntology(classes, hierarchy, relations, constraints);

            assertThat(response.getErrorMessages())
                    .hasSize(1);
    }

    @Test
    void shouldCreateOntologyWithRelationsError() {
        relations.setRelations(
                Arrays.asList(
                        new RelationItem("Class1", "Relation1", "Class3", false)
                )
        );

        when(stringUtils.toUpperCamelCase("Class1")).thenReturn("Class1");
        when(stringUtils.toUpperCamelCase("Class2")).thenReturn("Class2");
        when(stringUtils.toLowerCamelCase("Relation1")).thenReturn("relation1");

        JenaService.Response response = jenaService.createOntology(classes, hierarchy, relations, constraints);

        assertThat(response.getErrorMessages())
                .hasSize(2);
    }

    @Test
    void shouldCreateOntologyWithConstraintsError() {
        constraints.setConstraints(
                Arrays.asList(
                        new ConstraintsItem("Class1", "Relation1", "Class3", "1", "N", false)
                )
        );

        when(stringUtils.toUpperCamelCase("Class1")).thenReturn("Class1");
        when(stringUtils.toUpperCamelCase("Class2")).thenReturn("Class2");
        when(stringUtils.toLowerCamelCase("Relation1")).thenReturn("relation1");

        JenaService.Response response = jenaService.createOntology(classes, hierarchy, relations, constraints);

        assertThat(response.getErrorMessages())
                .hasSize(1);
    }
}
