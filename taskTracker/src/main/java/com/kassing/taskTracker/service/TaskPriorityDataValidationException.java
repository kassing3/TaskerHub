package com.kassing.taskTracker.service;

public class TaskPriorityDataValidationException extends Exception{

    public TaskPriorityDataValidationException(String message){
        super(message);
    }

    public TaskPriorityDataValidationException(String message, Throwable cause){
        super(message,cause);
    }
}
