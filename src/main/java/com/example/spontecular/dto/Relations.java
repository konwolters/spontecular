package com.example.spontecular.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
public class Relations implements Feature{
    private List<List<String>> relations = new ArrayList<>();

    public Relations() {
    }

    public Relations(String relationsString) {
        // Create a pattern to match text within brackets
        Pattern pattern = Pattern.compile("\\[(.*?)]");
        Matcher matcher = pattern.matcher(relationsString);

        while (matcher.find()) {
            // Extracted string between brackets
            String extracted = matcher.group(1).trim();
            // Split the extracted string by comma and whitespace
            List<String> items = Arrays.asList(extracted.split(",\\s+"));
            this.relations.add(new ArrayList<>(items));
        }
    }

    @Override
    public Map<String, Object> getResponseMap() {
        return Map.of(
                "featureType", "relations",
                "nextFeatureType", "constraints",
                "itemList", getRelations()
        );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (List<String> relationElement : relations) {
            sb.append(relationElement.toString()).append(",\n");
        }
        return sb.toString();
    }
}
