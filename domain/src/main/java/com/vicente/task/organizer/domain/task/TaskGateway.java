package com.vicente.task.organizer.domain.task;

import com.vicente.task.organizer.domain.pagination.Pagination;
import com.vicente.task.organizer.domain.pagination.SearchQuery;

import java.util.Optional;

public interface TaskGateway {
    Task create(Task task);

    void deleteById(TaskID taskId);

    Optional<Task> findById(TaskID taskId);

    Task update(Task task);

    Pagination<Task> findAll(SearchQuery aQuery);
}
