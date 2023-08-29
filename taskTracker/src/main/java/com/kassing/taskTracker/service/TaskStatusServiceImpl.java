package com.kassing.taskTracker.service;

import com.kassing.taskTracker.DAO.TaskStatusDao;
import com.kassing.taskTracker.DTO.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskStatusServiceImpl implements TaskStatusService{

    @Autowired
    TaskStatusDao taskStatusDao;

    @Override
    public TaskStatus addTaskStatus(TaskStatus taskStatus) throws TaskStatusDataValidationException {
        return taskStatusDao.addTaskStatus(taskStatus);
    }

    @Override
    public List<TaskStatus> getAllTaskStatuses() {
        return taskStatusDao.getAllTaskStatuses();
    }

    @Override
    public TaskStatus getTaskStatusByID(int taskStatusID) {

        try {
            return taskStatusDao.getTaskStatusByID(taskStatusID);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void updateTaskStatus(TaskStatus taskStatus) {

        taskStatusDao.updateTaskStatus(taskStatus);

    }

    @Override
    public void deleteTaskStatusByID(int taskStatusID) {

        taskStatusDao.deleteTaskStatus(taskStatusID);

    }
}
