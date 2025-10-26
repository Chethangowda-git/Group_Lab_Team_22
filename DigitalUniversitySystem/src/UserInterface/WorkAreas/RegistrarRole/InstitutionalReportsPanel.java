package UserInterface.WorkAreas.RegistrarRole;

import Business.Business;
import Business.Profiles.RegistrarProfile;

import info5100.university.example.Department.Department;
import info5100.university.example.CourseSchedule.CourseSchedule;
import info5100.university.example.CourseSchedule.CourseOffer;
import info5100.university.example.CourseCatalog.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * Institutional Reports Panel
 * Generate enrollment reports by department/course
 */
public class InstitutionalReportsPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    RegistrarProfile registrar;
    
    // UI Components
    private JLabel lblTitle;
    private JLabel lblReportType;
    private JComboBox<String> cmbReportType;
    private JLabel lblSemester;
    private JComboBox<String> cmbSemester;
    private JButton btnGenerate;
    private JScrollPane scrollPane;
    private JTable tblReport;
    private JPanel summaryPanel;
    private JLabel lblTotalCourses;
    private JLabel lblTotalEnrollments;
    private JLabel lblAvgEnrollment;
    private JButton btnBack;
    
    public InstitutionalReportsPanel(Business b, RegistrarProfile rp, JPanel clp) {
        business = b;
        registrar = rp;
        CardSequencePanel = clp;
        
        initComponents();
        loadSemesters();
    }
    
    private void initComponents() {
        // Title
        lblTitle = new JLabel();
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));
        lblTitle.setText("Institutional Reports");
        
        // Report Type
        lblReportType = new JLabel();
        lblReportType.setText("Report Type:");
        
        cmbReportType = new JComboBox<>();
        cmbReportType.addItem("Enrollment by Course");
        cmbReportType.addItem("GPA Distribution (Coming Soon)");
        
        // Semester
        lblSemester = new JLabel();
        lblSemester.setText("Semester:");
        
        cmbSemester = new JComboBox<>();
        
        // Generate Button
        btnGenerate = new JButton();
        btnGenerate.setBackground(new java.awt.Color(102, 153, 255));
        btnGenerate.setForeground(new java.awt.Color(255, 255, 255));
        btnGenerate.setText("Generate Report");
        btnGenerate.addActionListener(evt -> btnGenerateActionPerformed(evt));
        
        // Report Table
        tblReport = new JTable();
        tblReport.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Course Number", "Course Name", "Department", "Capacity", "Enrolled", "Enrollment %"
            }
        ) {
            boolean[] canEdit = new boolean[] {false, false, false, false, false, false};
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(tblReport);
        
        // Summary Panel
        summaryPanel = new JPanel();
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Report Summary"));
        summaryPanel.setLayout(null);
        
        lblTotalCourses = new JLabel();
        lblTotalCourses.setText("Total Courses: 0");
        
        lblTotalEnrollments = new JLabel();
        lblTotalEnrollments.setText("Total Enrollments: 0");
        
        lblAvgEnrollment = new JLabel();
        lblAvgEnrollment.setText("Average Enrollment: 0%");
        
        summaryPanel.add(lblTotalCourses);
        lblTotalCourses.setBounds(20, 25, 200, 25);
        
        summaryPanel.add(lblTotalEnrollments);
        lblTotalEnrollments.setBounds(20, 50, 200, 25);
        
        summaryPanel.add(lblAvgEnrollment);
        lblAvgEnrollment.setBounds(250, 25, 200, 25);
        
        // Back Button
        btnBack = new JButton();
        btnBack.setText("<< Back");
        btnBack.addActionListener(evt -> btnBackActionPerformed(evt));
        
        // Layout
        setLayout(null);
        
        add(lblTitle);
        lblTitle.setBounds(30, 20, 400, 30);
        
        add(lblReportType);
        lblReportType.setBounds(30, 70, 100, 25);
        
        add(cmbReportType);
        cmbReportType.setBounds(140, 70, 200, 25);
        
        add(lblSemester);
        lblSemester.setBounds(370, 70, 80, 25);
        
        add(cmbSemester);
        cmbSemester.setBounds(460, 70, 150, 25);
        
        add(btnGenerate);
        btnGenerate.setBounds(630, 70, 150, 25);
        
        add(scrollPane);
        scrollPane.setBounds(30, 110, 750, 300);
        
        add(summaryPanel);
        summaryPanel.setBounds(30, 420, 750, 90);
        
        add(btnBack);
        btnBack.setBounds(30, 530, 100, 35);
    }
    
    private void loadSemesters() {
        cmbSemester.removeAllItems();
        cmbSemester.addItem("All Semesters");
        cmbSemester.addItem("Fall2025");
    }
    
    private void btnGenerateActionPerformed(java.awt.event.ActionEvent evt) {
        String reportType = (String) cmbReportType.getSelectedItem();
        
        if ("Enrollment by Course".equals(reportType)) {
            generateEnrollmentReport();
        } else {
            JOptionPane.showMessageDialog(this, "This report type is not yet implemented.");
        }
    }
    
    private void generateEnrollmentReport() {
        // Clear table
        DefaultTableModel model = (DefaultTableModel) tblReport.getModel();
        model.setRowCount(0);
        
        String selectedSemester = (String) cmbSemester.getSelectedItem();
        boolean allSemesters = "All Semesters".equals(selectedSemester);
        
        Department dept = business.getDepartment();
        String departmentName = dept.getName();
        
        int totalCourses = 0;
        int totalEnrollments = 0;
        int totalCapacity = 0;
        
        // If specific semester selected
        if (!allSemesters) {
            CourseSchedule schedule = dept.getCourseSchedule(selectedSemester);
            
            if (schedule == null) {
                JOptionPane.showMessageDialog(this, "No data found for " + selectedSemester);
                return;
            }
            
            ArrayList<CourseOffer> offerings = schedule.getCourseOffers();
            
            for (CourseOffer offer : offerings) {
                Object[] row = new Object[6];
                
                Course course = offer.getSubjectCourse();
                int capacity = offer.getCapacity();
                int enrolled = offer.getEnrolledCount();
                double enrollmentPercent = capacity > 0 ? (enrolled * 100.0 / capacity) : 0;
                
                row[0] = course.getCOurseNumber();
                row[1] = course.getName();
                row[2] = departmentName;
                row[3] = capacity;
                row[4] = enrolled;
                row[5] = String.format("%.1f%%", enrollmentPercent);
                
                model.addRow(row);
                
                totalCourses++;
                totalEnrollments += enrolled;
                totalCapacity += capacity;
            }
        } else {
            // All semesters - for now just Fall2025
            // You can expand this to loop through multiple semesters
            CourseSchedule schedule = dept.getCourseSchedule("Fall2025");
            
            if (schedule != null) {
                ArrayList<CourseOffer> offerings = schedule.getCourseOffers();
                
                for (CourseOffer offer : offerings) {
                    Object[] row = new Object[6];
                    
                    Course course = offer.getSubjectCourse();
                    int capacity = offer.getCapacity();
                    int enrolled = offer.getEnrolledCount();
                    double enrollmentPercent = capacity > 0 ? (enrolled * 100.0 / capacity) : 0;
                    
                    row[0] = course.getCOurseNumber();
                    row[1] = course.getName();
                    row[2] = departmentName;
                    row[3] = capacity;
                    row[4] = enrolled;
                    row[5] = String.format("%.1f%%", enrollmentPercent);
                    
                    model.addRow(row);
                    
                    totalCourses++;
                    totalEnrollments += enrolled;
                    totalCapacity += capacity;
                }
            }
        }
        
        // Update summary
        double avgEnrollmentPercent = totalCapacity > 0 ? (totalEnrollments * 100.0 / totalCapacity) : 0;
        
        lblTotalCourses.setText("Total Courses: " + totalCourses);
        lblTotalEnrollments.setText("Total Enrollments: " + totalEnrollments);
        lblAvgEnrollment.setText("Average Enrollment: " + String.format("%.1f%%", avgEnrollmentPercent));
        
        System.out.println("✅ Generated enrollment report: " + totalCourses + " courses, " + totalEnrollments + " enrollments");
    }
    
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
}