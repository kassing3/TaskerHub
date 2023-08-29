package com.kassing.taskTracker.service;

import com.kassing.taskTracker.DTO.Employee;
import com.kassing.taskTracker.DTO.Project;
import com.kassing.taskTracker.DTO.Task;

import java.util.List;

public interface TaskService {

    Task addTask(Task task) throws TaskDataValidationException;
    List<Task> getAllTasks();
    Task getTaskByID(int taskID);
    void updateTask(Task task);
    void deleteTaskById(int taskID);

    List<Task> getTasksByEmployees(Employee employee);
    List<Task> getTasksByProject(Project project);
}
