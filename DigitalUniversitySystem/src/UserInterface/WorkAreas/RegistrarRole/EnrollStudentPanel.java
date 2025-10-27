package UserInterface.WorkAreas.RegistrarRole;

import Business.Business;
import Business.Profiles.StudentProfile;

import info5100.university.example.Department.Department;
import info5100.university.example.CourseSchedule.CourseSchedule;
import info5100.university.example.CourseSchedule.CourseOffer;
import info5100.university.example.CourseSchedule.CourseLoad;
import info5100.university.example.CourseSchedule.SeatAssignment;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Panel to enroll a student in a course
 * Registrar can enroll students (admin-side enrollment)
 * 
 * @author chethan
 */
public class EnrollStudentPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    StudentProfile student;
    
    private JLabel lblTitle;
    private JLabel lblStudent;
    private JLabel lblStudentName;
    private JLabel lblSemester;
    private JComboBox<String> cmbSemester;
    private JLabel lblCourse;
    private JComboBox<String> cmbCourse;
    private JLabel lblMessage;
    private JButton btnEnroll;
    private JButton btnCancel;
    
    public EnrollStudentPanel(Business b, StudentProfile sp, JPanel clp) {
        business = b;
        student = sp;
        CardSequencePanel = clp;
        
        initComponents();
        loadSemesters();
    }
    
    private void initComponents() {
        lblTitle = new JLabel();
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));
        lblTitle.setText("Enroll Student in Course");
        
        lblStudent = new JLabel();
        lblStudent.setFont(new java.awt.Font("Dialog", 1, 14));
        lblStudent.setText("Student:");
        
        lblStudentName = new JLabel();
        lblStudentName.setFont(new java.awt.Font("Dialog", 0, 14));
        lblStudentName.setText(student.getPerson().getFullName());
        
        lblSemester = new JLabel();
        lblSemester.setText("Select Semester: *");
        
        cmbSemester = new JComboBox<>();
        cmbSemester.addActionListener(evt -> cmbSemesterActionPerformed(evt));
        
        lblCourse = new JLabel();
        lblCourse.setText("Select Course: *");
        
        cmbCourse = new JComboBox<>();
        
        lblMessage = new JLabel();
        lblMessage.setForeground(java.awt.Color.RED);
        
        btnEnroll = new JButton();
        btnEnroll.setBackground(new java.awt.Color(102, 153, 255));
        btnEnroll.setForeground(new java.awt.Color(255, 255, 255));
        btnEnroll.setText("Enroll Student");
        btnEnroll.addActionListener(evt -> btnEnrollActionPerformed(evt));
        
        btnCancel = new JButton();
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(evt -> btnCancelActionPerformed(evt));
        
        setLayout(null);
        
        add(lblTitle);
        lblTitle.setBounds(30, 20, 400, 30);
        
        add(lblStudent);
        lblStudent.setBounds(30, 70, 100, 25);
        
        add(lblStudentName);
        lblStudentName.setBounds(140, 70, 300, 25);
        
        add(lblSemester);
        lblSemester.setBounds(30, 110, 200, 25);
        
        add(cmbSemester);
        cmbSemester.setBounds(30, 140, 300, 30);
        
        add(lblCourse);
        lblCourse.setBounds(30, 180, 200, 25);
        
        add(cmbCourse);
        cmbCourse.setBounds(30, 210, 400, 30);
        
        add(lblMessage);
        lblMessage.setBounds(30, 250, 500, 25);
        
        add(btnEnroll);
        btnEnroll.setBounds(200, 300, 150, 35);
        
        add(btnCancel);
        btnCancel.setBounds(30, 300, 100, 35);
    }
    
    private void loadSemesters() {
        cmbSemester.removeAllItems();
        cmbSemester.addItem("Fall2025");
        // Add more semesters if needed
    }
    
    private void cmbSemesterActionPerformed(java.awt.event.ActionEvent evt) {
        loadCoursesForSemester();
    }
    
    private void loadCoursesForSemester() {
        cmbCourse.removeAllItems();
        
        String semester = (String) cmbSemester.getSelectedItem();
        if (semester == null) return;
        
        Department dept = business.getDepartment();
        CourseSchedule schedule = dept.getCourseSchedule(semester);
        
        if (schedule == null) return;
        
        ArrayList<CourseOffer> offerings = schedule.getCourseOffers();
        
        for (CourseOffer offer : offerings) {
            String display = offer.getCourseNumber() + " - " + 
                           offer.getSubjectCourse().getName() + 
                           " (Capacity: " + offer.getCapacity() + 
                           ", Enrolled: " + offer.getEnrolledCount() + ")";
            cmbCourse.addItem(display);
        }
    }
    
    /**
     * Enroll student in selected course
     * UPDATED: Now includes automatic tuition billing
     */
   /**
 * Enroll student in selected course
 * UPDATED: Added 8-credit limit validation + tuition billing
 */
private void btnEnrollActionPerformed(java.awt.event.ActionEvent evt) {
    lblMessage.setText("");
    
    // 1. VALIDATE INPUT
    if (cmbSemester.getSelectedItem() == null) {
        lblMessage.setText("Please select a semester!");
        return;
    }
    
    if (cmbCourse.getSelectedItem() == null) {
        lblMessage.setText("Please select a course!");
        return;
    }
    
    String semester = (String) cmbSemester.getSelectedItem();
    String courseDisplay = (String) cmbCourse.getSelectedItem();
    
    // Extract course number from display
    String courseNumber = courseDisplay.split(" - ")[0];
    
    // 2. GET COURSE OFFERING
    Department dept = business.getDepartment();
    CourseSchedule schedule = dept.getCourseSchedule(semester);
    
    if (schedule == null) {
        lblMessage.setText("Error: Semester not found!");
        return;
    }
    
    CourseOffer offering = schedule.getCourseOfferByNumber(courseNumber);
    
    if (offering == null) {
        lblMessage.setText("Error: Course offering not found!");
        return;
    }
    
    // 3. CHECK IF COURSE IS FULL
    if (offering.getEnrolledCount() >= offering.getCapacity()) {
        lblMessage.setText("Course is full! Cannot enroll.");
        JOptionPane.showMessageDialog(this,
            "This course is at full capacity!\n\n" +
            "Capacity: " + offering.getCapacity() + "\n" +
            "Enrolled: " + offering.getEnrolledCount(),
            "Course Full",
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // 4. GET OR CREATE COURSE LOAD FOR STUDENT
    info5100.university.example.Persona.StudentProfile universityStudent = 
        student.getUniversityProfile();
    
    CourseLoad courseLoad = universityStudent.getCourseLoadBySemester(semester);
    
    if (courseLoad == null) {
        courseLoad = universityStudent.newCourseLoad(semester);
        System.out.println("Created new course load for " + semester);
    }
    
    // 5. CHECK 8-CREDIT LIMIT (NEW - CRITICAL!)
    int currentCredits = courseLoad.getTotalCreditHours();
    int courseCredits = offering.getSubjectCourse().getCredits();
    
    if (currentCredits + courseCredits > 8) {
        lblMessage.setText("Cannot enroll! Exceeds 8-credit limit.");
        JOptionPane.showMessageDialog(this,
            "Cannot enroll! Exceeds 8-credit limit.\n\n" +
            "Student: " + student.getPerson().getFullName() + "\n" +
            "Current credits: " + currentCredits + "\n" +
            "Course credits: " + courseCredits + "\n" +
            "Total would be: " + (currentCredits + courseCredits) + "\n" +
            "Maximum allowed: 8\n\n" +
            "Student must drop a course first or choose a lower-credit course.",
            "Credit Limit Exceeded",
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // 6. CHECK IF STUDENT IS ALREADY ENROLLED IN THIS COURSE
    ArrayList<SeatAssignment> currentEnrollments = courseLoad.getSeatAssignments();

    for (SeatAssignment sa : currentEnrollments) {
        String enrolledCourseNumber = sa.getAssociatedCourse().getCOurseNumber();
        if (enrolledCourseNumber.equals(courseNumber)) {
            lblMessage.setText("Student is already enrolled in " + courseNumber + "!");
            JOptionPane.showMessageDialog(this,
                "Student is already enrolled in this course!\n\n" +
                "Course: " + courseNumber,
                "Duplicate Enrollment",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
    }
    
    // 7. ENROLL STUDENT
    SeatAssignment sa = offering.assignEmptySeat(courseLoad);
    
    if (sa == null) {
        lblMessage.setText("Error: Could not enroll student!");
        JOptionPane.showMessageDialog(this,
            "Enrollment failed!\n\nThe course may be full or there was a system error.",
            "Enrollment Error",
            JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // 8. AUTO-BILL TUITION
    double courseTuition = offering.getSubjectCourse().getCoursePrice();
    student.addTuitionCharge(courseTuition);
    
    System.out.println("Enrolled " + student.getPerson().getFullName() + 
                      " in " + courseNumber);
    System.out.println("Credits: " + (currentCredits + courseCredits) + "/8");
    System.out.println("Billed tuition: $" + courseTuition + 
                      " | New balance: $" + student.getTuitionBalance());
    
    // 9. SUCCESS MESSAGE
    JOptionPane.showMessageDialog(this,
        "Student enrolled successfully!\n\n" +
        "Student: " + student.getPerson().getFullName() + "\n" +
        "Course: " + courseNumber + " - " + offering.getSubjectCourse().getName() + "\n" +
        "Semester: " + semester + "\n" +
        "Credits: " + courseCredits + " (Total: " + (currentCredits + courseCredits) + "/8)\n\n" +
        "Tuition Billed: $" + String.format("%,d", (int)courseTuition) + "\n" +
        "New Balance: $" + String.format("%,d", (int)student.getTuitionBalance()),
        "Enrollment Complete",
        JOptionPane.INFORMATION_MESSAGE);
    
    // Go back to Student Registration panel
    CardSequencePanel.remove(this);
    ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
}
    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
}