package com.example.TaskBuddySpringBoot.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int Id;
    @Column(name = "Title")
    private String Title;
    @Column(name = "Description")
    private String Description;
    @Column(name = "Status")
    private String Status;
    @Column(name = "Category")
    private String Category;
    @Column(name = "DueDate")
    private LocalDateTime DueDate;
    @Column(name = "IsActive", columnDefinition = "tinyint(1)")
    private boolean IsActive;

    public Task(String title, String description, String status, String category, LocalDateTime dueDate, boolean isActive) {
        Title = title;
        Description = description;
        Status = status;
        Category = category;
        DueDate = dueDate;
        IsActive = isActive;
    }

    @Override
    public String toString() {
        return "Task{" +
                "Id=" + Id +
                ", Title='" + Title + '\'' +
                ", Description='" + Description + '\'' +
                ", Status='" + Status + '\'' +
                ", Category='" + Category + '\'' +
                ", DueDate=" + DueDate +
                ", IsActive=" + IsActive +
                '}';
    }
}
