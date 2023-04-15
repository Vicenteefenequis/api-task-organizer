package com.vicente.task.organizer.infrastructure.api.controllers;

import com.vicente.task.organizer.application.retrieve.list.ListTasksUseCase;
import com.vicente.task.organizer.domain.pagination.Pagination;
import com.vicente.task.organizer.domain.pagination.SearchQuery;
import com.vicente.task.organizer.infrastructure.api.TaskAPI;
import com.vicente.task.organizer.application.create.CreateTaskCommand;
import com.vicente.task.organizer.application.create.CreateTaskUseCase;
import com.vicente.task.organizer.infrastructure.task.models.CreateTaskRequest;
import com.vicente.task.organizer.infrastructure.task.models.TaskListResponse;
import com.vicente.task.organizer.infrastructure.task.presenters.TaskApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
public class TaskController implements TaskAPI {

    private final CreateTaskUseCase createTaskUseCase;

    private final ListTasksUseCase listTasksUseCase;

    public TaskController(
            final CreateTaskUseCase createTaskUseCase,
            final ListTasksUseCase listTasksUseCase
    ) {
        this.createTaskUseCase = Objects.requireNonNull(createTaskUseCase);
        this.listTasksUseCase = Objects.requireNonNull(listTasksUseCase);
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

    @Override
    public Pagination<TaskListResponse> listTasks(String search, int page, int perPage, String sort, String direction) {
        final var aQuery = new SearchQuery(
                page,
                perPage,
                search,
                sort,
                direction
        );

        return this.listTasksUseCase.execute(aQuery).map(TaskApiPresenter::present);
    }
}
