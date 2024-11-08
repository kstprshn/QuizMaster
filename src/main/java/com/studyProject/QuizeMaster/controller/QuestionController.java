package com.studyProject.QuizeMaster.controller;

import com.studyProject.QuizeMaster.entity.Question;
import com.studyProject.QuizeMaster.excepions.QuestionNotFoundException;
import com.studyProject.QuizeMaster.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/question")
@Tag(name = "Question API", description = "API для управления вопросами викторин")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/quiz/{quizId}")
    @Operation(summary = "Получить вопросы викторины", description = "Возвращает список вопросов для викторины по ID викторины")
    public ResponseEntity<List<Question>> getQuestionsForQuiz(@PathVariable String quizId) {
        List<Question> questions = questionService.getQuestionsForQuiz(quizId);
        return ResponseEntity.status(HttpStatus.OK).body(questions);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить вопрос по ID", description = "Возвращает вопрос по его ID")
    public ResponseEntity<Question> getQuestion(@PathVariable String id) {
        Question foundQuestion = questionService.getQuestionById(id).orElseThrow(() -> new QuestionNotFoundException("Question not found"));
        return new ResponseEntity<>(foundQuestion, HttpStatus.OK);
    }

    @PostMapping("/add")
    @Operation(summary = "Добавить вопрос", description = "Добавляет новый вопрос к викторине")
    public ResponseEntity<Question> addQuestion(@RequestParam String userId, @RequestParam String quizId,
                                                @RequestParam String text, @RequestParam List<String> options,
                                                @RequestParam String correctAnswer) {

        Question question = questionService.addQuestionToQuiz(userId, quizId, text, options, correctAnswer);
        return ResponseEntity.status(HttpStatus.CREATED).body(question);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить вопрос", description = "Удаляет вопрос по его ID")
    public ResponseEntity<Void> deleteQuestion(@PathVariable String id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
