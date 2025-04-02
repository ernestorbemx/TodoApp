package com.encora.ernesto.ramirez.todo_app.dtos;

public class Sorting {
    private String field;
    private boolean ascending;

    public Sorting() {
    }

    public Sorting(String field, boolean ascending) {
        this.field = field;
        this.ascending = ascending;
    }

    public static Sorting fromString(String src) {
        boolean descending = src.contains("-");
        return new Sorting(src.replace("-",""), !descending);
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }
}
