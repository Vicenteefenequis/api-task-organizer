package com.vicente.task.organizer.infrastructure.api;

import com.vicente.task.organizer.infrastructure.task.models.CreateTaskRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "tasks")
@Tag(name = "Tasks")
public interface TaskAPI {
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created"),
            @ApiResponse(responseCode = "422", description = "A Validation error was thrown"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<?> createCategory(@RequestBody CreateTaskRequest request);


}
