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
public class Hierarchy implements Feature{
    private List<HierarchyItem> hierarchy = new ArrayList<>();

    @Override
    public Map<String, Object> getResponseMap() {
        return Map.of(
                "featureType", "hierarchy",
                "nextFeatureType", "relations",
                "itemList", getHierarchy()
        );
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
