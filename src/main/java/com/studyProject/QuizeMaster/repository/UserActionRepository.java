package com.studyProject.QuizeMaster.repository;

import com.studyProject.QuizeMaster.entity.UserAction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserActionRepository extends CrudRepository<UserAction, String> {
    List<UserAction> findByUserId(String userId);
}
