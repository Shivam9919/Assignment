package com.example.requested_APIs.repo;

import com.example.requested_APIs.Dtos.UserCreateDto;
import com.example.requested_APIs.model.SubTask;
import com.example.requested_APIs.model.Task;
import com.example.requested_APIs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

@EnableJpaRepositories
public interface SubTaskRepository extends JpaRepository<SubTask, Long> {

    List<SubTask> findByTask_UserAndIsDeletedFalse(User user, boolean isDeleted);

    List<SubTask> findByTask_UserAndIsDeletedFalse(UserCreateDto user, boolean isDeleted);

    List<SubTask> findByTask_IdAndIsDeletedFalse(Long taskId, boolean isDeleted);

    Optional<SubTask> findByIdAndIsDeletedFalse(Long id);

    @Query("SELECT s FROM SubTask s WHERE s.task.id = :taskId AND s.isDeleted = false")
    List<SubTask> findByTaskIdAndIsDeletedFalse(@Param("taskId") Long taskId);

    List<SubTask> findByTaskAndIsDeletedFalse(Task task);
}
