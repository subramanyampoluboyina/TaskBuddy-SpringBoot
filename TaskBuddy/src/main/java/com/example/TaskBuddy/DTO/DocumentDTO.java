package com.example.TaskBuddy.DTO;

import lombok.Data;

@Data
public class DocumentDTO {
    private int id;
    private String documentName;
    private String base64Content;
    private String documentSize;
    private String documentType;
}
