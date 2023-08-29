package com.kassing.taskTracker.DAO;

import com.kassing.taskTracker.DAO.Mappers.TaskStatusMapper;
import com.kassing.taskTracker.DTO.Task;
import com.kassing.taskTracker.DTO.TaskStatus;
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
public class TaskStatusDaoDB implements TaskStatusDao{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public TaskStatus getTaskStatusByID(int taskStatusID) {
        try {
            final String GET_TASK_STATUS_BY_ID = "SELECT * FROM taskStatus WHERE taskStatusID = ?;";

            TaskStatus taskStatus = jdbcTemplate.queryForObject(GET_TASK_STATUS_BY_ID, new TaskStatusMapper(), taskStatusID);

            return taskStatus;

        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<TaskStatus> getAllTaskStatuses() {

        final String GET_ALL_TASK_STATUSES = "SELECT * FROM taskStatus;";

        return jdbcTemplate.query(GET_ALL_TASK_STATUSES, new TaskStatusMapper());
    }

    @Override
    @Transactional
    public TaskStatus addTaskStatus(TaskStatus taskStatus) {

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        final String ADD_TASK_STATUS = "INSERT INTO taskStatus(taskStatusName, taskStatusDesc) VALUES (?,?);";

        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(
                    ADD_TASK_STATUS,
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setString(1, taskStatus.getTaskStatusName());
            statement.setString(2, taskStatus.getTaskStatusDesc());

            return statement;

        },keyHolder);

        taskStatus.setTaskStatusID(keyHolder.getKey().intValue());

        return taskStatus;
    }

    @Override
    public void updateTaskStatus(TaskStatus taskStatus) {

        final String UPDATE_TASK_STATUS = "UPDATE taskStatus SET taskStatusName = ?, taskStatusDesc = ? WHERE taskStatusID = ?;";

        jdbcTemplate.update(UPDATE_TASK_STATUS,
                taskStatus.getTaskStatusName(),
                taskStatus.getTaskStatusDesc(),
                taskStatus.getTaskStatusID());
    }

    @Override
    @Transactional
    public void deleteTaskStatus(int taskStatusID) {

        final String UPDATE_TASK_TASK_STATUS = "UPDATE Task SET taskStatusID = NULL WHERE taskStatusID = ?;";
        jdbcTemplate.update(UPDATE_TASK_TASK_STATUS, taskStatusID);

        final String DELETE_STATUS = "DELETE FROM taskStatus WHERE taskStatusID = ?;";
        jdbcTemplate.update(DELETE_STATUS, taskStatusID);

    }
}
