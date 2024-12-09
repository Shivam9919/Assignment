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
@RequestMapping("/api/subtasks")
public class SubTaskController {

    @Autowired
    private SubTaskService subTaskService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping
    public ResponseEntity<SubTask> createSubTask(@RequestBody @Valid CreateSubTaskDto createSubTaskDto,
            @RequestHeader("Authorization") String token) {
        UserCreateDto userDto = (UserCreateDto) jwtUtils.getUserFromToken(token);  // Use UserDto here
        SubTask subTask = subTaskService.createSubTask(createSubTaskDto, userDto);  // Pass UserDto to the service
        return ResponseEntity.status(HttpStatus.CREATED).body(subTask);
    }

    @GetMapping
    public ResponseEntity<List<SubTask>> getSubTasks(
            @RequestParam Optional<Long> taskId,
            @RequestHeader("Authorization") String token) {

        UserCreateDto userDto = (UserCreateDto) jwtUtils.getUserFromToken(token);  // Use UserDto here
        List<SubTask> subTasks = taskId.isPresent()
                ? subTaskService.getSubTasksByTask(taskId.get(), userDto) // Pass UserDto to service method
                : subTaskService.getAllSubTasks(userDto);  // Pass UserDto to service method

        return ResponseEntity.ok(subTasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubTask> updateSubTask(
            @PathVariable Long id,
            @RequestBody @Valid UpdateSubTaskDto updateSubTaskDto,
            @RequestHeader("Authorization") String token) {

        UserCreateDto userDto = (UserCreateDto) jwtUtils.getUserFromToken(token);  // Use UserDto here
        SubTask updatedSubTask = subTaskService.updateSubTask(id, updateSubTaskDto, userDto);  // Pass UserDto
        return ResponseEntity.ok(updatedSubTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubTask(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        UserCreateDto userDto = (UserCreateDto) jwtUtils.getUserFromToken(token);  // Use UserDto here
        subTaskService.deleteSubTask(id, userDto);  // Pass UserDto to service method
        return ResponseEntity.noContent().build();
    }
}
