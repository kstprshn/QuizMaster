package com.studyProject.QuizeMaster.excepions;

public class QuestionNotFoundException extends RuntimeException{
    public QuestionNotFoundException(String message){
        super(message);
    }
}
