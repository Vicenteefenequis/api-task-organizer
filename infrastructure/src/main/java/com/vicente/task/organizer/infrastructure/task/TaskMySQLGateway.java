package com.vicente.task.organizer.infrastructure.task;

import com.vicente.task.organizer.domain.pagination.Pagination;
import com.vicente.task.organizer.domain.pagination.SearchQuery;
import com.vicente.task.organizer.domain.task.Task;
import com.vicente.task.organizer.domain.task.TaskGateway;
import com.vicente.task.organizer.domain.task.TaskID;
import com.vicente.task.organizer.infrastructure.task.persistence.TaskJpaEntity;
import com.vicente.task.organizer.infrastructure.task.persistence.TaskRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.util.Optional;

import static com.vicente.task.organizer.infrastructure.utils.SpecificationUtils.like;

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
        if (this.taskRepository.existsById(anId)) {
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
        final var page = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        // Busca dinamica pelo criterio terms (name ou description)

        final var specifications = Optional.ofNullable(aQuery.terms())
                .filter(str -> !str.isBlank())
                .map(this::assembleSpecification)
                .orElse(null);

        final var pageResult = this.taskRepository.findAll(Specification.where(specifications), page);
        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(TaskJpaEntity::toAggregate).toList()
        );
    }


    private Task save(final Task aTask) {
        return this.taskRepository.save(TaskJpaEntity.from(aTask)).toAggregate();
    }

    private Specification<TaskJpaEntity> assembleSpecification(final String str) {
        final Specification<TaskJpaEntity> nameLike = like("name", str);
        final Specification<TaskJpaEntity> descriptionLike = like("description", str);
        return nameLike.or(descriptionLike);
    }
}
