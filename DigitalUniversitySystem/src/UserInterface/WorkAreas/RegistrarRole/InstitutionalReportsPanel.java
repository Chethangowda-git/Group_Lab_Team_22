package UserInterface.WorkAreas.RegistrarRole;

import Business.Business;
import Business.Profiles.RegistrarProfile;
import Business.Profiles.StudentDirectory;
import Business.Profiles.StudentProfile;

import info5100.university.example.Department.Department;
import info5100.university.example.CourseSchedule.CourseSchedule;
import info5100.university.example.CourseSchedule.CourseOffer;
import info5100.university.example.CourseCatalog.Course;
import info5100.university.example.Persona.Transcript;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * Institutional Reports Panel
 * Author: Chethan (NUID: Your NUID)
 * Generate enrollment reports and GPA distribution by department/course
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
        cmbReportType.addItem("GPA Distribution by Program");
        
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
                "Column 1", "Column 2", "Column 3", "Column 4"
            }
        ) {
            boolean[] canEdit = new boolean[] {false, false, false, false};
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
        lblTotalCourses.setText("Metric 1: 0");
        
        lblTotalEnrollments = new JLabel();
        lblTotalEnrollments.setText("Metric 2: 0");
        
        lblAvgEnrollment = new JLabel();
        lblAvgEnrollment.setText("Metric 3: 0%");
        
        summaryPanel.add(lblTotalCourses);
        lblTotalCourses.setBounds(20, 25, 300, 25);
        
        summaryPanel.add(lblTotalEnrollments);
        lblTotalEnrollments.setBounds(20, 50, 300, 25);
        
        summaryPanel.add(lblAvgEnrollment);
        lblAvgEnrollment.setBounds(330, 25, 300, 25);
        
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
        cmbReportType.setBounds(140, 70, 220, 25);
        
        add(lblSemester);
        lblSemester.setBounds(390, 70, 80, 25);
        
        add(cmbSemester);
        cmbSemester.setBounds(480, 70, 150, 25);
        
        add(btnGenerate);
        btnGenerate.setBounds(650, 70, 150, 25);
        
        add(scrollPane);
        scrollPane.setBounds(30, 110, 770, 300);
        
        add(summaryPanel);
        summaryPanel.setBounds(30, 420, 770, 90);
        
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
        } else if ("GPA Distribution by Program".equals(reportType)) {
            generateGPADistributionReport();
        }
    }
    
    private void generateEnrollmentReport() {
        DefaultTableModel model = (DefaultTableModel) tblReport.getModel();
        model.setRowCount(0);
        model.setColumnIdentifiers(new String[] {
            "Course Number", "Course Name", "Department", "Capacity", "Enrolled", "Enrollment %"
        });
        
        String selectedSemester = (String) cmbSemester.getSelectedItem();
        boolean allSemesters = "All Semesters".equals(selectedSemester);
        
        Department dept = business.getDepartment();
        String departmentName = dept.getName();
        
        int totalCourses = 0;
        int totalEnrollments = 0;
        int totalCapacity = 0;
        
        CourseSchedule schedule;
        if (!allSemesters) {
            schedule = dept.getCourseSchedule(selectedSemester);
        } else {
            schedule = dept.getCourseSchedule("Fall2025");
        }
        
        if (schedule == null) {
            JOptionPane.showMessageDialog(this, "No data found");
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
        
        double avgEnrollmentPercent = totalCapacity > 0 ? (totalEnrollments * 100.0 / totalCapacity) : 0;
        
        lblTotalCourses.setText("Total Courses: " + totalCourses);
        lblTotalEnrollments.setText("Total Enrollments: " + totalEnrollments);
        lblAvgEnrollment.setText("Average Enrollment: " + String.format("%.1f%%", avgEnrollmentPercent));
    }
    
    private void generateGPADistributionReport() {
        DefaultTableModel model = (DefaultTableModel) tblReport.getModel();
        model.setRowCount(0);
        model.setColumnIdentifiers(new String[] {
            "GPA Range", "Number of Students", "Percentage", "Status"
        });
        
        StudentDirectory sd = business.getStudentDirectory();
        ArrayList<StudentProfile> students = sd.getStudentList();
        
        int range_4_0 = 0;
        int range_3_7_to_3_99 = 0;
        int range_3_3_to_3_69 = 0;
        int range_3_0_to_3_29 = 0;
        int range_2_7_to_2_99 = 0;
        int range_2_0_to_2_69 = 0;
        int range_below_2_0 = 0;
        int noGPA = 0;
        int totalStudents = 0;
        
        for (StudentProfile sp : students) {
            info5100.university.example.Persona.StudentProfile univStudent = sp.getUniversityProfile();
            Transcript transcript = univStudent.getTranscript();
            
            float gpa = transcript.calculateOverallGPA();
            int credits = transcript.getTotalCreditHours();
            
            if (credits == 0) {
                noGPA++;
                continue;
            }
            
            if (gpa == 4.0f) {
                range_4_0++;
            } else if (gpa >= 3.7f) {
                range_3_7_to_3_99++;
            } else if (gpa >= 3.3f) {
                range_3_3_to_3_69++;
            } else if (gpa >= 3.0f) {
                range_3_0_to_3_29++;
            } else if (gpa >= 2.7f) {
                range_2_7_to_2_99++;
            } else if (gpa >= 2.0f) {
                range_2_0_to_2_69++;
            } else {
                range_below_2_0++;
            }
            
            totalStudents++;
        }
        
        addGPARow(model, "4.0 (A - Perfect)", range_4_0, totalStudents, "Excellent");
        addGPARow(model, "3.7 - 3.99 (A-)", range_3_7_to_3_99, totalStudents, "Excellent");
        addGPARow(model, "3.3 - 3.69 (B+)", range_3_3_to_3_69, totalStudents, "Good Standing");
        addGPARow(model, "3.0 - 3.29 (B)", range_3_0_to_3_29, totalStudents, "Good Standing");
        addGPARow(model, "2.7 - 2.99 (B-)", range_2_7_to_2_99, totalStudents, "Warning Risk");
        addGPARow(model, "2.0 - 2.69 (C Range)", range_2_0_to_2_69, totalStudents, "Academic Warning");
        addGPARow(model, "Below 2.0 (F)", range_below_2_0, totalStudents, "Academic Probation");
        
        if (noGPA > 0) {
            addGPARow(model, "No GPA (No courses)", noGPA, totalStudents + noGPA, "N/A");
        }
        
        lblTotalCourses.setText("Total Students with GPA: " + totalStudents);
        lblTotalEnrollments.setText("Students without GPA: " + noGPA);
        
        if (totalStudents > 0) {
            int goodStanding = range_4_0 + range_3_7_to_3_99 + range_3_3_to_3_69 + range_3_0_to_3_29;
            double percentGood = (goodStanding * 100.0) / totalStudents;
            lblAvgEnrollment.setText("Good Standing: " + String.format("%.1f%%", percentGood));
        } else {
            lblAvgEnrollment.setText("Good Standing: N/A");
        }
    }
    
    private void addGPARow(DefaultTableModel model, String range, int count, int total, String status) {
        Object[] row = new Object[4];
        row[0] = range;
        row[1] = count;
        row[2] = total > 0 ? String.format("%.1f%%", (count * 100.0 / total)) : "0%";
        row[3] = status;
        model.addRow(row);
    }
    
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
}