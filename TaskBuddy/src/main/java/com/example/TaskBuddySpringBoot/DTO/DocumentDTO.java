package com.example.TaskBuddySpringBoot.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDTO {
    private int id;
    private String documentName;
    private String base64Content;
    private String documentSize;
    private String documentType;
}
