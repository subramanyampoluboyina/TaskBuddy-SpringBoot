package com.example.TaskBuddySpringBoot.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TaskWithDocuments {
    private int id;
    private String title;
    private String description;
    private String status;
    private String category;
    private LocalDateTime dueDate;
    private List<DocumentDTO> documents;
}
