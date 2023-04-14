package com.vicente.task.organizer.application.retrieve.list;

import com.vicente.task.organizer.domain.task.Task;

import java.time.Instant;

public record TaskListOutput(
        String id,
        String name,
        String description,
        boolean isCompleted,
        Instant dueDateAt,
        Instant deletedAt
) {
    public static TaskListOutput from(final Task aTask) {
        return new TaskListOutput(
                aTask.getId().getValue(),
                aTask.getName(),
                aTask.getDescription(),
                aTask.isCompleted(),
                aTask.getDueDateAt(),
                aTask.getDeletedAt()
        );
    }
}
