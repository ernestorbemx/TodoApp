package com.encora.ernesto.ramirez.todo_app.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity representing a Todo item.
 * Maps to a database table using JPA annotations.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Primary key

    public Todo(String text, LocalDateTime dueDate, Priority priority, LocalDateTime creationDate) {
        this.text = text;
        this.dueDate = dueDate;
        this.priority = priority;
        this.creationDate = creationDate;
    }

    private String text; // Description of the Todo

    private LocalDateTime dueDate; // Due date for the Todo

    private boolean done; // Status indicating if the Todo is completed

    private LocalDateTime completionDate; // Completion date, set when the Todo is marked as done

    @Enumerated(EnumType.STRING)
    private Priority priority; // Priority level of the Todo

    private LocalDateTime creationDate; // Creation date of the Todo
}
