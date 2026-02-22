package com.example.TaskBuddySpringBoot.DAO;

import com.example.TaskBuddySpringBoot.Entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActivityDAO extends JpaRepository<Activity, Number> {
    @Query(value = "SELECT * FROM Activities WHERE TaskId = :taskId",nativeQuery = true)
    List<Activity> getActivitiesByTaskId(@Param("taskId") int taskId);
}
