package com.vicente.task.organizer.task.update;

import com.vicente.task.organizer.task.Task;

import java.time.Instant;

public record UpdateTaskOutput(
        String id

) {
    public static UpdateTaskOutput from(final Task aTask) {
        return new UpdateTaskOutput(aTask.getId().getValue());
    }

}
