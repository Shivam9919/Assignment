package com.example.requested_APIs.repo;

import com.example.requested_APIs.Dtos.UserCreateDto;
import com.example.requested_APIs.model.SubTask;
import com.example.requested_APIs.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubTaskRepository extends JpaRepository<SubTask, Long> {

    List<SubTask> findByTask_UserAndIsDeletedFalse(UserCreateDto user, boolean isDeleted);

    List<SubTask> findByTask_IdAndIsDeletedFalse(Long taskId, boolean isDeleted);

    Optional<SubTask> findByIdAndIsDeletedFalse(Long id);

    List<SubTask> findByTaskAndIsDeletedFalse(Task task);
}
