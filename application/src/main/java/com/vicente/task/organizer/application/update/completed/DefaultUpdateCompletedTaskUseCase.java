package com.vicente.task.organizer.application.update.completed;

import com.vicente.task.organizer.domain.exceptions.NotFoundException;
import com.vicente.task.organizer.domain.task.Task;
import com.vicente.task.organizer.domain.task.TaskGateway;
import com.vicente.task.organizer.domain.task.TaskID;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultUpdateCompletedTaskUseCase extends UpdateCompletedTaskUseCase {
    private final TaskGateway taskGateway;

    public DefaultUpdateCompletedTaskUseCase(TaskGateway taskGateway) {
        this.taskGateway = Objects.requireNonNull(taskGateway);
    }
    @Override
    public void execute(String anIn) {
        final var aTaskId = TaskID.from(anIn);

        final var aTask = this.taskGateway.findById(aTaskId)
                .orElseThrow(notFound(aTaskId));

        aTask.updateCompleted();

        this.taskGateway.update(aTask);
    }

    private static Supplier<NotFoundException> notFound(final TaskID anId) {
        return () -> NotFoundException.with(Task.class, anId);
    }
}
