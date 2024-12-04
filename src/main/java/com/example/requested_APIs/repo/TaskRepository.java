package com.example.requested_APIs.repo;

import com.example.requested_APIs.model.Task;
import com.example.requested_APIs.model.User;
import jakarta.annotation.Priority;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByIdAndIsDeletedFalse(Long id);

    /**
     *
     * @param user
     * @param pageable
     * @return
     */
    Page<Task> findByUserAndIsDeletedFalse(User user, PageRequest pageable);

    Page<Task> findByUserAndPriorityAndDueDateAndIsDeletedFalse(User user, Priority priority, LocalDate dueDate, Pageable pageable);

    Page<Task> findByUserAndPriorityAndIsDeletedFalse(User user, Priority priority, Pageable pageable);

    Page<Task> findByUserAndDueDateAndIsDeletedFalse(User user, LocalDate dueDate, Pageable pageable);

    Page<Task> findByUserAndIsDeletedFalse(User user, Pageable pageable);

}
