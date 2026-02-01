package com.TaskBuddy.TaskBuddy.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AddTaskDTO {
    private String TaskTitle;
    private String Description;
    private StatusDTO status;
    private CategoryDTO category;
    private LocalDateTime dueDate;
    private List<DocumentsDTO> documents;
}
