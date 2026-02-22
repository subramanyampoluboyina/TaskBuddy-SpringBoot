package com.example.TaskBuddySpringBoot.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDTO {
    private int id;
    private String title;
    private String description;
    private String status;
    private String category;
    private LocalDateTime dueDate;
}
