package com.vicente.task.organizer.infrastructure.task.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record TaskResponse(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("completed") boolean completed,
        @JsonProperty("due_date_at") Instant dueDateAt,
        @JsonProperty("created_at") Instant createdAt,
        @JsonProperty("updated_at") Instant updatedAt,
        @JsonProperty("deleted_at") Instant deletedAt
) {
}
