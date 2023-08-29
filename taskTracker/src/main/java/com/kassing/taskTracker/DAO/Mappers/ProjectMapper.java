package com.kassing.taskTracker.DAO.Mappers;

import com.kassing.taskTracker.DTO.Project;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectMapper implements RowMapper<Project> {
    @Override
    public Project mapRow(ResultSet rs, int rowNum) throws SQLException {

        Project project = new Project();

        project.setProjectID(rs.getInt("projectID"));
        project.setProjectName(rs.getString("projectName"));
        project.setProjectDesc(rs.getString("projectDesc"));

        return project;
    }
}
