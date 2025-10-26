/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Business.Person.PersonDirectory;
import Business.Profiles.EmployeeDirectory;
import Business.Profiles.StudentDirectory;
import Business.Profiles.FacultyDirectory;
import Business.Profiles.RegistrarDirectory;
import Business.UserAccounts.UserAccountDirectory;

import info5100.university.example.Department.Department;

/**
 *
 * @author kal bugrara
 */
public class Business {

    String name;
    
    // ProfileWorkArea components
    PersonDirectory persondirectory;
    EmployeeDirectory employeedirectory;
    StudentDirectory studentdirectory;
    FacultyDirectory facultydirectory;
    RegistrarDirectory registrardirectory;
    UserAccountDirectory useraccountdirectory;
    
    // University Model main entry point
    Department department;

    public Business(String n) {
        name = n;
        
        // Initialize ProfileWorkArea components
        persondirectory = new PersonDirectory();
        employeedirectory = new EmployeeDirectory(this);
        studentdirectory = new StudentDirectory();
        facultydirectory = new FacultyDirectory();
        registrardirectory = new RegistrarDirectory();
        useraccountdirectory = new UserAccountDirectory();
        
        // Initialize University Model
        department = new Department(n);
    }

    // Getters for ProfileWorkArea
    public PersonDirectory getPersonDirectory() {
        return persondirectory;
    }

    public UserAccountDirectory getUserAccountDirectory() {
        return useraccountdirectory;
    }

    public EmployeeDirectory getEmployeeDirectory() {
        return employeedirectory;
    }

    public StudentDirectory getStudentDirectory(){
        return studentdirectory;
    }
    
    public FacultyDirectory getFacultyDirectory(){
        return facultydirectory;
    }
    
    public RegistrarDirectory getRegistrarDirectory(){
        return registrardirectory;
    }
    
    // Getter for University Model (IMPORTANT!)
    public Department getDepartment() {
        return department;
    }
}
