package com.studyProject.QuizeMaster.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("Quiz")
public class Quiz implements Serializable {

    @Id
    private String id;
    private String name;
    private List<String> questionIds = new ArrayList<>();

}
