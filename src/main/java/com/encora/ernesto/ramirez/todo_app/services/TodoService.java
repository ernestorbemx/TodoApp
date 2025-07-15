package com.encora.ernesto.ramirez.todo_app.services;

import com.encora.ernesto.ramirez.todo_app.dtos.*;
import com.encora.ernesto.ramirez.todo_app.models.Priority;
import com.encora.ernesto.ramirez.todo_app.models.Todo;
import com.encora.ernesto.ramirez.todo_app.repositories.TodoRepository;
import org.springframework.stereotype.Service;
import static java.time.temporal.ChronoUnit.SECONDS;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * Service layer for managing Todo entities.
 * Provides business logic for CRUD operations, filtering, sorting, and
 * statistics.
 */
@Service
public class TodoService {

    private final TodoRepository todoRepository;

    /**
     * Constructor to inject the TodoRepository dependency.
     * 
     * @param todoRepository Repository for Todo operations
     */
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    /**
     * Retrieves paginated and filtered Todos.
     * 
     * @param filter     Filtering criteria
     * @param pagination Pagination settings
     * @return Paginated result of Todos
     */
    public PaginationResult<Todo> getTodos(TodoFilter filter, Pagination pagination) {
        return this.todoRepository.getTodos(filter, pagination);
    }

    /**
     * Creates a new Todo.
     * 
     * @param todo Data transfer object containing Todo details
     * @return The created Todo entity
     */
    public Todo createTodo(TodoDto todo) {
        Todo newTodo = new Todo(todo.getText(), todo.getDueDate(), todo.getPriority(), LocalDateTime.now());
        return this.todoRepository.save(newTodo);
    }

    /**
     * Edits an existing Todo.
     * 
     * @param id  ID of the Todo to be edited
     * @param dto Data transfer object containing updated Todo details
     * @return Optional containing the updated Todo if found, otherwise empty
     */
    public Optional<Todo> editTodo(int id, TodoDto dto) {
        Optional<Todo> ot = this.todoRepository.findById(id);
        if (ot.isPresent()) {
            Todo t = ot.get();
            t.setText(dto.getText());
            t.setDueDate(dto.getDueDate());
            t.setPriority(dto.getPriority());
            return Optional.of(this.todoRepository.save(t)); // Save the updated entity
        }
        return Optional.empty();
    }

    /**
     * Edits the completion status of an existing Todo.
     * 
     * @param id   ID of the Todo to be edited
     * @param done Completion status
     * @return Optional containing the updated Todo if found, otherwise empty
     */
    public Optional<Todo> editTodoCompletion(int id, boolean done) {
        Optional<Todo> ot = this.todoRepository.findById(id);
        if (ot.isPresent()) {
            Todo t = ot.get();
            t.setDone(done);
            t.setCompletionDate(done ? LocalDateTime.now() : null);
            return Optional.of(this.todoRepository.save(t)); // Save the updated entity
        }
        return Optional.empty();
    }

    /**
     * Deletes an existing Todo.
     * 
     * @param todo ID of the Todo to be deleted
     * @return Optional containing the deleted Todo if found, otherwise empty
     */
    public Optional<Todo> deleteTodo(int todo) {
        Optional<Todo> ot = this.todoRepository.findById(todo);
        if (ot.isPresent()) {
            this.todoRepository.deleteById(todo);
        }
        return ot;
    }

    /**
     * Retrieves statistics about Todos.
     * 
     * @return Statistics object containing average completion times
     */
    public Stats getStats() {
        Iterable<Todo> todos = this.todoRepository.findAll();

        long overallAverage = calculateAverageCompletionTime(todos, null);
        long lowPriorityAverage = calculateAverageCompletionTime(todos, Priority.LOW);
        long mediumPriorityAverage = calculateAverageCompletionTime(todos, Priority.MEDIUM);
        long highPriorityAverage = calculateAverageCompletionTime(todos, Priority.HIGH);

        return new Stats(overallAverage, lowPriorityAverage, mediumPriorityAverage, highPriorityAverage);
    }

    /**
     * Calculates the average completion time for Todos.
     * 
     * @param todos    Iterable of Todos
     * @param priority Priority filter (optional)
     * @return Average completion time in seconds
     */
    private long calculateAverageCompletionTime(Iterable<Todo> todos, Priority priority) {
        return (long) StreamSupport.stream(todos.spliterator(), false)
                .filter(t -> t.getCompletionDate() != null && (priority == null || t.getPriority() == priority))
                .mapToLong(t -> SECONDS.between(t.getCreationDate(), t.getCompletionDate()))
                .average()
                .orElse(-1);
    }
}
