package com.vicente.task.organizer.task.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record CreateTaskRequest(
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("is_completed") Boolean isCompleted,
        @JsonProperty("due_date_at") Instant dueDateAt
) {
}
