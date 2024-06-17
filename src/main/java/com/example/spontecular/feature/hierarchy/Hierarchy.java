package com.example.spontecular.feature.hierarchy;

import com.example.spontecular.feature.Feature;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Hierarchy implements Feature {
    private List<HierarchyItem> hierarchy = new ArrayList<>();

    @JsonIgnore
    @Override
    public Map<String, Object> getResponseMap() {

        //Only show non blacklisted items
        hierarchy = hierarchy.stream()
                .filter(item -> !item.isBlacklisted())
                .toList();

        return new HashMap<>() {
            {
                put("featureType", "hierarchy");
                put("nextFeatureType", "relations");
                put("itemList", getHierarchy());
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (HierarchyItem hierarchyItem : hierarchy) {
            sb.append(hierarchyItem.toString()).append(",\n");
        }
        return sb.toString();
    }
}
