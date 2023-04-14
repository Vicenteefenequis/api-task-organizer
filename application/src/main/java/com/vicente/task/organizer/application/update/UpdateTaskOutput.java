package com.vicente.task.organizer.application.update;

import com.vicente.task.organizer.domain.task.Task;

public record UpdateTaskOutput(
        String id

) {
    public static UpdateTaskOutput from(final Task aTask) {
        return new UpdateTaskOutput(aTask.getId().getValue());
    }

}
