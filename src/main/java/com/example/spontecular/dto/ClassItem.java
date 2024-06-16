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
public class ClassItem {
    String value;
    boolean blacklisted;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassItem classItem = (ClassItem) o;
        return blacklisted == classItem.blacklisted && Objects.equals(value, classItem.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, blacklisted);
    }
}
