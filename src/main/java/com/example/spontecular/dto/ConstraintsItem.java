package com.example.spontecular.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConstraintsItem {
    private String subject;
    private String predicate;
    private String object;
    private String minCardinality;
    private String maxCardinality;
    private boolean blacklisted;

    @Override
    public String toString() {
        return "ConstraintsItem{" +
                "subject='" + subject + '\'' +
                ", predicate='" + predicate + '\'' +
                ", object='" + object + '\'' +
                ", minCardinality=" + minCardinality +
                ", maxCardinality=" + maxCardinality +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConstraintsItem that = (ConstraintsItem) o;
        return Objects.equals(minCardinality, that.minCardinality) && Objects.equals(maxCardinality, that.maxCardinality) && blacklisted == that.blacklisted && Objects.equals(subject, that.subject) && Objects.equals(predicate, that.predicate) && Objects.equals(object, that.object);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, predicate, object, minCardinality, maxCardinality, blacklisted);
    }
}
