package com.kassing.taskTracker.controller;

import com.kassing.taskTracker.DTO.*;
import com.kassing.taskTracker.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
public class TaskController {

    @Autowired
    TaskService taskService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    TaskStatusService taskStatusService;
    @Autowired
    TaskPriorityServiceImpl taskPriorityService;

    @Autowired
    ProjectService projectService;

    @GetMapping("tasks")
    public String displayAllTasks(Model model) {

        List<Task> tasks = taskService.getAllTasks();
        List<Employee> employees = employeeService.getAllEmployees();
        List<Project> projects = projectService.getAllProjects();

        model.addAttribute("tasks", tasks);
        model.addAttribute("employees", employees);
        model.addAttribute("projects", projects);
        model.addAttribute("noFilterSelected", false);

        return "tasks";

    }

    @GetMapping("filterTasks")
    public String displayEmployeesFilter(Integer[] employeeID, Model model){

        Set<Task> filteredTasks = new HashSet<>();
        List<Employee> employees = employeeService.getAllEmployees();

        model.addAttribute("employees", employees);

        if(employeeID == null) {

            model.addAttribute("tasks", filteredTasks);
            model.addAttribute("noFilterSelected", true);

            return "tasks";
        } else {

            List<Employee> requestEmployees = new ArrayList<>();
            List<Integer> requestEmployeesIDs = new ArrayList<>();

            if(employeeID != null) {

                for(Integer employID : employeeID) {
                    requestEmployees.add(employeeService.getEmployeeByID(employID));
                }

                for (Employee requestEmployee : requestEmployees) {
                    filteredTasks.addAll(taskService.getTasksByEmployees(requestEmployee));
                }

                requestEmployeesIDs = requestEmployees.stream()
                        .map(Employee::getEmployeeID)
                        .collect(Collectors.toList());


                model.addAttribute("requestEmployeesIDs",requestEmployeesIDs);


            }
        }



        model.addAttribute("noFilterSelected", false);
        model.addAttribute("tasks", filteredTasks);


        return "filterTasks";
    }

    @GetMapping("addTask")
    public String addTask(Model model){

        List<TaskStatus> taskStatuses = taskStatusService.getAllTaskStatuses();
        List<TaskPriority> taskPriorities = taskPriorityService.getAllTaskPriorities();
        List<Project> projects = projectService.getAllProjects();
        List<Employee> employees = employeeService.getAllEmployees();

        model.addAttribute("task", new Task());
        model.addAttribute("taskStatuses", taskStatuses);
        model.addAttribute("taskPriorities",taskPriorities );
        model.addAttribute("projects", projects);
        model.addAttribute("employees", employees);

        return "addTask";
    }

    @PostMapping("addTask")
    public String performAddTask(@Valid Task task, BindingResult result, HttpServletRequest request, Model model) throws TaskDataValidationException{

        String taskDueDate = request.getParameter("taskDueDate");
        String projectID = request.getParameter("projectID");
        String taskPriorityID = request.getParameter("taskPriorityID");
        String taskStatusID = request.getParameter("taskStatusID");
        String[] employeeIDs = request.getParameterValues("employeeID");

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/mm/dd") ;

        task.setTaskDueDate(LocalDate.parse(taskDueDate));
        task.setProject(projectService.getProjectByID(Integer.parseInt(projectID)));
        task.setTaskPriority(taskPriorityService.getTaskPriorityByID(Integer.parseInt(taskPriorityID)));
        task.setTaskStatus(taskStatusService.getTaskStatusByID(Integer.parseInt(taskStatusID)));



        List<Employee> employees = new ArrayList<>();
        if(employeeIDs != null) {
            for (String employeeID : employeeIDs) {
                employees.add(employeeService.getEmployeeByID(Integer.parseInt(employeeID)));
            }
        }

        task.setEmployees(employees);

        taskService.addTask(task);

//        if(result.hasErrors()){
//            model.addAttribute("task", task);
//            model.addAttribute("taskStatuses", taskStatusService.getAllTaskStatuses());
//            model.addAttribute("taskPriorities",taskPriorityService.getAllTaskPriorities() );
//            model.addAttribute("projects", projectService.getAllProjects());
//            model.addAttribute("employees", employeeService.getAllEmployees());
//
//
//            return "addTask";
//        }

        return "redirect:/tasks";
    }

    @GetMapping("editTask")
    public String editTask(Integer id, Model model){

        Task task = taskService.getTaskByID(id);
        List<TaskStatus> taskStatuses = taskStatusService.getAllTaskStatuses();
        List<TaskPriority> taskPriorities = taskPriorityService.getAllTaskPriorities();
        List<Project> projects = projectService.getAllProjects();
        List<Employee> employees = employeeService.getAllEmployees();

        List<Integer> taskEmployeeIDs = task.getEmployees().stream()
                .map(Employee::getEmployeeID)
                .collect(Collectors.toList());

        model.addAttribute("task", task);
        model.addAttribute("taskStatuses", taskStatuses);
        model.addAttribute("taskPriorities",taskPriorities );
        model.addAttribute("projects", projects);
        model.addAttribute("employees", employees);
        model.addAttribute("taskEmployeeIDs", taskEmployeeIDs);

        return "editTask";
    }

    @PostMapping("editTask")
    public String performEditTask(@Valid Task task, BindingResult result, HttpServletRequest request, Model model) throws TaskDataValidationException{

        String taskName = request.getParameter("taskName");
        String taskDesc = request.getParameter("taskDesc");
        String taskDueDate = request.getParameter("taskDueDate");
        String projectID = request.getParameter("projectID");
        String taskPriorityID = request.getParameter("taskPriorityID");
        String taskStatusID = request.getParameter("taskStatusID");
        String[] taskEmployees = request.getParameterValues("employeeID");

        task.setTaskName(taskName);
        task.setTaskDesc(taskDesc);
        task.setTaskDueDate(LocalDate.parse(taskDueDate));
        task.setProject(projectService.getProjectByID(Integer.parseInt(projectID)));
        task.setTaskPriority(taskPriorityService.getTaskPriorityByID(Integer.parseInt(taskPriorityID)));
        task.setTaskStatus(taskStatusService.getTaskStatusByID(Integer.parseInt(taskStatusID)));

        List<Employee> employees = new ArrayList<>();

        if(taskEmployees != null) {
            for (String employeeID : taskEmployees) {
                employees.add(employeeService.getEmployeeByID(Integer.parseInt(employeeID)));
            }
        }

        task.setEmployees(employees);

        taskService.updateTask(task);

//        if(result.hasErrors()){
//            model.addAttribute("task", task);
//            model.addAttribute("taskStatuses", taskStatusService.getAllTaskStatuses());
//            model.addAttribute("taskPriorities",taskPriorityService.getAllTaskPriorities() );
//            model.addAttribute("projects", projectService.getAllProjects());
//            model.addAttribute("employees", employeeService.getAllEmployees());
//            model.addAttribute("taskEmployeeIDs", task.getEmployees().stream()
//                    .map(Employee::getEmployeeID)
//                    .collect(Collectors.toList()));
//
//            return "editTask";
//        }

        return "redirect:/tasks";



    }


    @GetMapping("taskDetails")
    public String taskDetails(Integer id, Model model) {

        Task task = taskService.getTaskByID(id);
        List<TaskStatus> taskStatuses = taskStatusService.getAllTaskStatuses();
        List<TaskPriority> taskPriorities = taskPriorityService.getAllTaskPriorities();
        List<Project> projects = projectService.getAllProjects();
        List<Employee> employees = employeeService.getAllEmployees();


        model.addAttribute("task", task);
        model.addAttribute("taskStatuses", taskStatuses);
        model.addAttribute("taskPriorities",taskPriorities );
        model.addAttribute("projects", projects);
        model.addAttribute("employees", employees);

        return "taskDetails";


    }

    @GetMapping("deleteTask")
    public String deleteTask(Integer id) {

        taskService.deleteTaskById(id);

        return "redirect:/tasks";
    }



}
