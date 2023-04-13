package com.vicente.task.organizer.task.retrieve.list;

import com.vicente.task.organizer.pagination.Pagination;
import com.vicente.task.organizer.pagination.SearchQuery;
import com.vicente.task.organizer.task.Task;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class ListTaskUseCaseTest {
    @InjectMocks
    private DefaultListTasksUseCase listTaskUseCase;
    @Mock
    private TaskGateway taskGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(taskGateway);
    }


    @Test
    public void givenAValidCommandQuery_whenCallListTask_thenReturnTaskList() {
        // given
        final var expectedPage = 1;
        final var expectedPerPage = 2;
        final var expectedTerms = "";
        final var expectedSort = "name";
        final var expectedDirection = "desc";

        final var tasks = List.of(
                Task.newTask(
                        "Task 1",
                        "Description 1",
                        false,
                        Instant.parse("2021-01-01T00:00:00Z")
                ),
                Task.newTask(
                        "Task 2",
                        "Description 2",
                        false,
                        Instant.parse("2021-01-01T00:00:00Z")
                )
        );

        final var aQuery = SearchQuery.with(
                expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDirection
        );

        final var expectedPagination = new Pagination<>(expectedPage, expectedPerPage, tasks.size(), tasks);
        final var expectedItems = expectedPagination.items().stream().map(TaskListOutput::from).toList();

        final var expectedItemsCount = 2;

        Mockito.when(taskGateway.findAll(eq(aQuery)))
                .thenReturn(expectedPagination);

        final var actualResult = listTaskUseCase.execute(aQuery);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedItems, actualResult.items());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(tasks.size(), actualResult.total());
    }


    @Test
    public void givenAValidCommandQuery_whenCallListTaskEmptyResult_thenReturnEmptyList() {
        // given
        final var expectedPage = 1;
        final var expectedPerPage = 2;
        final var expectedTerms = "";
        final var expectedSort = "name";
        final var expectedDirection = "desc";

        final var tasks = List.<Task>of();

        final var aQuery = SearchQuery.with(
                expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDirection
        );

        final var expectedPagination = new Pagination<>(expectedPage, expectedPerPage, tasks.size(), tasks);
        final var expectedItems = expectedPagination.items().stream().map(TaskListOutput::from).toList();

        final var expectedItemsCount = 0;

        Mockito.when(taskGateway.findAll(eq(aQuery)))
                .thenReturn(expectedPagination);

        final var actualResult = listTaskUseCase.execute(aQuery);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedItems, actualResult.items());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(tasks.size(), actualResult.total());
    }


    @Test
    public void givenAValidQuery_whenGatewayThrowsException_shouldReturnException() {
        // given
        final var expectedPage = 1;
        final var expectedPerPage = 2;
        final var expectedTerms = "";
        final var expectedSort = "name";
        final var expectedDirection = "desc";
        final var expectedErrorMessage = "gateway error";


        Mockito.when(taskGateway.findAll(any()))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var aQuery = SearchQuery.with(
                expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDirection
        );

        final var actualException = Assertions.assertThrows(IllegalStateException.class, () -> listTaskUseCase.execute(aQuery));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

        Mockito.verify(taskGateway, Mockito.times(1)).findAll(eq(aQuery));
    }

}
