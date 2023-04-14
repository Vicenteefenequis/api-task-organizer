package com.vicente.task.organizer.application.create;

import java.time.Instant;

public record CreateTaskCommand(
        String name,
        String description,
        boolean isCompleted,
        Instant dueDate
) {
    public static CreateTaskCommand with(final String aName, final String aDescription, final boolean isCompleted, final Instant aDueDate) {
        return new CreateTaskCommand(aName, aDescription, isCompleted, aDueDate);
    }
}
