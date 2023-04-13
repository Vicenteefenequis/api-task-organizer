package com.vicente.task.organizer.task.retrieve.list;

import com.vicente.task.organizer.pagination.Pagination;
import com.vicente.task.organizer.pagination.SearchQuery;
import com.vicente.task.organizer.task.TaskGateway;

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
