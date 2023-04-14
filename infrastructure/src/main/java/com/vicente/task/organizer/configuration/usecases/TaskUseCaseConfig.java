package com.vicente.task.organizer.configuration.usecases;

import com.vicente.task.organizer.task.TaskGateway;
import com.vicente.task.organizer.task.create.CreateTaskUseCase;
import com.vicente.task.organizer.task.create.DefaultCreateTaskUseCase;
import com.vicente.task.organizer.task.delete.DefaultDeleteTaskUseCase;
import com.vicente.task.organizer.task.delete.DeleteTaskUseCase;
import com.vicente.task.organizer.task.retrieve.get.DefaultGetTaskByIdUseCase;
import com.vicente.task.organizer.task.retrieve.get.GetTaskByIdUseCase;
import com.vicente.task.organizer.task.retrieve.list.DefaultListTasksUseCase;
import com.vicente.task.organizer.task.retrieve.list.ListTasksUseCase;
import com.vicente.task.organizer.task.update.DefaultUpdateTaskUseCase;
import com.vicente.task.organizer.task.update.UpdateTaskUseCase;
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
