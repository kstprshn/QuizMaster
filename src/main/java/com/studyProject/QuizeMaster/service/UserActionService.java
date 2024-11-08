package com.studyProject.QuizeMaster.service;

import com.studyProject.QuizeMaster.entity.UserAction;
import com.studyProject.QuizeMaster.repository.UserActionRepository;
import com.studyProject.QuizeMaster.service.abstr.UserActionServiceAbstr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserActionService implements UserActionServiceAbstr {

    private final UserActionRepository userActionRepository;

    @Autowired
    public UserActionService(UserActionRepository userActionRepository) {
        this.userActionRepository = userActionRepository;
    }

    @Override
    public void recordAction(String userId, UserAction.ActionType actionType, String quizId, String questionId) {
        UserAction action = new UserAction();
        action.setId(UUID.randomUUID().toString());
        action.setUserId(userId);
        action.setActionType(actionType);
        action.setTimestamp(LocalDateTime.now());
        action.setQuizId(quizId);
        action.setQuestionId(questionId);
        userActionRepository.save(action);
    }

    @Override
    public void recordAnswer(String userId, String quizId, String questionId) {
        recordAction(userId, UserAction.ActionType.ANSWER_QUESTION, quizId, questionId);
    }

    @Override
    public List<UserAction> getRecentActions(String userId) {
        return userActionRepository.findByUserId(userId);
    }
}

