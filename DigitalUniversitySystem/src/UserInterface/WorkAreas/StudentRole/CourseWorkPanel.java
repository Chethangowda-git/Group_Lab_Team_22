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

/**
 * Course Work Panel
 * Student can view courses and track assignment progress
 */
public class CourseWorkPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    StudentProfile student;
    
    private JLabel lblTitle;
    private JLabel lblSemester;
    private JComboBox<String> cmbSemester;
    private JLabel lblMyCourses;
    private JScrollPane scrollCourses;
    private JTable tblMyCourses;
    private JLabel lblAssignmentInfo;
    private JTextArea txtAssignmentInfo;
    private JScrollPane scrollInfo;
    private JButton btnRefresh;
    private JButton btnBack;
    
    public CourseWorkPanel(Business b, StudentProfile sp, JPanel clp) {
        business = b;
        student = sp;
        CardSequencePanel = clp;
        
        initComponents();
        loadMyCourses();
    }
    
    private void initComponents() {
        lblTitle = new JLabel();
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));
        lblTitle.setText("My Course Work");
        
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
                "Course Number", "Course Name", "Credits", "Current Grade", "Status"
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
        
        btnRefresh = new JButton();
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(evt -> loadMyCourses());
        
        btnBack = new JButton();
        btnBack.setText("<< Back");
        btnBack.addActionListener(evt -> btnBackActionPerformed(evt));
        
        setLayout(null);
        
        add(lblTitle);
        lblTitle.setBounds(30, 20, 400, 30);
        
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
        
        add(lblAssignmentInfo);
        lblAssignmentInfo.setBounds(30, 345, 300, 25);
        add(scrollInfo);
        scrollInfo.setBounds(30, 375, 750, 150);
        
        add(btnBack);
        btnBack.setBounds(30, 545, 100, 35);
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
        
        for (SeatAssignment sa : enrollments) {
            Course course = sa.getAssociatedCourse();
            String letterGrade = sa.getLetterGrade();
            String status;
            
            if (letterGrade != null && !letterGrade.isEmpty()) {
                status = "Graded: " + letterGrade;
                gradedCount++;
            } else {
                status = "In Progress";
            }
            
            Object[] row = new Object[5];
            row[0] = course.getCOurseNumber();
            row[1] = course.getName();
            row[2] = course.getCredits();
            row[3] = letterGrade != null ? letterGrade : "N/A";
            row[4] = status;
            
            model.addRow(row);
            
            // Add to progress summary
            progressSummary.append("📚 ").append(course.getCOurseNumber()).append(" - ").append(course.getName()).append("\n");
            progressSummary.append("   Grade: ").append(letterGrade != null ? letterGrade : "Not yet graded").append("\n");
            progressSummary.append("   Status: ").append(status).append("\n\n");
        }
        
        progressSummary.append("\nPROGRESS METRICS:\n");
        progressSummary.append("-".repeat(60)).append("\n");
        progressSummary.append("Courses Graded: ").append(gradedCount).append(" / ").append(enrollments.size()).append("\n");
        progressSummary.append("Courses In Progress: ").append(enrollments.size() - gradedCount).append("\n");
        
        if (gradedCount == enrollments.size() && enrollments.size() > 0) {
            progressSummary.append("\n✅ All courses have been graded!");
        }
        
        txtAssignmentInfo.setText(progressSummary.toString());
        
        System.out.println("✅ Loaded course work for " + enrollments.size() + " courses");
    }
    
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
}