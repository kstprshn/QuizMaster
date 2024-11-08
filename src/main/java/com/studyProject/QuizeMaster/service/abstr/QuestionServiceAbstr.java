package com.studyProject.QuizeMaster.service.abstr;

import com.studyProject.QuizeMaster.entity.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionServiceAbstr {

    Optional<Question> getQuestionById(String id);

    List<Question> getQuestionsForQuiz(String quizId);

    Question addQuestionToQuiz(String userId, String quizId, String text, List<String> options, String correctAnswer);

    void deleteQuestion(String id);

}
