package com.vicente.task.organizer.application.retrieve.list;

import com.vicente.task.organizer.application.UseCase;
import com.vicente.task.organizer.domain.pagination.Pagination;
import com.vicente.task.organizer.domain.pagination.SearchQuery;

public abstract class ListTasksUseCase extends UseCase<SearchQuery, Pagination<TaskListOutput>> {
}
