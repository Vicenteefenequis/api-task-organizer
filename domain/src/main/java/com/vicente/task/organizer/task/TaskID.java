package com.vicente.task.organizer.task;

import com.vicente.task.organizer.Identifier;

import javax.swing.plaf.PanelUI;
import java.util.Objects;
import java.util.UUID;

public class TaskID extends Identifier {
    private final String value;

    public TaskID(String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static TaskID unique() {
        return TaskID.from(UUID.randomUUID());
    }

    public static TaskID from(final UUID anId) {
        return new TaskID(anId.toString().toLowerCase());
    }

    public static TaskID from(final String anId) {
        return new TaskID(anId);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TaskID that = (TaskID) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
