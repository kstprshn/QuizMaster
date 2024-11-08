package com.studyProject.QuizeMaster.service.abstr;

import com.studyProject.QuizeMaster.entity.Quiz;

import java.util.List;
import java.util.Optional;

public interface QuizServiceAbstr {

    List<Quiz> getAllQuizzes();

    Quiz createQuiz(String userId, String quizName);

    Optional<Quiz> getQuizById(String id);

    void deleteQuiz(String id);

}
