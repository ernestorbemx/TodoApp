package com.encora.ernesto.ramirez.todo_app.dtos;

public class Stats {


    private long avg;
    private long lowPriorityAvg;
    private long mediumPriorityAvg;

    public Stats() {
    }

    public Stats(long avg, long lowPriorityAvg, long mediumPriorityAvg, long highPriorityAvg) {
        this.avg = avg;
        this.lowPriorityAvg = lowPriorityAvg;
        this.mediumPriorityAvg = mediumPriorityAvg;
        this.highPriorityAvg = highPriorityAvg;
    }

    public long getHighPriorityAvg() {
        return highPriorityAvg;
    }

    public void setHighPriorityAvg(long highPriorityAvg) {
        this.highPriorityAvg = highPriorityAvg;
    }

    public long getMediumPriorityAvg() {
        return mediumPriorityAvg;
    }

    public void setMediumPriorityAvg(long mediumPriorityAvg) {
        this.mediumPriorityAvg = mediumPriorityAvg;
    }

    public long getLowPriorityAvg() {
        return lowPriorityAvg;
    }

    public void setLowPriorityAvg(long lowPriorityAvg) {
        this.lowPriorityAvg = lowPriorityAvg;
    }

    public long getAvg() {
        return avg;
    }

    public void setAvg(long avg) {
        this.avg = avg;
    }

    private long highPriorityAvg;


}
