package com.vicente.task.organizer.application.create;

import com.vicente.task.organizer.domain.exceptions.NotificationException;
import com.vicente.task.organizer.domain.task.Task;
import com.vicente.task.organizer.domain.task.TaskGateway;
import com.vicente.task.organizer.domain.validation.handler.Notification;

import java.util.Objects;

public class DefaultCreateTaskUseCase extends CreateTaskUseCase {

    private final TaskGateway taskGateway;

    public DefaultCreateTaskUseCase(TaskGateway taskGateway) {
        this.taskGateway = Objects.requireNonNull(taskGateway);
    }

    @Override
    public CreateTaskOutput execute(final CreateTaskCommand aCommand) {
        final var aName = aCommand.name();
        final var aDescription = aCommand.description();
        final var aDueDate = aCommand.dueDate();
        final var aIsCompleted = aCommand.isCompleted();

        final var notification = Notification.create();

        final var aTask = notification.validate(() -> Task.newTask(aName, aDescription, aIsCompleted, aDueDate));

        if (notification.hasError()) {
            throw new NotificationException("Could not create Aggregate Task", notification);
        }


        return CreateTaskOutput.from(this.taskGateway.create(aTask));
    }
}
