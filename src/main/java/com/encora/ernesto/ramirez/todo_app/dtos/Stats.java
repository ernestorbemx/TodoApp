package com.encora.ernesto.ramirez.todo_app.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stats {

    private long avg;
    private long lowPriorityAvg;
    private long mediumPriorityAvg;
    private long highPriorityAvg;
}
