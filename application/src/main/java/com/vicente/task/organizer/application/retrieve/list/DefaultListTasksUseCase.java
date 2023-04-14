package com.vicente.task.organizer.application.retrieve.list;

import com.vicente.task.organizer.domain.pagination.Pagination;
import com.vicente.task.organizer.domain.pagination.SearchQuery;
import com.vicente.task.organizer.domain.task.TaskGateway;

import java.util.Objects;

public class DefaultListTasksUseCase extends ListTasksUseCase {

    private final TaskGateway taskGateway;

    public DefaultListTasksUseCase(TaskGateway taskGateway) {
        this.taskGateway = Objects.requireNonNull(taskGateway);
    }

    @Override
    public Pagination<TaskListOutput> execute(SearchQuery aQuery) {
        return taskGateway.findAll(aQuery).map(TaskListOutput::from);
    }
}
