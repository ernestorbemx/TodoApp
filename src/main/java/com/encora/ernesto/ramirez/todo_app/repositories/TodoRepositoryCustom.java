package com.encora.ernesto.ramirez.todo_app.repositories;

import com.encora.ernesto.ramirez.todo_app.dtos.Pagination;
import com.encora.ernesto.ramirez.todo_app.dtos.PaginationResult;
import com.encora.ernesto.ramirez.todo_app.dtos.TodoFilter;
import com.encora.ernesto.ramirez.todo_app.models.Todo;

/**
 * Custom repository interface for Todo entities.
 * Defines methods for advanced queries such as filtering and pagination.
 */
public interface TodoRepositoryCustom {
    /**
     * Retrieves paginated and filtered Todo entities based on the provided filter
     * and pagination settings.
     *
     * @param filter     Filtering criteria for the query.
     * @param pagination Pagination settings for the query.
     * @return A PaginationResult containing the filtered and paginated Todo
     *         entities.
     */
    PaginationResult<Todo> getTodos(TodoFilter filter, Pagination pagination);
}
