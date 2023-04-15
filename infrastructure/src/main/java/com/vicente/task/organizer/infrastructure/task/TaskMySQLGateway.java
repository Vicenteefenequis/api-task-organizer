package com.vicente.task.organizer.infrastructure.task;

import com.vicente.task.organizer.domain.pagination.Pagination;
import com.vicente.task.organizer.domain.pagination.SearchQuery;
import com.vicente.task.organizer.domain.task.Task;
import com.vicente.task.organizer.domain.task.TaskGateway;
import com.vicente.task.organizer.domain.task.TaskID;
import com.vicente.task.organizer.infrastructure.task.persistence.TaskJpaEntity;
import com.vicente.task.organizer.infrastructure.task.persistence.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskMySQLGateway implements TaskGateway {
    private final TaskRepository taskRepository;

    public TaskMySQLGateway(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task create(final Task aTask) {
        return save(aTask);
    }

    @Override
    public void deleteById(TaskID taskId) {
        String anId = taskId.getValue();
        if(this.taskRepository.existsById(anId)) {
            this.taskRepository.deleteById(anId);
        }
    }

    @Override
    public Optional<Task> findById(TaskID taskId) {
        final var anId = taskId.getValue();
        return this.taskRepository.findById(anId).map(TaskJpaEntity::toAggregate);
    }

    @Override
    public Task update(Task task) {
        return save(task);
    }

    @Override
    public Pagination<Task> findAll(SearchQuery aQuery) {
        return null;
    }


    private Task save(final Task aTask) {
        return this.taskRepository.save(TaskJpaEntity.from(aTask)).toAggregate();
    }
}
