Input text:
"{inputText}"

Extract all ontology classes/concepts from this technical specification. {definition}.
Also include potential subclasses that are mentioned in the specification and represent specific hardware
parts or software modules. Do not include components that are not physical or virtual components that are being used
to build the system. The text may contain a significant number of relevant classes. Make sure you examine the entire
text carefully and extract 100% of the relevant classes. These also include potential subclasses that are mentioned
in the specification and represent specific hardware parts or software modules.

{examples}

{blacklist}

Output your findings in JSON format, listing each identified class in singular form under the 'classes' field as a list of classes.”