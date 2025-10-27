package UserInterface.WorkAreas.StudentRole;

import Business.Business;
import Business.Profiles.StudentProfile;

import info5100.university.example.Persona.Transcript;
import info5100.university.example.CourseSchedule.CourseLoad;
import info5100.university.example.CourseSchedule.SeatAssignment;
import info5100.university.example.CourseCatalog.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Course Work Panel
 * Student can view courses, track academic progress, and submit assignments
 * 
 * @author chethan
 */
public class CourseWorkPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    StudentProfile student;
    
    // Track assignment submissions (mock - just tracking submission status)
    private HashMap<String, Boolean> assignmentSubmissions;
    
    private JLabel lblTitle;
    private JLabel lblSemester;
    private JComboBox<String> cmbSemester;
    private JLabel lblMyCourses;
    private JScrollPane scrollCourses;
    private JTable tblMyCourses;
    private JLabel lblAssignmentInfo;
    private JTextArea txtAssignmentInfo;
    private JScrollPane scrollInfo;
    private JButton btnSubmitAssignment;
    private JButton btnRefresh;
    private JButton btnBack;
    
    public CourseWorkPanel(Business b, StudentProfile sp, JPanel clp) {
        business = b;
        student = sp;
        CardSequencePanel = clp;
        
        // Initialize assignment tracking
        assignmentSubmissions = new HashMap<>();
        
        initComponents();
        loadMyCourses();
    }
    
    private void initComponents() {
        lblTitle = new JLabel();
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));
        lblTitle.setText("My Course Work & Assignments");
        
        lblSemester = new JLabel();
        lblSemester.setText("Semester:");
        
        cmbSemester = new JComboBox<>();
        cmbSemester.addItem("Fall2025");
        cmbSemester.addActionListener(evt -> loadMyCourses());
        
        lblMyCourses = new JLabel();
        lblMyCourses.setFont(new java.awt.Font("Dialog", 1, 14));
        lblMyCourses.setText("My Enrolled Courses:");
        
        tblMyCourses = new JTable();
        tblMyCourses.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Course Number", "Course Name", "Credits", "Current Grade", "Assignment Status"
            }
        ) {
            boolean[] canEdit = new boolean[] {false, false, false, false, false};
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        
        scrollCourses = new JScrollPane();
        scrollCourses.setViewportView(tblMyCourses);
        
        lblAssignmentInfo = new JLabel();
        lblAssignmentInfo.setFont(new java.awt.Font("Dialog", 1, 14));
        lblAssignmentInfo.setText("Academic Progress Summary:");
        
        txtAssignmentInfo = new JTextArea(8, 50);
        txtAssignmentInfo.setEditable(false);
        txtAssignmentInfo.setLineWrap(true);
        txtAssignmentInfo.setWrapStyleWord(true);
        scrollInfo = new JScrollPane(txtAssignmentInfo);
        
        btnSubmitAssignment = new JButton();
        btnSubmitAssignment.setBackground(new java.awt.Color(76, 175, 80));
        btnSubmitAssignment.setForeground(java.awt.Color.WHITE);
        btnSubmitAssignment.setText("Submit Assignment for Selected Course");
        btnSubmitAssignment.addActionListener(evt -> btnSubmitAssignmentActionPerformed(evt));
        
        btnRefresh = new JButton();
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(evt -> loadMyCourses());
        
        btnBack = new JButton();
        btnBack.setText("<< Back");
        btnBack.addActionListener(evt -> btnBackActionPerformed(evt));
        
        setLayout(null);
        
        add(lblTitle);
        lblTitle.setBounds(30, 20, 500, 30);
        
        add(lblSemester);
        lblSemester.setBounds(30, 65, 80, 25);
        add(cmbSemester);
        cmbSemester.setBounds(120, 65, 150, 25);
        
        add(btnRefresh);
        btnRefresh.setBounds(280, 65, 100, 25);
        
        add(lblMyCourses);
        lblMyCourses.setBounds(30, 100, 300, 25);
        add(scrollCourses);
        scrollCourses.setBounds(30, 130, 750, 200);
        
        add(btnSubmitAssignment);
        btnSubmitAssignment.setBounds(420, 345, 330, 35);
        
        add(lblAssignmentInfo);
        lblAssignmentInfo.setBounds(30, 390, 300, 25);
        add(scrollInfo);
        scrollInfo.setBounds(30, 420, 750, 130);
        
        add(btnBack);
        btnBack.setBounds(30, 565, 100, 35);
    }
    
    private void loadMyCourses() {
        DefaultTableModel model = (DefaultTableModel) tblMyCourses.getModel();
        model.setRowCount(0);
        
        String semester = (String) cmbSemester.getSelectedItem();
        
        info5100.university.example.Persona.StudentProfile univStudent = student.getUniversityProfile();
        Transcript transcript = univStudent.getTranscript();
        CourseLoad courseLoad = transcript.getCourseLoadBySemester(semester);
        
        if (courseLoad == null || courseLoad.getSeatAssignments().isEmpty()) {
            Object[] row = new Object[5];
            row[0] = "No courses";
            row[1] = "You are not enrolled in any courses this semester";
            model.addRow(row);
            
            txtAssignmentInfo.setText("No academic progress to display.\n\nPlease enroll in courses through the Registration panel.");
            return;
        }
        
        ArrayList<SeatAssignment> enrollments = courseLoad.getSeatAssignments();
        
        StringBuilder progressSummary = new StringBuilder();
        progressSummary.append("ACADEMIC PROGRESS SUMMARY\n");
        progressSummary.append("=".repeat(60)).append("\n\n");
        progressSummary.append("Semester: ").append(semester).append("\n");
        progressSummary.append("Courses Enrolled: ").append(enrollments.size()).append("\n");
        progressSummary.append("Total Credits: ").append(courseLoad.getTotalCreditHours()).append("\n");
        progressSummary.append("Current GPA: ").append(String.format("%.2f", courseLoad.calculateGPA())).append("\n\n");
        progressSummary.append("COURSE DETAILS:\n");
        progressSummary.append("-".repeat(60)).append("\n\n");
        
        int gradedCount = 0;
        int submittedCount = 0;
        
        for (SeatAssignment sa : enrollments) {
            Course course = sa.getAssociatedCourse();
            String courseNumber = course.getCOurseNumber();
            String letterGrade = sa.getLetterGrade();
            
            // Check assignment submission status
            String submissionKey = semester + "-" + courseNumber;
            boolean isSubmitted = assignmentSubmissions.getOrDefault(submissionKey, false);
            
            String assignmentStatus;
            if (letterGrade != null && !letterGrade.isEmpty()) {
                assignmentStatus = "Graded";
                gradedCount++;
            } else if (isSubmitted) {
                assignmentStatus = "Submitted - Pending Grade";
                submittedCount++;
            } else {
                assignmentStatus = "Not Submitted";
            }
            
            Object[] row = new Object[5];
            row[0] = courseNumber;
            row[1] = course.getName();
            row[2] = course.getCredits();
            row[3] = letterGrade != null ? letterGrade : "N/A";
            row[4] = assignmentStatus;
            
            model.addRow(row);
            
            // Add to progress summary
            progressSummary.append(">> ").append(courseNumber).append(" - ").append(course.getName()).append("\n");
            progressSummary.append("   Grade: ").append(letterGrade != null ? letterGrade : "Not yet graded").append("\n");
            progressSummary.append("   Assignment: ").append(assignmentStatus).append("\n\n");
        }
        
        progressSummary.append("\nPROGRESS METRICS:\n");
        progressSummary.append("-".repeat(60)).append("\n");
        progressSummary.append("Courses Graded: ").append(gradedCount).append(" / ").append(enrollments.size()).append("\n");
        progressSummary.append("Assignments Submitted: ").append(submittedCount).append(" / ").append(enrollments.size() - gradedCount).append("\n");
        progressSummary.append("Pending Submission: ").append(enrollments.size() - gradedCount - submittedCount).append("\n");
        
        if (gradedCount == enrollments.size() && enrollments.size() > 0) {
            progressSummary.append("\nAll courses have been graded!");
        } else if (submittedCount + gradedCount == enrollments.size() && enrollments.size() > 0) {
            progressSummary.append("\nAll assignments submitted! Waiting for grades.");
        }
        
        txtAssignmentInfo.setText(progressSummary.toString());
        
        System.out.println("Loaded course work for " + enrollments.size() + " courses");
    }
    
    /**
     * Submit assignment for selected course
     */
    private void btnSubmitAssignmentActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = tblMyCourses.getSelectedRow();
        
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, 
                "Please select a course from the table first!",
                "No Course Selected",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String courseNumber = (String) tblMyCourses.getValueAt(selectedRow, 0);
        String courseName = (String) tblMyCourses.getValueAt(selectedRow, 1);
        String currentGrade = (String) tblMyCourses.getValueAt(selectedRow, 3);
        String currentStatus = (String) tblMyCourses.getValueAt(selectedRow, 4);
        
        // Check if already graded
        if (!"N/A".equals(currentGrade)) {
            JOptionPane.showMessageDialog(this,
                "This course has already been graded!\n\n" +
                "Course: " + courseNumber + "\n" +
                "Grade: " + currentGrade + "\n\n" +
                "You cannot submit assignments for graded courses.",
                "Already Graded",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Check if already submitted
        String semester = (String) cmbSemester.getSelectedItem();
        String submissionKey = semester + "-" + courseNumber;
        
        if (assignmentSubmissions.getOrDefault(submissionKey, false)) {
            JOptionPane.showMessageDialog(this,
                "Assignment already submitted for this course!\n\n" +
                "Course: " + courseNumber + " - " + courseName + "\n" +
                "Status: Waiting for faculty to grade",
                "Already Submitted",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Confirm submission
        int confirm = JOptionPane.showConfirmDialog(this,
            "Submit assignment for:\n\n" +
            courseNumber + " - " + courseName + "\n\n" +
            "Note: This is a mock submission for demonstration.\n" +
            "In a real system, you would upload files here.",
            "Confirm Submission",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirm != JOptionPane.YES_OPTION) return;
        
        // Record submission
        assignmentSubmissions.put(submissionKey, true);
        
        JOptionPane.showMessageDialog(this,
            "Assignment submitted successfully!\n\n" +
            "Course: " + courseNumber + " - " + courseName + "\n" +
            "Status: Submitted - Pending Grade\n\n" +
            "Your faculty will grade this soon.",
            "Submission Complete",
            JOptionPane.INFORMATION_MESSAGE);
        
        System.out.println("Assignment submitted for " + courseNumber);
        
        // Refresh display
        loadMyCourses();
    }
    
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
}