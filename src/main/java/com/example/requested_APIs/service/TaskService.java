package com.example.requested_APIs.service;

import com.example.requested_APIs.Dtos.CreateTaskDto;
import com.example.requested_APIs.Dtos.UpdateTaskDto;
import com.example.requested_APIs.exception.ResourceNotFoundException;
import com.example.requested_APIs.exception.UnauthorizedException;
import com.example.requested_APIs.model.SubTask;
import com.example.requested_APIs.model.Task;
import com.example.requested_APIs.model.TaskStatus;
import com.example.requested_APIs.model.User;
import com.example.requested_APIs.repo.SubTaskRepository;
import com.example.requested_APIs.repo.TaskRepository;
import com.example.requested_APIs.model.Task.Priority; // Correct Priority import
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SubTaskRepository subTaskRepository;

    // Helper method to check if the user has access to the task
    private Task checkUserAuthorization(Long id, User user) {
        Task task = taskRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if (!task.getUser().equals(user)) {
            throw new UnauthorizedException("You are not authorized to perform this action");
        }
        return task;
    }

    public Task createTask(CreateTaskDto createTaskDto, User user) {
        Task task = new Task();
        task.setTitle(createTaskDto.getTitle());
        task.setDescription(createTaskDto.getDescription());

        // Assign the LocalDate directly
        task.setDueDate(createTaskDto.getDueDate());

        // Correctly cast to Task.Priority
        task.setPriority(Task.Priority.valueOf(createTaskDto.getPriority()));
        task.setStatus(TaskStatus.TODO);
        task.setUser(user);
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, UpdateTaskDto updateTaskDto, User user) {
        Task task = checkUserAuthorization(id, user);

        // Assign the LocalDate directly
        task.setDueDate(updateTaskDto.getDueDate());

        task.setStatus(updateTaskDto.getStatus());

        // Update all subtasks to DONE if task is marked as DONE
        if (updateTaskDto.getStatus() == TaskStatus.DONE) {
            List<SubTask> subTasks = subTaskRepository.findByTaskAndIsDeletedFalse(task);
            subTasks.forEach(subTask -> subTask.setStatus(TaskStatus.DONE));
            subTaskRepository.saveAll(subTasks);
        }

        return taskRepository.save(task);
    }

    public void deleteTask(Long id, User user) {
        // Check user authorization and retrieve the task
        Task task = checkUserAuthorization(id, user);

        // Soft delete task
        task.setIsDeleted(true);

        // Soft delete associated subtasks
        List<SubTask> subTasks = subTaskRepository.findByTaskAndIsDeletedFalse(task);
        subTasks.forEach(subTask -> subTask.setIsDeleted(true));
        subTaskRepository.saveAll(subTasks);

        taskRepository.save(task);
    }

    // Corrected method to properly filter tasks based on priority and dueDate
    public Page<Task> getTasks(User user, Optional<Priority> priority, Optional<LocalDate> dueDate, PageRequest pageable) {
        if (priority.isPresent() && dueDate.isPresent()) {
            return taskRepository.findByUserAndPriorityAndDueDateAndIsDeletedFalse(user, priority.get(), dueDate.get(), pageable);
        } else if (priority.isPresent()) {
            return taskRepository.findByUserAndPriorityAndIsDeletedFalse(user, priority.get(), pageable);
        } else if (dueDate.isPresent()) {
            return taskRepository.findByUserAndDueDateAndIsDeletedFalse(user, dueDate.get(), pageable);
        } else {
            return taskRepository.findByUserAndIsDeletedFalse(user, pageable);
        }
    }
}
