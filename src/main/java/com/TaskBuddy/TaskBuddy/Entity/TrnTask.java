package com.TaskBuddy.TaskBuddy.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "trnTask")
public class TrnTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int Id;

    @Column(name = "TaskTitle")
    private String TaskTitle;

    @Column(name = "Description")
    private String Description;

    @ManyToOne
    @JoinColumn(name = "StatusId")
    private MstStatus mstStatus;

    @ManyToOne
    @JoinColumn(name = "CategoryId")
    private MstCategory mstCategory;

    @Column(name = "DueDate")
    private LocalDateTime DueDate;

    @Column(name = "IsActive", columnDefinition = "tinyint(1)")
    private boolean IsActive;

    public TrnTask(String taskTitle, String description, MstStatus mstStatus, MstCategory mstCategory, LocalDateTime dueDate) {
        TaskTitle = taskTitle;
        Description = description;
        this.mstStatus = mstStatus;
        this.mstCategory = mstCategory;
        DueDate = dueDate;
    }

    @Override
    public String toString() {
        return "TrnTask{" +
                "Id=" + Id +
                ", TaskTitle='" + TaskTitle + '\'' +
                ", Description='" + Description + '\'' +
                ", mstStatus=" + mstStatus +
                ", mstCategory=" + mstCategory +
                ", DueDate=" + DueDate +
                '}';
    }
}
