package com.encora.ernesto.ramirez.todo_app;

import com.encora.ernesto.ramirez.todo_app.dtos.Pagination;
import com.encora.ernesto.ramirez.todo_app.dtos.PaginationResult;
import com.encora.ernesto.ramirez.todo_app.dtos.TodoDto;
import com.encora.ernesto.ramirez.todo_app.dtos.TodoFilter;
import com.encora.ernesto.ramirez.todo_app.models.Priority;
import com.encora.ernesto.ramirez.todo_app.models.Todo;
import com.encora.ernesto.ramirez.todo_app.services.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class TodoControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TodoService service;

    @Autowired
    private ObjectMapper objectMapper;  // Jackson's ObjectMapper to serialize the User object


    @Test
    void testCreateDtoValidation() throws Exception {
        TodoDto dto = new TodoDto();
        dto.setText("");//blank is failure
        String dtoJson = objectMapper.writeValueAsString(dto);
        mockMvc.perform(post("/todos").content(dtoJson).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(400)); //.andExpect(content().)
    }

    @Test
    void testCreateDto() throws Exception {
        TodoDto dto = new TodoDto();
        dto.setText("Hola");//blank is failure
        dto.setPriority(Priority.HIGH);
        dto.setDueDate(LocalDateTime.now());
        String dtoJson = objectMapper.writeValueAsString(dto);
        Todo todo = new Todo("mock", null, Priority.LOW, LocalDateTime.now());
        Mockito.when(service.createTodo(Mockito.any())).thenReturn(todo);
        mockMvc.perform(
                post("/todos")
                .content(dtoJson)
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().is(200))
                .andExpect(jsonPath("$.text").value(todo.getText()));
    }

    @Test
    void testEditDto() throws Exception {
        TodoDto dto = new TodoDto();
        dto.setText("Hola");//blank is failure
        dto.setPriority(Priority.HIGH);
        dto.setDueDate(LocalDateTime.now());
        String dtoJson = objectMapper.writeValueAsString(dto);
        Todo todo = new Todo("mock", null, Priority.LOW, LocalDateTime.now());
        Mockito.when(service.editTodo(Mockito.anyInt(), Mockito.any())).thenReturn(Optional.of(todo));
        mockMvc.perform(
                        put("/todos/1")
                                .content(dtoJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.text").value(todo.getText()));
    }

    @Test
    void testDelete() throws Exception {
        Todo todo = new Todo("mock", null, Priority.LOW, LocalDateTime.now());
        Mockito.when(service.deleteTodo(Mockito.anyInt())).thenReturn(Optional.of(todo));
        mockMvc.perform(
                        delete("/todos/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.text").value(todo.getText()));
    }

    @Test
    void testGetTodos() throws Exception {
        TodoFilter filter = new TodoFilter();
        Pagination pagination = new Pagination();
        pagination.setSize(10);
        pagination.setPage(1);
        Todo todo = new Todo("mock", null, Priority.LOW, LocalDateTime.now());
        Mockito.when(service.getTodos(Mockito.any(), Mockito.any())).thenReturn(new PaginationResult<>(1,100,10, List.of(todo)));
        mockMvc.perform(
                        get("/todos")
                                .param("text", filter.getText())
                                .param("sortingFields", filter.getSortingFields())
                                .param("priority", filter.getPriority() != null ? filter.getPriority().toString() : "")
                                .param("done", filter.getDone() != null ? filter.getDone().toString() : "")
                                .param("size", String.valueOf(pagination.getSize()))
                                .param("page", String.valueOf(pagination.getPage()))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.actualPage").value(1))
                .andExpect(jsonPath("$.total").value(100))
                .andExpect(jsonPath("$.availablePages").value(10))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void testStats() throws Exception {
        mockMvc.perform(
                get("/todos/stats")
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().is(200));
        Mockito.verify(service, Mockito.times(1)).getStats();
    }
}
