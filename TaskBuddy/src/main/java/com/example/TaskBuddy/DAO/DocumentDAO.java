package com.example.TaskBuddy.DAO;

import com.example.TaskBuddy.Entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentDAO extends JpaRepository<Document, Integer> {

    @Query(value = "SELECT * FROM Documents WHERE IsActive=true",nativeQuery = true)
    List<Document> activeDocuments();

    @Query(value = "SELECT * FROM Documents WHERE TaskId = :taskId AND IsActive=true",nativeQuery = true)
    List<Document> getDocumentsByTaskId(@Param("taskId") int taskId);
}
