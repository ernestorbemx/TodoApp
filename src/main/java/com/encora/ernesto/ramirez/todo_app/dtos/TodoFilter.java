package com.encora.ernesto.ramirez.todo_app.dtos;

import com.encora.ernesto.ramirez.todo_app.models.Priority;

import java.util.List;
import java.util.Optional;

public class TodoFilter {
    private Boolean done;
    private String text;

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    private Priority priority;
    private List<String> sortingFields;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Priority getPriority() {
        return priority;
    }

    public List<String> getSortingFields() {
        return sortingFields;
    }

    public void setSortingFields(List<String> sortingFields) {
        this.sortingFields = sortingFields;
    }

    @Override
    public String toString() {
        return "TodoFilter{" +
                "done=" + done +
                ", text='" + text + '\'' +
                ", priority=" + priority +
                ", sortingFields=" + sortingFields +
                '}';
    }
}
