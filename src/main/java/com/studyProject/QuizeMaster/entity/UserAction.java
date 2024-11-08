package com.studyProject.QuizeMaster.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("UserAction")
public class UserAction implements Serializable {

    @Id
    private String id;
    private String userId;
    private ActionType actionType;
    private LocalDateTime timestamp;
    private String quizId;
    private String questionId;

    public enum ActionType {
        CREATE_QUIZ,
        ADD_QUESTION,
        ANSWER_QUESTION
    }
}
