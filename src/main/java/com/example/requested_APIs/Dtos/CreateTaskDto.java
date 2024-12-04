package com.example.requested_APIs.Dtos;

import jakarta.annotation.Priority;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTaskDto {

    private String title;
    private String description;
    private LocalDate dueDate;
    private Priority priority;
}
