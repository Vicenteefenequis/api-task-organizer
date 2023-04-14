package com.vicente.task.organizer.infrastructure.task;

import com.vicente.task.organizer.MySQGatewayTest;
import com.vicente.task.organizer.domain.task.Task;
import com.vicente.task.organizer.infrastructure.task.persistence.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

@MySQGatewayTest
public class TaskMySQLGatewayTest {
    @Autowired
    private TaskMySQLGateway taskMySQLGateway;

    @Autowired
    private TaskRepository taskRepository;


    @Test
    public void givenAValidTask_whenCallsCreate_shouldReturnANewTask() {
        final var expectedName = "Task 1";
        final var expectedDescription = "Description 1";
        final var expectedIsCompleted = false;
        final var expectedDueDate = Instant.parse("2023-11-04T22:37:30.00Z");

        final var aTask = Task.newTask(expectedName, expectedDescription, expectedIsCompleted, expectedDueDate);

        Assertions.assertEquals(0, taskRepository.count());

        final var actualTask = taskMySQLGateway.create(aTask);

        Assertions.assertEquals(1, taskRepository.count());

        Assertions.assertNotNull(actualTask);
        Assertions.assertEquals(aTask.getId(), actualTask.getId());
        Assertions.assertEquals(expectedName, actualTask.getName());
        Assertions.assertEquals(expectedDescription, actualTask.getDescription());
        Assertions.assertEquals(expectedIsCompleted, actualTask.isCompleted());
        Assertions.assertEquals(expectedDueDate, actualTask.getDueDateAt());
        Assertions.assertEquals(aTask.getCreatedAt(), actualTask.getCreatedAt());
        Assertions.assertEquals(aTask.getUpdatedAt(), actualTask.getUpdatedAt());
        Assertions.assertNull(aTask.getDeletedAt());


        final var actualEntity = taskRepository.findById(actualTask.getId().getValue()).get();

        Assertions.assertEquals(aTask.getId().getValue(), actualEntity.getId());
        Assertions.assertEquals(expectedName, actualEntity.getName());
        Assertions.assertEquals(expectedDescription, actualEntity.getDescription());
        Assertions.assertEquals(expectedIsCompleted, actualEntity.isCompleted());
        Assertions.assertEquals(expectedDueDate, actualEntity.getDueDateAt());
        Assertions.assertEquals(aTask.getCreatedAt(), actualEntity.getCreatedAt());
        Assertions.assertEquals(aTask.getUpdatedAt(), actualEntity.getUpdatedAt());
        Assertions.assertNull(aTask.getDeletedAt());
    }
}
