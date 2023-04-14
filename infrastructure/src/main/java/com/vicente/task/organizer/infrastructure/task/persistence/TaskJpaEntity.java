package com.vicente.task.organizer.infrastructure.task.persistence;

import com.vicente.task.organizer.domain.task.Task;
import com.vicente.task.organizer.domain.task.TaskID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "task")
public class TaskJpaEntity {
    @Id
    private String id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", length = 4000, nullable = false)
    private String description;
    @Column(name = "completed", nullable = false)
    private boolean isCompleted;
    @Column(name = "due_date_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant dueDateAt;
    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant updatedAt;
    @Column(name = "deleted_at", columnDefinition = "DATETIME(6)")
    private Instant deletedAt;

    public TaskJpaEntity() {
    }

    private TaskJpaEntity(
            final String id,
            final String name,
            final String description,
            final boolean isCompleted,
            final Instant dueDateAt,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isCompleted = isCompleted;
        this.dueDateAt = dueDateAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }


    public static TaskJpaEntity from(final Task aTask) {
        return new TaskJpaEntity(
                aTask.getId().getValue(),
                aTask.getName(),
                aTask.getDescription(),
                aTask.isCompleted(),
                aTask.getDueDateAt(),
                aTask.getCreatedAt(),
                aTask.getUpdatedAt(),
                aTask.getDeletedAt()
        );
    }

    public Task toAggregate() {
        return Task.with(
                TaskID.from(getId()),
                getName(),
                getDescription(),
                isCompleted(),
                getDueDateAt(),
                getCreatedAt(),
                getUpdatedAt(),
                getDeletedAt()
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Instant getDueDateAt() {
        return dueDateAt;
    }

    public void setDueDateAt(Instant dueDateAt) {
        this.dueDateAt = dueDateAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }
}
