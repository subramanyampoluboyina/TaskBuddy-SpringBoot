package com.TaskBuddy.TaskBuddy.Service;

import com.TaskBuddy.TaskBuddy.DAO.MstCategoryDAO;
import com.TaskBuddy.TaskBuddy.Entity.MstCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MstCategoryService {
    private MstCategoryDAO mstCategoryDAO;

    @Autowired
    public MstCategoryService(MstCategoryDAO mstCategoryDAO) {
        this.mstCategoryDAO = mstCategoryDAO;
    }

    public List<MstCategory> GetAllCategories(){
        return mstCategoryDAO.findAll();
    }

    public MstCategory GetCategory(int Id){
        return mstCategoryDAO.findById(Id).orElseThrow(()->new RuntimeException("Category not found"));
    }
}
