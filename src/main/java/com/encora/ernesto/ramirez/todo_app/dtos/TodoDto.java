package com.encora.ernesto.ramirez.todo_app.dtos;

import com.encora.ernesto.ramirez.todo_app.models.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TodoDto {

    @Size(max = 120, message = "To-do text cannot be more than 120 chars")
    @NotBlank()
    private String text;

    private LocalDateTime dueDate;

    private Priority priority;
}
