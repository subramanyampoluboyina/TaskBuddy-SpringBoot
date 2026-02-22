package com.example.TaskBuddySpringBoot.Controller;

import com.example.TaskBuddySpringBoot.DTO.*;
import com.example.TaskBuddySpringBoot.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("Task")
public class TaskController {
    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("AddTask")
    public ResponseEntity<?> AddTask(@RequestBody TaskWithDocuments taskWithDocuments){

        taskService.AddTask(taskWithDocuments);
        return new ResponseEntity<>(new APIResponse("Success","Task added successfully",null), HttpStatus.OK);
    }

    @GetMapping("GetTasks")
    public ResponseEntity<?> GetTasks(){
        List<TaskDTO> tasks = taskService.GetAllTasks();
        return new ResponseEntity<>(new APIResponse("Success","Tasks retrieved",tasks),HttpStatus.OK);
    }

    @GetMapping("GetTaskWithDocuments")
    public ResponseEntity<?> GetTaskWithDocuments(@RequestParam int taskId){
        TaskWithDocuments taskWithDocuments = taskService.GetTaskWithDocuments(taskId);
        return new ResponseEntity<>(new APIResponse("Success","Data retrieved",taskWithDocuments),HttpStatus.OK);
    }

    @PostMapping("DeleteTask")
    public ResponseEntity<?> DeleteTask(@RequestBody List<Integer> taskIds){
        taskService.DeleteTask(taskIds);
        return new ResponseEntity<>(new APIResponse("Success","Task deleted successfully",null),HttpStatus.OK);
    }

    @PostMapping("UpdateTask")
    public ResponseEntity<?> UpdateTask(@RequestBody TaskWithDocuments taskWithDocuments){
        taskService.UpdateTask(taskWithDocuments);
        return new ResponseEntity<>(new APIResponse("Success","Task updated successfully",null),HttpStatus.OK);
    }

    @PostMapping("UpdateTaskStatus")
    public ResponseEntity<?> UpdateTask(@RequestBody List<Integer> taskIds, @RequestParam String status){
        taskService.UpdateTaskStatus(taskIds, status);
        return new ResponseEntity<>(new APIResponse("Success","Task status updated successfully",null),HttpStatus.OK);
    }

    @GetMapping("GetActivities")
    public ResponseEntity<?> GetActivities(@RequestParam int taskId){
        List<ActivityResponse> activities = taskService.GetActivities(taskId);
        return new ResponseEntity<>(new APIResponse("Success","Task status updated successfully",activities),HttpStatus.OK);
    }
}
