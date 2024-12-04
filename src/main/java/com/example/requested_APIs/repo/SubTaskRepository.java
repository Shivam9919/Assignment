package com.example.requested_APIs.repo;

import com.example.requested_APIs.model.SubTask;
import com.example.requested_APIs.model.Task;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SubTaskRepository extends JpaRepository<SubTask, Long> {

    Optional<SubTask> findByIdAndIsDeletedFalse(Long id);

    // Add this method to retrieve subtasks based on task ID
    List<SubTask> findByTask_IdAndIsDeletedFalse(Long taskId, Boolean isDeleted);

    public List<SubTask> findByTaskAndIsDeletedFalse(Task task);
}
