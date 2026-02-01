package com.TaskBuddy.TaskBuddy.Entity;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private String result;
    private String message;
    private LocalDateTime timestamp;
}
