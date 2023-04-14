package com.vicente.task.organizer.application.task.delete;

import com.vicente.task.organizer.application.delete.DefaultDeleteTaskUseCase;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteTaskUseCaseTest {

    @InjectMocks
    private DefaultDeleteTaskUseCase deleteTaskUseCase;

    @Mock
    private TaskGateway taskGateway;


    @BeforeEach
    void cleanUp() {
        Mockito.reset(taskGateway);
    }

    @Test
    public void givenAValidId_whenCallDeleteTask_shouldBeOK() {
        // given
        final var aTaskId = TaskID.from("1");

        // when
        doNothing().when(taskGateway).deleteById(eq(aTaskId));

        // then
        Assertions.assertDoesNotThrow(() -> deleteTaskUseCase.execute(aTaskId.getValue()));
        verify(taskGateway, times(1)).deleteById(Mockito.eq(aTaskId));
    }


    @Test
    public void givenAValidId_whenGatewayThrowsError_shouldReturnException() {
        //given
        final var expectedId = TaskID.from("1");
        //when
        doThrow(new IllegalStateException("Gateway error")).when(taskGateway).deleteById(eq(expectedId));
        //then
        Assertions.assertThrows(IllegalStateException.class, () -> deleteTaskUseCase.execute(expectedId.getValue()));
        verify(taskGateway, times(1)).deleteById(Mockito.eq(expectedId));
    }

}
