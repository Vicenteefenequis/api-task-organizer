package com.vicente.task.organizer.infrastructure.task;

import com.vicente.task.organizer.MySQGatewayTest;
import com.vicente.task.organizer.domain.task.Task;
import com.vicente.task.organizer.domain.task.TaskID;
import com.vicente.task.organizer.infrastructure.task.persistence.TaskJpaEntity;
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

    @Test
    public void givenAValidTask_whenCallsUpdate_shouldReturnAnUpdatedTask() {
        final var expectedName = "Task 1";
        final var expectedDescription = "Description 1";
        final var expectedIsCompleted = false;
        final var expectedDueDate = Instant.parse("2023-11-04T22:37:30.00Z");

        final var aTask = Task.newTask("ANY_TASK", "ANY_DESCRIPTION", false, expectedDueDate);

        Assertions.assertEquals(0, taskRepository.count());

        taskRepository.saveAndFlush(TaskJpaEntity.from(aTask));

        Assertions.assertEquals(1, taskRepository.count());

        final var aUpdateTask = aTask.clone().update(expectedName, expectedDescription, expectedIsCompleted, expectedDueDate);

        final var actualTask = taskMySQLGateway.update(aUpdateTask);

        Assertions.assertEquals(1, taskRepository.count());

        Assertions.assertNotNull(actualTask);
        Assertions.assertEquals(aUpdateTask.getId(), actualTask.getId());
        Assertions.assertEquals(expectedName, actualTask.getName());
        Assertions.assertEquals(expectedDescription, actualTask.getDescription());
        Assertions.assertEquals(expectedIsCompleted, actualTask.isCompleted());
        Assertions.assertEquals(expectedDueDate, actualTask.getDueDateAt());
        Assertions.assertEquals(aUpdateTask.getCreatedAt(), actualTask.getCreatedAt());
        Assertions.assertTrue(aTask.getUpdatedAt().isBefore(aUpdateTask.getUpdatedAt()));
        Assertions.assertNull(aUpdateTask.getDeletedAt());
    }

    @Test
    public void givenAPrePersistedAnValidTaskId_whenTryToDeleteIt_shouldDeleteTask() {
        final var aTask = Task.newTask(
                "Task 1",
                "Description 1",
                false,
                Instant.parse("2023-11-04T22:37:30.00Z")
        );

        Assertions.assertEquals(0, taskRepository.count());

        taskRepository.saveAndFlush(TaskJpaEntity.from(aTask));

        Assertions.assertEquals(1, taskRepository.count());

        taskMySQLGateway.deleteById(aTask.getId());

        Assertions.assertEquals(0, taskRepository.count());
    }

    @Test
    public void givenInvalidTaskId_whenTryToDeleteIt_shouldDeleteTask() {
        Assertions.assertEquals(0, taskRepository.count());

        taskMySQLGateway.deleteById(TaskID.from("INVALID_ID"));

        Assertions.assertEquals(0, taskRepository.count());
    }

    @Test
    public void givenAPrePersistedTaskAnValidTaskID_whenCallsFindById_shouldReturnCategory() {
        final var expectedName = "Task 1";
        final var expectedDescription = "Description 1";
        final var expectedIsCompleted = false;
        final var expectedDueDate = Instant.parse("2023-11-04T22:37:30.00Z");

        final var aTask = Task.newTask(expectedName, expectedDescription, expectedIsCompleted, expectedDueDate);

        Assertions.assertEquals(0, taskRepository.count());

        taskRepository.saveAndFlush(TaskJpaEntity.from(aTask));

        Assertions.assertEquals(1, taskRepository.count());

        final var actualTask = taskMySQLGateway.findById(aTask.getId()).get();

        Assertions.assertNotNull(actualTask);
        Assertions.assertEquals(aTask.getId(), actualTask.getId());
        Assertions.assertEquals(expectedName, actualTask.getName());
        Assertions.assertEquals(expectedDescription, actualTask.getDescription());
        Assertions.assertEquals(expectedIsCompleted, actualTask.isCompleted());
        Assertions.assertEquals(expectedDueDate, actualTask.getDueDateAt());
        Assertions.assertEquals(aTask.getCreatedAt(), actualTask.getCreatedAt());
        Assertions.assertEquals(aTask.getUpdatedAt(), actualTask.getUpdatedAt());
        Assertions.assertNull(aTask.getDeletedAt());
    }

    @Test
    public void givenValidTaskNotStorage_whenCallsFindByID_shouldReturnEmpty() {
        Assertions.assertEquals(0, taskRepository.count());

        final var actualTask = taskMySQLGateway.findById(TaskID.from("INVALID_ID"));

        Assertions.assertTrue(actualTask.isEmpty());
    }
}
