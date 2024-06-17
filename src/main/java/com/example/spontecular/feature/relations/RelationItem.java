package com.example.spontecular.feature.relations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RelationItem {
    private String subject;
    private String predicate;
    private String object;
    private boolean blacklisted;

    @Override
    public String toString() {
        return "RelationItem{" +
                "subject='" + subject + '\'' +
                ", predicate='" + predicate + '\'' +
                ", object='" + object + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelationItem that = (RelationItem) o;
        return blacklisted == that.blacklisted && Objects.equals(subject, that.subject) && Objects.equals(predicate, that.predicate) && Objects.equals(object, that.object);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, predicate, object, blacklisted);
    }
}
