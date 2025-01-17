package com.studyProject.QuizeMaster.repository;

import com.studyProject.QuizeMaster.entity.Quiz;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends CrudRepository<Quiz, String> {
}
