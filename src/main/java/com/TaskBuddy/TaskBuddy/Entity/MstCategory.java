package com.TaskBuddy.TaskBuddy.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "mstCategory")
public class MstCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int Id;
    @Column(name = "Name")
    private String Name;
    @Column(name = "IsActive", columnDefinition = "tinyint(1)")
    private boolean IsActive;

    public MstCategory(String name, boolean isActive) {
        Name = name;
        IsActive = isActive;
    }

    @Override
    public String toString() {
        return "MstCategory{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", IsActive=" + IsActive +
                '}';
    }
}
