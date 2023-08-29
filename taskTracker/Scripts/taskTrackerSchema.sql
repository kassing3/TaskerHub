DROP DATABASE IF EXISTS taskTracker;
CREATE DATABASE taskTracker;
USE taskTracker;


-- Create the Task Priority Type table
DROP TABLE IF EXISTS taskPriorityType;
CREATE TABLE taskPriorityType (
	taskPriorityTypeID INT AUTO_INCREMENT PRIMARY KEY,
	taskPriorityType VARCHAR(50) NOT NULL,
	taskPriorityDesc VARCHAR(255)
);


-- Create the Task Status table
DROP TABLE IF EXISTS taskStatus;
CREATE TABLE taskStatus (
	taskStatusID INT AUTO_INCREMENT PRIMARY KEY,
	taskStatusName VARCHAR(50) NOT NULL,
	taskStatusDesc VARCHAR(255)
);

-- Create the Department table
DROP TABLE IF EXISTS Department;
CREATE TABLE Department(
	departmentID INT PRIMARY KEY AUTO_INCREMENT,
    departmentName VARCHAR(50) NOT NULL
);

-- Create the Employee table
DROP TABLE IF EXISTS Employee;
CREATE TABLE Employee (
  employeeID INT PRIMARY KEY AUTO_INCREMENT,
  firstName varchar(50) NOT NULL,
  lastName varchar(50) NOT NULL,
  departmentID INT,
  FOREIGN KEY (departmentID) REFERENCES Department(departmentID)
);

-- Create the Project table
DROP TABLE IF EXISTS Project;
CREATE TABLE Project (
  projectID INT PRIMARY KEY AUTO_INCREMENT,
  projectName varchar(50) NOT NULL,
  projectDesc varchar(255),
  employeeID INT,
  FOREIGN KEY (employeeID) REFERENCES Employee(employeeID)
);

-- Create the Task table
DROP TABLE IF EXISTS Task;
CREATE TABLE Task (
  taskID INT PRIMARY KEY AUTO_INCREMENT,
  taskName varchar(50) NOT NULL,
  taskDesc varchar(255),
  taskDueDate date,
  projectID INT,
  FOREIGN KEY (projectID) REFERENCES Project(projectID),
  taskPriorityTypeID INT,
  FOREIGN KEY (taskPriorityTypeID) REFERENCES taskPriorityType(taskPriorityTypeID),
  taskStatusID INT,
  FOREIGN KEY (taskStatusID) REFERENCES taskStatus(taskStatusID)
);


-- Create the TaskEmployee table
DROP TABLE IF EXISTS TaskEmployee;
CREATE TABLE TaskEmployee (
    taskID INT,
    employeeID INT,
    CONSTRAINT pk_TaskEmployee
    	PRIMARY KEY (taskID, employeeID),
    CONSTRAINT fk_pk_TaskEmployee_Task
    	FOREIGN KEY (taskID)
    	REFERENCES Task(taskID),
    CONSTRAINT fk_pk__TaskEmployee_Employee
    	FOREIGN KEY (employeeID)
    	REFERENCES Employee(employeeID)
);


