package com.example.requested_APIs.Dtos;

import com.example.requested_APIs.model.TaskStatus;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTaskDto {

    private LocalDate dueDate;
    private TaskStatus status; // Use the enum directly
}
