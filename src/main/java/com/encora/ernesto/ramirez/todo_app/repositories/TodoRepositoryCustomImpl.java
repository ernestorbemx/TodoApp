package com.encora.ernesto.ramirez.todo_app.repositories;

import com.encora.ernesto.ramirez.todo_app.dtos.Pagination;
import com.encora.ernesto.ramirez.todo_app.dtos.PaginationResult;
import com.encora.ernesto.ramirez.todo_app.dtos.Sorting;
import com.encora.ernesto.ramirez.todo_app.dtos.TodoFilter;
import com.encora.ernesto.ramirez.todo_app.models.Todo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the custom repository interface for Todo entities.
 * Provides advanced query functionality such as filtering, sorting, and
 * pagination.
 */
@Repository
public class TodoRepositoryCustomImpl implements TodoRepositoryCustom {

    private final EntityManager entityManager;

    public TodoRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public PaginationResult<Todo> getTodos(TodoFilter filter, Pagination pagination) {
        StringBuilder queryBuilder = new StringBuilder("SELECT t FROM Todo t WHERE 1=1");

        // Apply filtering criteria
        if (filter.getPriority() != null) {
            queryBuilder.append(" AND t.priority = :priority");
        }
        if (filter.getDone() != null) {
            queryBuilder.append(" AND t.done = :done");
        }
        if (filter.getText() != null) {
            queryBuilder.append(" AND LOWER(t.text) LIKE LOWER(:text)");
        }

        // Apply sorting criteria
        if (filter.getSortingFields() != null) {
            List<Sorting> sortingCriteria = List.of(filter.getSortingFields().split(","))
                    .stream()
                    .filter(s -> s.isBlank() == false)
                    .map(Sorting::fromString)
                    .collect(Collectors.toList());

            if (sortingCriteria.size() > 0) {
                String sortingClause = sortingCriteria.stream()
                        .map(s -> "t." + s.getField() + (s.isAscending() ? " ASC" : " DESC"))
                        .collect(Collectors.joining(", "));

                queryBuilder.append(" ORDER BY ").append(sortingClause);
            }
        }

        TypedQuery<Todo> query = entityManager.createQuery(queryBuilder.toString(), Todo.class);

        // Set query parameters
        if (filter.getPriority() != null) {
            query.setParameter("priority", filter.getPriority());
        }
        if (filter.getDone() != null) {
            query.setParameter("done", filter.getDone());
        }
        if (filter.getText() != null) {
            query.setParameter("text", "%" + filter.getText() + "%");
        }

        // Apply pagination
        int totalRecords = query.getResultList().size();
        int totalPages = (int) Math.ceil((double) totalRecords / pagination.getSize());

        query.setFirstResult((pagination.getPage() - 1) * pagination.getSize());
        query.setMaxResults(pagination.getSize());

        List<Todo> todos = query.getResultList();

        return new PaginationResult<>(pagination.getPage(), totalRecords, totalPages, todos);
    }
}
