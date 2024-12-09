package com.example.requested_APIs.Dtos;

import java.time.LocalDate;

public class CreateTaskDto {

    private String title;
    private String description;
    private LocalDate dueDate;
    private String priority;  // Use String or Task.Priority directly

    // Getters and setters...
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

}
