package com.example.spontecular.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
public class Hierarchy {
    private List<List<String>> hierarchy = new ArrayList<>();

    public Hierarchy() {
    }

    public Hierarchy(String hierarchyString) {
        // Create a pattern to match text within brackets
        Pattern pattern = Pattern.compile("\\[(.*?)]");
        Matcher matcher = pattern.matcher(hierarchyString);

        while (matcher.find()) {
            // Extracted string between brackets
            String extracted = matcher.group(1).trim();
            // Split the extracted string by comma and whitespace
            List<String> items = Arrays.asList(extracted.split(",\\s+"));
            this.hierarchy.add(new ArrayList<>(items));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (List<String> hierarchyElement : hierarchy) {
            sb.append(hierarchyElement.toString()).append(",\n");
        }
        return sb.toString();
    }
}
