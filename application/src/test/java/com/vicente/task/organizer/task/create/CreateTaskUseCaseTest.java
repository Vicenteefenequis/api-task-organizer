package com.vicente.task.organizer.task.create;

import com.vicente.task.organizer.exceptions.DomainException;
import com.vicente.task.organizer.exceptions.NotificationException;
import com.vicente.task.organizer.task.TaskGateway;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateTaskUseCaseTest {
    @InjectMocks
    private DefaultCreateTaskUseCase useCase;

    @Mock
    private TaskGateway taskGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(taskGateway);
    }

    //1. Teste do caminho feliz
    //2. Teste passando uma propriedade invalida(name,description,dueDate)

    @Test
    public void givenAValidCommand_whenCallsCreateTask_shouldReturnTask() {
        final var expectedName = "Task 1";
        final var expectedDescription = "Description 1";
        final var expectedDueDate = Instant.parse("2024-11-04T22:37:30.00Z");
        final var expectedIsCompleted = false;

        final var aCommand = CreateTaskCommand.with(expectedName, expectedDescription, expectedIsCompleted, expectedDueDate);

        when(taskGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        verify(taskGateway, times(1)).create(argThat(task ->
                Objects.equals(expectedName, task.getName()) &&
                        Objects.equals(expectedDescription, task.getDescription()) &&
                        Objects.equals(expectedDueDate, task.getDueDateAt()) &&
                        Objects.equals(expectedIsCompleted, task.isCompleted())
        ));
    }


    @Test
    public void givenAInvalidEmptyName_whenCallsCreateTask_shouldReturnDomainException() {
        final var expectedName = " ";
        final var expectedDescription = "Description 1";
        final var expectedDueDate = Instant.parse("2024-11-04T22:37:30.00Z");
        final var expectedIsCompleted = false;
        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedErrorCount = 1;

        final var aCommand = CreateTaskCommand.with(expectedName, expectedDescription, expectedIsCompleted, expectedDueDate);

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> useCase.execute(aCommand));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());

        verify(taskGateway, times(0)).create(any());
    }


    @Test
    public void givenAInvalidNullName_whenCallsCreateTask_shouldReturnDomainException() {
        final String expectedName = null;
        final var expectedDescription = "Description 1";
        final var expectedDueDate = Instant.parse("2024-11-04T22:37:30.00Z");
        final var expectedIsCompleted = false;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand = CreateTaskCommand.with(expectedName, expectedDescription, expectedIsCompleted, expectedDueDate);

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> useCase.execute(aCommand));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());

        verify(taskGateway, times(0)).create(any());
    }


    @Test
    public void givenAInvalidEmptyDescription_whenCallsCreateTask_shouldReturnDomainException() {
        final var expectedName = "Task 1";
        final var expectedDescription = " ";
        final var expectedDueDate = Instant.parse("2024-11-04T22:37:30.00Z");
        final var expectedIsCompleted = false;
        final var expectedErrorMessage = "'description' should not be empty";
        final var expectedErrorCount = 1;

        final var aCommand = CreateTaskCommand.with(expectedName, expectedDescription, expectedIsCompleted, expectedDueDate);

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> useCase.execute(aCommand));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());

        verify(taskGateway, times(0)).create(any());
    }


    @Test
    public void givenAInvalidNullDescription_whenCallsCreateTask_shouldReturnDomainException() {
        final var expectedName = "Task 1";
        final String expectedDescription = null;
        final var expectedDueDate = Instant.parse("2024-11-04T22:37:30.00Z");
        final var expectedIsCompleted = false;
        final var expectedErrorMessage = "'description' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand = CreateTaskCommand.with(expectedName, expectedDescription, expectedIsCompleted, expectedDueDate);

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> useCase.execute(aCommand));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());

        verify(taskGateway, times(0)).create(any());
    }

}
