package com.kassing.taskTracker.service;

import com.kassing.taskTracker.DTO.TaskPriority;
import com.kassing.taskTracker.DTO.TaskStatus;

import java.util.List;

public interface TaskPriorityService {

    TaskPriority addTaskPriority(TaskPriority taskPriorityTye) throws TaskPriorityDataValidationException;
    List<TaskPriority> getAllTaskPriorities();
    TaskPriority getTaskPriorityByID(int taskPriorityTyeID);
    void updateTaskPriority(TaskPriority taskPriorityTye);
    void deleteTaskPriorityByID(int taskPriorityTyeID);
}
