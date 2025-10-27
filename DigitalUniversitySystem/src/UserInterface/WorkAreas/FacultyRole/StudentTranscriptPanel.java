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
 * Student Transcript Panel
 * Faculty can view complete academic history of a student
 * 
 * @author chethan
 */
public class StudentTranscriptPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    FacultyProfile faculty;
    StudentProfile student;
    
    private JLabel lblTitle;
    private JLabel lblStudentInfo;
    private JLabel lblOverallGPA;
    private JLabel lblTotalCredits;
    private JLabel lblAcademicStanding;
    private JScrollPane scrollPane;
    private JTable tblTranscript;
    private JButton btnBack;
    
    public StudentTranscriptPanel(Business b, FacultyProfile fp, StudentProfile sp, JPanel clp) {
        business = b;
        faculty = fp;
        student = sp;
        CardSequencePanel = clp;
        
        initComponents();
        loadTranscript();
    }
    
    private void initComponents() {
        lblTitle = new JLabel();
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));
        lblTitle.setText("Student Transcript");
        
        lblStudentInfo = new JLabel();
        lblStudentInfo.setFont(new java.awt.Font("Dialog", 1, 16));
        lblStudentInfo.setText("Student: " + student.getPerson().getFullName() + 
                              " (ID: " + student.getPerson().getPersonId() + ")");
        
        lblOverallGPA = new JLabel();
        lblOverallGPA.setFont(new java.awt.Font("Dialog", 1, 16));
        lblOverallGPA.setForeground(new java.awt.Color(0, 102, 204));
        
        lblTotalCredits = new JLabel();
        lblTotalCredits.setFont(new java.awt.Font("Dialog", 0, 14));
        
        lblAcademicStanding = new JLabel();
        lblAcademicStanding.setFont(new java.awt.Font("Dialog", 1, 14));
        
        tblTranscript = new JTable();
        tblTranscript.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Course Number", "Course Name", "Credits", "Letter Grade", "Grade Points", "Quality Points"
            }
        ) {
            boolean[] canEdit = new boolean[] {false, false, false, false, false, false};
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(tblTranscript);
        
        btnBack = new JButton();
        btnBack.setText("<< Back");
        btnBack.addActionListener(evt -> btnBackActionPerformed(evt));
        
        setLayout(null);
        
        add(lblTitle);
        lblTitle.setBounds(30, 20, 400, 30);
        
        add(lblStudentInfo);
        lblStudentInfo.setBounds(30, 60, 600, 25);
        
        add(lblOverallGPA);
        lblOverallGPA.setBounds(30, 90, 300, 25);
        
        add(lblTotalCredits);
        lblTotalCredits.setBounds(350, 90, 300, 25);
        
        add(lblAcademicStanding);
        lblAcademicStanding.setBounds(30, 120, 600, 25);
        
        add(scrollPane);
        scrollPane.setBounds(30, 160, 850, 350);
        
        add(btnBack);
        btnBack.setBounds(30, 530, 100, 35);
    }
    
    private void loadTranscript() {
        DefaultTableModel model = (DefaultTableModel) tblTranscript.getModel();
        model.setRowCount(0);
        
        info5100.university.example.Persona.StudentProfile univStudent = student.getUniversityProfile();
        Transcript transcript = univStudent.getTranscript();
        
        ArrayList<SeatAssignment> allCourses = transcript.getCourseList();
        
        double totalQualityPoints = 0;
        int totalCredits = 0;
        
        for (SeatAssignment sa : allCourses) {
            Object[] row = new Object[6];
            
            row[0] = sa.getAssociatedCourse().getCOurseNumber();
            row[1] = sa.getAssociatedCourse().getName();
            
            int credits = sa.getCreditHours();
            row[2] = credits;
            
            String letterGrade = sa.getLetterGrade();
            row[3] = letterGrade != null ? letterGrade : "In Progress";
            
            float gradePoints = sa.getGrade();
            row[4] = gradePoints > 0 ? String.format("%.2f", gradePoints) : "N/A";
            
            float qualityPoints = gradePoints * credits;
            row[5] = gradePoints > 0 ? String.format("%.2f", qualityPoints) : "N/A";
            
            model.addRow(row);
            
            if (gradePoints > 0) {
                totalQualityPoints += qualityPoints;
                totalCredits += credits;
            }
        }
        
        float overallGPA = transcript.calculateOverallGPA();
        int totalCreditHours = transcript.getTotalCreditHours();
        
        lblOverallGPA.setText(String.format("Overall GPA: %.2f", overallGPA));
        lblTotalCredits.setText("Total Credits Completed: " + totalCreditHours);
        
        String standing = transcript.getOverallAcademicStanding();
        lblAcademicStanding.setText("Academic Standing: " + standing);
        
        if ("Good Standing".equals(standing)) {
            lblAcademicStanding.setForeground(new java.awt.Color(0, 128, 0));
        } else {
            lblAcademicStanding.setForeground(new java.awt.Color(204, 0, 0));
        }
        
        System.out.println("Loaded transcript for " + student.getPerson().getFullName() + 
                          " - " + allCourses.size() + " courses");
    }
    
    private void btnViewFullTranscriptActionPerformed(java.awt.event.ActionEvent evt) {
        // Already viewing full transcript, so just show message
        JOptionPane.showMessageDialog(this, 
            "You are viewing the complete transcript.\n\n" +
            "All courses across all semesters are displayed.",
            "Full Transcript",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
}