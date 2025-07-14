package com.encora.ernesto.ramirez.todo_app.repositories;

import com.encora.ernesto.ramirez.todo_app.models.Todo;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for managing Todo entities.
 * Extends CrudRepository for basic CRUD operations and TodoRepositoryCustom for
 * custom queries.
 */
public interface TodoRepository extends CrudRepository<Todo, Integer>, TodoRepositoryCustom {
    // Custom methods can be added here if needed
}
