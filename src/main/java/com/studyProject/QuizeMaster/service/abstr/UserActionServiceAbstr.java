package com.studyProject.QuizeMaster.service.abstr;

import com.studyProject.QuizeMaster.entity.UserAction;

import java.util.List;

public interface UserActionServiceAbstr {

    List<UserAction> getRecentActions(String userId);

    void recordAction(String userId, UserAction.ActionType actionType, String quizId, String questionId);

    void recordAnswer(String userId, String quizId, String questionId);
}
