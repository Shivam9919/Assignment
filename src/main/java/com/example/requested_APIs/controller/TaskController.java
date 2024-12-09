package com.example.requested_APIs.controller;

import com.example.requested_APIs.Dtos.CreateTaskDto;
import com.example.requested_APIs.Dtos.UpdateTaskDto;
import com.example.requested_APIs.jwt.JwtUtils;
import com.example.requested_APIs.model.Task;
import com.example.requested_APIs.model.Task.Priority;
import com.example.requested_APIs.model.User;
import com.example.requested_APIs.service.TaskService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody @Valid CreateTaskDto createTaskDto,
            @RequestHeader("Authorization") String token) {
        String username = jwtUtils.extractUsername(token); // Extract username from token
        User user = (User) jwtUtils.getUserFromToken(token); // Fetch user details from JWT

        // Log the username (if needed for debugging or monitoring)
        System.out.println("Task creation request by user: " + username);

        Task task = taskService.createTask(createTaskDto, user); // Assuming createTask() returns Task directly
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @GetMapping
    public ResponseEntity<Page<Task>> getAllTasks(
            @RequestParam Optional<Priority> priority,
            @RequestParam Optional<LocalDate> dueDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestHeader("Authorization") String token) {

        User user = (User) jwtUtils.getUserFromToken(token); // Get user details from JWT
        PageRequest pageable = PageRequest.of(page, size, Sort.by("dueDate").ascending());

        Page<Task> tasks = taskService.getTasks(user, priority, dueDate, pageable); // Assuming this method returns a Page<Task>
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @RequestBody @Valid UpdateTaskDto updateTaskDto,
            @RequestHeader("Authorization") String token) {

        User user = (User) jwtUtils.getUserFromToken(token); // Get user details from JWT
        Task updatedTask = taskService.updateTask(id, updateTaskDto, user); // Assuming this returns Task directly
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        User user = (User) jwtUtils.getUserFromToken(token); // Get user details from JWT
        taskService.deleteTask(id, user); // Assuming this method performs the delete and doesn't return anything
        return ResponseEntity.noContent().build();
    }
}
