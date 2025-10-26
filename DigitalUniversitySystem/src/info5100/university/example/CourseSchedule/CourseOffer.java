/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info5100.university.example.CourseSchedule;

import info5100.university.example.CourseCatalog.Course;
import info5100.university.example.Persona.Faculty.FacultyAssignment;
import info5100.university.example.Persona.Faculty.FacultyProfile;
import java.util.ArrayList;

/**
 *
 * @author kal bugrara
 */
public class CourseOffer {

    Course course;
    ArrayList<Seat> seatlist;
    FacultyAssignment facultyassignment;
    String room;
    String scheduleTime;
    String syllabus;              // Course syllabus/description
    boolean enrollmentOpen;

  public CourseOffer(Course c) {
    course = c;
    seatlist = new ArrayList();
    enrollmentOpen = true;  // Default: enrollment is open
    syllabus = "Syllabus not yet uploaded"; // Default
}

    public void AssignAsTeacher(FacultyProfile fp) {

        facultyassignment = new FacultyAssignment(fp, this);
    }

    public FacultyProfile getFacultyProfile() {
        return facultyassignment.getFacultyProfile();
    }

    public String getCourseNumber() {
        return course.getCOurseNumber();
    }

    public void generatSeats(int n) {

        for (int i = 0; i < n; i++) {

            seatlist.add(new Seat(this, i));

        }

    }

    public Seat getEmptySeat() {

        for (Seat s : seatlist) {

            if (!s.isOccupied()) {
                return s;
            }
        }
        return null;
    }

    public SeatAssignment assignEmptySeat(CourseLoad cl) {

        Seat seat = getEmptySeat();
        if (seat == null) {
            return null;
        }
        SeatAssignment sa = seat.newSeatAssignment(cl); //seat is already linked to course offer
        cl.registerStudent(sa); //coures offer seat is now linked to student
        return sa;
    }

    public int getTotalCourseRevenues() {

        int sum = 0;

        for (Seat s : seatlist) {
            if (s.isOccupied() == true) {
                sum = sum + course.getCoursePrice();
            }

        }
        return sum;
    }

    public Course getSubjectCourse() {
        return course;
    }

    public int getCreditHours() {
        return course.getCredits();
    }

    /**
     * Get total capacity (number of seats)
     */
    public int getCapacity() {
        return seatlist.size();
    }

    /**
     * Get number of enrolled students (occupied seats)
     */
    public int getEnrolledCount() {
        int count = 0;
        for (Seat s : seatlist) {
            if (s.isOccupied()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Get room assignment
     */
    public String getRoom() {
        return room;
    }

    /**
     * Set room assignment
     */
    public void setRoom(String room) {
        this.room = room;
    }

    /**
     * Get schedule time
     */
    public String getScheduleTime() {
        return scheduleTime;
    }

    /**
     * Set schedule time
     */
    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }
    public ArrayList<Seat> getSeatList() {
    return seatlist;
}
    
    /**
 * Get syllabus
 */
public String getSyllabus() {
    return syllabus;
}

/**
 * Set syllabus
 */
public void setSyllabus(String syllabus) {
    this.syllabus = syllabus;
}

/**
 * Check if enrollment is open
 */
public boolean isEnrollmentOpen() {
    return enrollmentOpen;
}

/**
 * Set enrollment status
 */
public void setEnrollmentOpen(boolean enrollmentOpen) {
    this.enrollmentOpen = enrollmentOpen;
}

}
