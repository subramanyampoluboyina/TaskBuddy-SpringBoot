package com.TaskBuddy.TaskBuddy.DAO;

import com.TaskBuddy.TaskBuddy.Entity.MstCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MstCategoryDAO extends JpaRepository<MstCategory,Integer> {
}
