package com.TaskBuddy.TaskBuddy.DTO;

import lombok.Data;

@Data
public class DocumentsDTO {
    private String DocumentName;
    private String Base64Content;
    private String DocumentSize;
    private String DocumentType;
}
