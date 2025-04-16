package com.encora.ernesto.ramirez.todo_app.repositories;

import com.encora.ernesto.ramirez.todo_app.dtos.Pagination;
import com.encora.ernesto.ramirez.todo_app.dtos.Sorting;
import com.encora.ernesto.ramirez.todo_app.dtos.TodoFilter;
import com.encora.ernesto.ramirez.todo_app.models.Todo;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Stream;


@Component
public class TodoRepositoryImpl implements TodoRepository {
    public final List<Todo> todos = new ArrayList<>();

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
            Optional<Todo> todoToDelete = this.todos.stream().filter(s -> s.getId() == i).findFirst();
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
        Optional<Todo> todoToDelete = this.todos.stream().filter(s -> s.getId() == id).findFirst();
        todoToDelete.ifPresent(todos::remove);
    }

    @Override
    public void delete(Todo entity) {
        todos.remove(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {
        for (Integer i : integers) {
            Optional<Todo> todoToDelete = this.todos.stream().filter(s -> s.getId() == i).findFirst();
            todoToDelete.ifPresent(todos::remove);
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

        int skip = (pagination.getPage() - 1) * pagination.getSize();
        Stream<Todo> filteredTodos = this.todos.stream()
                .filter((t) -> filter.getPriority() == null || filter.getPriority().equals(t.getPriority()))
                .filter((t) -> filter.getDone() == null || filter.getDone() == t.isDone())
                .filter((t) -> filter.getText() == null || t.getText().contains(filter.getText()));
                //.skip((pagination.getPage() - 1) * pagination.getSize());

        List<Sorting> criterias = filter.getSortingFields() == null ? new ArrayList<>() : Arrays.stream(filter.getSortingFields().split(",")).map(Sorting::fromString).toList();
        Stream<Comparator<Todo>> comparators = criterias.stream()
                .filter(c -> c.getField().equalsIgnoreCase("priority") || c.getField().equalsIgnoreCase("dueDate"))
                .map(c -> (t1, t2) -> {
                    if (c.getField().equalsIgnoreCase("priority")) {
                        return t1.getPriority() == null ? -1 : t1.getPriority().compareTo(t2.getPriority()) * (c.isAscending() ? 1 : -1);
                    }
                    if (c.getField().equalsIgnoreCase("dueDate")) {
                        if(t1.getDueDate() == null && t2.getDueDate() != null) {
                            return -1 * (c.isAscending() ? 1 : -1);
                        }
                        if (t2.getDueDate() == null && t1.getDueDate() != null) {
                            return (c.isAscending() ? 1 : -1);
                        }
                        if(t1.getDueDate() == null && t2.getDueDate()==null) {
                            return 0;
                        }
                        return t1.getDueDate().compareTo(t2.getDueDate()) * (c.isAscending() ? 1 : -1);
                    }
                    return 0;
                });

        Optional<Comparator<Todo>> sortComparator = comparators.reduce(Comparator::thenComparing);


        return sortComparator.map(todoComparator -> filteredTodos.sorted(todoComparator)
                .skip(skip).limit(pagination.getSize()).toList()).orElseGet(() -> filteredTodos.skip(skip).limit(pagination.getSize()).toList());
    }
}
