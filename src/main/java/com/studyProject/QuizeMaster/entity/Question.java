package com.studyProject.QuizeMaster.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("Question")
public class Question implements Serializable {

    @Id
    private String id;
    private String text;
    private List<String> options;
    private String correctAnswer;
    private String quizId;

    public Question(String id, String text, List<String> options, String correctAnswer) {
        this.id = id;
        this.text = text;
        this.options = options;
    }
}

