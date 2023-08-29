package com.kassing.taskTracker.service;

public class EmployeeDataValidationException extends  Exception{

    public EmployeeDataValidationException(String message){
        super(message);
    }

    public EmployeeDataValidationException(String message, Throwable cause){
        super(message,cause);
    }
}
