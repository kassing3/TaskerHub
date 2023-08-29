package com.kassing.taskTracker.service;

public class ProjectDataValidationException extends Exception{

    public ProjectDataValidationException(String message){
        super(message);
    }

    public ProjectDataValidationException(String message, Throwable cause){
        super(message,cause);
    }
}
