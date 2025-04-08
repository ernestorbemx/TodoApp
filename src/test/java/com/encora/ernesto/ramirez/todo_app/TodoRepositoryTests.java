package com.encora.ernesto.ramirez.todo_app;


import com.encora.ernesto.ramirez.todo_app.dtos.Pagination;
import com.encora.ernesto.ramirez.todo_app.dtos.TodoFilter;
import com.encora.ernesto.ramirez.todo_app.models.Priority;
import com.encora.ernesto.ramirez.todo_app.models.Todo;
import com.encora.ernesto.ramirez.todo_app.repositories.TodoRepository;
import com.encora.ernesto.ramirez.todo_app.repositories.TodoRepositoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

public class TodoRepositoryTests {

    private TodoRepository repository;

    @BeforeEach
    public void beforeEach() {
        this.repository = new TodoRepositoryImpl();
    }

    @AfterEach
    public void afterEach() {
        this.repository = null;
    }


    @Test
    public void testCreation() {

        Todo todo = new Todo("Test todo", LocalDateTime.now().plusDays(2), Priority.HIGH, LocalDateTime.now()) ;
        assertEquals(0, repository.count());
        Todo saved = repository.save(todo);
        assertEquals(1, repository.count());
        assertEquals(todo,saved);
    }

    @Test
    public void testDeletion() {
        Todo todo = new Todo("Test todo", LocalDateTime.now().plusDays(2), Priority.HIGH, LocalDateTime.now()) ;
        assertEquals(0, repository.count());
        repository.save(todo);
        assertEquals(1, repository.count());
        assertTrue(repository.findById(todo.getId()).isPresent());
        repository.delete(todo);
        assertEquals(0, repository.count());
        assertTrue(repository.findById(todo.getId()).isEmpty());
    }

    @Test
    public void testNullPaginationAndFiltering() {
        Todo todo1 = new Todo("Test todo 1", LocalDateTime.now().plusDays(2), Priority.HIGH, LocalDateTime.now()) ;
        Todo todo2 = new Todo("Test todo 2", LocalDateTime.now().plusDays(4), Priority.MEDIUM, LocalDateTime.now().plusMinutes(1)) ;
        Todo todo3 = new Todo("Test todo 3", LocalDateTime.now().plusDays(5), Priority.LOW, LocalDateTime.now().plusMinutes(2)) ;
        Todo todo4 = new Todo("Test todo 4", LocalDateTime.now().plusDays(1), Priority.HIGH, LocalDateTime.now().plusMinutes(3)) ;
        Todo todo5 = new Todo("Test todo 5", LocalDateTime.now().plusDays(2), Priority.HIGH, LocalDateTime.now().plusMinutes(4)) ;
        assertEquals(0, repository.count());
        repository.save(todo1);
        repository.save(todo2);
        repository.save(todo3);
        repository.save(todo4);
        repository.save(todo5);
        assertEquals(5, repository.count());
        TodoFilter filter = new TodoFilter();
        Pagination pagination = new Pagination();
        pagination.setSize(10);
        pagination.setPage(1);
        assertEquals(5, repository.getTodos(filter,pagination).size());
    }

    @Test
    public void testTextFiltering() {
        Todo todo1 = new Todo("Test todo 1 :)", LocalDateTime.now().plusDays(2), Priority.HIGH, LocalDateTime.now()) ;
        Todo todo2 = new Todo("Test todo 2", LocalDateTime.now().plusDays(4), Priority.MEDIUM, LocalDateTime.now().plusMinutes(1)) ;
        Todo todo3 = new Todo("Test todo 3 :)", LocalDateTime.now().plusDays(5), Priority.LOW, LocalDateTime.now().plusMinutes(2)) ;
        Todo todo4 = new Todo("Test todo 4", LocalDateTime.now().plusDays(1), Priority.HIGH, LocalDateTime.now().plusMinutes(3)) ;
        Todo todo5 = new Todo("Test todo 5", LocalDateTime.now().plusDays(2), Priority.HIGH, LocalDateTime.now().plusMinutes(4)) ;
        assertEquals(0, repository.count());
        repository.save(todo1);
        repository.save(todo2);
        repository.save(todo3);
        repository.save(todo4);
        repository.save(todo5);
        assertEquals(5, repository.count());
        TodoFilter filter = new TodoFilter();
        filter.setText("5");
        Pagination pagination = new Pagination();
        pagination.setSize(10);
        pagination.setPage(1);
        assertEquals(1, repository.getTodos(filter,pagination).size());
        filter.setText(":)");
        assertEquals(2, repository.getTodos(filter,pagination).size());
    }

    @Test
    public void testPriorityFiltering() {
        Todo todo1 = new Todo("Test todo 1", LocalDateTime.now().plusDays(2), Priority.HIGH, LocalDateTime.now()) ;
        Todo todo2 = new Todo("Test todo 2", LocalDateTime.now().plusDays(4), Priority.MEDIUM, LocalDateTime.now().plusMinutes(1)) ;
        Todo todo3 = new Todo("Test todo 3", LocalDateTime.now().plusDays(5), Priority.LOW, LocalDateTime.now().plusMinutes(2)) ;
        Todo todo4 = new Todo("Test todo 4", LocalDateTime.now().plusDays(1), Priority.HIGH, LocalDateTime.now().plusMinutes(3)) ;
        Todo todo5 = new Todo("Test todo 5", LocalDateTime.now().plusDays(2), Priority.HIGH, LocalDateTime.now().plusMinutes(4)) ;
        assertEquals(0, repository.count());
        repository.save(todo1);
        repository.save(todo2);
        repository.save(todo3);
        repository.save(todo4);
        repository.save(todo5);
        assertEquals(5, repository.count());
        TodoFilter filter = new TodoFilter();
        filter.setPriority(Priority.HIGH);
        Pagination pagination = new Pagination();
        pagination.setSize(10);
        pagination.setPage(1);
        assertEquals(3, repository.getTodos(filter,pagination).size());
    }

    @Test
    public void testStatusFiltering() {
        Todo todo1 = new Todo("Test todo 1", LocalDateTime.now().plusDays(2), Priority.HIGH, LocalDateTime.now()) ;
        Todo todo2 = new Todo("Test todo 2", LocalDateTime.now().plusDays(4), Priority.MEDIUM, LocalDateTime.now().plusMinutes(1)) ;
        Todo todo3 = new Todo("Test todo 3", LocalDateTime.now().plusDays(5), Priority.LOW, LocalDateTime.now().plusMinutes(2)) ;
        Todo todo4 = new Todo("Test todo 4", LocalDateTime.now().plusDays(1), Priority.HIGH, LocalDateTime.now().plusMinutes(3)) ;
        Todo todo5 = new Todo("Test todo 5", LocalDateTime.now().plusDays(2), Priority.HIGH, LocalDateTime.now().plusMinutes(4)) ;
        todo2.setDone(true);
        todo4.setDone(true);
        assertEquals(0, repository.count());
        repository.save(todo1);
        repository.save(todo2);
        repository.save(todo3);
        repository.save(todo4);
        repository.save(todo5);
        assertEquals(5, repository.count());
        TodoFilter filter = new TodoFilter();
        filter.setDone(true);
        Pagination pagination = new Pagination();
        pagination.setSize(10);
        pagination.setPage(1);
        assertEquals(2, repository.getTodos(filter,pagination).size());
        filter.setDone(false);
        assertEquals(3, repository.getTodos(filter,pagination).size());
    }

    @Test
    public void testPagination() {
        Todo todo1 = new Todo("Test todo 1", LocalDateTime.now().plusDays(2), Priority.HIGH, LocalDateTime.now()) ;
        Todo todo2 = new Todo("Test todo 2", LocalDateTime.now().plusDays(4), Priority.MEDIUM, LocalDateTime.now().plusMinutes(1)) ;
        Todo todo3 = new Todo("Test todo 3", LocalDateTime.now().plusDays(5), Priority.LOW, LocalDateTime.now().plusMinutes(2)) ;
        Todo todo4 = new Todo("Test todo 4", LocalDateTime.now().plusDays(1), Priority.HIGH, LocalDateTime.now().plusMinutes(3)) ;
        Todo todo5 = new Todo("Test todo 5", LocalDateTime.now().plusDays(2), Priority.HIGH, LocalDateTime.now().plusMinutes(4)) ;
        assertEquals(0, repository.count());
        repository.save(todo1);
        repository.save(todo2);
        repository.save(todo3);
        repository.save(todo4);
        repository.save(todo5);
        assertEquals(5, repository.count());
        TodoFilter filter = new TodoFilter();
        Pagination pagination = new Pagination();
        pagination.setSize(2);
        pagination.setPage(1);
        assertEquals(2, repository.getTodos(filter,pagination).size());
        pagination.setPage(2);
        assertEquals(2, repository.getTodos(filter,pagination).size());
        pagination.setPage(3);
        assertEquals(1, repository.getTodos(filter,pagination).size());
        pagination.setPage(4);
        assertEquals(0, repository.getTodos(filter,pagination).size());
    }

    @Test
    public void testPaginationAndFiltering() {
        Todo todo1 = new Todo("Test todo 1 :(", LocalDateTime.now().plusDays(2), Priority.HIGH, LocalDateTime.now()) ;
        Todo todo2 = new Todo("Test todo 2 :)", LocalDateTime.now().plusDays(4), Priority.MEDIUM, LocalDateTime.now().plusMinutes(1)) ;
        Todo todo3 = new Todo("Test todo 3 :)", LocalDateTime.now().plusDays(5), Priority.LOW, LocalDateTime.now().plusMinutes(2)) ;
        Todo todo4 = new Todo("Test todo 4 :(", LocalDateTime.now().plusDays(1), Priority.HIGH, LocalDateTime.now().plusMinutes(3)) ;
        Todo todo5 = new Todo("Test todo 5 :3", LocalDateTime.now().plusDays(2), Priority.HIGH, LocalDateTime.now().plusMinutes(4)) ;
        todo2.setDone(true);
        todo4.setDone(false);
        assertEquals(0, repository.count());
        repository.save(todo1);
        repository.save(todo2);
        repository.save(todo3);
        repository.save(todo4);
        repository.save(todo5);
        assertEquals(5, repository.count());
        TodoFilter filter = new TodoFilter();
        filter.setText(":)");
        filter.setPriority(Priority.HIGH);
        Pagination pagination = new Pagination();
        pagination.setSize(2);
        pagination.setPage(1);
        assertEquals(0, repository.getTodos(filter,pagination).size());
        filter.setPriority(null);
        assertEquals(2, repository.getTodos(filter,pagination).size());
        pagination.setPage(2);
        assertEquals(0, repository.getTodos(filter,pagination).size());

    }


}
