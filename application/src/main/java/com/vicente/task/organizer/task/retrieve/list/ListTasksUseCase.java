package com.vicente.task.organizer.task.retrieve.list;

import com.vicente.task.organizer.UseCase;
import com.vicente.task.organizer.pagination.Pagination;
import com.vicente.task.organizer.pagination.SearchQuery;

public abstract class ListTasksUseCase extends UseCase<SearchQuery, Pagination<TaskListOutput>> {
}
