package com.kassing.taskTracker.DAO;

import com.kassing.taskTracker.DTO.TaskStatus;

import java.util.List;

public interface TaskStatusDao {

    TaskStatus getTaskStatusByID(int taskStatusID);
    List<TaskStatus> getAllTaskStatuses();
    TaskStatus addTaskStatus(TaskStatus taskStatus);
    void updateTaskStatus(TaskStatus taskStatus);
    void deleteTaskStatus(int taskStatusID);

}
