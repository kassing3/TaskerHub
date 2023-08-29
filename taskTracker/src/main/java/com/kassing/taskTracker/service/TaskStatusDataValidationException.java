package com.kassing.taskTracker.service;

public class TaskStatusDataValidationException extends Exception{

    public TaskStatusDataValidationException(String message){
        super(message);
    }

    public TaskStatusDataValidationException(String message, Throwable cause){
        super(message,cause);
    }
}
