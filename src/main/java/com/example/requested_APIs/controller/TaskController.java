package com.example.requested_APIs.controller;

import com.example.requested_APIs.Dtos.CreateTaskDto;
import com.example.requested_APIs.Dtos.UpdateTaskDto;
import com.example.requested_APIs.jwt.JwtUtils;
import com.example.requested_APIs.model.Task;
import com.example.requested_APIs.model.User;
import com.example.requested_APIs.service.TaskService;
import jakarta.annotation.Priority;
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
    private JwtUtils jwtUtils; // Utility class for decoding JWT tokens

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody @Valid CreateTaskDto createTaskDto, @RequestHeader("Authorization") String token) {
        String username = jwtUtils.extractUsername(token);
        User user = jwtUtils.getUserFromToken(token); // Fetch user details from JWT
        Task task = taskService.createTask(createTaskDto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @GetMapping
    public ResponseEntity<Page<Task>> getAllTasks(
            @RequestParam Optional<Priority> priority,
            @RequestParam Optional<LocalDate> dueDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestHeader("Authorization") String token) {

        User user = jwtUtils.getUserFromToken(token);
        PageRequest pageable = PageRequest.of(page, size, Sort.by("dueDate").ascending());

        Page<Task> tasks = taskService.getTasks(user, pageable);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @RequestBody @Valid UpdateTaskDto updateTaskDto,
            @RequestHeader("Authorization") String token) {

        User user = jwtUtils.getUserFromToken(token);
        Task updatedTask = taskService.updateTask(id, updateTaskDto, user);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        User user = jwtUtils.getUserFromToken(token);
        taskService.deleteTask(id, user);
        return ResponseEntity.noContent().build();
    }
}
