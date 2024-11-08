package com.studyProject.QuizeMaster.controller;

import com.studyProject.QuizeMaster.entity.UserAction;
import com.studyProject.QuizeMaster.service.UserActionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actions")
@Tag(name = "User Actions API", description = "API для действий пользователей")
public class UserActionController {

    private final UserActionService userActionService;

    @Autowired
    public UserActionController(UserActionService userActionService) {
        this.userActionService = userActionService;
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Получить действия пользователя", description = "Возвращает список последних действий пользователя")
    public ResponseEntity<List<UserAction>> getUserActions(@PathVariable String userId) {
        List<UserAction> actions = userActionService.getRecentActions(userId);
        return ResponseEntity.status(HttpStatus.OK).body(actions);
    }

    @PostMapping("/answer")
    @Operation(summary = "Ответить на вопрос", description = "Записывает ответ пользователя на вопрос")
    public ResponseEntity<Void> answerQuestion(@RequestParam String userId, @RequestParam String quizId,
                                               @RequestParam String questionId) {
        userActionService.recordAnswer(userId, quizId, questionId);
        return ResponseEntity.ok().build();
    }
}
