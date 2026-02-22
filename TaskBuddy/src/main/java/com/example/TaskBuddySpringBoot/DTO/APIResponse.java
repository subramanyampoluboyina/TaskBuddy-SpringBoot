package com.example.TaskBuddySpringBoot.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class APIResponse<T> {
    private String result;
    private String message;
    private T data;
}
