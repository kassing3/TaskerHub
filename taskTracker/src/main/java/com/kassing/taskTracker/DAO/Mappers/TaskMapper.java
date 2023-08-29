package com.kassing.taskTracker.DAO.Mappers;

import com.kassing.taskTracker.DTO.Task;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskMapper implements RowMapper<Task> {
    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {

        Task task = new Task();

        task.setTaskID(rs.getInt("taskID"));
        task.setTaskName(rs.getString("taskName"));
        task.setTaskDesc(rs.getString("taskDesc"));
        task.setTaskDueDate(rs.getDate("taskDueDate").toLocalDate());

        return task;
    }
}
