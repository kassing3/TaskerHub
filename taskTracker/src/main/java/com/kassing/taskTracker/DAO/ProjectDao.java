package com.kassing.taskTracker.DAO;

import com.kassing.taskTracker.DTO.Employee;
import com.kassing.taskTracker.DTO.Project;

import java.util.List;

public interface ProjectDao {

    Project getProjectByID(int projectID);
    List<Project> getAllProjects();
    Project addProject(Project project);
    void updateProject(Project project);
    void deleteProjectByID(int projectID);
}
