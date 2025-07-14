package com.encora.ernesto.ramirez.todo_app.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sorting {
    private String field;
    private boolean ascending;

    public static Sorting fromString(String src) {
        boolean descending = src.contains("-");
        return new Sorting(src.replace("-", ""), !descending);
    }
}
