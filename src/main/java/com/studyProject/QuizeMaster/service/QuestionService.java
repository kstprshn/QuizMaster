package com.studyProject.QuizeMaster.service;

import com.studyProject.QuizeMaster.entity.Question;
import com.studyProject.QuizeMaster.entity.Quiz;
import com.studyProject.QuizeMaster.entity.UserAction;
import com.studyProject.QuizeMaster.repository.QuestionRepository;
import com.studyProject.QuizeMaster.repository.QuizRepository;
import com.studyProject.QuizeMaster.service.abstr.QuestionServiceAbstr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuestionService implements QuestionServiceAbstr {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final UserActionService userActionService;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, QuizRepository quizRepository, UserActionService userActionService) {
        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
        this.userActionService = userActionService;
    }

    @Cacheable(value = "questions", key = "#id")
    @Override
    public Optional<Question> getQuestionById(String id) {
        return questionRepository.findById(id);
    }

    @Override
    public List<Question> getQuestionsForQuiz(String quizId) {
        return ((List<Question>) questionRepository.findAll()).stream()
                .filter(question -> question.getQuizId().equals(quizId))
                .collect(Collectors.toList());
    }

    @Override
    public Question addQuestionToQuiz(String userId, String quizId, String text, List<String> options, String correctAnswer) {
        Question question = new Question(UUID.randomUUID().toString(), text, options, correctAnswer, quizId);
        questionRepository.save(question);

        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new NoSuchElementException("Quiz not found"));
        quiz.getQuestionIds().add(question.getId());
        quizRepository.save(quiz);

        userActionService.recordAction(userId, UserAction.ActionType.ADD_QUESTION, quizId, question.getId());
        return question;
    }

    @Override
    @CacheEvict(value = "questions", key = "#id")
    public void deleteQuestion(String id) {
        questionRepository.deleteById(id);
    }
}
