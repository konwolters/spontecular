Input text:
"{inputText}"

Extract all ontology cardinality constraints from this technical specification. If the cardinality is not explicitly
mentioned within the text, infer it based on the usage of singular or plural forms of the objects involved in the
relationship as well as other indicator words like “multiple”, “at least”, “one or more” etc.

{definition}

{examples}

Concentrate exclusively on the following relationships: {relations}

{blacklist}

Output your findings in JSON format, listing each identified cardinality constraint as [Subject, Predicate,
Object, Min. Cardinality, Max. Cardinality] under the ’constraints’ field as a list.
Example: "constraints": [ ["User", "has", "Role", 1, 1], ["Role", "has", "Permission", 1, 1] ]