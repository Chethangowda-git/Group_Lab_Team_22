/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info5100.university.example.CourseSchedule;

import java.util.ArrayList;

/**
 *
 * @author kal bugrara
 */
public class CourseLoad {

    String semester;
    ArrayList<SeatAssignment> seatassignments;

    public CourseLoad(String s) {
        seatassignments = new ArrayList();
        semester = s;
    }

    public SeatAssignment newSeatAssignment(CourseOffer co) {

        Seat seat = co.getEmptySeat(); // seat linked to courseoffer
        if (seat == null) {
            return null;
        }
        SeatAssignment sa = seat.newSeatAssignment(this);
        seatassignments.add(sa);  //add to students course 
        return sa;
    }

    public void registerStudent(SeatAssignment sa) {

        sa.assignSeatToStudent(this);
        seatassignments.add(sa);
    }

    public float getSemesterScore() { //total score for a full semeter
        float sum = 0;
        for (SeatAssignment sa : seatassignments) {
            sum = sum + sa.GetCourseStudentScore();
        }
        return sum;
    }

    public ArrayList<SeatAssignment> getSeatAssignments() {
        return seatassignments;
    }

    public String getSemester() {
        return semester;
    }

    /**
     * Get total credit hours for this semester
     */
    public int getTotalCreditHours() {
        int total = 0;
        for (SeatAssignment sa : seatassignments) {
            total += sa.getCreditHours();
        }
        return total;
    }

    /**
     * Calculate GPA for this semester (term GPA)
     */
    public float calculateGPA() {
        int totalCredits = getTotalCreditHours();
        if (totalCredits == 0) {
            return 0.0f;
        }

        float totalQualityPoints = getSemesterScore(); // Uses existing method
        return totalQualityPoints / totalCredits;
    }

    /**
     * Get academic standing for this semester
     */
    public String getAcademicStanding(float overallGPA) {
        float termGPA = calculateGPA();

        if (termGPA >= 3.0 && overallGPA >= 3.0) {
            return "Good Standing";
        } else if (termGPA < 3.0) {
            return "Academic Warning";
        } else if (overallGPA < 3.0) {
            return "Academic Probation";
        }
        return "Good Standing";
    }

}
