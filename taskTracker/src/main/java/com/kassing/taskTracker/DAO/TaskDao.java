package com.kassing.taskTracker.DAO;

import com.kassing.taskTracker.DTO.Employee;
import com.kassing.taskTracker.DTO.Project;
import com.kassing.taskTracker.DTO.Task;

import java.util.List;

public interface TaskDao {

    Task getTaskByID(int taskID);
    List<Task> getAllTasks();
    Task addTask(Task task);
    void updateTask(Task task);
    void deleteTaskByID (int taskID);
    List<Task> getTasksByEmployee(Employee employee);
    List<Task> getTasksByProject(Project Project);
 }
