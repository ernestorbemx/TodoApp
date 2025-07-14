package com.encora.ernesto.ramirez.todo_app.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class Pagination {

    @Min(message = "Page must be one or greater", value = 1)
    private int page;

    @Min(message = "Page size need to be at least 10", value = 10)
    @Max(message = "Page size need to be at most 20", value = 20)
    private int size;
}
