package com.example.spontecular.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Relations implements Feature {
    private List<RelationItem> relations = new ArrayList<>();


    @JsonIgnore
    @Override
    public Map<String, Object> getResponseMap() {

        //Only show non blacklisted items
        relations = relations.stream()
                .filter(item -> !item.isBlacklisted())
                .toList();

        return new HashMap<>() {{
            put("featureType", "relations");
            put("nextFeatureType", "constraints");
            put("itemList", getRelations());
        }};
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (RelationItem relationItem : relations) {
            sb.append(relationItem.toString()).append(",\n");
        }
        return sb.toString();
    }
}
