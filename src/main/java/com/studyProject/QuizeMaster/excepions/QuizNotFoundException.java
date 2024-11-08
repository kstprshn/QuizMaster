package com.studyProject.QuizeMaster.excepions;

public class QuizNotFoundException extends RuntimeException {
    public QuizNotFoundException(String message) {
        super(message);
    }

}
