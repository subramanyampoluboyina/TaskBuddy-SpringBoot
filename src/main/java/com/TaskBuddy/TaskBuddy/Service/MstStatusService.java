package com.TaskBuddy.TaskBuddy.Service;

import com.TaskBuddy.TaskBuddy.DAO.MstStatusDAO;
import com.TaskBuddy.TaskBuddy.Entity.MstStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MstStatusService {
    private MstStatusDAO mstStatusDAO;

    @Autowired
    public MstStatusService(MstStatusDAO mstStatusDAO) {
        this.mstStatusDAO = mstStatusDAO;
    }

    public List<MstStatus> GetAllStatuses(){
        return mstStatusDAO.findAll();
    }

    public MstStatus GetStatus(int Id) {
        return mstStatusDAO.findById(Id).orElseThrow(() -> new RuntimeException("Status not found "));
    }
}
