package com.kassing.taskTracker.service;

public class DepartmentDataValidationException extends Exception{

    public DepartmentDataValidationException(String message){
        super(message);
    }

    public DepartmentDataValidationException(String message, Throwable cause){
        super(message,cause);
    }
}
