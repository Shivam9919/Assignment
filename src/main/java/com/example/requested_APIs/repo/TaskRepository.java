package com.example.requested_APIs.repo;

import com.example.requested_APIs.model.Task;
import com.example.requested_APIs.model.User;
import java.util.Optional;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findByUserAndIsDeletedFalse(User user, Pageable pageable);

    Optional<Task> findByIdAndIsDeletedFalse(Long id);
}
