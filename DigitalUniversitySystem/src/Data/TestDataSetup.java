/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data;

import info5100.university.example.Persona.Registrar.RegistrarProfile;
import info5100.university.example.Persona.Registrar.RegistrarDirectory;
import info5100.university.example.Persona.Admin.AdminDirectory;
import info5100.university.example.Persona.Admin.AdminProfile;
import Auth.UserAccount;
import info5100.university.example.CourseCatalog.Course;
import info5100.university.example.CourseCatalog.CourseCatalog;
import info5100.university.example.CourseSchedule.CourseLoad;
import info5100.university.example.CourseSchedule.CourseOffer;
import info5100.university.example.CourseSchedule.CourseSchedule;
import info5100.university.example.Department.Department;
import info5100.university.example.Persona.*;
import info5100.university.example.Persona.Faculty.FacultyDirectory;
import info5100.university.example.Persona.Faculty.FacultyProfile;
import Auth.UserAccountDirectory;

import java.util.ArrayList;

/**
 *
 * @author chethan
 */
public class TestDataSetup {
    
    /**
     * Initialize the system with pre-populated data
     * Requirements: 1 dept, 30 persons, 10 students, 10 faculty, 
     * 1 admin, 1 registrar, 1 semester, 5 course offers
     * @return Department with all data configured
     */
    public static Department configure() {
        
        // System.out.println("Initializing University Management System...");
        
        // 1. Create Department
        Department department = new Department("Information Systems");
        
        // Get directories
        PersonDirectory personDirectory = department.getPersonDirectory();
        StudentDirectory studentDirectory = department.getStudentDirectory();
        FacultyDirectory facultyDirectory = new FacultyDirectory(department);
        AdminDirectory adminDirectory = new AdminDirectory();
        RegistrarDirectory registrarDirectory = new RegistrarDirectory();
        
        // 2. Create Course Catalog (5 courses)
        CourseCatalog courseCatalog = department.getCourseCatalog();
        
        Course c1 = courseCatalog.newCourse("Application Engineering", "INFO5100", 4);
        Course c2 = courseCatalog.newCourse("Web Design", "INFO6150", 4);
        Course c3 = courseCatalog.newCourse("Data Structures", "INFO6205", 4);
        Course c4 = courseCatalog.newCourse("Database Management", "DAMG6210", 4);
        Course c5 = courseCatalog.newCourse("Big Data", "CSYE7200", 4);
        
        // 3. Create Semester and Course Schedule
        CourseSchedule fall2025 = department.newCourseSchedule("Fall2025");
        
        // 4. Create 10 Faculty Members
        FacultyProfile[] faculties = new FacultyProfile[10];
        for (int i = 1; i <= 10; i++) {
            Person p = personDirectory.newPerson(
                "F" + String.format("%03d", i),
                "faculty" + i + "@university.edu",
                "Faculty",
                "Member" + i,
                "617-555-" + String.format("%04d", i)
            );
            faculties[i-1] = facultyDirectory.newFacultyProfile(p);
        }
        
        // 5. Create 10 Students
        StudentProfile[] students = new StudentProfile[10];
        for (int i = 1; i <= 10; i++) {
            Person p = personDirectory.newPerson(
                "S" + String.format("%03d", i),
                "student" + i + "@university.edu",
                "Student",
                "Name" + i,
                "617-555-" + String.format("%04d", 1000 + i)
            );
            students[i-1] = studentDirectory.newStudentProfile(p);
        }
        
        // 6. Create 1 Admin
        Person adminPerson = personDirectory.newPerson(
            "A001",
            "admin@university.edu",
            "System",
            "Administrator",
            "617-555-0001"
        );
        AdminProfile admin = adminDirectory.newAdminProfile(adminPerson);
        
        // 7. Create 1 Registrar
        Person registrarPerson = personDirectory.newPerson(
            "R001",
            "registrar@university.edu",
            "University",
            "Registrar",
            "617-555-0002"
        );
        RegistrarProfile registrar = registrarDirectory.newRegistrarProfile(registrarPerson);
        
        // 8. Create 5 Course Offerings with Faculty Assigned
        CourseOffer co1 = fall2025.newCourseOffer("INFO5100");
        if (co1 != null) {
            co1.generatSeats(30);
            co1.AssignAsTeacher(faculties[0]);
        }
        
        CourseOffer co2 = fall2025.newCourseOffer("INFO6150");
        if (co2 != null) {
            co2.generatSeats(25);
            co2.AssignAsTeacher(faculties[1]);
        }
        
        CourseOffer co3 = fall2025.newCourseOffer("INFO6205");
        if (co3 != null) {
            co3.generatSeats(30);
            co3.AssignAsTeacher(faculties[2]);
        }
        
        CourseOffer co4 = fall2025.newCourseOffer("DAMG6210");
        if (co4 != null) {
            co4.generatSeats(28);
            co4.AssignAsTeacher(faculties[3]);
        }
        
        CourseOffer co5 = fall2025.newCourseOffer("CSYE7200");
        if (co5 != null) {
            co5.generatSeats(20);
            co5.AssignAsTeacher(faculties[4]);
        }
        
        // 9. Enroll some students in courses (for testing)
        // Student 1 enrolls in 2 courses
        CourseLoad cl1 = students[0].newCourseLoad("Fall2025");
        if (co1 != null) cl1.newSeatAssignment(co1);
        if (co2 != null) cl1.newSeatAssignment(co2);
        
        // Student 2 enrolls in 1 course
        CourseLoad cl2 = students[1].newCourseLoad("Fall2025");
        if (co1 != null) cl2.newSeatAssignment(co1);
        
        // System.out.println("Test Data Setup Complete!");
        // System.out.println("Created: 1 dept, 30 persons, 10 students, 10 faculty, 1 admin, 1 registrar");
        // System.out.println("Created: 5 courses, 5 course offerings with faculty assigned");
        
        return department;
    }
    
    /**
     * Setup user accounts for authentication
     * Call this after configure()
     * @param department
     * @return UserAccountDirectory with all accounts
     */
    public static UserAccountDirectory setupUserAccounts(Department department) {
        
        UserAccountDirectory uad = new UserAccountDirectory();
        
        PersonDirectory pd = department.getPersonDirectory();
        
        // Admin account
        Person adminPerson = pd.findPerson("A001");
        if (adminPerson != null) {
            uad.newUserAccount("admin", "admin", adminPerson, "Admin");
        }
        
        // Registrar account
        Person registrarPerson = pd.findPerson("R001");
        if (registrarPerson != null) {
            uad.newUserAccount("registrar", "registrar", registrarPerson, "Registrar");
        }
        
        // Faculty accounts (faculty1/faculty1, faculty2/faculty2, etc.)
        for (int i = 1; i <= 10; i++) {
            Person p = pd.findPerson("F" + String.format("%03d", i));
            if (p != null) {
                uad.newUserAccount("faculty" + i, "faculty" + i, p, "Faculty");
            }
        }
        
        // Student accounts (student1/student1, student2/student2, etc.)
        for (int i = 1; i <= 10; i++) {
            Person p = pd.findPerson("S" + String.format("%03d", i));
            if (p != null) {
                uad.newUserAccount("student" + i, "student" + i, p, "Student");
            }
        }
        
        // System.out.println("User Accounts Setup Complete!");
        // System.out.println("Created: 1 admin, 1 registrar, 10 faculty, 10 student accounts");
        // System.out.println("Default credentials: username=role+number, password=same");
        // System.out.println("Examples: admin/admin, student1/student1, faculty1/faculty1");
        
        return uad;
    }
}
