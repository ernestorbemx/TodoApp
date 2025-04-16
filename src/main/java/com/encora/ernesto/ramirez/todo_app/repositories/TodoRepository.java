package com.encora.ernesto.ramirez.todo_app.repositories;

import com.encora.ernesto.ramirez.todo_app.dtos.Pagination;
import com.encora.ernesto.ramirez.todo_app.dtos.PaginationResult;
import com.encora.ernesto.ramirez.todo_app.dtos.TodoFilter;
import com.encora.ernesto.ramirez.todo_app.models.Todo;

import java.util.Optional;

public interface TodoRepository {

    PaginationResult<Todo> getTodos(TodoFilter filter, Pagination pagination);

    <S extends Todo> S save(S entity);

    <S extends Todo> Iterable<S> saveAll(Iterable<S> entities);

    Optional<Todo> findById(Integer id);

    Iterable<Todo> findAll();

    Iterable<Todo> findAllById(Iterable<Integer> integers);

    boolean existsById(Integer integer);

    long count();

    void deleteById(Integer id);

    void delete(Todo entity);

    void deleteAllById(Iterable<? extends Integer> integers);


    void deleteAll(Iterable<? extends Todo> entities);

    void deleteAll();

}
