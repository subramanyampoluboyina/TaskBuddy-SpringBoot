package com.example.TaskBuddySpringBoot.DAO;

import com.example.TaskBuddySpringBoot.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskDAO extends JpaRepository<Task, Integer> {
    @Query(value = "SELECT * FROM Tasks WHERE IsActive=true", nativeQuery = true)
    List<Task> activeTasks();
}
