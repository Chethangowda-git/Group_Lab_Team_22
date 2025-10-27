package UserInterface.WorkAreas.FacultyRole;

import Business.Business;
import Business.Profiles.FacultyProfile;
import Business.Profiles.StudentProfile;

import info5100.university.example.CourseSchedule.SeatAssignment;
import info5100.university.example.Persona.Transcript;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * Student Progress Panel - Shows current semester progress
 */
public class StudentProgressPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    FacultyProfile faculty;
    StudentProfile student;
    
    private JLabel lblTitle;
    private JLabel lblStudentInfo;
    private JLabel lblCurrentGPA;
    private JLabel lblCredits;
    private JLabel lblAcademicStanding;
    private JScrollPane scrollPane;
    private JTable tblCourses;
    private JButton btnViewFullTranscript;
    private JButton btnBack;
    
    public StudentProgressPanel(Business b, FacultyProfile fp, StudentProfile sp, JPanel clp) {
        business = b;
        faculty = fp;
        student = sp;
        CardSequencePanel = clp;
        
        initComponents();
        loadStudentProgress();
    }
    
    private void initComponents() {
        // Title
        lblTitle = new JLabel();
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));
        lblTitle.setText("Student Progress Report");
        
        // Student Info
        lblStudentInfo = new JLabel();
        lblStudentInfo.setFont(new java.awt.Font("Dialog", 1, 16));
        lblStudentInfo.setText("Student: " + student.getPerson().getFullName() + 
                              " (ID: " + student.getPerson().getPersonId() + ")");
        
        // Current GPA
        lblCurrentGPA = new JLabel();
        lblCurrentGPA.setFont(new java.awt.Font("Dialog", 0, 14));
        lblCurrentGPA.setForeground(new java.awt.Color(0, 102, 204));
        
        // Credits
        lblCredits = new JLabel();
        lblCredits.setFont(new java.awt.Font("Dialog", 0, 14));
        
        // Academic Standing
        lblAcademicStanding = new JLabel();
        lblAcademicStanding.setFont(new java.awt.Font("Dialog", 1, 14));
        
        // Courses Table
        tblCourses = new JTable();
        tblCourses.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Course Number", "Course Name", "Credits", "Letter Grade", "Grade Points"
            }
        ) {
            boolean[] canEdit = new boolean[] {false, false, false, false, false};
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(tblCourses);
        
        // Buttons
        btnViewFullTranscript = new JButton();
        btnViewFullTranscript.setBackground(new java.awt.Color(102, 153, 255));
        btnViewFullTranscript.setForeground(new java.awt.Color(255, 255, 255));
        btnViewFullTranscript.setText("View Full Transcript");
        btnViewFullTranscript.addActionListener(evt -> btnViewFullTranscriptActionPerformed(evt));
        
        btnBack = new JButton();
        btnBack.setText("<< Back");
        btnBack.addActionListener(evt -> btnBackActionPerformed(evt));
        
        // Layout
        setLayout(null);
        
        add(lblTitle);
        lblTitle.setBounds(30, 20, 400, 30);
        
        add(lblStudentInfo);
        lblStudentInfo.setBounds(30, 60, 600, 25);
        
        add(lblCurrentGPA);
        lblCurrentGPA.setBounds(30, 90, 300, 25);
        
        add(lblCredits);
        lblCredits.setBounds(350, 90, 300, 25);
        
        add(lblAcademicStanding);
        lblAcademicStanding.setBounds(30, 120, 600, 25);
        
        add(scrollPane);
        scrollPane.setBounds(30, 160, 750, 300);
        
        add(btnViewFullTranscript);
        btnViewFullTranscript.setBounds(580, 480, 200, 35);
        
        add(btnBack);
        btnBack.setBounds(30, 480, 100, 35);
    }
    
    private void loadStudentProgress() {
        DefaultTableModel model = (DefaultTableModel) tblCourses.getModel();
        model.setRowCount(0);
        
        info5100.university.example.Persona.StudentProfile univStudent = student.getUniversityProfile();
        Transcript transcript = univStudent.getTranscript();
        
        // Get current semester course load
        info5100.university.example.CourseSchedule.CourseLoad currentLoad = transcript.getCurrentCourseLoad();
        
        if (currentLoad == null) {
            lblCurrentGPA.setText("Current Semester GPA: N/A");
            lblCredits.setText("Credits This Semester: 0");
            lblAcademicStanding.setText("Academic Standing: No Current Enrollment");
            return;
        }
        
        ArrayList<SeatAssignment> currentCourses = currentLoad.getSeatAssignments();
        
        double totalQualityPoints = 0;
        int totalCredits = 0;
        int gradedCourses = 0;
        
        for (SeatAssignment sa : currentCourses) {
            Object[] row = new Object[5];
            
            row[0] = sa.getAssociatedCourse().getCOurseNumber();
            row[1] = sa.getAssociatedCourse().getName();
            row[2] = sa.getCreditHours();
            
            String letterGrade = sa.getLetterGrade();
            row[3] = letterGrade != null ? letterGrade : "In Progress";
            
            float gradePoints = sa.getGrade();
            row[4] = gradePoints > 0 ? String.format("%.2f", gradePoints) : "N/A";
            
            model.addRow(row);
            
            totalCredits += sa.getCreditHours();
            if (gradePoints > 0) {
                totalQualityPoints += gradePoints * sa.getCreditHours();
                gradedCourses++;
            }
        }
        
        // Calculate current semester GPA
        float semesterGPA = totalCredits > 0 ? (float)(totalQualityPoints / totalCredits) : 0.0f;
        
        // Get overall GPA
        float overallGPA = transcript.calculateOverallGPA();
        
        lblCurrentGPA.setText(String.format("Current Semester GPA: %.2f | Overall GPA: %.2f", 
                                           semesterGPA, overallGPA));
        lblCredits.setText("Credits This Semester: " + totalCredits + 
                          " | Total Credits: " + transcript.getTotalCreditHours());
        
        // Academic Standing
        String standing = transcript.getOverallAcademicStanding();
        lblAcademicStanding.setText("Academic Standing: " + standing);
        
        if ("Good Standing".equals(standing)) {
            lblAcademicStanding.setForeground(new java.awt.Color(0, 128, 0));
        } else {
            lblAcademicStanding.setForeground(new java.awt.Color(204, 0, 0));
        }
    }
    
    private void btnViewFullTranscriptActionPerformed(java.awt.event.ActionEvent evt) {
        StudentTranscriptPanel panel = new StudentTranscriptPanel(business, faculty, student, CardSequencePanel);
        CardSequencePanel.add("StudentTranscript", panel);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
    
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
}