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


    @Test
    public void givenInvalidNullName_whenCallNewTaskAndValidate_shouldReceiveAnError() {
        final String expectedName = null;
        final var expectedDescription = "By the time a prospect arrives your signup page, in most cases, they`ve already evaluation";
        final var expectedDueDate = Instant.parse("2023-11-04T22:37:30.00Z");
        final var expectedErrorMessage = "'name' should not be null";
        final var actualException = Assertions.assertThrows(NotificationException.class,() -> Task.newTask(expectedName,expectedDescription,expectedDueDate));

        Assertions.assertEquals(actualException.getErrors().get(0).message(),expectedErrorMessage);
    }

    @Test
    public void givenInvalidNullDescription_whenCallNewTaskAndValidate_shouldReceiveAnError() {
        final var expectedName = "Design sign up flow";
        final String expectedDescription = null;
        final var expectedDueDate = Instant.parse("2023-11-04T22:37:30.00Z");
        final var expectedErrorMessage = "'description' should not be null";
        final var actualException = Assertions.assertThrows(NotificationException.class,() -> Task.newTask(expectedName,expectedDescription,expectedDueDate));

        Assertions.assertEquals(actualException.getErrors().get(0).message(),expectedErrorMessage);
    }


    @Test
    public void givenInvalidEmptyName_whenCallNewTaskAndValidate_shouldReceiveAnError() {
        final var expectedName = "";
        final var expectedDescription = "By the time a prospect arrives your signup page, in most cases, they`ve already evaluation";
        final var expectedDueDate = Instant.parse("2023-11-04T22:37:30.00Z");
        final var expectedErrorMessage = "'name' should not be empty";
        final var actualException = Assertions.assertThrows(NotificationException.class,() -> Task.newTask(expectedName,expectedDescription,expectedDueDate));

        Assertions.assertEquals(actualException.getErrors().get(0).message(),expectedErrorMessage);
    }

    @Test
    public void givenInvalidEmptyDescription_whenCallNewTaskAndValidate_shouldReceiveAnError() {
        final var expectedName = "Design sign up flow";
        final var expectedDescription = "";
        final var expectedDueDate = Instant.parse("2023-11-04T22:37:30.00Z");
        final var expectedErrorMessage = "'description' should not be empty";
        final var actualException = Assertions.assertThrows(NotificationException.class,() -> Task.newTask(expectedName,expectedDescription,expectedDueDate));

        Assertions.assertEquals(actualException.getErrors().get(0).message(),expectedErrorMessage);
    }

}
