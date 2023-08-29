package com.kassing.taskTracker.controller;

import com.kassing.taskTracker.DTO.Employee;
import com.kassing.taskTracker.DTO.Project;
import com.kassing.taskTracker.DTO.Task;
import com.kassing.taskTracker.service.EmployeeServiceImpl;
import com.kassing.taskTracker.service.ProjectDataValidationException;
import com.kassing.taskTracker.service.ProjectServiceImpl;
import com.kassing.taskTracker.service.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class ProjectController {

    @Autowired
    ProjectServiceImpl projectService;

    @Autowired
    EmployeeServiceImpl employeeService;

    @Autowired
    TaskServiceImpl taskService;

    Set<ConstraintViolation<Project>> violations = new HashSet<>();

    @GetMapping("projects")
    public String displayAllProjects(Model model){

        List<Project> projects = projectService.getAllProjects();
        List<Employee> employees = employeeService.getAllEmployees();

        model.addAttribute("projects", projects);
        model.addAttribute("employees",employees);
        model.addAttribute("noFilterSelected", false);
        model.addAttribute("errors", violations);

        return "projects";

    }

    @PostMapping("addProject")
    public String addProject(@Valid Project project, BindingResult result, HttpServletRequest request, Model model) throws ProjectDataValidationException {

        String employeeID = request.getParameter("employeeID");

        project.setEmployee(employeeService.getEmployeeByID(Integer.parseInt(employeeID)));

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(project);

        if(violations.isEmpty()){
            projectService.addProject(project);
        }

        return "redirect:/projects";
    }

    @GetMapping("editProject")
    public String editProject(Integer id, Model model){

        Project project = projectService.getProjectByID(id);
        List<Employee> employees = employeeService.getAllEmployees();

        model.addAttribute("project", project);
        model.addAttribute("employees", employees);

        return "editProject";
    }

    @PostMapping("editProject")
    public String performEditProject(@Valid Project project, BindingResult result, HttpServletRequest request, Model model ){

        String projectName = request.getParameter("projectName");
        String projectDesc = request.getParameter("projectDesc");
        String employeeID = request.getParameter("employeeID");

        project.setProjectName(projectName);
        project.setProjectDesc(projectDesc);
        project.setEmployee(employeeService.getEmployeeByID(Integer.parseInt(employeeID)));

        if(result.hasErrors()) {
            model.addAttribute("project", project);
            model.addAttribute("employees", employeeService.getAllEmployees());

            return "editProject";
        }

        projectService.updateProject(project);

        return "redirect:/projects";
    }

    @GetMapping("projectTasks")
    public String projectTasks(Integer id, Model model) {

        Project project = projectService.getProjectByID(id);
        List<Task> tasks = taskService.getAllTasks();

        model.addAttribute("project", project);
        model.addAttribute("tasks",tasks);

        return "projectTasks";
    }
    @GetMapping("deleteProject")
    public String deleteProject(Integer id) {
        projectService.deleteProjectByID(id);
        return "redirect:/projects";
    }

}
