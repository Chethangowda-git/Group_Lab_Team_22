/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info5100.university.example.CourseSchedule;

import info5100.university.example.CourseCatalog.Course;

/**
 *
 * @author kal bugrara
 */
public class SeatAssignment {

    float grade; //(Letter grade mappings: A=4.0, A-=3.7, B+=3.3, B=3.0, )
    Seat seat;
    boolean like; //true means like and false means not like
    CourseLoad courseload;
    private String letterGrade;

    public SeatAssignment(CourseLoad cl, Seat s) {
        seat = s;
        courseload = cl;
    }

    public boolean getLike() {
        return like;
    }

    public void assignSeatToStudent(CourseLoad cl) {
        courseload = cl;
    }

    public int getCreditHours() {
        return seat.getCourseCredits();

    }

    public Seat getSeat() {
        return seat;
    }

    public CourseOffer getCourseOffer() {

        return seat.getCourseOffer();
    }

    public Course getAssociatedCourse() {

        return getCourseOffer().getSubjectCourse();
    }

    public float GetCourseStudentScore() {
        return getCreditHours() * grade;
    }

    public CourseLoad getCourseLoad() {
        return courseload;
    }

    public String getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(String letter) {
        this.letterGrade = letter;
        this.grade = convertLetterToGradePoints(letter);
    }

    private float convertLetterToGradePoints(String letter) {
        if (letter == null) {
            return 0.0f;
        }

        switch (letter.toUpperCase()) {
            case "A":
                return 4.0f;
            case "A-":
                return 3.7f;
            case "B+":
                return 3.3f;
            case "B":
                return 3.0f;
            case "B-":
                return 2.7f;
            case "C+":
                return 2.3f;
            case "C":
                return 2.0f;
            case "C-":
                return 1.7f;
            case "F":
                return 0.0f;
            default:
                return 0.0f;
        }
    }

    public void setGrade(float g) {
        this.grade = g;
    }

    /**
     * Get numeric grade
     */
    public float getGrade() {
        return grade;
    }

}
