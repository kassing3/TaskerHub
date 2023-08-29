package com.kassing.taskTracker.service;

import com.kassing.taskTracker.DAO.ProjectDao;
import com.kassing.taskTracker.DTO.Employee;
import com.kassing.taskTracker.DTO.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements  ProjectService{

    @Autowired
    ProjectDao projectDao;

    @Override
    public Project addProject(Project project) throws ProjectDataValidationException {

        validateProjectData(project);
        return projectDao.addProject(project);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectDao.getAllProjects();
    }

    @Override
    public Project getProjectByID(int projectID) {

        try {

            return  projectDao.getProjectByID(projectID);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void updateProject(Project project) {

        projectDao.updateProject(project);

    }

    @Override
    public void deleteProjectByID(int projectID) {

        projectDao.deleteProjectByID(projectID);

    }

    //Validation Helper Functions
    public void validateProjectData(Project project) throws ProjectDataValidationException {
        if(project.getProjectName() == null
                || project.getProjectName().trim().length() ==  0
                ||project.getProjectDesc() == null
                ||project.getProjectDesc().trim().length() ==0
                ||project.getEmployee() == null){
            throw new ProjectDataValidationException("ERROR: Name, Description, and Employee Fields are required.");
        }
    }
}
