package com.TaskBuddy.TaskBuddy.Controller;

import com.TaskBuddy.TaskBuddy.Entity.APIResponse;
import com.TaskBuddy.TaskBuddy.Entity.MstCategory;
import com.TaskBuddy.TaskBuddy.Service.MstCategoryService;
import com.TaskBuddy.TaskBuddy.Service.MstStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MstCategoryController {
    private MstCategoryService mstCategoryService;

    @Autowired
    public MstCategoryController(MstCategoryService mstCategoryService) {
        this.mstCategoryService = mstCategoryService;
    }

    @GetMapping("MstCategory")
    public ResponseEntity<?> GetAllCategories(){
        List<MstCategory> categories = mstCategoryService.GetAllCategories();
        if(categories.size()>0){
            return new ResponseEntity<>(new APIResponse("Success","Categories retrieved",categories), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new APIResponse("Failed","No Categories found",null),HttpStatus.NOT_FOUND);
        }
    }
}
