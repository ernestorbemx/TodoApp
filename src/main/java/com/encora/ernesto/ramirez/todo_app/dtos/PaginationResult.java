package com.encora.ernesto.ramirez.todo_app.dtos;

import java.util.List;

public class PaginationResult<T> {
    private long actualPage;
    private long total;
    private long availablePages;
    private List<T> data;

    public PaginationResult() {
    }

    public PaginationResult(long actualPage, long total, long availablePages, List<T> data) {
        this.actualPage = actualPage;
        this.total = total;
        this.data = data;
        this.availablePages = availablePages;
    }

    public long getAvailablePages() {
        return availablePages;
    }

    public void setAvailablePages(long availablePages) {
        this.availablePages = availablePages;
    }

    public long getActualPage() {
        return actualPage;
    }

    public void setActualPage(long actualPage) {
        this.actualPage = actualPage;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
