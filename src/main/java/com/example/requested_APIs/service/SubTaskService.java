package com.example.requested_APIs.service;

import com.example.requested_APIs.Dtos.CreateSubTaskDto;
import com.example.requested_APIs.Dtos.UpdateSubTaskDto;
import com.example.requested_APIs.exception.ResourceNotFoundException;
import com.example.requested_APIs.exception.UnauthorizedException;
import com.example.requested_APIs.model.SubTask;
import com.example.requested_APIs.model.SubTask.SubTaskStatus;
import com.example.requested_APIs.model.SubTaskStatus;
import com.example.requested_APIs.model.Task;
import com.example.requested_APIs.model.User;
import com.example.requested_APIs.repo.SubTaskRepository;
import com.example.requested_APIs.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubTaskService {

    @Autowired
    private SubTaskRepository subTaskRepository;

    @Autowired
    private TaskRepository taskRepository;

    // Create a new SubTask
    public SubTask createSubTask(CreateSubTaskDto createSubTaskDto, User user) {
        Task task = taskRepository.findByIdAndIsDeletedFalse(createSubTaskDto.getTaskId())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if (!task.getUser().equals(user)) {
            throw new UnauthorizedException("You are not authorized to add subtasks to this task");
        }

        SubTask subTask = new SubTask();
        subTask.setTitle(createSubTaskDto.getTitle());
        subTask.setDescription(createSubTaskDto.getDescription());
        subTask.setDueDate(createSubTaskDto.getDueDate());
        subTask.setStatus(SubTaskStatus.TODO);
        subTask.setTask(task);

        return subTaskRepository.save(subTask);
    }

    // Update an existing SubTask
    public SubTask updateSubTask(Long id, UpdateSubTaskDto updateSubTaskDto, User user) {
        SubTask subTask = subTaskRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("SubTask not found"));

        if (!subTask.getTask().getUser().equals(user)) {
            throw new UnauthorizedException("You are not authorized to update this subtask");
        }

        subTask.setTitle(updateSubTaskDto.getTitle());
        subTask.setDescription(updateSubTaskDto.getDescription());
        subTask.setStatus(updateSubTaskDto.getStatus());
        subTask.setDueDate(updateSubTaskDto.getDueDate());

        return subTaskRepository.save(subTask);
    }

    // Delete a SubTask
    public void deleteSubTask(Long id, User user) {
        SubTask subTask = subTaskRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("SubTask not found"));

        if (!subTask.getTask().getUser().equals(user)) {
            throw new UnauthorizedException("You are not authorized to delete this subtask");
        }

        subTask.setIsDeleted(true);
        subTaskRepository.save(subTask);
    }

    // Get all SubTasks for a specific user
    public List<SubTask> getAllSubTasks(User user) {
        return subTaskRepository.findByTask_UserAndIsDeletedFalse(user, false);
    }

    // Get SubTasks by task ID for a specific user
    public List<SubTask> getSubTasksByTask(Long taskId, User user) {
        Task task = taskRepository.findByIdAndIsDeletedFalse(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if (!task.getUser().equals(user)) {
            throw new UnauthorizedException("You are not authorized to access subtasks for this task");
        }

        return subTaskRepository.findByTask_IdAndIsDeletedFalse(taskId, false);
    }
}
