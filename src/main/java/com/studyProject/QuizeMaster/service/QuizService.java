package com.studyProject.QuizeMaster.service;

import com.studyProject.QuizeMaster.entity.Quiz;
import com.studyProject.QuizeMaster.entity.UserAction;
import com.studyProject.QuizeMaster.repository.QuizRepository;
import com.studyProject.QuizeMaster.service.abstr.QuizServiceAbstr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class QuizService implements QuizServiceAbstr {

    private final QuizRepository quizRepository;
    private final UserActionService userActionService;

     private final RedisCacheManager quizCacheManager;


    @Autowired
    public QuizService(QuizRepository quizRepository, UserActionService userActionService,
                       @Qualifier("quizCacheManager") RedisCacheManager quizCacheManager) {
        this.quizRepository = quizRepository;
        this.userActionService = userActionService;
        this.quizCacheManager = quizCacheManager;
    }

    @CachePut(value = "quizzes", key = "#id", cacheManager = "quizCacheManager")
    @Override
    public Quiz createQuiz(String userId, String quizName) {
        Quiz quiz = new Quiz(UUID.randomUUID().toString(), quizName, new ArrayList<>());
        quizRepository.save(quiz);
        userActionService.recordAction(userId, UserAction.ActionType.CREATE_QUIZ, quiz.getId(), null);
        return quiz;
    }

    @Override
    public Optional<Quiz> getQuizById(String id) {
        return quizRepository.findById(id);
    }

    @Override
    @Cacheable(value = "quizzes", cacheManager = "quizCacheManager")
    public List<Quiz> getAllQuizzes() {
        return (List<Quiz>) quizRepository.findAll();
    }

    @Override
    @CacheEvict(value = "quizzes", key = "#id")
    public void deleteQuiz(String id) {
        quizRepository.deleteById(id);
    }
}
