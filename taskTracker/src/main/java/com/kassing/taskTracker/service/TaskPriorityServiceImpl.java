package com.kassing.taskTracker.service;

import com.kassing.taskTracker.DAO.TaskPriorityDao;
import com.kassing.taskTracker.DTO.TaskPriority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskPriorityServiceImpl implements TaskPriorityService{

    @Autowired
    TaskPriorityDao taskPriorityDao;

    @Override
    public TaskPriority addTaskPriority(TaskPriority taskPriorityTye) throws TaskPriorityDataValidationException {
        return taskPriorityDao.addTaskPriority(taskPriorityTye);
    }

    @Override
    public List<TaskPriority> getAllTaskPriorities() {
        return taskPriorityDao.getAllTaskPriorities();
    }

    @Override
    public TaskPriority getTaskPriorityByID(int taskPriorityTyeID) {

        try {
            return taskPriorityDao.getTaskPriorityByID(taskPriorityTyeID);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void updateTaskPriority(TaskPriority taskPriorityTye) {

        taskPriorityDao.updateTaskPriority(taskPriorityTye);

    }

    @Override
    public void deleteTaskPriorityByID(int taskPriorityTyeID) {

        taskPriorityDao.deleteTaskPriorityByID(taskPriorityTyeID);

    }
}
