package com.encora.ernesto.ramirez.todo_app.dtos;

import com.encora.ernesto.ramirez.todo_app.models.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class TodoDto {

    @Size(max = 120, message = "To-do text cannot be more than 120 chars")
    @NotBlank()
    private String text;
    private LocalDateTime dueDate;
    private Priority priority;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

}
