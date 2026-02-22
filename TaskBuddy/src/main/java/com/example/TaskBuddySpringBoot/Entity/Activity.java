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
@Table(name = "Activities")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int Id;
    @ManyToOne
    @JoinColumn(name = "TaskId", nullable = false)
    private Task task;
    @Column(name = "Field")
    private String Field;
    @Column(name = "OldValue")
    private String OldValue;
    @Column(name = "NewValue")
    private String NewValue;
    @Column(name = "CreatedDate")
    private LocalDateTime CreatedDate;
    @Column(name = "CreatedBy")
    private String CreatedBy;

    public Activity(Task task, String field, String oldValue, String newValue, LocalDateTime createdDate, String createdBy) {
        this.task = task;
        Field = field;
        OldValue = oldValue;
        NewValue = newValue;
        CreatedDate = createdDate;
        CreatedBy = createdBy;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "Id=" + Id +
                ", task='" + task + '\'' +
                ", Field='" + Field + '\'' +
                ", OldValue='" + OldValue + '\'' +
                ", NewValue='" + NewValue + '\'' +
                ", CreatedDate=" + CreatedDate +
                ", CreatedBy='" + CreatedBy + '\'' +
                '}';
    }
}
