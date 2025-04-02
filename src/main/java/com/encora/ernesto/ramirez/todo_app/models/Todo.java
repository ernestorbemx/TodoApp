package com.encora.ernesto.ramirez.todo_app.models;

import java.time.LocalDateTime;

public class Todo {

    private static int nextId = 1;
    protected Todo() {

    }

    // This constructor is used for new Todo creation
    public Todo(String text, LocalDateTime dueDate, Priority priority, LocalDateTime creationDate) {
        this.id = nextId++;
        this.text = text;
        this.dueDate = dueDate;
        this.priority = priority;
        this.creationDate = creationDate;
    }

    private int id;

    private String text;

    private LocalDateTime dueDate;
    private boolean done;

    private LocalDateTime completionDate;

    private Priority priority;

    private LocalDateTime creationDate;

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
