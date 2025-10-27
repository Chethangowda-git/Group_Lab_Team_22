# Group_Lab_Team_22
1. Project Title
Digital University System with Role-Based Access Control

2. Team Information

Member 3
Name: Samriddhi Devraj
NUID: 002025281
Assigned Use Case : Administrator Use Case

Member 4
Name: Nidhi Mahesh
NUID: 002570640 
Assigned Use Case: Faculty Use Case

Member 2
Name: Akshay Dnyaneshwar Govind
NUID: 002509562
Assigned Use Case: Student

Member 1
Name: Chethan Gowda Guruvanahalli Thimmegowda 
NUID: 002529890
Assigned Role Use Case: Registrar

3. Project Overview

Purpose & Objectives

The Digital University System is designed to simulate a real-world academic management platform. Its primary objective is to provide a secure, role-based environment where different university stakeholders (Administrator, Faculty, Student, Registrar) can perform their responsibilities while ensuring proper authentication and authorization.
The project integrates a reference Access Control Layer with the Digital University model to enforce login/logout flows, role-based permissions, and secure data handling.

Key Features Implemented

Administrator Use Case – Samriddhi Devraj
⦁	Person registration with duplicate prevention and auto‑generated IDs
⦁	User account management (create, modify, delete)
⦁	Student/faculty records management with 3 search methods (name, ID, department)
⦁	Edit/delete capabilities with enrollment validation
⦁	University‑level analytics dashboard showing:
⦁	User counts by role
⦁	Course statistics
⦁	Tuition revenue

Faculty Use Case – Nidhi Mahesh
⦁	Course management including syllabus upload and enrollment control
⦁	Student grading with letter grade assignment and automatic GPA computation
⦁	Student ranking by performance
⦁	Class GPA calculation
⦁	Performance reports with:
⦁	Grade distribution
⦁	Semester filtering

Student Use Case – Akshay Dnyaneshwar Govind
⦁	Course registration with 3 search methods and 8‑credit limit validation
⦁	Transcript viewing with term GPA, overall GPA, and academic standing
⦁	Graduation audit tracking 32‑credit requirement and INFO 5100 core course
⦁	Financial management with:
⦁	Automatic billing
⦁	Refunds for dropped courses
⦁	Transcript blocking until payment is complete
⦁	Coursework progress tracking

Registrar Use Case – Chethan Gowda Guruvanahalli Thimmegowda
⦁	Course offering creation with faculty assignment and room/time scheduling
⦁	Admin‑side student enrollment and drops
⦁	Financial reconciliation showing payment status across all students
⦁	Institutional reports including:
	Enrollment statistics
	GPA distribution


4. Installation & Setup Instructions

Prerequisites
Before running the project, ensure the following software and tools are installed on your system:
Java Development Kit (JDK): Version 19
Integrated Development Environment (IDE): NetBeans

Setup Instructions

⦁	Clone the Repository

Open a terminal or Git Bash and run:

git clone https://github.com/Chethangowda-git/Group_Lab_Team_22.git
cd Group_Lab_Team_22

⦁	Open the Project in NetBeans
Launch NetBeans.
Go to File → Open Project.
Navigate to the cloned Group_Lab_Team_22 folder and open it.

⦁	Build the Project
In NetBeans, right‑click the project in the Projects panel.
Select Clean and Build.
This will compile the code and resolve any dependencies.

⦁	Run the Project
Right‑click the main class (e.g., Main.java) and select Run File, or
Right‑click the project and select Run.
The application will launch and display the login screen.

5. Authentication & Access Control

⦁	Authentication: Users log in with a username and password, validated against the UserAccountDirectory which stores credentials linked to role‑specific profiles.
⦁	Authorization: Once authenticated, users are routed to their designated work area (AdminRoleWorkArea, FacultyWorkArea, StudentWorkArea, RegistrarWorkArea). Each work area restricts access to features appropriate for that role, ensuring strict separation of privileges.
⦁	Session Management: Logging out clears the current session and returns the user to the login screen, preventing unauthorized reuse of credentials.

6. Features Implemented

Administrator Use Case – Implemented by Samriddhi Devraj 
⦁	Manage user accounts (create, modify, delete)
⦁	Register new persons (students, faculty, registrar) with unique IDs
⦁	Prevent duplicate registrations
⦁	Manage student and faculty records (view, edit, delete, assign faculty to courses)
⦁	Search records by name, ID, or department
⦁	Manage registrar records (for group of 4)
⦁	Profile management
⦁	University-level analytics dashboard with reports (active users, courses, enrollment, tuition revenue) displayed via JTable

Faculty Use Case – Implemented by Nidhi Mahesh 
⦁	Manage assigned courses (update details, upload syllabus, open/close enrollment)
⦁	Profile management
⦁	Manage students (view enrolled students, access transcripts, grade assignments, compute GPA, rank students)
⦁	Generate course-level performance reports (average grade, distribution, enrollment count, semester filtering)
⦁	View tuition collected from enrolled students

Student Use Case – Implemented by Akshay Dnyaneshwar Govind 
⦁	Coursework management (submit assignments, track progress)
⦁	Course registration (search by ID, teacher, etc.; enforce 8 credit-hour limit)
⦁	Graduation audit (track credits, check readiness for graduation)
⦁	Transcript review (view by semester, GPA calculation, academic standing)
⦁	Profile management
⦁	Financial management (pay tuition, track history, enforce transcript lock until tuition is paid, refunds for dropped courses)

Registrar Use Case – Implemented by Chethan Gowda Guruvanahalli Thimmegowda
⦁	Manage semester course offerings (create, assign faculty, set capacity, update schedules)
⦁	Student registration (enroll/drop students)
⦁	Profile management
⦁	Tuition and financial reconciliation (monitor payments, generate financial reports)
⦁	Institutional reporting and analytics (enrollment by department, GPA distribution, JTable display)

7. Usage Instructions

Step‑by‑Step Guide
⦁	Launch the application → login screen appears.
⦁	Enter credentials → validated against UserAccountDirectory.
⦁	Redirected to role‑specific dashboard.
⦁	Perform authorized actions (Admin, Faculty, Student, Registrar).
⦁	Logout → session cleared.

Example Scenarios for Different User Roles:
SCENARIO 1: Administrator - Registering a New Student
⦁	Goal: Register a new student and create their user account
⦁	Steps:
1.	Login credentials: admin / admin
2.	Click "Register Persons (HR)" button
3.	System auto-generates Person ID (e.g., P001040)
4.	Fill in the form:
5.	First Name: "Alice"
6.	Last Name: "Cooper"
7.	Email: "alice.cooper@neu.edu"
8.	Phone: "617-555-1234"
9.	Role: Select "Student" from dropdown
10.	Username: "alice"
11.	Password: "alice123"
12.	Click "Register Person"
13.	Success message appears confirming registration
14.	Form clears and new ID is generated
15.	Logout and verify new student can login with alice/alice123
⦁	Expected Outcome: New student is created and can immediately access the Student Portal


SCENARIO 2: Registrar - Creating Course Offering and Enrolling Students
⦁	Goal: Create a new course offering for a semester and enroll students
⦁	Steps:
1.	Login credentials: registrar / registrar
2.	Click "Course Offerings Management"
3.	Click "Create New Offering"
4.	Fill the form:
5.	Course: Select "INFO6350 - Machine Learning"
6.	Faculty: Select "Sarah Moore"
7.	Capacity: 25
8.	Room: "Richards Hall 301"
9.	Schedule Time: "Tuesday/Thursday 2:00 PM - 3:30 PM"
10.	Click "Create Offering"
11.	Return to main menu, click "Student Registration (Admin-Side)"
12.	Select student "John Smith" from the table
13.	Click "Enroll in Course"
14.	Select semester: Fall2025
15.	Select course: INFO6350
16.	Click "Enroll Student"
⦁	Expected Outcome: Course offering is created with all details, student is enrolled, and tuition is automatically billed


SCENARIO 3: Faculty - Grading Students and Viewing Performance
⦁	Goal: Assign grades to students and generate a performance report
⦁	Steps:
1.	Login credentials: faculty1 / faculty1
2.	Click "Manage Students"
3.	Double-click on "INFO5100 - Application Engineering"
4.	Student list appears showing enrolled students
5.	Click on "John Smith" to select
6.	Click "Assign Grade"
7.	Select grade "A" from dropdown
8.	Click OK - success message appears
9.	Click "View Rankings" to see students ranked by performance
10.	Return to main menu
11.	Click "Performance Reports"
12.	Select course: INFO5100
13.	Select semester: Fall2025
14.	Click "Generate Report"
⦁	Expected Outcome: Grade is assigned (4.0), student ranking is displayed, performance report shows grade distribution and class average


SCENARIO 4: Student - Complete Registration and Payment Workflow
⦁	Goal: Register for courses, pay tuition, and view transcript
⦁	Steps:
1.	Login credentials: student1 / student1
2.	Click "Registration"
3.	View available courses in top table
4.	Search for courses:
5.	Search Type: "Teacher Name"
6.	Enter: "Wilson"
7.	Click "Search" (shows David Wilson's courses)
8.	Click "Show All" to see all courses again
9.	Select "INFO5100 - Application Engineering"
10.	Click "Enroll in Selected Course"
11.	Confirm enrollment (shows tuition billed: $6,000)
12.	Try to view transcript - blocked with payment warning
13.	Click "Pay Tuition"
14.	Click "Pay Full Balance"
15.	Confirm payment
16.	Return to main menu
17.	Click "Transcript" - now accessible
18.	View grades, GPA, and academic standing
19.	Click "Graduation Audit"
20.	Check progress toward 32-credit requirement
⦁	Expected Outcome: Student successfully enrolls (with 8-credit validation), pays tuition, accesses transcript, and can track graduation progress

SCENARIO 5: Cross-Role Integration - Complete Student Journey
⦁	Goal: Demonstrate full integration across all roles
⦁	Steps:
1.	Admin registers new student "Bob Test"
2.	Registrar enrolls Bob in INFO5100 and INFO6150 (8 credits total)
3.	Faculty (David Wilson) views enrolled students, sees Bob
4.	Faculty assigns grade "B+" to Bob
5.	Bob logs in, sees $12,000 tuition balance
6.	Bob pays $6,000 (partial payment)
7.	Registrar checks Financial Reconciliation - sees Bob with $6,000 paid, $6,000 balance
8.	Bob pays remaining $6,000
9.	Bob views transcript - sees B+ grade with GPA 3.3
10.	Registrar GPA Distribution report updates to show Bob in 3.3-3.69 range
11.	Admin Analytics Dashboard shows updated enrollment and tuition revenue
⦁	Expected Outcome: All roles see updated data in real-time, financial integration works, GPA calculations are accurate


8. Testing Guide

⦁	How to Test
Unit Tests: GPA calculation, tuition payment, enrollment limits.
Integration Tests: Login/logout flows, role‑based routing.
Manual Tests: GUI workflows for each role.
⦁	Sample Test Cases
Invalid login → error message.
Student tries to access Admin panel → access denied.
Student enrolls in >8 credits → blocked.
Student drops course after payment → refund issued.

9. Challenges & Solutions

Challenge: GPA calculation inconsistencies across modules.
Solution: Centralized grade‑point mapping in a utility class.

Challenge: Git merge conflicts during collaboration.
Solution: Adopted branch‑based workflow with regular pull requests.

Challenge: JTable not refreshing after CRUD operations.
Solution: Implemented table model refresh methods.

Challenge: Enforcing credit‑hour and tuition payment rules.
Solution: Added validation checks in enrollment and payment modules.

10. Future Enhancements
⦁	Multi‑factor authentication for login.
⦁	Notifications for assignment deadlines and tuition reminders.
⦁	REST API endpoints for mobile app integration.
⦁	Advanced analytics dashboards with charts and export options.
⦁	Role‑based audit logs for security tracking.

11. Contribution Breakdown

Registrar – Chethan Gowda Guruvanahalli Thimmegowda (Member 1, NUID: 002529890)
⦁	Implemented core panels: CourseOfferingManagement, CreateCourseOffering, StudentRegistration, EnrollStudent, DropStudent, FinancialReconciliation, InstitutionalReports, RegistrarProfile
⦁	Added getters to CourseSchedule, CourseOffer, Seat, FacultyProfile, and Person classes
⦁	Integrated GPA system with institutional reporting
⦁	Completed 15+ commits and 5+ pull PRs

Faculty – Nidhi Mahesh (Member 2, NUID: 002570640)
⦁	Implemented 6 panels: ViewMyCourses, ManageCourseDetails, EditCourseDetails, PerformanceReports, FacultyProfile
⦁	Developed course and student management features with grading functionality
⦁	Added syllabus and enrollmentOpen fields to CourseOffer
⦁	Integrated letter grade system with GPA calculation
⦁	Completed 12+ commits and 5+ PRs


Student – Akshay Dnyaneshwar Govind (Member 3, NUID: 002509562)
⦁	Implemented 6 panels: CourseRegistration, Transcript, GraduationAudit, PayTuition, CourseWork, StudentProfile
⦁	Added financial system (tuitionBalance, payment tracking) to StudentProfile
⦁	Implemented 8‑credit validation and transcript blocking until tuition is paid
⦁	Integrated Student financials with Registrar’s reconciliation reports
⦁	Completed 14+ commits and 5+ PRs


Administrator – Samriddhi Devraj (Member 4, NUID: 002025281)
⦁	Implemented 5 panels: PersonRegistration, ManageStudents, ManageFaculty, EditStudent, EditFaculty, AnalyticsDashboard, AdminProfile
⦁	Added duplicate checking and auto‑ID generation for new registrations
⦁	Implemented 3 search methods (name, ID, department) across student/faculty management
⦁	Created university analytics dashboard with user counts, enrollment statistics, and tuition revenue metrics
⦁	Completed 11+ commits and 5+ PRs

