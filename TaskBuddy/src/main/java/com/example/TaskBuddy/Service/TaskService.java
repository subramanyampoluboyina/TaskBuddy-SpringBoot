package com.example.TaskBuddy.Service;

import com.example.TaskBuddy.DAO.ActivityDAO;
import com.example.TaskBuddy.DAO.DocumentDAO;
import com.example.TaskBuddy.DAO.TaskDAO;
import com.example.TaskBuddy.DTO.ActivityDTO;
import com.example.TaskBuddy.DTO.DocumentDTO;
import com.example.TaskBuddy.DTO.TaskDTO;
import com.example.TaskBuddy.DTO.TaskWithDocuments;
import com.example.TaskBuddy.Entity.Activity;
import com.example.TaskBuddy.Entity.Document;
import com.example.TaskBuddy.Entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private final TaskDAO taskDao;
    private final DocumentDAO documentDAO;
    private final ActivityDAO activityDAO;

    @Autowired
    public TaskService(TaskDAO taskDao, DocumentDAO documentDAO, ActivityDAO activityDAO) {
        this.taskDao = taskDao;
        this.documentDAO = documentDAO;
        this.activityDAO = activityDAO;
    }

    public Task AddTask(TaskWithDocuments taskWithDocuments){
        Task task = new Task();
        task.setTitle(taskWithDocuments.getTitle());
        task.setDescription(taskWithDocuments.getDescription());
        task.setStatus(taskWithDocuments.getStatus());
        task.setCategory(taskWithDocuments.getCategory());
        task.setDueDate(taskWithDocuments.getDueDate());
        task.setIsActive(true);
        Task result = taskDao.save(task);

        for(DocumentDTO documentsDto : taskWithDocuments.getDocuments()){
            Document document = new Document();
            document.setDocumentName(documentsDto.getDocumentName());
            document.setBase64Content(documentsDto.getBase64Content());
            document.setDocumentType(documentsDto.getDocumentType());
            document.setDocumentSize(documentsDto.getDocumentSize());
            document.setIsActive(true);
            document.setTask(result);

            documentDAO.save(document);
        }
        return result;
    }

    public List<TaskDTO> GetAllTasks(){
        List<Task> tasks =  taskDao.activeTasks();
        List<TaskDTO> taskDtos = new ArrayList<TaskDTO>();
        for(Task task:tasks){
            TaskDTO taskDto = new TaskDTO();
            taskDto.setId(task.getId());
            taskDto.setTitle(task.getTitle());
            taskDto.setStatus(task.getStatus());
            taskDto.setCategory(task.getCategory());
            taskDto.setDescription(task.getDescription());
            taskDto.setDueDate(task.getDueDate());
            taskDtos.add(taskDto);
        }
        return taskDtos;
    }

    public Document AddDocument(Document document){
        return documentDAO.save(document);
    }

    public TaskWithDocuments GetTaskWithDocuments(int id){
        TaskWithDocuments result = new TaskWithDocuments();
        try{
            Task task = taskDao.findById(id).orElseThrow(()-> new Exception("Task not found"));
            List<Document> documents = documentDAO.getDocumentsByTaskId(id);

            result.setId(task.getId());
            result.setTitle(task.getTitle());
            result.setDescription(task.getDescription());
            result.setStatus(task.getStatus());
            result.setCategory(task.getCategory());
            result.setDueDate(task.getDueDate());

            List<DocumentDTO> documentsDtoList = new ArrayList<>();
            for (Document document : documents){
                DocumentDTO documentsDto = new DocumentDTO();
                documentsDto.setId(document.getId());
                documentsDto.setDocumentName((document.getDocumentName()));
                documentsDto.setBase64Content(document.getBase64Content());
                documentsDto.setDocumentSize(document.getDocumentSize());
                documentsDto.setDocumentType(document.getDocumentType());
                documentsDtoList.add(documentsDto);
            }
            result.setDocuments(documentsDtoList);

            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public TaskWithDocuments UpdateTask(TaskWithDocuments taskWithDocuments){
        try{
            Task task = taskDao.findById(taskWithDocuments.getId()).orElseThrow(()-> new Exception("Task not found"));
            task.setTitle(taskWithDocuments.getTitle());
            task.setDescription(taskWithDocuments.getDescription());
            task.setStatus(taskWithDocuments.getStatus());
            task.setCategory(taskWithDocuments.getCategory());
            task.setDueDate(taskWithDocuments.getDueDate());
            taskDao.save(task);

            List<Document> documents = documentDAO.getDocumentsByTaskId(taskWithDocuments.getId());
            
            documentDAO.deleteAll(documents);

            for(DocumentDTO documentsDto : taskWithDocuments.getDocuments()){
                Document document = new Document();
                document.setDocumentName(documentsDto.getDocumentName());
                document.setBase64Content(documentsDto.getBase64Content());
                document.setDocumentType(documentsDto.getDocumentType());
                document.setDocumentSize(documentsDto.getDocumentSize());
                document.setIsActive(true);
                document.setTask(task);
                documentDAO.save(document);
            }
            return taskWithDocuments;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void UpdateTaskStatus(List<Integer> ids, String status){
        try{
            for(int id : ids){
                Task task = taskDao.findById(id).orElseThrow(()-> new Exception("Task not found"));

                if(!status.isBlank()){

                    // Update Activity
                    ActivityDTO activityDTO = new ActivityDTO("status", task.getStatus(), status);
                    UpdateActivity(activityDTO, task);

                    task.setStatus(status);
                    taskDao.save(task);
                }else{
                    throw new Exception("Status should not be blank");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void DeleteDocument(int id){
        try{
            Document document = documentDAO.findById(id).orElseThrow(()-> new Exception("Document not found"));
            document.setIsActive(false);
            documentDAO.save(document);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void DeleteTask(List<Integer> ids){
        try{
            for(int id : ids) {
                Task task = taskDao.findById(id).orElseThrow(() -> new Exception("Task not found"));
                task.setIsActive(false);
                taskDao.save(task);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void UpdateActivity(ActivityDTO activityDTO, Task task){
        Activity activity = new Activity();
        activity.setField(activityDTO.getField());
        activity.setOldValue(activityDTO.getOldValue());
        activity.setNewValue(activityDTO.getNewValue());
        activity.setCreatedDate(LocalDateTime.now());
        activity.setCreatedBy("System");
        activity.setTask(task);

        activityDAO.save(activity);
//        if(Objects.equals(activityDTO.getField(), "dueDate")){
//            activity.setOldValue();
//        }
    }

    public List<ActivityDTO> GetActivities(int taskId){
        List<Activity> activities = activityDAO.getActivitiesByTaskId(taskId);
        List<ActivityDTO> activityDTOList =  new ArrayList<ActivityDTO>();
        for (Activity activity : activities){
            ActivityDTO activityDTO = new ActivityDTO();
            activityDTO.setId(activity.getId());
            activityDTO.setField(activity.getField());
            activityDTO.setOldValue(activity.getOldValue());
            activityDTO.setNewValue(activity.getNewValue());
            activityDTO.setCreatedDate(activity.getCreatedDate());
            activityDTO.setCreatedBy(activity.getCreatedBy());

            activityDTOList.add(activityDTO);
        }
        return activityDTOList;
    }
}
