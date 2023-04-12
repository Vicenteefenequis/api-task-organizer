package com.vicente.task.organizer.task;

import com.vicente.task.organizer.AggregateRoot;
import com.vicente.task.organizer.exceptions.NotificationException;
import com.vicente.task.organizer.utils.InstantUtils;
import com.vicente.task.organizer.validation.ValidationHandler;
import com.vicente.task.organizer.validation.handler.Notification;

import java.time.Instant;

public class Task extends AggregateRoot<TaskID> {
    private String name;
    private String description;
    private Instant dueDateAt;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    protected Task(
            final TaskID anId,
            final String aName,
            final String aDescription,
            final Instant aDueDateAt,
            final Instant aCreatedAt,
            final Instant aUpdatedAt,
            final Instant aDeletedAt
    ) {
        super(anId);
        this.name = aName;
        this.description = aDescription;
        this.dueDateAt = aDueDateAt;
        this.createdAt = aCreatedAt;
        this.updatedAt = aUpdatedAt;
        this.deletedAt = aDeletedAt;

        selfValidate();
    }


    public static Task newTask(final String aName, final String aDescription, final Instant aDueDateAt) {
        final var anId = TaskID.unique();
        final var now = InstantUtils.now();

        return new Task(
                anId,
                aName,
                aDescription,
                aDueDateAt,
                now,
                now,
                null
        );
    }

    public static Task with(
            final TaskID anId,
            final String aName,
            final String aDescription,
            final Instant aDueDateAt,
            final Instant aCreatedAt,
            final Instant aUpdatedAt,
            final Instant aDeletedAt
    ) {
        return new Task(
                anId,
                aName,
                aDescription,
                aDueDateAt,
                aCreatedAt,
                aUpdatedAt,
                aDeletedAt
        );
    }

    public static Task with(final Task aTask) {
        return new Task(
                aTask.id,
                aTask.name,
                aTask.description,
                aTask.dueDateAt,
                aTask.createdAt,
                aTask.updatedAt,
                aTask.deletedAt
        );
    }

    public Task update(final String aName, final String aDescription, final Instant aDueDateAt) {
        this.name = aName;
        this.description = aDescription;
        this.dueDateAt = aDueDateAt;
        this.updatedAt = InstantUtils.now();
        selfValidate();
        return this;
    }

    @Override
    public void validate(final ValidationHandler aHandler) {
        new TaskValidator(this, aHandler).validate();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Instant getDueDateAt() {
        return dueDateAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    private void selfValidate() {
        final var notification = Notification.create();
        validate(notification);

        if (notification.hasError()) {
            throw new NotificationException("", notification);
        }
    }

}
