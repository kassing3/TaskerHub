package com.kassing.taskTracker.DAO;

import com.kassing.taskTracker.DTO.TaskPriority;

import java.util.List;

public interface TaskPriorityDao {

    TaskPriority getTaskPriorityByID(int taskPriorityID);
    List<TaskPriority> getAllTaskPriorities();
    TaskPriority addTaskPriority(TaskPriority taskPriority);
    void updateTaskPriority(TaskPriority taskPriority);
    void deleteTaskPriorityByID(int taskPriorityID);
}
