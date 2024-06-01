Input text:
"{inputText}"

Extract all ontology cardinality constraints from this technical specification.  If the cardinality is not explicitly
mentioned within the text, infer it based on the usage of singular or plural forms of the objects involved in the
relationship as well as other indicator words like “multiple”, “at least”, “one or more” etc.

Examples:
“The main computer receives data from multiple sensors”: Inferred as min. cardinality = 2, since “multiple” means more
than one sensor, max. cardinality = N.

“An action is assigned to one or more users”: Inferred as min. cardinality = 1, since “one or more” means at least one
user, max. cardinality = N.

“Admins can delete actions”: Inferred as min. cardinality = 0, since “can” implies that the number of actions being
deleted might be 0, max. cardinality = N.

Concentrate exclusively on the following relationships: {relations}

Output your findings in JSON format, listing each identified cardinality constraint as [Subject, Predicate,
Object, Min. Cardinality, Max. Cardinality] under the ’constraints’ field as a list.
Example: "constraints": [ ["User", "has", "Role", 1, 1], ["Role", "has", "Permission", 1, 1] ]