package com.vicente.task.organizer.infrastructure.api.controllers;

import com.vicente.task.organizer.application.delete.DeleteTaskUseCase;
import com.vicente.task.organizer.application.retrieve.get.GetTaskByIdUseCase;
import com.vicente.task.organizer.application.retrieve.list.ListTasksUseCase;
import com.vicente.task.organizer.application.update.UpdateTaskCommand;
import com.vicente.task.organizer.application.update.UpdateTaskUseCase;
import com.vicente.task.organizer.domain.pagination.Pagination;
import com.vicente.task.organizer.domain.pagination.SearchQuery;
import com.vicente.task.organizer.infrastructure.api.TaskAPI;
import com.vicente.task.organizer.application.create.CreateTaskCommand;
import com.vicente.task.organizer.application.create.CreateTaskUseCase;
import com.vicente.task.organizer.infrastructure.task.models.CreateTaskRequest;
import com.vicente.task.organizer.infrastructure.task.models.TaskListResponse;
import com.vicente.task.organizer.infrastructure.task.models.TaskResponse;
import com.vicente.task.organizer.infrastructure.task.models.UpdateTaskRequest;
import com.vicente.task.organizer.infrastructure.task.presenters.TaskApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class TaskController implements TaskAPI {

    private final CreateTaskUseCase createTaskUseCase;
    private final ListTasksUseCase listTasksUseCase;
    private final GetTaskByIdUseCase getTaskByIdUseCase;
    private final UpdateTaskUseCase updateTaskUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;

    public TaskController(
            final CreateTaskUseCase createTaskUseCase,
            final ListTasksUseCase listTasksUseCase,
            final GetTaskByIdUseCase getTaskByIdUseCase,
            final UpdateTaskUseCase updateTaskUseCase,
            final DeleteTaskUseCase deleteTaskUseCase
    ) {
        this.createTaskUseCase = Objects.requireNonNull(createTaskUseCase);
        this.listTasksUseCase = Objects.requireNonNull(listTasksUseCase);
        this.getTaskByIdUseCase = Objects.requireNonNull(getTaskByIdUseCase);
        this.updateTaskUseCase = Objects.requireNonNull(updateTaskUseCase);
        this.deleteTaskUseCase = Objects.requireNonNull(deleteTaskUseCase);
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

    @Override
    public TaskResponse getTask(final String id) {
        return TaskApiPresenter.present(this.getTaskByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateTask(String id, UpdateTaskRequest request) {
        final var aCommand = UpdateTaskCommand.with(
                id,
                request.name(),
                request.description(),
                request.isCompleted() != null ? request.isCompleted() : false,
                request.dueDateAt()
        );

        final var output = this.updateTaskUseCase.execute(aCommand);

        return ResponseEntity.ok(output);
    }

    @Override
    public void deleteTask(String id) {
        this.deleteTaskUseCase.execute(id);
    }
}
