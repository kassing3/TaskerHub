package com.kassing.taskTracker.service;


import com.kassing.taskTracker.DTO.TaskStatus;

import java.util.List;

public interface TaskStatusService {

    TaskStatus addTaskStatus(TaskStatus taskStatus) throws TaskStatusDataValidationException;
    List<TaskStatus> getAllTaskStatuses();
    TaskStatus getTaskStatusByID(int taskStatusID);
    void updateTaskStatus(TaskStatus taskStatus);
    void deleteTaskStatusByID(int taskStatusID);

}
