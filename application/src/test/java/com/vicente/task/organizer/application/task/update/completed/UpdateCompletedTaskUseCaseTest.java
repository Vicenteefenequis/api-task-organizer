package com.vicente.task.organizer.application.task.update.completed;


import com.vicente.task.organizer.application.update.DefaultUpdateTaskUseCase;
import com.vicente.task.organizer.application.update.completed.DefaultUpdateCompletedTaskUseCase;
import com.vicente.task.organizer.domain.exceptions.NotFoundException;
import com.vicente.task.organizer.domain.task.Task;
import com.vicente.task.organizer.domain.task.TaskGateway;
import com.vicente.task.organizer.domain.task.TaskID;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateCompletedTaskUseCaseTest {

    @InjectMocks
    private DefaultUpdateCompletedTaskUseCase useCase;

    @Mock
    private TaskGateway taskGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(taskGateway);
    }

    @Test
    public void givenAValidId_whenCallsUpdateCompletedTask_shouldReturnTask() {
        // given
        final var aTaskId = TaskID.from("1");
        final var aTask = Task.newTask("ts", "any", true, Instant.now());
        final var expectedIsCompleted = false;

        // when
        when(taskGateway.findById(eq(aTaskId))).thenReturn(Optional.of(Task.with(aTask)));
        when(taskGateway.update(any())).thenAnswer(returnsFirstArg());

        // then
        Assertions.assertDoesNotThrow(() -> useCase.execute(aTaskId.getValue()));

        verify(taskGateway, times(1)).update(
                argThat(task -> Objects.equals(aTask.getId(), task.getId()) &&
                        Objects.equals(aTask.getName(), task.getName()) &&
                        Objects.equals(aTask.getDescription(), task.getDescription()) &&
                        Objects.equals(aTask.getDueDateAt(), task.getDueDateAt()) &&
                        Objects.equals(expectedIsCompleted, task.isCompleted())
                )
        );
    }

    @Test
    public void givenAInvalidId_whenCallsUpdateCompletedTask_shouldThrowNotFoundException() {
        // given
        final var aTaskId = TaskID.from("1");

        // when
        when(taskGateway.findById(eq(aTaskId))).thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(NotFoundException.class, () -> useCase.execute(aTaskId.getValue()));

        verify(taskGateway, times(0)).update(any());
    }
}
