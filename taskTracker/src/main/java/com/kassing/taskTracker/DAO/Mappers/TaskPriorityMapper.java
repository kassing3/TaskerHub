package com.kassing.taskTracker.DAO.Mappers;


import com.kassing.taskTracker.DTO.TaskPriority;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskPriorityMapper implements RowMapper<TaskPriority> {
    @Override
    public TaskPriority mapRow(ResultSet rs, int rowNum) throws SQLException {

        TaskPriority taskPriority = new TaskPriority();

        taskPriority.setTaskPriorityID(rs.getInt("taskPriorityTypeID"));
        taskPriority.setTaskPriorityType(rs.getString("taskPriorityType"));
        taskPriority.setTaskPriorityDesc(rs.getString("taskPriorityDesc"));

        return taskPriority;
    }
}
