package com.vicente.task.organizer.task.presenters;


import com.vicente.task.organizer.task.models.TaskListResponse;
import com.vicente.task.organizer.task.models.TaskResponse;
import com.vicente.task.organizer.task.retrieve.get.TaskOutput;

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

    static TaskListResponse present(final TaskOutput output, final String id) {
        return new TaskListResponse(
                id,
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