package com.TaskBuddy.TaskBuddy.DAO;

import com.TaskBuddy.TaskBuddy.Entity.MstStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MstStatusDAO extends JpaRepository<MstStatus, Integer> {
}
