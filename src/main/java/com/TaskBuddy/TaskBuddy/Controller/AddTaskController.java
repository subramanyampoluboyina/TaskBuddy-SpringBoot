package com.TaskBuddy.TaskBuddy.Controller;

import com.TaskBuddy.TaskBuddy.DTO.AddTaskDTO;
import com.TaskBuddy.TaskBuddy.DTO.DocumentsDTO;
import com.TaskBuddy.TaskBuddy.Entity.*;
import com.TaskBuddy.TaskBuddy.Service.AddTaskService;
import com.TaskBuddy.TaskBuddy.Service.MstCategoryService;
import com.TaskBuddy.TaskBuddy.Service.MstStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class AddTaskController {
    private AddTaskService addTaskService;
    private MstStatusService mstStatusService;
    private MstCategoryService mstCategoryService;

    @Autowired
    public AddTaskController(AddTaskService addTaskService, MstStatusService mstStatusService, MstCategoryService mstCategoryService) {
        this.addTaskService = addTaskService;
        this.mstStatusService = mstStatusService;
        this.mstCategoryService = mstCategoryService;
    }
    @PostMapping("AddTask")
    public ResponseEntity<?> AddTask(@RequestBody AddTaskDTO taskDTO){
        if(taskDTO==null){
            return new ResponseEntity<>(new APIResponse("Failed","Task is empty",null), HttpStatus.BAD_REQUEST);
        }
        MstStatus mstStatus = mstStatusService.GetStatus(taskDTO.getStatus().getId());
        MstCategory mstCategory = mstCategoryService.GetCategory(taskDTO.getCategory().getId());

        TrnTask task = new TrnTask();
        task.setTaskTitle(taskDTO.getTaskTitle());
        task.setDescription(taskDTO.getDescription());
        task.setMstStatus(mstStatus);
        task.setMstCategory(mstCategory);
        task.setDueDate(taskDTO.getDueDate());
        task.setIsActive(true);

        TrnTask result=addTaskService.AddTask(task);

        for(DocumentsDTO documentsDTO: taskDTO.getDocuments()){
            TrnDocuments document=new TrnDocuments();
            document.setDocumentName(documentsDTO.getDocumentName());
            document.setBase64Content(documentsDTO.getBase64Content());
            document.setDocumentSize(documentsDTO.getDocumentSize());
            document.setDocumentType(documentsDTO.getDocumentType());
            document.setIsActive(true);
            document.setTrnTask(result);
            addTaskService.AddDocuments(document);
        }

        return new ResponseEntity<>(new APIResponse("Success","Task added successfully",result),HttpStatus.OK);
    }

    @GetMapping("AddTask")
    public ResponseEntity<?> GetAllTasks(){
        List<TrnTask> tasks=addTaskService.GetAllTasks();
        if(tasks.size()>0){
            return new ResponseEntity<>(new APIResponse("Success","Tasks retrieved",tasks),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new APIResponse("Failed","No Tasks found",null),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("GetDocuments/{taskId}")
    public ResponseEntity<?> GetDocuments(@PathVariable int taskId){
        List<TrnDocuments> documents = addTaskService.GetDocuments(taskId);
        if(documents.size()>0){
            return new ResponseEntity<>(new APIResponse("Success","Documents retrieved",documents),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new APIResponse("Failed","Not documents found",null),HttpStatus.OK);
        }
    }
}
