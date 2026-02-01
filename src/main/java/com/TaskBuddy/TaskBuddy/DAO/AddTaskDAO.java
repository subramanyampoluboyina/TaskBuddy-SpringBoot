package com.TaskBuddy.TaskBuddy.DAO;

import com.TaskBuddy.TaskBuddy.Entity.TrnTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddTaskDAO extends JpaRepository<TrnTask,Integer> {
    @Query(value = "Select * from TrnTask where IsActive=true",nativeQuery = true)
    List<TrnTask> activeTasks();
}
