package com.encora.ernesto.ramirez.todo_app.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationResult<T> {
    private long actualPage;
    private long total;
    private long availablePages;
    private List<T> data;
}
