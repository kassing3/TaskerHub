package com.kassing.taskTracker.DAO;

import com.kassing.taskTracker.DAO.Mappers.TaskPriorityMapper;
import com.kassing.taskTracker.DTO.TaskPriority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TaskPriorityDaoDB implements TaskPriorityDao{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public TaskPriority getTaskPriorityByID(int taskPriorityID) {

        try {
            final String GET_TASK_PRIORITY_BY_ID = "SELECT * FROM taskPriorityType WHERE taskPriorityTypeID = ?;";

            TaskPriority taskPriority = jdbcTemplate.queryForObject(GET_TASK_PRIORITY_BY_ID, new TaskPriorityMapper(), taskPriorityID);

            return  taskPriority;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<TaskPriority> getAllTaskPriorities() {

        final String GET_ALL_TASK_PRIORITIES = "SELECT * FROM taskPriorityType;";

        return jdbcTemplate.query(GET_ALL_TASK_PRIORITIES, new TaskPriorityMapper());
    }

    @Override
    @Transactional
    public TaskPriority addTaskPriority(TaskPriority taskPriority) {

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        final String ADD_TASK_PRIORITY = "INSERT INTO taskPriorityType(taskPriorityType, taskPriorityDesc) VALUES (?,?);";

        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(
                    ADD_TASK_PRIORITY,
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setString(1, taskPriority.getTaskPriorityType());
            statement.setString(2, taskPriority.getTaskPriorityDesc());

            return statement;
        }, keyHolder);

        taskPriority.setTaskPriorityID(keyHolder.getKey().intValue());

        return taskPriority;
    }

    @Override
    public void updateTaskPriority(TaskPriority taskPriority) {

        final String UPDATE_TASK_PRIORITY = "UPDATE taskPriorityType SET taskPriorityType =?, taskPriorityDesc = ? WHERE taskPriorityTypeID = ?;";

        jdbcTemplate.update(UPDATE_TASK_PRIORITY,
                taskPriority.getTaskPriorityType(),
                taskPriority.getTaskPriorityDesc(),
                taskPriority.getTaskPriorityID());

    }

    @Override
    @Transactional
    public void deleteTaskPriorityByID(int taskPriorityID) {

        final String UPDATE_TASK_TASK_PRIORITY = "UPDATE Task set taskPriorityTypeID = NULL WHERE taskPriorityTypeID = ?;";
        jdbcTemplate.update(UPDATE_TASK_TASK_PRIORITY, taskPriorityID);

        final String DELETE_TASK_PRIORITY = "DELETE FROM taskPriorityType WHERE taskPriorityTypeID = ?;";
        jdbcTemplate.update(DELETE_TASK_PRIORITY, taskPriorityID);


    }
}
