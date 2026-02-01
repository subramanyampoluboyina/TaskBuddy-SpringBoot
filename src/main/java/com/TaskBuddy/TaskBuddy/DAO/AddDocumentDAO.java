package com.TaskBuddy.TaskBuddy.DAO;

import com.TaskBuddy.TaskBuddy.DTO.DocumentsDTO;
import com.TaskBuddy.TaskBuddy.Entity.TrnDocuments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddDocumentDAO extends JpaRepository<TrnDocuments,Integer> {
    @Query(value = "SELECT * FROM TrnDocuments WHERE IsActive=true AND TaskId=:taskId", nativeQuery = true)
    List<TrnDocuments> getDocumentByTaskId(int taskId);
}
