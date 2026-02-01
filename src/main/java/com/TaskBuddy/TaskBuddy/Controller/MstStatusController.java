package com.TaskBuddy.TaskBuddy.Controller;

import com.TaskBuddy.TaskBuddy.Entity.APIResponse;
import com.TaskBuddy.TaskBuddy.Entity.MstStatus;
import com.TaskBuddy.TaskBuddy.Service.MstStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MstStatusController {
    private MstStatusService mstStatusService;

    @Autowired
    public MstStatusController(MstStatusService mstStatusService) {
        this.mstStatusService = mstStatusService;
    }
    @GetMapping("MstStatus")
    public ResponseEntity<?> GetAllStatuses(){
        List<MstStatus> statuses = mstStatusService.GetAllStatuses();
        if(statuses.size()>0){
            return new ResponseEntity<>(new APIResponse("Success","Statuses retrieved",statuses),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new APIResponse("Failed","No statuses found",null),HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/MstStatus/{Id}")
    public ResponseEntity<?> GetStatus(@PathVariable int Id){
        MstStatus status = mstStatusService.GetStatus(Id);
        if (status!=null){
            return new ResponseEntity<>(new APIResponse("Success","Status retrieved",status),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new APIResponse("Failed","No status found",null),HttpStatus.NOT_FOUND);
        }
    }

}
