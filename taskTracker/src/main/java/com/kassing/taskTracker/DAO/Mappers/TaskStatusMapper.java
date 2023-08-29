package com.kassing.taskTracker.DAO.Mappers;

import com.kassing.taskTracker.DTO.TaskStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskStatusMapper implements RowMapper<TaskStatus> {
    @Override
    public TaskStatus mapRow(ResultSet rs, int rowNum) throws SQLException {

        TaskStatus taskStatus = new TaskStatus();

        taskStatus.setTaskStatusID(rs.getInt("taskStatusID"));
        taskStatus.setTaskStatusName(rs.getString("taskStatusName"));
        taskStatus.setTaskStatusDesc(rs.getString("taskStatusDesc"));

        return taskStatus;
    }
}
