package com.kassing.taskTracker.controller;

import com.kassing.taskTracker.DAO.TaskDao;
import com.kassing.taskTracker.DTO.*;
import com.kassing.taskTracker.service.*;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class EmployeeController {

    @Autowired
    EmployeeServiceImpl employeeService;

    @Autowired
    DepartmentServiceImpl departmentService;

    @Autowired
    TaskDao taskDao;

    @Autowired
    TaskStatusServiceImpl statusService;

    @Autowired
    ProjectService projectService;


    Set<ConstraintViolation<Employee>> violations = new HashSet<>();

    @GetMapping("employees")
    public String displayAllEmployees(Model model) {

        List<Employee> employees = employeeService.getAllEmployees();
        List<Task> tasks = taskDao.getAllTasks();
        List<Department> departments = departmentService.getAllDepartments();

        model.addAttribute("employees", employees);
        model.addAttribute("tasks", tasks);
        model.addAttribute("departments", departments);
        model.addAttribute("noFilterSelected", false);
        model.addAttribute("errors", violations);

        return "employees";

    }

    @GetMapping("employeeTasks")
    public String employeeTasks(Integer id, Model model) {
        Employee employee = employeeService.getEmployeeByID(id);
        List<Department> departments = departmentService.getAllDepartments();
        List<Project> projects = projectService.getAllProjects();
        List<Task> tasks = taskDao.getAllTasks();
        List<TaskStatus> taskStatuses = statusService.getAllTaskStatuses();


        model.addAttribute("employee", employee);
        model.addAttribute("departments", departments);
        model.addAttribute("projects", projects);
        model.addAttribute("tasks", tasks);
        model.addAttribute("taskStatuses", taskStatuses);

        return "employeeTasks";
    }

    @PostMapping("addEmployee")
    public String addEmployee(@Valid Employee employee, BindingResult result, HttpServletRequest request, Model model) throws EmployeeDataValidationException {

        String departmentID = request.getParameter("departmentID");


        employee.setDepartment(departmentService.getDepartmentByID(Integer.parseInt(departmentID)));


        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(employee);

        if(violations.isEmpty()){
            employeeService.addEmployee(employee);
        }

        return "redirect:/employees";

    }

    @GetMapping("editEmployee")
    public String editEmployee(Integer id, Model model) {

        Employee employee = employeeService.getEmployeeByID(id);
        List<Department> departments = departmentService.getAllDepartments();

        model.addAttribute("employee", employee);
        model.addAttribute("departments", departments);

        return "editEmployee";

    }

    @PostMapping("editEmployee")
    public String performEditEmployee(@Valid Employee employee, BindingResult result, HttpServletRequest request, Model model){

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String departmentID = request.getParameter("departmentID");


        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setDepartment(departmentService.getDepartmentByID(Integer.parseInt(departmentID)));

        if(result.hasErrors())  {
            model.addAttribute("employee", employee);
            model.addAttribute("departments", departmentService.getAllDepartments());

            return "editEmployee";
        }

        employeeService.updateEmployee(employee);
        return "redirect:/employees";

    }

    @GetMapping("deleteEmployee")
    public String deleteEmployee(Integer id) {

        employeeService.deleteEmployeeByID(id);

        return "redirect:/employees";
    }
}
