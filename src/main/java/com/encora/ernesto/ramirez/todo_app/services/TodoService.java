package com.encora.ernesto.ramirez.todo_app.services;

import com.encora.ernesto.ramirez.todo_app.dtos.Pagination;
import com.encora.ernesto.ramirez.todo_app.dtos.PaginationResult;
import com.encora.ernesto.ramirez.todo_app.dtos.TodoDto;
import com.encora.ernesto.ramirez.todo_app.dtos.TodoFilter;
import com.encora.ernesto.ramirez.todo_app.models.Todo;
import com.encora.ernesto.ramirez.todo_app.repositories.TodoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private static final int PAG_SIZE = 10;

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public PaginationResult<Todo> getTodos(TodoFilter filter, Pagination pagination) {
        long count = this.todoRepository.count();
        return new PaginationResult<>(
                pagination.getPage(),
                count,
                Math.max(1,(long)Math.ceil((double) count / pagination.getSize())),
                this.todoRepository.getTodos(filter, pagination)
        );
    }

    public Todo createTodo(TodoDto todo) {
        Todo newTodo = new Todo(todo.getText(), todo.getDueDate(), todo.getPriority(), LocalDateTime.now());
        this.todoRepository.save(newTodo);
        return newTodo;
    }

    // Edition is not guaranteed
    public Optional<Todo> editTodo(int id, TodoDto dto) {
        Optional<Todo> ot = this.todoRepository.findById(id);
        if(ot.isPresent()) {
            Todo t = ot.get();
            t.setText(dto.getText());
            t.setDueDate(dto.getDueDate());
            t.setPriority(dto.getPriority());
            return Optional.of(t);
        }
        return Optional.empty();
    }

    public Optional<Todo> editTodoCompletion(int id, boolean done) {
        Optional<Todo> ot = this.todoRepository.findById(id);
        if(ot.isPresent()) {
            Todo t = ot.get();
            t.setDone(done);
            t.setCompletionDate(done ? LocalDateTime.now() : null);
            return Optional.of(t);
        }
        return Optional.empty();
    }

    public Optional<Todo> deleteTodo(int todo) {
        Optional<Todo> ot = this.todoRepository.findById(todo);
        if(ot.isPresent()) {
            this.todoRepository.deleteById(todo);
        }
        return ot;
    }

}
