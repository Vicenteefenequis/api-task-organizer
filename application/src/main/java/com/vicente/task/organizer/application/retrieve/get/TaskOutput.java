package com.vicente.task.organizer.application.retrieve.get;

import com.vicente.task.organizer.domain.task.Task;

import java.time.Instant;

public record TaskOutput(
        String id,
        String name,
        String description,
        boolean isCompleted,
        Instant dueDateAt,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {
    public static TaskOutput with(final Task aTask) {
        return new TaskOutput(
                aTask.getId().getValue(),
                aTask.getName(),
                aTask.getDescription(),
                aTask.isCompleted(),
                aTask.getDueDateAt(),
                aTask.getCreatedAt(),
                aTask.getUpdatedAt(),
                aTask.getDeletedAt()
        );
    }
}
