package com.vicente.task.organizer.task;

import com.vicente.task.organizer.pagination.Pagination;
import com.vicente.task.organizer.pagination.SearchQuery;

import java.util.Optional;

public interface TaskGateway {
    Task create(Task task);

    void deleteById(TaskID taskId);

    Optional<Task> findById(TaskID taskId);

    Task update(Task task);

    Pagination<Task> findAll(SearchQuery aQuery);
}
