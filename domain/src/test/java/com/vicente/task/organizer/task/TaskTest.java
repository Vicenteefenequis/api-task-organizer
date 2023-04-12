package com.vicente.task.organizer.task;

import com.vicente.task.organizer.exceptions.NotificationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class TaskTest {


    @Test
    public void givenAValidParams_whenCallNewTask_thenInstantiateATask() {
        final var expectedName = "Design sign up flow";
        final var expectedDescription = "By the time a prospect arrives your signup page, in most cases, they`ve already evaluation";
        final var expectedDueDate = Instant.parse("2023-11-04T22:37:30.00Z");

        final var actualTask = Task.newTask(expectedName,expectedDescription,expectedDueDate);

        Assertions.assertNotNull(actualTask);
        Assertions.assertNotNull(actualTask.getId());
        Assertions.assertEquals(expectedName,actualTask.getName());
        Assertions.assertEquals(expectedDescription,actualTask.getDescription());
        Assertions.assertEquals(expectedDueDate,actualTask.getDueDateAt());
    }




}
