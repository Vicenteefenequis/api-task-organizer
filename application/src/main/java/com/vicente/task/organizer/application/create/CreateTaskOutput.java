package com.vicente.task.organizer.application.create;

import com.vicente.task.organizer.domain.task.Task;

public record CreateTaskOutput(String id) {
    public static CreateTaskOutput with(final String anId) {
        return new CreateTaskOutput(anId);
    }

    public static CreateTaskOutput from(final Task aTask) {
        return new CreateTaskOutput(aTask.getId().getValue());
    }
}
