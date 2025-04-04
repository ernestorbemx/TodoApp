package com.encora.ernesto.ramirez.todo_app.controllers;

import com.encora.ernesto.ramirez.todo_app.dtos.Pagination;
import com.encora.ernesto.ramirez.todo_app.dtos.TodoDto;
import com.encora.ernesto.ramirez.todo_app.dtos.TodoFilter;
import com.encora.ernesto.ramirez.todo_app.models.Todo;
import com.encora.ernesto.ramirez.todo_app.services.TodoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:8080"})
@RestController()
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public Todo createTodo(@Valid @RequestBody TodoDto dto) {
        return this.todoService.createTodo(dto);
    }

    @PutMapping("/{id}")
    public Optional<Todo> createTodo(@PathVariable("id") int id, @Valid @RequestBody TodoDto dto) {
        return this.todoService.editTodo(id, dto);
    }


    @PutMapping("/{id}/done")
    public Optional<Todo> markAsDone(@PathVariable("id") int id) {
        return this.todoService.editTodoCompletion(id, true);
    }

    @PutMapping("/{id}/undone")
    public Optional<Todo> markAsUndone(@PathVariable("id") int id) {
        return this.todoService.editTodoCompletion(id, false);
    }

    @DeleteMapping("/{id}")
    public Optional<Todo> delete(@PathVariable("id") int id) {
        return this.todoService.deleteTodo(id);
    }

    @GetMapping
    public List<Todo> getTodos(@Valid @ModelAttribute TodoFilter dto, @Valid @ModelAttribute Pagination paginationDto) {
        System.out.println(dto.toString());
        System.out.println(paginationDto.toString());
        return this.todoService.getTodos(dto, paginationDto);
    }

    @GetMapping("/example")
    public String example() {
        return "Hola";
    }
}
