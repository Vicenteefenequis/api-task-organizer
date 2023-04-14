package com.vicente.task.organizer.application.task.update;

import com.vicente.task.organizer.domain.exceptions.NotFoundException;
import com.vicente.task.organizer.domain.exceptions.NotificationException;
import com.vicente.task.organizer.domain.task.Task;
import com.vicente.task.organizer.domain.task.TaskGateway;
import com.vicente.task.organizer.domain.task.TaskID;
import com.vicente.task.organizer.application.update.DefaultUpdateTaskUseCase;
import com.vicente.task.organizer.application.update.UpdateTaskCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateTaskUseCaseTest {
    @InjectMocks
    private DefaultUpdateTaskUseCase useCase;

    @Mock
    private TaskGateway taskGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(taskGateway);
    }


    @Test
    public void givenAValidCommand_whenCallsUpdateTask_shouldReturnTask() {
        final var expectedName = "Task 1";
        final var expectedDescription = "Description 1";
        final var expectedDueDate = Instant.parse("2024-11-04T22:37:30.00Z");
        final var expectedIsCompleted = false;

        final var aTask = Task.newTask("ts", "any", true, Instant.now());


        final var aCommand = UpdateTaskCommand.with(aTask.getId().getValue(), expectedName, expectedDescription, expectedIsCompleted, expectedDueDate);

        when(taskGateway.update(any())).thenAnswer(returnsFirstArg());

        when(taskGateway.findById(eq(aTask.getId()))).thenReturn(Optional.of(Task.with(aTask)));

        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(aTask.getId().getValue(), actualOutput.id());

        verify(taskGateway, times(1)).update(argThat(task ->
                        Objects.equals(aTask.getId(), task.getId()) &&
                                Objects.equals(expectedName, task.getName()) &&
                                Objects.equals(expectedDescription, task.getDescription()) &&
                                Objects.equals(expectedDueDate, task.getDueDateAt()) &&
                                Objects.equals(expectedIsCompleted, task.isCompleted()) &&
                                Objects.equals(aTask.getCreatedAt(), task.getCreatedAt()) &&
                                aTask.getUpdatedAt().isBefore(task.getUpdatedAt()) &&
                                Objects.equals(aTask.getDeletedAt(), task.getDeletedAt())
                )
        );
    }


    @Test
    public void givenAInvalidNullNameAndDescription_whenCallsUpdateTask_shouldThrowNotificationError() {
        final String expectedName = null;
        final String expectedDescription = null;
        final var expectedDueDate = Instant.parse("2024-11-04T22:37:30.00Z");
        final var expectedIsCompleted = false;
        final var expectedNameError = "'name' should not be null";
        final var expectedDescriptionError = "'description' should not be null";
        final var expectedErrorCount = 2;

        final var aTask = Task.newTask("ts", "any", true, Instant.now());


        final var aCommand = UpdateTaskCommand.with(aTask.getId().getValue(), expectedName, expectedDescription, expectedIsCompleted, expectedDueDate);


        when(taskGateway.findById(eq(aTask.getId()))).thenReturn(Optional.of(Task.with(aTask)));

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedNameError, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedDescriptionError, actualException.getErrors().get(1).message());
        verify(taskGateway, times(0)).update(any());
    }

    @Test
    public void givenAInvalidEmptyNameAndDescription_whenCallsUpdateTask_shouldThrowNotificationError() {
        final String expectedName = " ";
        final String expectedDescription = " ";
        final var expectedDueDate = Instant.parse("2024-11-04T22:37:30.00Z");
        final var expectedIsCompleted = false;
        final var expectedNameError = "'name' should not be empty";
        final var expectedDescriptionError = "'description' should not be empty";
        final var expectedErrorCount = 2;

        final var aTask = Task.newTask("ts", "any", true, Instant.now());


        final var aCommand = UpdateTaskCommand.with(aTask.getId().getValue(), expectedName, expectedDescription, expectedIsCompleted, expectedDueDate);


        when(taskGateway.findById(eq(aTask.getId()))).thenReturn(Optional.of(Task.with(aTask)));

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedNameError, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedDescriptionError, actualException.getErrors().get(1).message());
        verify(taskGateway, times(0)).update(any());
    }


    @Test
    public void givenACommandWithInvalidID_whenCallsUpdateTask_shouldThrowNotFoundException() {
        final var expectedName = "Task 1";
        final var expectedDescription = "Description 1";
        final var expectedDueDate = Instant.parse("2024-11-04T22:37:30.00Z");
        final var expectedIsCompleted = false;
        final var expectedId = "123";
        final var expectedErrorMessage = "Task with ID 123 was not found";

        final var aCommand = UpdateTaskCommand.with(expectedId, expectedName, expectedDescription, expectedIsCompleted, expectedDueDate);

        when(taskGateway.findById(Mockito.eq(TaskID.from(expectedId)))).thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(NotFoundException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

        verify(taskGateway, times(1)).findById(eq(TaskID.from(expectedId)));
        verify(taskGateway, times(0)).update(any());
    }


    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnAException() {
        final var aTask = Task.newTask("ts", "any", true, Instant.now());

        final var expectedName = "Task 1";
        final var expectedDescription = "Description 1";
        final var expectedDueDate = Instant.parse("2024-11-04T22:37:30.00Z");
        final var expectedIsCompleted = false;
        final var expectedId = aTask.getId();
        final var expectedErrorMessage = "Gateway error";

        final var aCommand = UpdateTaskCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsCompleted, expectedDueDate);

        when(taskGateway.findById(Mockito.eq(TaskID.from(expectedId.getValue())))).thenReturn(Optional.of(Task.with(aTask)));

        when(taskGateway.update(any())).thenThrow(new IllegalStateException(expectedErrorMessage));

        final var actualException = Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

        verify(taskGateway, times(1)).update(argThat(task ->
                        Objects.equals(expectedId, task.getId()) &&
                                Objects.equals(expectedName, task.getName()) &&
                                Objects.equals(expectedDescription, task.getDescription()) &&
                                Objects.equals(expectedDueDate, task.getDueDateAt()) &&
                                Objects.equals(expectedIsCompleted, task.isCompleted()) &&
                                Objects.equals(aTask.getCreatedAt(), task.getCreatedAt()) &&
                                aTask.getUpdatedAt().isBefore(task.getUpdatedAt()) &&
                                Objects.equals(aTask.getDeletedAt(), task.getDeletedAt())
                )
        );
    }


}
