package com.example.TaskBuddySpringBoot.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityResponse {
    private String activity;
    private String date;
    private String time;
}
