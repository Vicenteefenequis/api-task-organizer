package com.vicente.task.organizer.application.update;

import java.time.Instant;

public record UpdateTaskCommand(
        String id,
        String name,
        String description,
        boolean isCompleted,
        Instant dueDate
) {
    public static UpdateTaskCommand with(final String anId, final String aName, final String aDescription, final boolean isCompleted, final Instant aDueDate) {
        return new UpdateTaskCommand(anId, aName, aDescription, isCompleted, aDueDate);
    }
}
