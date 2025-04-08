package com.encora.ernesto.ramirez.todo_app;

import com.encora.ernesto.ramirez.todo_app.dtos.*;
import com.encora.ernesto.ramirez.todo_app.models.Priority;
import com.encora.ernesto.ramirez.todo_app.models.Todo;
import com.encora.ernesto.ramirez.todo_app.repositories.TodoRepository;
import com.encora.ernesto.ramirez.todo_app.services.TodoService;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TodoServiceTests {

    @Mock
    public TodoRepository todoRepository;

    @InjectMocks
    public TodoService service;

    @Test
    public void testCreationReturnsRepositoryTodo() {
        TodoDto dto = new TodoDto();
        Todo todo1 = new Todo("Test todo 1 :(", LocalDateTime.now().plusDays(2), Priority.HIGH, LocalDateTime.now()) ;
        Mockito.when(todoRepository.save(ArgumentMatchers.any(Todo.class))).thenReturn(todo1);
        assertEquals(service.createTodo(dto), todo1);
    }

    @Test
    public void testEdition() {
        TodoDto dto = new TodoDto();
        dto.setDueDate(LocalDateTime.now().plusMonths(1));
        dto.setPriority(Priority.LOW);
        dto.setText("Edited");
        Todo todo1 = new Todo("Test todo 1 :(", LocalDateTime.now().plusDays(2), Priority.HIGH, LocalDateTime.now()) ;
        Mockito.when(todoRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(todo1));
        assertTrue( service.editTodo(todo1.getId(),dto).isPresent());
        assertEquals(todo1.getText(), dto.getText());
        assertEquals(todo1.getPriority(), dto.getPriority());
        assertEquals(todo1.getDueDate(), dto.getDueDate());
    }

    @Test
    public void testStatusEdition() {
        Todo todo1 = new Todo("Test todo 1 :(", LocalDateTime.now().plusDays(2), Priority.HIGH, LocalDateTime.now()) ;
        Mockito.when(todoRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(todo1));
        assertTrue( service.editTodoCompletion(todo1.getId(),true).isPresent());
        assertNotNull(todo1.getCompletionDate());
        assertTrue(todo1.isDone());
        assertTrue( service.editTodoCompletion(todo1.getId(),false).isPresent());
        assertFalse(todo1.isDone());
        assertNull( todo1.getCompletionDate());
    }

    @Test
    public void testDeletion() {
        Todo todo1 = new Todo("Test todo 1 :(", LocalDateTime.now().plusDays(2), Priority.HIGH, LocalDateTime.now()) ;
        Mockito.when(todoRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(todo1));
        Optional<Todo> optionalTodo = service.deleteTodo(todo1.getId());
        assertTrue(optionalTodo.isPresent());
        assertEquals(todo1, optionalTodo.get());
        Mockito.verify(todoRepository, Mockito.times(1)).findById(ArgumentMatchers.anyInt());
    }

    @Test
    public void testStats() {
        List<Todo> todos = new ArrayList<>();
        todos.add(
                new Todo(
                        "Test todo 1 :(",
                        LocalDateTime.now().plusDays(2),
                        Priority.HIGH,
                        LocalDateTime.now()
                )
        );

        todos.add(
                new Todo(
                        "Test todo 2 :3",
                        null,
                        Priority.LOW,
                        LocalDateTime.now()
                )
        );


        todos.add(
                new Todo(
                        "Test todo 3 :)",
                        null,
                        Priority.MEDIUM,
                        LocalDateTime.now()
                )
        );

        Mockito.when(todoRepository.findAll()).thenReturn(todos);
        Stats stats = service.getStats();
        assertNotNull(stats);
        assertEquals(-1, stats.getAvg());
        assertEquals(-1, stats.getHighPriorityAvg());
        assertEquals(-1, stats.getMediumPriorityAvg());
        assertEquals(-1, stats.getLowPriorityAvg());
        Mockito.verify(todoRepository, Mockito.times(4)).findAll();
        // Set completion
        todos.get(0).setCompletionDate(LocalDateTime.now().plusMinutes(10));
        todos.get(0).setDone(true);
        todos.get(1).setCompletionDate(LocalDateTime.now().plusMinutes(5));
        todos.get(1).setDone(true);
        todos.get(2).setCompletionDate(LocalDateTime.now().plusMinutes(15));
        todos.get(2).setDone(true);
        stats = service.getStats();
        assertNotNull(stats);
        assertEquals(60*10, stats.getAvg());
        assertEquals(60*10, stats.getHighPriorityAvg());
        assertEquals(60*15, stats.getMediumPriorityAvg());
        assertEquals(60*5, stats.getLowPriorityAvg());

    }


    @Test
    public void testPagination() {
        List<Todo> todos = new ArrayList<>();
        todos.add(
                new Todo(
                        "Test todo 1 :(",
                        LocalDateTime.now().plusDays(2),
                        Priority.HIGH,
                        LocalDateTime.now()
                )
        );

        todos.add(
                new Todo(
                        "Test todo 2 :3",
                        null,
                        Priority.LOW,
                        LocalDateTime.now()
                )
        );


        todos.add(
                new Todo(
                        "Test todo 3 :)",
                        null,
                        Priority.MEDIUM,
                        LocalDateTime.now()
                )
        );

        TodoFilter filters = new TodoFilter();
        Pagination pagination = new Pagination();
        pagination.setPage(1);
        pagination.setSize(10);
        Mockito.when(todoRepository.findAll()).thenReturn(todos);
        Mockito.when(todoRepository.count()).thenReturn((long)todos.size());
        PaginationResult<Todo> result = service.getTodos(filters, pagination);
        Mockito.verify(todoRepository, Mockito.times(1)).getTodos(filters, pagination);
        assertNotNull(result);
        assertEquals(1, result.getActualPage());
        assertEquals(3, result.getTotal());
        assertEquals(1, result.getAvailablePages());
    }



}
