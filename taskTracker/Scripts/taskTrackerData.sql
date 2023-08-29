USE taskTracker;

INSERT INTO taskPriorityType(taskPriorityType, taskPriorityDesc) VALUES
	("Do", "An urgent and important task with deadlines or consequences."),
	("Plan", "A non-urgent and important task with unclear deadlines that contribute to long-term success."),
	("Delegate", "An urgent and unimportant task that must get done, but don't require you specific skill set. "),
	("Delete", "A non urgent and unimportant task that is unnecessary.");
	
    
INSERT INTO taskStatus (taskStatusName, taskStatusDesc) VALUES 
    ("Not Started", "This task has not been started yet."),
    ("In-Progress", "This task is currently in the progress of being completed."),
    ("On Hold", "This task is paused until further information has been provided or additional steps taken."),
    ("Completed","This task has been completed."),
    ("Overdue","This task passed it's deadline and must be addressed immediately.");
    
    
INSERT INTO Department(departmentName) VALUES 
	("Business Development"),
	("UX/UI Design"),
	("Product Development"),
	("Sales & Marketing"),
	("Product Testing"),
	("Operations"),
	("Customer Support");
    
INSERT INTO Employee(firstName, lastName, departmentID) VALUES
	("Rebekah", "Michaleson",1),
    ("Marcel", "Benson", 2),
    ("Caleb", "Jones", 3),
    ("Fiona", "Ghallegar", 4),
    ("Justin", "Combs", 5),
    ("Adonis", "Creed", 1),
    ("Alana", "Smith", 2);
    
    
INSERT INTO Project(projectName, projectDesc, employeeID) VALUES 
	("Launch Task Tracker Product", "The purpose of this project is to launch a productivity tool to help with project management", 1),
    ("Transition to New Tracking System", "This project is to transition current sales tracking tools", 6);

INSERT INTO Task(taskName, taskDesc, taskDueDate, projectID, taskPriorityTypeID, taskStatusID) VALUES 	
	("Task Tracker: Determine Project Details", "Meet with stakeholders to confirm the scope and budget of the product", '2023-07-25', 1, 1, 4),
    ("Create Case Study", "Conduct user research to create a case study for the product",'2023-08-01', 1, 3, 2),
    ("Engineer MVP", "Develop a base model of the project",'2023-09-01', 1, 2, 1),
    ("Test Product", "Test software",'2023-10-01', 1, 2, 3),
    ("Tracking System: Determine Project Requirements", "Meet with stakeholders to confirm the scope and budget of the transition", '2023-07-26', 2, 1, 5),
    ("Create SOPs for Transition Docs", "Optimize Sales Process for Streamline Tracking", '2023-08-26', 2, 2, 2);
    
    
INSERT INTO TaskEmployee(taskID, employeeID) VALUES
    (1, 1), -- Rebekah is assigned to the Project Scope for Task Tracker task
    (1, 6),-- Adonis is assigned to the Project Scope task
    (2, 2), -- Marcel is assigned to the Create a User Case Study task
    (2, 7), -- Alana is assigned to the Create a User Case Study task
    (3, 3), -- Caleb is assigned to the Engineer MVP task
    (4, 5), -- Justin is assigned to the Test Product task
    (5, 1), -- Rebekah is assigned to the Project Scope for Tracking System task
    (6, 6),-- Adonis is assigned to the SOPs task
    (6, 4);-- Fiona is assigned to the SOPs task