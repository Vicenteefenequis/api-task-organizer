package com.vicente.task.organizer.infrastructure.task.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskJpaEntity, String> {
}
