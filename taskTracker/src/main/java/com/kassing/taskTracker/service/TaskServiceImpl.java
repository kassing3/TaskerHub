package com.kassing.taskTracker.service;

import com.kassing.taskTracker.DAO.*;
import com.kassing.taskTracker.DTO.Employee;
import com.kassing.taskTracker.DTO.Project;
import com.kassing.taskTracker.DTO.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{

    @Autowired
    TaskDao taskDao;

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    TaskStatusDao taskStatusDao;

    @Autowired
    TaskPriorityDao taskPriorityDao;

    @Autowired
    ProjectDao projectDao;

    @Override
    public Task addTask(Task task) throws TaskDataValidationException {

        validateTaskData(task);
        return taskDao.addTask(task);
    }

    @Override
    public List<Task> getAllTasks() {

        return taskDao.getAllTasks();
    }

    @Override
    public Task getTaskByID(int taskID) {

        try {
            return taskDao.getTaskByID(taskID);
        } catch (DataAccessException ex ) {
            return null;
        }
    }

    @Override
    public void updateTask(Task task) {

        taskDao.updateTask(task);

    }

    @Override
    public void deleteTaskById(int taskID) {

        taskDao.deleteTaskByID(taskID);

    }

    @Override
    public List<Task> getTasksByEmployees(Employee employee) {
        return taskDao.getTasksByEmployee(employee);
    }

    @Override
    public List<Task> getTasksByProject(Project project) {
        return taskDao.getTasksByProject(project);
    }

    public void validateTaskData(Task task) throws TaskDataValidationException{
        if(task.getTaskName() == null
                || task.getTaskName().trim().length() == 0
                || task.getTaskDesc() == null
                || task.getTaskDesc().trim().length() == 0) {

            throw new TaskDataValidationException("ERROR fields are required.");
        }
    }
}
