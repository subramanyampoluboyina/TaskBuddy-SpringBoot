package com.TaskBuddy.TaskBuddy.Service;

import com.TaskBuddy.TaskBuddy.DAO.AddDocumentDAO;
import com.TaskBuddy.TaskBuddy.DAO.AddTaskDAO;
import com.TaskBuddy.TaskBuddy.DTO.AddTaskDTO;
import com.TaskBuddy.TaskBuddy.DTO.DocumentsDTO;
import com.TaskBuddy.TaskBuddy.Entity.TrnDocuments;
import com.TaskBuddy.TaskBuddy.Entity.TrnTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddTaskService {
    private AddTaskDAO addTaskDAO;
    private AddDocumentDAO addDocumentDAO;

    @Autowired
    public AddTaskService(AddTaskDAO addTaskDAO, AddDocumentDAO addDocumentDAO) {
        this.addTaskDAO = addTaskDAO;
        this.addDocumentDAO = addDocumentDAO;
    }

    public TrnTask AddTask(TrnTask trnTask){
        return addTaskDAO.save(trnTask);
    }

    public List<TrnTask> GetAllTasks(){
        return addTaskDAO.activeTasks();
    }

    public TrnDocuments AddDocuments(TrnDocuments trnDocuments){
        return addDocumentDAO.save(trnDocuments);
    }

    public List<TrnDocuments> GetDocuments(int taskId){
        return addDocumentDAO.getDocumentByTaskId(taskId);
    }
}
