package com.example.spontecular.feature.hierarchy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HierarchyItem {
    private String parent;
    private String child;
    private boolean blacklisted;


    @Override
    public String toString() {
        return "HierarchyItem{" +
                "parentClass='" + parent + '\'' +
                ", subClass='" + child + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HierarchyItem that = (HierarchyItem) o;
        return Objects.equals(parent, that.parent) && Objects.equals(child, that.child);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, child);
    }
}
