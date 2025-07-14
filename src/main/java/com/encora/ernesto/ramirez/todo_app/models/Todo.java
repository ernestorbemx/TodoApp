package com.encora.ernesto.ramirez.todo_app.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing a Todo item.
 * Maps to a database table using JPA annotations.
 */
@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Primary key

    private String text; // Description of the Todo

    private LocalDateTime dueDate; // Due date for the Todo

    private boolean done; // Status indicating if the Todo is completed

    private LocalDateTime completionDate; // Completion date, set when the Todo is marked as done

    @Enumerated(EnumType.STRING)
    private Priority priority; // Priority level of the Todo

    private LocalDateTime creationDate; // Creation date of the Todo

    protected Todo() {
        // Default constructor for JPA
    }

    public Todo(String text, LocalDateTime dueDate, Priority priority, LocalDateTime creationDate) {
        this.text = text;
        this.dueDate = dueDate;
        this.priority = priority;
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate = completionDate;
    }
}
