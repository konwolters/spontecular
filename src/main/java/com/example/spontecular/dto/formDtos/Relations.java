package com.example.spontecular.dto.formDtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Relations {
    List<Relation> relations;

    public Relations(List<Relation> relations) {
        this.relations = relations;
    }
}
