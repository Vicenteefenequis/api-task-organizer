package com.vicente.task.organizer.infrastructure.configuration.usecases;

import com.vicente.task.organizer.application.update.completed.DefaultUpdateCompletedTaskUseCase;
import com.vicente.task.organizer.application.update.completed.UpdateCompletedTaskUseCase;
import com.vicente.task.organizer.domain.task.TaskGateway;
import com.vicente.task.organizer.application.create.CreateTaskUseCase;
import com.vicente.task.organizer.application.create.DefaultCreateTaskUseCase;
import com.vicente.task.organizer.application.delete.DefaultDeleteTaskUseCase;
import com.vicente.task.organizer.application.delete.DeleteTaskUseCase;
import com.vicente.task.organizer.application.retrieve.get.DefaultGetTaskByIdUseCase;
import com.vicente.task.organizer.application.retrieve.get.GetTaskByIdUseCase;
import com.vicente.task.organizer.application.retrieve.list.DefaultListTasksUseCase;
import com.vicente.task.organizer.application.retrieve.list.ListTasksUseCase;
import com.vicente.task.organizer.application.update.DefaultUpdateTaskUseCase;
import com.vicente.task.organizer.application.update.UpdateTaskUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskUseCaseConfig {
    private final TaskGateway taskGateway;

    public TaskUseCaseConfig(TaskGateway taskGateway) {
        this.taskGateway = taskGateway;
    }

    @Bean
    public CreateTaskUseCase createTaskUseCase() {
        return new DefaultCreateTaskUseCase(taskGateway);
    }

    @Bean
    public UpdateTaskUseCase updateTaskUseCase() {
        return new DefaultUpdateTaskUseCase(taskGateway);
    }

    @Bean
    public UpdateCompletedTaskUseCase updateCompletedTaskUseCase() {
        return new DefaultUpdateCompletedTaskUseCase(taskGateway);
    }

    @Bean
    public GetTaskByIdUseCase getTaskByIdUseCase() {
        return new DefaultGetTaskByIdUseCase(taskGateway);
    }

    @Bean
    public ListTasksUseCase listTasksUseCase() {
        return new DefaultListTasksUseCase(taskGateway);
    }

    @Bean
    public DeleteTaskUseCase deleteTaskUseCase() {
        return new DefaultDeleteTaskUseCase(taskGateway);
    }
}
