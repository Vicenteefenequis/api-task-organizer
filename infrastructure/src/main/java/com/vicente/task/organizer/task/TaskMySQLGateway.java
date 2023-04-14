package com.vicente.task.organizer.task;

import com.vicente.task.organizer.pagination.Pagination;
import com.vicente.task.organizer.pagination.SearchQuery;
import com.vicente.task.organizer.task.persistence.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskMySQLGateway implements TaskGateway {
    private final TaskRepository taskRepository;

    public TaskMySQLGateway(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    @Override
    public Task create(Task task) {
        return null;
    }

    @Override
    public void deleteById(TaskID taskId) {

    }
    @Override
    public Optional<Task> findById(TaskID taskId) {
        return Optional.empty();
    }
    @Override
    public Task update(Task task) {
        return null;
    }
    @Override
    public Pagination<Task> findAll(SearchQuery aQuery) {
        return null;
    }
}
