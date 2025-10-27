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
 * Transcript Panel
 * Student can view academic history with GPA and academic standing
 * 
 * @author chethan
 */
public class TranscriptPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    StudentProfile student;
    
    private JLabel lblTitle;
    private JLabel lblStudentName;
    private JLabel lblSemesterFilter;
    private JComboBox<String> cmbSemesterFilter;
    private JLabel lblOverallGPA;
    private JLabel lblAcademicStanding;
    private JScrollPane scrollPane;
    private JTable tblTranscript;
    private JButton btnRefresh;
    private JButton btnBack;
    
    public TranscriptPanel(Business b, StudentProfile sp, JPanel clp) {
        business = b;
        student = sp;
        CardSequencePanel = clp;
        
        initComponents();
        loadSemesters();
        loadTranscript();
    }
    
    private void initComponents() {
        lblTitle = new JLabel();
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));
        lblTitle.setText("Academic Transcript");
        
        lblStudentName = new JLabel();
        lblStudentName.setFont(new java.awt.Font("Dialog", 1, 16));
        lblStudentName.setText("Student: " + student.getPerson().getFullName());
        
        lblSemesterFilter = new JLabel();
        lblSemesterFilter.setText("Filter by Semester:");
        
        cmbSemesterFilter = new JComboBox<>();
        cmbSemesterFilter.addActionListener(evt -> loadTranscript());
        
        lblOverallGPA = new JLabel();
        lblOverallGPA.setFont(new java.awt.Font("Dialog", 1, 14));
        lblOverallGPA.setForeground(new java.awt.Color(0, 102, 204));
        lblOverallGPA.setText("Overall GPA: 0.00");
        
        lblAcademicStanding = new JLabel();
        lblAcademicStanding.setFont(new java.awt.Font("Dialog", 1, 14));
        lblAcademicStanding.setText("Academic Standing: N/A");
        
        tblTranscript = new JTable();
        tblTranscript.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Term", "Academic Standing", "Course ID", "Course Name", "Grade", "Term GPA", "Overall GPA"
            }
        ) {
            boolean[] canEdit = new boolean[] {false, false, false, false, false, false, false};
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(tblTranscript);
        
        btnRefresh = new JButton();
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(evt -> loadTranscript());
        
        btnBack = new JButton();
        btnBack.setText("<< Back");
        btnBack.addActionListener(evt -> btnBackActionPerformed(evt));
        
        setLayout(null);
        
        add(lblTitle);
        lblTitle.setBounds(30, 20, 400, 30);
        
        add(lblStudentName);
        lblStudentName.setBounds(30, 60, 400, 25);
        
        add(lblOverallGPA);
        lblOverallGPA.setBounds(30, 90, 300, 25);
        
        add(lblAcademicStanding);
        lblAcademicStanding.setBounds(350, 90, 400, 25);
        
        add(lblSemesterFilter);
        lblSemesterFilter.setBounds(30, 125, 150, 25);
        add(cmbSemesterFilter);
        cmbSemesterFilter.setBounds(180, 125, 150, 25);
        
        add(btnRefresh);
        btnRefresh.setBounds(340, 125, 100, 25);
        
        add(scrollPane);
        scrollPane.setBounds(30, 160, 850, 300);
        
        add(btnBack);
        btnBack.setBounds(30, 480, 100, 35);
    }
    
    private void loadSemesters() {
        cmbSemesterFilter.removeAllItems();
        cmbSemesterFilter.addItem("All Semesters");
        cmbSemesterFilter.addItem("Fall2025");
    }
    
    private void loadTranscript() {
        DefaultTableModel model = (DefaultTableModel) tblTranscript.getModel();
        model.setRowCount(0);
        
        String filterSemester = (String) cmbSemesterFilter.getSelectedItem();
        boolean showAll = "All Semesters".equals(filterSemester);
        
        info5100.university.example.Persona.StudentProfile univStudent = student.getUniversityProfile();
        Transcript transcript = univStudent.getTranscript();
        
        // Calculate overall GPA
        float overallGPA = transcript.calculateOverallGPA();
        lblOverallGPA.setText(String.format("Overall GPA: %.2f", overallGPA));
        
        // Determine overall academic standing
        String standing = transcript.getOverallAcademicStanding();
        lblAcademicStanding.setText("Academic Standing: " + standing);
        
        // Color code academic standing
        if ("Good Standing".equals(standing)) {
            lblAcademicStanding.setForeground(new java.awt.Color(0, 128, 0));
        } else {
            lblAcademicStanding.setForeground(java.awt.Color.RED);
        }
        
        // Get all enrollments
        ArrayList<SeatAssignment> allEnrollments = transcript.getCourseList();
        
        if (allEnrollments == null || allEnrollments.isEmpty()) {
            Object[] row = new Object[7];
            row[0] = "No courses";
            row[1] = "N/A";
            row[2] = "";
            row[3] = "You have not enrolled in any courses yet";
            row[4] = "";
            row[5] = "";
            row[6] = "";
            model.addRow(row);
            return;
        }
        
        // Group by semester and display
        for (SeatAssignment sa : allEnrollments) {
            CourseLoad cl = sa.getCourseLoad();
            String semester = cl.getSemester();
            
            // Apply filter
            if (!showAll && !semester.equals(filterSemester)) {
                continue;
            }
            
            Object[] row = new Object[7];
            
            row[0] = semester;  // Term
            
            // Academic Standing for this term
            float termGPA = cl.calculateGPA();
            String termStanding = cl.getAcademicStanding(overallGPA);
            row[1] = termStanding;
            
            // Course info
            Course course = sa.getAssociatedCourse();
            row[2] = course.getCOurseNumber();
            row[3] = course.getName();
            
            // Grade
            String grade = sa.getLetterGrade();
            row[4] = grade != null ? grade : "In Progress";
            
            // Term GPA
            row[5] = String.format("%.2f", termGPA);
            
            // Overall GPA
            row[6] = String.format("%.2f", overallGPA);
            
            model.addRow(row);
        }
        
        System.out.println("Loaded transcript with Overall GPA: " + overallGPA);
    }
    
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
}