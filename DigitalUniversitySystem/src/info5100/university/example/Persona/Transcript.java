/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info5100.university.example.Persona;

import info5100.university.example.CourseSchedule.CourseLoad;
import info5100.university.example.CourseSchedule.SeatAssignment;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author kal bugrara
 */
public class Transcript {

    StudentProfile student;
    HashMap<String, CourseLoad> courseloadlist;

    CourseLoad currentcourseload;

    public Transcript(StudentProfile sp) {
        student = sp;
        courseloadlist = new HashMap();

    }

    public int getStudentSatisfactionIndex() {
        //for each courseload 
        //get seatassigmnets; 
        //for each seatassignment add 1 if like =true;
        return 0;
    }

    public CourseLoad newCourseLoad(String sem) {

        currentcourseload = new CourseLoad(sem);
        courseloadlist.put(sem, currentcourseload);
        return currentcourseload;
    }

    public CourseLoad getCurrentCourseLoad() {

        return currentcourseload;

    }

    public CourseLoad getCourseLoadBySemester(String semester) {

        return courseloadlist.get(semester);

    }

    public float getStudentTotalScore() {

        float sum = 0;

        for (CourseLoad cl : courseloadlist.values()) {
            sum = sum + cl.getSemesterScore();

        }
        return sum;
    }
    //sat index means student rated their courses with likes;
    public int getStudentSatifactionIndex() {
        ArrayList<SeatAssignment> courseregistrations = getCourseList();
        int sum = 0;
        for (SeatAssignment sa : courseregistrations) {

            if (sa.getLike()) {
                sum = sum + 1;
            }
        }
        return sum;
    }
    //generate a list of all courses taken so far (seetassignments) 
    //from multiple semesters (course loads)
    //from seat assignments we will be able to access the course offers

    public ArrayList<SeatAssignment> getCourseList() {
        ArrayList temp2;
        temp2 = new ArrayList();

        for (CourseLoad cl : courseloadlist.values()) { //extract cl list as objects --ignore label
            temp2.addAll(cl.getSeatAssignments()); //merge one array list to another
        }

        return temp2;

    }
/**
 * Calculate overall GPA across all semesters
 */
public float calculateOverallGPA() {
    float totalQualityPoints = 0;
    int totalCreditHours = 0;
    
    // Sum quality points and credits from all course loads
    for (CourseLoad cl : courseloadlist.values()) {
        totalQualityPoints += cl.getSemesterScore();
        totalCreditHours += cl.getTotalCreditHours();
    }
    
    if (totalCreditHours == 0) return 0.0f;
    
    return totalQualityPoints / totalCreditHours;
}

/**
 * Get overall academic standing
 */
public String getOverallAcademicStanding() {
    float overallGPA = calculateOverallGPA();
    
    if (overallGPA >= 3.0) {
        return "Good Standing";
    } else {
        return "Academic Probation";
    }
}

/**
 * Get total credit hours completed across all semesters
 */
public int getTotalCreditHours() {
    int total = 0;
    for (CourseLoad cl : courseloadlist.values()) {
        total += cl.getTotalCreditHours();
    }
    return total;
}
}
