Input text:
"{inputText}"

Extract all ontology relationships from this technical specification that represent a strict non-taxonomic relationship.
{definition} Do not include subtype or "has"-relationships that indicate that a class is a attribute of another class.
Limit yourself to relations that are actually mentioned in the specification and to the verbs that are used to describe them.
Concentrate exclusively on the following classes: {classes}

{examples}

{blacklist}

Output your findings in JSON format, listing each identified non-taxonomic relationship as [Subject, Predicate, Object]
under the ’relations’ field as a list.”Output your findings in JSON format, listing each identified non-taxonomic
relationship as [Subject, Predicate, Object] under the ’relations’ field as a list