package com.encora.ernesto.ramirez.todo_app.controllers;

import com.encora.ernesto.ramirez.todo_app.dtos.*;
import com.encora.ernesto.ramirez.todo_app.models.Todo;
import com.encora.ernesto.ramirez.todo_app.services.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * REST controller for managing Todo entities.
 * Provides endpoints for CRUD operations, filtering, sorting, and statistics.
 */
@CrossOrigin(origins = { "http://localhost:8080", "http://127.0.0.1:8080" })
@RestController()
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    /**
     * Constructor to inject the TodoService dependency.
     * 
     * @param todoService Service layer for Todo operations
     */
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    /**
     * Endpoint to create a new Todo.
     * 
     * @param dto Data transfer object containing Todo details
     * @return The newly created Todo
     */
    @Operation(summary = "Create a new Todo", description = "Create a new Todo and returns the newly created Todo")
    @PostMapping
    public Todo createTodo(@Valid @RequestBody TodoDto dto) {
        return this.todoService.createTodo(dto);
    }

    /**
     * Endpoint to edit an existing Todo.
     * 
     * @param id  ID of the Todo to edit
     * @param dto Data transfer object containing updated Todo details
     * @return The edited Todo, if found
     */
    @Operation(summary = "Edit an existing Todo", description = "Edit an existing Todo and return the edited version of the Todo")
    @PutMapping("/{id}")
    public Optional<Todo> createTodo(@PathVariable("id") int id, @Valid @RequestBody TodoDto dto) {
        return this.todoService.editTodo(id, dto);
    }

    /**
     * Endpoint to mark an existing Todo as done.
     * 
     * @param id ID of the Todo to mark as done
     * @return The updated Todo, if found
     */
    @Operation(summary = "Mark existing Todo as done", description = "Mark existing Todo as done, it sets completion date and affect the result of GET /stats")
    @PutMapping("/{id}/done")
    public Optional<Todo> markAsDone(@PathVariable("id") int id) {
        return this.todoService.editTodoCompletion(id, true);
    }

    /**
     * Endpoint to mark an existing Todo as undone.
     * 
     * @param id ID of the Todo to mark as undone
     * @return The updated Todo, if found
     */
    @Operation(summary = "Mark existing Todo as undone", description = "Mark existing Todo as undone, it unsets completion date and affect the result of GET /stats. Opposite operation of PIT /{id}/undone")
    @PutMapping("/{id}/undone")
    public Optional<Todo> markAsUndone(@PathVariable("id") int id) {
        return this.todoService.editTodoCompletion(id, false);
    }

    /**
     * Endpoint to delete an existing Todo.
     * 
     * @param id ID of the Todo to delete
     * @return The deleted Todo, if found
     */
    @Operation(summary = "Delete existing Todo", description = "Delete existing Todo. If found, deletes and returns the deleted Todo. Otherwise, doesn't return anything.")
    @DeleteMapping("/{id}")
    public Optional<Todo> delete(@PathVariable("id") int id) {
        return this.todoService.deleteTodo(id);
    }

    /**
     * Endpoint to get paginated Todos based on filtering parameters.
     * 
     * @param dto           Data transfer object containing filtering parameters
     * @param paginationDto Data transfer object containing pagination parameters
     * @return Paginated result of Todos
     */
    @Operation(summary = "Returns paginated To-dos", description = "Returns paginates To-do based on the filtering parameters passed.")
    @GetMapping
    public PaginationResult<Todo> getTodos(@Valid @ModelAttribute TodoFilter dto,
            @Valid @ModelAttribute Pagination paginationDto) {
        return this.todoService.getTodos(dto, paginationDto);
    }

    /**
     * Endpoint to get completion average stats from previous completed Todos.
     * 
     * @return Completion average stats
     */
    @Operation(summary = "Returns completion average stats from previous completed To-dos", description = "Returns completion average stats from previous completed To-dos.")
    @GetMapping("/stats")
    public Stats getStats() {
        return this.todoService.getStats();
    }

}
