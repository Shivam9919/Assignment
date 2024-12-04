package com.example.requested_APIs.service;

import com.example.requested_APIs.Dtos.CreateTaskDto;
import com.example.requested_APIs.Dtos.UpdateTaskDto;
import com.example.requested_APIs.exception.ResourceNotFoundException;
import com.example.requested_APIs.exception.UnauthorizedException;
import com.example.requested_APIs.model.SubTask;
import com.example.requested_APIs.model.SubTaskStatus;
import com.example.requested_APIs.model.Task;
import org.springframework.boot.info.SslInfo.CertificateValidityInfo.Status;
import com.example.requested_APIs.model.User;
import com.example.requested_APIs.repo.SubTaskRepository;
import com.example.requested_APIs.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SubTaskRepository subTaskRepository;

    public Task createTask(CreateTaskDto createTaskDto, User user) {
        Task task = new Task();
        task.setTitle(createTaskDto.getTitle());
        task.setDescription(createTaskDto.getDescription());
        task.setDueDate(createTaskDto.getDueDate());
        task.setPriority(createTaskDto.getPriority());
        task.setStatus(Status.TODO); // Correct Status usage
        task.setUser(user);
        return taskRepository.save(task);
    }

    public Page<Task> getTasks(User user, Pageable pageable) {
        return taskRepository.findByUserAndIsDeletedFalse(user, (SpringDataWebProperties.Pageable) pageable);
    }

    public Task updateTask(Long id, UpdateTaskDto updateTaskDto, User user) {
        Task task = taskRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if (!task.getUser().equals(user)) {
            throw new UnauthorizedException("You are not authorized to update this task");
        }

        task.setDueDate(updateTaskDto.getDueDate());
        task.setStatus(updateTaskDto.getStatus());

        // Update all subtasks to DONE if task is marked as DONE
        if (updateTaskDto.getStatus() == Status.DONE) {
            List<SubTask> subTasks = subTaskRepository.findByTaskAndIsDeletedFalse(task);
            subTasks.forEach(subTask -> subTask.setStatus(Status.DONE));
            subTaskRepository.saveAll(subTasks);
        }

        return taskRepository.save(task);
    }

    public void deleteTask(Long id, User user) {
        Task task = taskRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if (!task.getUser().equals(user)) {
            throw new UnauthorizedException("You are not authorized to delete this task");
        }

        // Soft delete task
        task.setIsDeleted(true);

        // Soft delete associated subtasks
        List<SubTask> subTasks = subTaskRepository.findByTaskAndIsDeletedFalse(task);
        subTasks.forEach(subTask -> subTask.setIsDeleted(true));
        subTaskRepository.saveAll(subTasks);

        taskRepository.save(task);
    }
}
