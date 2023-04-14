package com.vicente.task.organizer.task.delete;

import com.vicente.task.organizer.task.TaskGateway;
import com.vicente.task.organizer.task.TaskID;

import java.util.Objects;

public class DefaultDeleteTaskUseCase extends DeleteTaskUseCase {

    private final TaskGateway taskGateway;

    public DefaultDeleteTaskUseCase(TaskGateway taskGateway) {
        this.taskGateway = Objects.requireNonNull(taskGateway);
    }

    @Override
    public void execute(String anId) {
        taskGateway.deleteById(TaskID.from(anId));
    }
}
