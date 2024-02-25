package com.example.spontecular.jenaMisc;

public class JenaTestClass {

//    // Namespace for our ontology
//    String namespace = "http://example.com/ontology#";
//
//    // Create an ontology model in OWL
//    OntModel ontModel = ModelFactory.createOntologyModel();
//
//    String jsonString = """
//                {
//                  "class": "LivingThing",
//                  "subclasses": [
//                    {
//                      "class": "Animal",
//                      "subclasses": [
//                        {
//                          "class": "Mammal",
//                          "subclasses": [
//                            {
//                              "class": "Dog",
//                              "subclasses": []
//                            },
//                            {
//                              "class": "Cat",
//                              "subclasses": [
//                                {
//                                  "class": "PersianCat",
//                                  "subclasses": []
//                                },
//                                {
//                                  "class": "SiameseCat",
//                                  "subclasses": []
//                                }
//                              ]
//                            }
//                          ]
//                        },
//                        {
//                          "class": "Bird",
//                          "subclasses": [
//                            {
//                              "class": "Sparrow",
//                              "subclasses": []
//                            },
//                            {
//                              "class": "Eagle",
//                              "subclasses": []
//                            }
//                          ]
//                        }
//                      ]
//                    },
//                    {
//                      "class": "Plant",
//                      "subclasses": [
//                        {
//                          "class": "FloweringPlant",
//                          "subclasses": []
//                        }
//                      ]
//                    }
//                  ]
//                }""";
//
//    JenaService jenaService = new JenaService();
//    // Create classes and their hierarchy
//        jenaService.createClassHierarchy(ontModel, namespace, jsonString);
//
//    // Print the ontology in RDF/XML format
//        ontModel.write(System.out, "TURTLE");

//        // Create classes
//        OntClass personClass = ontModel.createClass(namespace + "Person");
//        OntClass employeeClass = ontModel.createClass(namespace + "Employee");
//        OntClass managerClass = ontModel.createClass(namespace + "Manager");
//
//        // Establish taxonomic relationships
//        // Employee is a subclass of Person
//        personClass.addSubClass(employeeClass);
//        // Manager is a subclass of Employee
//        employeeClass.addSubClass(managerClass);
//
//        // Create non-taxonomic relationships (object properties)
//        ObjectProperty manages = ontModel.createObjectProperty(namespace + "manages");
//        // Set domain and range for the manages relationship
//        manages.addDomain(managerClass);
//        manages.addRange(employeeClass);
//
//
//        // Create non-taxonomic relationships (object properties)
//        ObjectProperty hasManager = ontModel.createObjectProperty(namespace + "hasManager");
//        // Set domain and range for the hasManager relationship
//        hasManager.addDomain(employeeClass);
//        hasManager.addRange(managerClass);
//
//        // Create a cardinality restriction
//        CardinalityRestriction hasManagerRestriction = ontModel.createCardinalityRestriction(null, hasManager, 1);
//        // Add the restriction as a superclass of Employee
//        employeeClass.addSuperClass(hasManagerRestriction);
//
//        // Print the ontology in RDF/XML format
//        //RDFDataMgr.write(System.out, ontModel, RDFFormat.RDFXML_ABBREV);
//        ontModel.write(System.out, "RDF/XML-ABBREV");


//        // Create an ontology model
//        OntModel ontModel = ModelFactory.createOntologyModel();
//        String namespace = "http://example.com/ontology#";
//
//        // Create two disjoint classes: Dog and Cat
//        OntClass dogClass = ontModel.createClass(namespace + "Dog");
//        OntClass catClass = ontModel.createClass(namespace + "Cat");
//        dogClass.addDisjointWith(catClass);
//
//        // Create an individual that is both a Dog and a Cat (logical inconsistency)
//        Individual fido = ontModel.createIndividual(namespace + "Fido", dogClass);
//        fido.addOntClass(catClass);
//
//        // Check for consistency using a reasoner
//        checkConsistency(ontModel);
//    }
//
//    private static void checkConsistency(OntModel ontModel) {
//        // Get the Pellet reasoner (or use the OWL reasoner from Jena)
//        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
//        // Bind the reasoner to the ontology model
//        InfModel infModel = ModelFactory.createInfModel(reasoner, ontModel);
//
//        // Check for consistency
//        ValidityReport validity = infModel.validate();
//
//        // Report the findings
//        if (validity.isValid()) {
//            System.out.println("The ontology is consistent.");
//        } else {
//            System.out.println("The ontology is not consistent. Issues:");
//            for (Iterator<ValidityReport.Report> it = validity.getReports(); it.hasNext(); ) {
//                ValidityReport.Report report = it.next();
//                System.out.println("- " + report);
//            }
//        }
}
