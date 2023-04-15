package com.vicente.task.organizer.infrastructure.api.controllers;

import com.vicente.task.organizer.infrastructure.api.TaskAPI;
import com.vicente.task.organizer.application.create.CreateTaskCommand;
import com.vicente.task.organizer.application.create.CreateTaskUseCase;
import com.vicente.task.organizer.infrastructure.task.models.CreateTaskRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
public class TaskController implements TaskAPI {

    private final CreateTaskUseCase createTaskUseCase;

    public TaskController(CreateTaskUseCase createTaskUseCase) {
        this.createTaskUseCase = Objects.requireNonNull(createTaskUseCase);
    }

    @Override
    public ResponseEntity<?> createCategory(CreateTaskRequest aInput) {
        final var aCommand = CreateTaskCommand.with(
                aInput.name(),
                aInput.description(),
                aInput.isCompleted(),
                aInput.dueDateAt()
        );
        final var output = this.createTaskUseCase.execute(aCommand);

        return ResponseEntity.created(URI.create("/tasks/" + output.id())).body(output);
    }
}