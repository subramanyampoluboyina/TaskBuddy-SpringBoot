package com.example.TaskBuddySpringBoot.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDTO {
    private int Id;
    private String Field;
    private String OldValue;
    private String NewValue;
    private LocalDateTime CreatedDate;
    private String CreatedBy;

    public ActivityDTO(String field, String oldValue, String newValue) {
        Field = field;
        OldValue = oldValue;
        NewValue = newValue;
    }
}
