package com.TaskBuddy.TaskBuddy.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "mstStatus")
public class MstStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int Id;
    @Column(name = "Name")
    private String Name;
    @Column(name = "IsActive", columnDefinition = "tinyint(1)")
    private boolean IsActive;

    public MstStatus() {
    }

    public MstStatus(String name, boolean isActive) {
        Name = name;
        IsActive = isActive;
    }
    @Override
    public String toString() {
        return "MstStatus{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", IsActive=" + IsActive +
                '}';
    }
}
