package com.kassing.taskTracker.service;

public class TaskDataValidationException extends Exception{

    public TaskDataValidationException(String message){
        super(message);
    }

    public TaskDataValidationException(String message, Throwable cause){
        super(message,cause);
    }
}
