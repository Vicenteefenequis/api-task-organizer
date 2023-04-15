package com.vicente.task.organizer.domain.task;

import com.vicente.task.organizer.domain.exceptions.NotificationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class TaskTest {


    @Test
    public void givenAValidParams_whenCallNewTask_thenInstantiateATask() {
        final var expectedName = "Design sign up flow";
        final var expectedDescription = "By the time a prospect arrives your signup page, in most cases, they`ve already evaluation";
        final var expectedDueDate = Instant.parse("2023-11-04T22:37:30.00Z");
        final var expectedIsCompleted = false;

        final var actualTask = Task.newTask(expectedName, expectedDescription, expectedIsCompleted, expectedDueDate);

        Assertions.assertNotNull(actualTask);
        Assertions.assertNotNull(actualTask.getId());
        Assertions.assertEquals(expectedName, actualTask.getName());
        Assertions.assertEquals(expectedDescription, actualTask.getDescription());
        Assertions.assertFalse(actualTask.isCompleted());
        Assertions.assertEquals(expectedDueDate, actualTask.getDueDateAt());
        Assertions.assertNotNull(actualTask.getCreatedAt());
        Assertions.assertNotNull(actualTask.getUpdatedAt());
        Assertions.assertNull(actualTask.getDeletedAt());
    }


    @Test
    public void givenInvalidNullName_whenCallNewTaskAndValidate_shouldReceiveAnError() {
        final String expectedName = null;
        final var expectedDescription = "By the time a prospect arrives your signup page, in most cases, they`ve already evaluation";
        final var expectedIsCompleted = false;
        final var expectedDueDate = Instant.parse("2023-11-04T22:37:30.00Z");
        final var expectedErrorMessage = "'name' should not be null";
        final var actualException = Assertions.assertThrows(NotificationException.class, () -> Task.newTask(expectedName, expectedDescription, expectedIsCompleted, expectedDueDate));

        Assertions.assertEquals(actualException.getErrors().get(0).message(), expectedErrorMessage);
    }

    @Test
    public void givenInvalidNullDescription_whenCallNewTaskAndValidate_shouldReceiveAnError() {
        final var expectedName = "Design sign up flow";
        final String expectedDescription = null;
        final var expectedIsCompleted = false;
        final var expectedDueDate = Instant.parse("2023-11-04T22:37:30.00Z");
        final var expectedErrorMessage = "'description' should not be null";
        final var actualException = Assertions.assertThrows(NotificationException.class, () -> Task.newTask(expectedName, expectedDescription, expectedIsCompleted, expectedDueDate));

        Assertions.assertEquals(actualException.getErrors().get(0).message(), expectedErrorMessage);
    }


    @Test
    public void givenInvalidEmptyName_whenCallNewTaskAndValidate_shouldReceiveAnError() {
        final var expectedName = "";
        final var expectedDescription = "By the time a prospect arrives your signup page, in most cases, they`ve already evaluation";
        final var expectedIsCompleted = false;
        final var expectedDueDate = Instant.parse("2023-11-04T22:37:30.00Z");
        final var expectedErrorMessage = "'name' should not be empty";
        final var actualException = Assertions.assertThrows(NotificationException.class, () -> Task.newTask(expectedName, expectedDescription, expectedIsCompleted, expectedDueDate));

        Assertions.assertEquals(actualException.getErrors().get(0).message(), expectedErrorMessage);
    }

    @Test
    public void givenInvalidEmptyDescription_whenCallNewTaskAndValidate_shouldReceiveAnError() {
        final var expectedName = "Design sign up flow";
        final var expectedDescription = "";
        final var expectedIsCompleted = false;
        final var expectedDueDate = Instant.parse("2023-11-04T22:37:30.00Z");
        final var expectedErrorMessage = "'description' should not be empty";
        final var actualException = Assertions.assertThrows(NotificationException.class, () -> Task.newTask(expectedName, expectedDescription, expectedIsCompleted, expectedDueDate));

        Assertions.assertEquals(actualException.getErrors().get(0).message(), expectedErrorMessage);
    }


    @Test
    public void givenAValidParams_whenCallUpdateTask_thenReturnTaskUpdated() {
        final var expectedName = "Design sign up flow";
        final var expectedDescription = "By the time a prospect arrives your signup page, in most cases, they`ve already evaluation";
        final var expectedDueDate = Instant.parse("2024-11-04T22:37:30.00Z");
        final var expectedIsCompleted = false;

        final var actualTask = Task.newTask("any_name", "any_description", false, Instant.parse("2023-11-04T22:37:30.00Z"));

        final var actualCreatedAt = actualTask.getCreatedAt();
        final var actualUpdatedAt = actualTask.getUpdatedAt();

        actualTask.update(
                expectedName,
                expectedDescription,
                expectedIsCompleted,
                expectedDueDate
        );

        Assertions.assertNotNull(actualTask);
        Assertions.assertNotNull(actualTask.getId());
        Assertions.assertEquals(expectedName, actualTask.getName());
        Assertions.assertEquals(expectedDescription, actualTask.getDescription());
        Assertions.assertEquals(expectedIsCompleted,actualTask.isCompleted());
        Assertions.assertEquals(expectedDueDate, actualTask.getDueDateAt());
        Assertions.assertEquals(actualCreatedAt, actualTask.getCreatedAt());
        Assertions.assertTrue(actualUpdatedAt.isBefore(actualTask.getUpdatedAt()));
        Assertions.assertNull(actualTask.getDeletedAt());

    }


    @Test
    public void givenAInvalidNullName_whenCallUpdateTask_thenThrowNotificationError() {
        final String expectedName = null;
        final var expectedDescription = "By the time a prospect arrives your signup page, in most cases, they`ve already evaluation";
        final var expectedIsCompleted = true;
        final var expectedDueDate = Instant.parse("2024-11-04T22:37:30.00Z");
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;
        final var actualTask = Task.newTask("any_name", "any_description",false, Instant.parse("2023-11-04T22:37:30.00Z"));

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> actualTask.update(
                expectedName,
                expectedDescription,
                expectedIsCompleted,
                expectedDueDate
        ));

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());

    }


    @Test
    public void givenAInvalidNullDescription_whenCallUpdateTask_thenThrowNotificationError() {
        final var expectedName = "Design sign up flow";
        final String expectedDescription = null;
        final var expectedIsCompleted = true;
        final var expectedDueDate = Instant.parse("2024-11-04T22:37:30.00Z");
        final var expectedErrorMessage = "'description' should not be null";
        final var expectedErrorCount = 1;
        final var actualTask = Task.newTask("any_name", "any_description",false, Instant.parse("2023-11-04T22:37:30.00Z"));

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> actualTask.update(
                expectedName,
                expectedDescription,
                expectedIsCompleted,
                expectedDueDate
        ));

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());

    }


    @Test
    public void givenAInvalidNullNameAndDescription_whenCallUpdateTask_thenThrowNotificationError() {
        final String expectedName = null;
        final String expectedDescription = null;
        final var expectedIsCompleted = true;
        final var expectedDueDate = Instant.parse("2024-11-04T22:37:30.00Z");
        final var expectedErrorMessageName = "'name' should not be null";
        final var expectedErrorMessageDescription = "'description' should not be null";
        final var expectedErrorCount = 2;
        final var actualTask = Task.newTask("any_name", "any_description", false,Instant.parse("2023-11-04T22:37:30.00Z"));

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> actualTask.update(
                expectedName,
                expectedDescription,
                expectedIsCompleted,
                expectedDueDate
        ));

        Assertions.assertEquals(expectedErrorMessageName, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorMessageDescription, actualException.getErrors().get(1).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());

    }

    @Test
    public void givenAValidParams_whenCallUpdateCompleted_thenTaskChangeCompleted() {
        final var expectedIsCompleted = true;
        final var actualTask = Task.newTask("any_name", "any_description", false, Instant.parse("2023-11-04T22:37:30.00Z"));

        actualTask.updateCompleted();

        Assertions.assertNotNull(actualTask);
        Assertions.assertNotNull(actualTask.getId());
        Assertions.assertEquals(expectedIsCompleted, actualTask.isCompleted());
        Assertions.assertNull(actualTask.getDeletedAt());
    }


}
