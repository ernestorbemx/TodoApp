package com.encora.ernesto.ramirez.todo_app;

import com.encora.ernesto.ramirez.todo_app.dtos.Pagination;
import com.encora.ernesto.ramirez.todo_app.dtos.TodoFilter;
import com.encora.ernesto.ramirez.todo_app.models.Priority;
import com.encora.ernesto.ramirez.todo_app.models.Todo;
import com.encora.ernesto.ramirez.todo_app.repositories.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for TodoRepository.
 * Verifies CRUD operations and custom query functionality.
 */
@DataJpaTest
public class TodoRepositoryTests {

    @Autowired
    private TodoRepository todoRepository;

    private Todo todo1;
    private Todo todo2;
    private Todo todo3;

    @BeforeEach
    public void setup() {
        // Initialize test data
        todo1 = new Todo("Test todo 1", LocalDateTime.now().plusDays(2), Priority.HIGH, LocalDateTime.now());
        todo2 = new Todo("Test todo 2", LocalDateTime.now().plusDays(4), Priority.MEDIUM, LocalDateTime.now());
        todo3 = new Todo("Test todo 3", LocalDateTime.now().plusDays(1), Priority.LOW, LocalDateTime.now());
        todoRepository.save(todo1);
        todoRepository.save(todo2);
        todoRepository.save(todo3);
    }

    @Test
    public void testSaveTodo() {
        // Verify saving a new Todo
        Todo todo = new Todo("New todo", LocalDateTime.now().plusDays(1), Priority.LOW, LocalDateTime.now());
        Todo savedTodo = todoRepository.save(todo);
        assertNotNull(savedTodo.getId());
        assertEquals("New todo", savedTodo.getText());
    }

    @Test
    public void testFindById() {
        // Verify finding a Todo by ID
        Optional<Todo> foundTodo = todoRepository.findById(todo1.getId());
        assertTrue(foundTodo.isPresent());
        assertEquals(todo1.getText(), foundTodo.get().getText());
    }

    @Test
    public void testUpdateTodo() {
        // Verify updating a Todo
        todo1.setText("Updated text");
        Todo updatedTodo = todoRepository.save(todo1);
        assertEquals("Updated text", updatedTodo.getText());
    }

    @Test
    public void testDeleteTodo() {
        // Verify deleting a Todo
        todoRepository.deleteById(todo1.getId());
        Optional<Todo> deletedTodo = todoRepository.findById(todo1.getId());
        assertFalse(deletedTodo.isPresent());
    }

    @Test
    public void testGetTodosWithFilter() {
        // Verify filtering Todos by priority
        TodoFilter filter = new TodoFilter();
        filter.setPriority(Priority.HIGH);
        Pagination pagination = new Pagination();
        pagination.setPage(1);
        pagination.setSize(10);

        var result = todoRepository.getTodos(filter, pagination);
        assertEquals(1, result.getData().size());
        assertEquals(todo1.getText(), result.getData().get(0).getText());
    }

    @Test
    public void testGetTodosWithPagination() {
        // Verify pagination functionality
        TodoFilter filter = new TodoFilter();
        Pagination pagination = new Pagination();
        pagination.setPage(1);
        pagination.setSize(2);

        var result = todoRepository.getTodos(filter, pagination);
        assertEquals(2, result.getData().size());
        assertEquals(todo1.getText(), result.getData().get(0).getText());
        assertEquals(todo2.getText(), result.getData().get(1).getText());

        pagination.setPage(2);
        result = todoRepository.getTodos(filter, pagination);
        assertEquals(1, result.getData().size());
        assertEquals(todo3.getText(), result.getData().get(0).getText());
    }

    @Test
    public void testGetTodosWithSorting() {
        // Verify sorting functionality
        TodoFilter filter = new TodoFilter();
        filter.setSortingFields("dueDate");

        Pagination pagination = new Pagination();
        pagination.setPage(1);
        pagination.setSize(10);

        var result = todoRepository.getTodos(filter, pagination);
        assertEquals(3, result.getData().size());
        assertEquals(todo3.getText(), result.getData().get(0).getText()); // Sorted by dueDate ascending
        assertEquals(todo1.getText(), result.getData().get(1).getText());
        assertEquals(todo2.getText(), result.getData().get(2).getText());

        filter.setSortingFields("-dueDate");
        result = todoRepository.getTodos(filter, pagination);
        assertEquals(3, result.getData().size());
        assertEquals(todo2.getText(), result.getData().get(0).getText()); // Sorted by dueDate descending
        assertEquals(todo1.getText(), result.getData().get(1).getText());
        assertEquals(todo3.getText(), result.getData().get(2).getText());
    }

    @Test
    public void testGetTodosWithTextFiltering() {
        // Verify filtering Todos by text
        TodoFilter filter = new TodoFilter();
        filter.setText("Test todo 1");

        Pagination pagination = new Pagination();
        pagination.setPage(1);
        pagination.setSize(10);

        var result = todoRepository.getTodos(filter, pagination);
        assertEquals(1, result.getData().size());
        assertEquals(todo1.getText(), result.getData().get(0).getText());
    }
}
