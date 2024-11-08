package com.studyProject.QuizeMaster.controller;

import com.studyProject.QuizeMaster.entity.Quiz;
import com.studyProject.QuizeMaster.excepions.QuizNotFoundException;
import com.studyProject.QuizeMaster.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
@Tag(name = "Quiz API", description = "API для управления викторинами")
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }



    @GetMapping("/all")
    @Operation(summary = "Получить все викторины", description = "Возвращает список всех викторин")
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        List<Quiz> quizzes = quizService.getAllQuizzes();
        return ResponseEntity.ok(quizzes);
    }

    @PostMapping("/create")
    @Operation(summary = "Создать викторину", description = "Создает новую викторину для пользователя")
    public ResponseEntity<Quiz> createQuiz(@RequestParam String userId, @RequestParam String quizName) {
        Quiz newQuiz = quizService.createQuiz(userId, quizName);
        return ResponseEntity.status(HttpStatus.CREATED).body(newQuiz);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить викторину по ID", description = "Возвращает викторину по ее ID")
    public ResponseEntity<Quiz> getQuiz(@PathVariable String id) {
        Quiz foundQuiz = quizService.getQuizById(id).orElseThrow(
                () -> new QuizNotFoundException("Quiz not found"));

        return ResponseEntity.status(HttpStatus.OK).body(foundQuiz);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить викторину", description = "Удаляет викторину по ее ID")
    public ResponseEntity<Void> deleteQuiz(@PathVariable String id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.noContent().build();
    }
}

