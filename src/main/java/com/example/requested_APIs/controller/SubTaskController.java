package com.example.requested_APIs.controller;

import com.example.requested_APIs.Dtos.CreateSubTaskDto;
import com.example.requested_APIs.Dtos.UpdateSubTaskDto;
import com.example.requested_APIs.Dtos.UserCreateDto;
import com.example.requested_APIs.jwt.JwtUtils;
import com.example.requested_APIs.model.SubTask;
import com.example.requested_APIs.service.SubTaskService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subtasks")
public class SubTaskController {

    @Autowired
    private SubTaskService subTaskService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping
    public ResponseEntity<SubTask> createSubTask(
            @RequestBody @Valid CreateSubTaskDto createSubTaskDto,
            @RequestHeader("Authorization") String token) {

        // Extract user info from the token
        UserCreateDto userDto = (UserCreateDto) jwtUtils.getUserFromToken(token);

        // Check if the title is in uppercase
        if (!createSubTaskDto.getTitle().equals(createSubTaskDto.getTitle().toUpperCase())) {
            return ResponseEntity.badRequest().body(null); // Reject if title is not uppercase
        }

        SubTask subTask = subTaskService.createSubTask(createSubTaskDto, userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(subTask);
    }

    @GetMapping
    public ResponseEntity<List<SubTask>> getSubTasks(
            @RequestParam Optional<Long> taskId,
            @RequestHeader("Authorization") String token) {

        UserCreateDto userDto = (UserCreateDto) jwtUtils.getUserFromToken(token);
        List<SubTask> subTasks = taskId.isPresent()
                ? subTaskService.getSubTasksByTask(taskId.get(), userDto)
                : subTaskService.getAllSubTasks(userDto);

        return ResponseEntity.ok(subTasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubTask> updateSubTask(
            @PathVariable Long id,
            @RequestBody @Valid UpdateSubTaskDto updateSubTaskDto,
            @RequestHeader("Authorization") String token) {

        UserCreateDto userDto = (UserCreateDto) jwtUtils.getUserFromToken(token);

        // Check if the title is in uppercase
        if (!updateSubTaskDto.getTitle().equals(updateSubTaskDto.getTitle().toUpperCase())) {
            return ResponseEntity.badRequest().body(null); // Reject if title is not uppercase
        }

        SubTask updatedSubTask = subTaskService.updateSubTask(id, updateSubTaskDto, userDto);
        return ResponseEntity.ok(updatedSubTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubTask(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {

        UserCreateDto userDto = (UserCreateDto) jwtUtils.getUserFromToken(token);
        subTaskService.deleteSubTask(id, userDto);
        return ResponseEntity.noContent().build();
    }
}
