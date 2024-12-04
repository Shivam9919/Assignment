package com.example.requested_APIs.Dtos;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSubTaskDto {

    private String title;
    private String description;
    private LocalDate dueDate; // Change this to LocalDate

    // Getters and Setters
}
