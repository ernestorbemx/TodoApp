package com.encora.ernesto.ramirez.todo_app.repositories;

import com.encora.ernesto.ramirez.todo_app.dtos.Pagination;
import com.encora.ernesto.ramirez.todo_app.dtos.TodoFilter;
import com.encora.ernesto.ramirez.todo_app.models.Todo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class TodoRepositoryImpl implements TodoRepository{
    public List<Todo> todos = new ArrayList<>();

    @Override
    public <S extends Todo> S save(S entity) {
        this.todos.add(entity);
        return entity;
    }

    @Override
    public <S extends Todo> Iterable<S> saveAll(Iterable<S> entities) {
        for (S s : entities) {
            this.todos.add(s);
        }
        return entities;
    }

    @Override
    public Optional<Todo> findById(Integer id) {
        return this.todos.stream().filter(s -> s.getId() == id).findFirst();
    }

    @Override
    public boolean existsById(Integer integer) {
        return findById(integer).isPresent();
    }

    @Override
    public Iterable<Todo> findAll() {
        return new ArrayList<>(this.todos);
    }

    @Override
    public Iterable<Todo> findAllById(Iterable<Integer> integers) {
        List<Todo> occurrences = new ArrayList<>();
        for (Integer i : integers) {
            Optional<Todo> todoToDelete =  this.todos.stream().filter(s -> s.getId() == i).findFirst();
            todoToDelete.ifPresent(occurrences::add);
        }
        return occurrences;
    }

    @Override
    public long count() {
        return this.todos.size();
    }

    @Override
    public void deleteById(Integer id) {
       Optional<Todo> todoToDelete =  this.todos.stream().filter(s -> s.getId() == id).findFirst();
       todoToDelete.ifPresent((t) -> todos.remove(t));
    }

    @Override
    public void delete(Todo entity) {
        todos.remove(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {
        for (Integer i : integers) {
            Optional<Todo> todoToDelete =  this.todos.stream().filter(s -> s.getId() == i).findFirst();
            todoToDelete.ifPresent((t) -> todos.remove(t));
        }
    }

    @Override
    public void deleteAll(Iterable<? extends Todo> entities) {
        for (Todo t : entities) {
            todos.remove(t);
        }
    }

    @Override
    public void deleteAll() {
        this.todos.clear();
    }

    @Override
    public List<Todo> getTodos(TodoFilter filter, Pagination pagination) {

//        List<Sorting> criterias = filter.getSortingFields().stream().map(Sorting::fromString).collect(Collectors.toList());
//        Optional<Sorting> prioritySort = criterias.stream().filter(sorting -> sorting.getField().equalsIgnoreCase("priority")).findFirst();
//        Optional<Sorting> dueDate = criterias.stream().filter(sorting -> sorting.getField().equalsIgnoreCase("dueDate")).findFirst();
        return this.todos.stream()
                .skip((pagination.getPage()-1) * pagination.getSize())
                .filter((t) -> filter.getPriority() == null ||  filter.getPriority().equals(t.getPriority()))
                .filter((t) -> filter.getDone() == null ||  filter.getDone() == t.isDone())
                .filter((t) -> filter.getText() == null || t.getText().contains(filter.getText()))
//                .sorted((t) -> {
//
//                    if(dueDate.isPresent()) {
//                        int c1 = t.getDueDate().compareTo();
//                    }
//                })
                .collect(Collectors.toList());
    }
}
