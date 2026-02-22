package com.example.TaskBuddySpringBoot.Service;

import com.example.TaskBuddySpringBoot.DAO.ActivityDAO;
import com.example.TaskBuddySpringBoot.DAO.DocumentDAO;
import com.example.TaskBuddySpringBoot.DAO.TaskDAO;
import com.example.TaskBuddySpringBoot.DTO.*;
import com.example.TaskBuddySpringBoot.Entity.Activity;
import com.example.TaskBuddySpringBoot.Entity.Document;
import com.example.TaskBuddySpringBoot.Entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Transactional
    public Task AddTask(TaskWithDocuments taskWithDocuments){
        Task task = new Task();
        task.setTitle(taskWithDocuments.getTitle());
        task.setDescription(taskWithDocuments.getDescription());
        task.setStatus(taskWithDocuments.getStatus());
        task.setCategory(taskWithDocuments.getCategory());
        task.setDueDate(taskWithDocuments.getDueDate());
        task.setIsActive(true);

        Task result = taskDao.save(task);
        UpdateActivity(new ActivityDTO("task","", result.getTitle()), result);

        for(DocumentDTO documentsDto : taskWithDocuments.getDocuments()){
            Document document = new Document();
            document.setDocumentName(documentsDto.getDocumentName());
            document.setBase64Content(documentsDto.getBase64Content());
            document.setDocumentType(documentsDto.getDocumentType());
            document.setDocumentSize(documentsDto.getDocumentSize());
            document.setIsActive(true);
            document.setTask(result);
            UpdateActivity(new ActivityDTO("document","",documentsDto.getDocumentName()), task);

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

    @Transactional
    public TaskWithDocuments UpdateTask(TaskWithDocuments taskWithDocuments){
        try{
            Task task = taskDao.findById(taskWithDocuments.getId()).orElseThrow(()-> new Exception("Task not found"));

            //Update Activity
            if(!Objects.equals(taskWithDocuments.getTitle(), task.getTitle())){
                UpdateActivity(new ActivityDTO("title", task.getTitle(), taskWithDocuments.getTitle()),task);
            }
            if(!Objects.equals(taskWithDocuments.getDescription(), task.getDescription())){
                UpdateActivity(new ActivityDTO("description", task.getDescription(), taskWithDocuments.getDescription()),task);
            }
            if(!Objects.equals(taskWithDocuments.getStatus(), task.getStatus())){
                UpdateActivity(new ActivityDTO("status", task.getStatus(), taskWithDocuments.getStatus()),task);
            }
            if(!Objects.equals(taskWithDocuments.getCategory(), task.getCategory())){
                UpdateActivity(new ActivityDTO("category", task.getCategory(), taskWithDocuments.getCategory()),task);
            }
            if(!Objects.equals(taskWithDocuments.getDueDate(), task.getDueDate())){
                UpdateActivity(new ActivityDTO("dueDate", task.getDueDate().toLocalDate().toString(), taskWithDocuments.getDueDate().toLocalDate().toString()),task);
            }

            task.setTitle(taskWithDocuments.getTitle());
            task.setDescription(taskWithDocuments.getDescription());
            task.setStatus(taskWithDocuments.getStatus());
            task.setCategory(taskWithDocuments.getCategory());
            task.setDueDate(taskWithDocuments.getDueDate());
            taskDao.save(task);

            //Update Document
            List<Document> allDocuments = documentDAO.getDocumentsByTaskId(taskWithDocuments.getId()).stream().toList();
            List<Integer> existedDocuments = taskWithDocuments.getDocuments().stream().map(DocumentDTO::getId).filter(id -> id != 0).toList();
            List<DocumentDTO> newDocuments = taskWithDocuments.getDocuments().stream().filter(d->d.getId() == 0).toList();
            List<Document> toBeDeletedDocuments = allDocuments.stream()
                    .filter(d->!existedDocuments.contains(d.getId())).toList();

            if(!toBeDeletedDocuments.isEmpty()){
                for (Document doc : toBeDeletedDocuments){
                    UpdateActivity(new ActivityDTO("document", doc.getDocumentName(), ""), task);
                    documentDAO.delete(doc);
                }
            }

            for(DocumentDTO documentsDto : newDocuments){
                Document document = new Document();
                document.setDocumentName(documentsDto.getDocumentName());
                document.setBase64Content(documentsDto.getBase64Content());
                document.setDocumentType(documentsDto.getDocumentType());
                document.setDocumentSize(documentsDto.getDocumentSize());
                document.setIsActive(true);
                document.setTask(task);
                UpdateActivity(new ActivityDTO("document","", documentsDto.getDocumentName()), task);
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
                    UpdateActivity(new ActivityDTO("status", task.getStatus(), status), task);

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
    }

    public List<ActivityResponse> GetActivities(int taskId){
        List<Activity> activities = activityDAO.getActivitiesByTaskId(taskId);
//        List<ActivityDTO> activityDTOList =  new ArrayList<ActivityDTO>();
//        for (Activity activity : activities){
//            ActivityDTO activityDTO = new ActivityDTO();
//            activityDTO.setId(activity.getId());
//            activityDTO.setField(activity.getField());
//            activityDTO.setOldValue(activity.getOldValue());
//            activityDTO.setNewValue(activity.getNewValue());
//            activityDTO.setCreatedDate(activity.getCreatedDate());
//            activityDTO.setCreatedBy(activity.getCreatedBy());
//
//            activityDTOList.add(activityDTO);
//        }
        List<ActivityResponse> result =  new ArrayList<ActivityResponse>();
        for (Activity activity : activities){
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd");
            String date = activity.getCreatedDate().format(dateFormatter);

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm a");
            String time = activity.getCreatedDate().format(timeFormatter);
            //Task
            if(Objects.equals(activity.getField(), "task")){
                if(Objects.equals(activity.getOldValue(), "") && !Objects.equals(activity.getNewValue(), "")){
                    result.add(new ActivityResponse("You created this task", date, time));
                }
                if(!Objects.equals(activity.getOldValue(), "") && Objects.equals(activity.getNewValue(), "")){
                    result.add(new ActivityResponse("You deleted this task", date, time));
                }
            }
            // region Title
            if(Objects.equals(activity.getField(), "title")){
                if(!Objects.equals(activity.getOldValue(), activity.getNewValue())){
                    result.add(new ActivityResponse("You changed title from "+activity.getOldValue()+" to "+activity.getNewValue(),
                            date, time));
                }
            }
            // Description
            if(Objects.equals(activity.getField(), "description")){
//                if(Objects.equals(activity.getOldValue(), "") && !Objects.equals(activity.getNewValue(), "")){
//                    result.add(new ActivityResponse("You updated description", activity.getCreatedDate().toLocalDate().toString(), activity.getCreatedDate().toLocalTime().toString()));
//                }
//                if(!Objects.equals(activity.getOldValue(), "") && Objects.equals(activity.getNewValue(), "")){
//                    result.add(new ActivityResponse("You updated description", activity.getCreatedDate().toLocalDate().toString(), activity.getCreatedDate().toLocalTime().toString()));
//                }
                if(!Objects.equals(activity.getOldValue(), activity.getNewValue())){
                    result.add(new ActivityResponse("You updated description",
                            date, time));
                }
            }
            // Category
            if(Objects.equals(activity.getField(), "category")){
                if(!Objects.equals(activity.getOldValue(), activity.getNewValue())){
                    result.add(new ActivityResponse("You changed category from "+activity.getOldValue()+" to "+activity.getNewValue(),
                            date, time));
                }
            }
            // Due date
            if(Objects.equals(activity.getField(), "dueDate")){
                if(!Objects.equals(activity.getOldValue(), activity.getNewValue())){
                    result.add(new ActivityResponse("You changed due date from "+activity.getOldValue()+" to "+activity.getNewValue(),
                            date, time));
                }
            }
            // Status
            if(Objects.equals(activity.getField(), "status")){
                if(!Objects.equals(activity.getOldValue(), activity.getNewValue())){
                    result.add(new ActivityResponse("You changed status from "+activity.getOldValue()+" to "+activity.getNewValue(),
                            date, time));
                }
            }
            //Document
            if(Objects.equals(activity.getField(), "document")){
                if(Objects.equals(activity.getOldValue(), "") && !Objects.equals(activity.getNewValue(),"")){
                    result.add(new ActivityResponse("You uploaded "+activity.getNewValue()+" file",
                            date, time));
                }
                if(!Objects.equals(activity.getOldValue(), "") && Objects.equals(activity.getNewValue(),"")){
                    result.add(new ActivityResponse("You deleted "+activity.getOldValue()+" file",
                            date, time));
                }
            }
        }
        return result;
    }
}
