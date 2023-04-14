package com.vicente.task.organizer.application.task.retrieve.get;

import com.vicente.task.organizer.domain.exceptions.NotFoundException;
import com.vicente.task.organizer.domain.task.Task;
import com.vicente.task.organizer.domain.task.TaskGateway;
import com.vicente.task.organizer.domain.task.TaskID;
import com.vicente.task.organizer.application.retrieve.get.DefaultGetTaskByIdUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class GetTaskByIdUseCaseTest {

    @InjectMocks
    private DefaultGetTaskByIdUseCase getTaskByIdUseCase;

    @Mock
    private TaskGateway taskGateway;


    @BeforeEach
    void cleanUp() {
        Mockito.reset(taskGateway);
    }


    @Test
    public void givenAValidCommand_whenCallGetTaskById_thenReturnTask() {
        // given
        final var expectedName = "Task 1";
        final var expectedDescription = "Description 1";
        final var expectedIsCompleted = false;
        final var expectedDueDate = Instant.parse("2021-01-01T00:00:00Z");

        final var aTask = Task.newTask(
                expectedName,
                expectedDescription,
                expectedIsCompleted,
                expectedDueDate
        );

        final var expectedId = aTask.getId();

        //when

        Mockito.when(taskGateway.findById(Mockito.eq(expectedId)))
                .thenReturn(Optional.of(Task.with(aTask)));

        final var actualOutput = getTaskByIdUseCase.execute(expectedId.getValue());

        //then

        verify(taskGateway, times(1))
                .findById(Mockito.eq(aTask.getId()));

        Assertions.assertEquals(aTask.getId().getValue(), actualOutput.id());
        Assertions.assertEquals(expectedName, actualOutput.name());
        Assertions.assertEquals(expectedDescription, actualOutput.description());
        Assertions.assertEquals(expectedIsCompleted, actualOutput.isCompleted());
        Assertions.assertEquals(expectedDueDate, actualOutput.dueDateAt());
        Assertions.assertEquals(aTask.getCreatedAt(), actualOutput.createdAt());
        Assertions.assertEquals(aTask.getUpdatedAt(), actualOutput.updatedAt());
        Assertions.assertNull(actualOutput.deletedAt());

    }


    @Test
    public void givenAnInvalidId_whenCallGetTaskById_thenReturnNotificationException() {
        // given
        final var expectedId = "123";
        final var expectedErrorMessage = "Task with ID 123 was not found";
        //when

        Mockito.when(taskGateway.findById(Mockito.eq(TaskID.from(expectedId))))
                .thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(NotFoundException.class, () -> getTaskByIdUseCase.execute(expectedId));

        //then

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

        verify(taskGateway, times(1))
                .findById(TaskID.from(expectedId));
    }

    @Test
    public void givenAValidId_whenGatewayError_thenReturnException() {
        // given
        final var expectedId = TaskID.from("123");
        final var expectedErrorMessage = "Gateway Error";
        //when

        Mockito.when(taskGateway.findById(Mockito.eq(expectedId)))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var actualException = Assertions.assertThrows(IllegalStateException.class, () -> getTaskByIdUseCase.execute(expectedId.getValue()));
        //then

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

        verify(taskGateway, times(1))
                .findById(expectedId);
    }


}
