package com.kassing.taskTracker.service;

import com.kassing.taskTracker.DTO.Project;

import java.util.List;

public interface ProjectService {

    Project addProject(Project project) throws ProjectDataValidationException;
    List<Project> getAllProjects();
    Project getProjectByID(int projectID);
    void updateProject(Project project);
    void deleteProjectByID(int projectID);

}
