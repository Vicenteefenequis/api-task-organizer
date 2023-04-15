package com.vicente.task.organizer.infrastructure.task.presenters;


import com.vicente.task.organizer.application.retrieve.list.TaskListOutput;
import com.vicente.task.organizer.infrastructure.task.models.TaskListResponse;
import com.vicente.task.organizer.infrastructure.task.models.TaskResponse;
import com.vicente.task.organizer.application.retrieve.get.TaskOutput;

public interface TaskApiPresenter {
    static TaskResponse present(final TaskOutput output) {
        return new TaskResponse(
                output.id(),
                output.name(),
                output.description(),
                output.isCompleted(),
                output.dueDateAt(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }

    static TaskListResponse present(final TaskListOutput output) {
        return new TaskListResponse(
                output.id(),
                output.name(),
                output.description(),
                output.isCompleted(),
                output.dueDateAt(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }
}