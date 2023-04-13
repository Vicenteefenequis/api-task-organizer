package com.vicente.task.organizer.task.retrieve.get;

import com.vicente.task.organizer.exceptions.NotFoundException;
import com.vicente.task.organizer.task.Task;
import com.vicente.task.organizer.task.TaskGateway;
import com.vicente.task.organizer.task.TaskID;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultGetTaskByIdUseCase extends GetTaskByIdUseCase {
    private final TaskGateway taskGateway;

    public DefaultGetTaskByIdUseCase(TaskGateway taskGateway) {
        this.taskGateway = Objects.requireNonNull(taskGateway);
    }

    @Override
    public TaskOutput execute(String anIn) {
        final var aTaskId = TaskID.from(anIn);
        return taskGateway.findById(aTaskId)
                .map(TaskOutput::with)
                .orElseThrow(notFound(aTaskId));
    }

    private static Supplier<NotFoundException> notFound(final TaskID anId) {
        return () -> NotFoundException.with(Task.class, anId);
    }
}
