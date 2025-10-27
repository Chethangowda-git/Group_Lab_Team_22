package UserInterface.WorkAreas.FacultyRole;

import Business.Business;
import Business.Profiles.FacultyProfile;

import info5100.university.example.Department.Department;
import info5100.university.example.CourseSchedule.CourseSchedule;
import info5100.university.example.CourseSchedule.CourseOffer;
import info5100.university.example.CourseSchedule.Seat;
import info5100.university.example.CourseSchedule.SeatAssignment;
import info5100.university.example.CourseCatalog.Course;

import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Performance Reports Panel
 * Faculty can generate course performance reports with grade distribution
 */
public class PerformanceReportsPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    FacultyProfile faculty;
    
    // UI Components
    private JLabel lblTitle;
    private JLabel lblSelectCourse;
    private JComboBox<String> cmbCourse;
    private JLabel lblSemester;
    private JComboBox<String> cmbSemester;
    private JButton btnGenerate;
    private JButton btnExport;
    private JScrollPane scrollPane;
    private JTable tblReport;
    private JPanel summaryPanel;
    private JLabel lblAverageGrade;
    private JLabel lblEnrollmentCount;
    private JLabel lblTotalTuition;
    private JButton btnBack;
    
    public PerformanceReportsPanel(Business b, FacultyProfile fp, JPanel clp) {
        business = b;
        faculty = fp;
        CardSequencePanel = clp;
        
        initComponents();
        loadSemesters();
        loadFacultyCourses();
    }
    
    private void initComponents() {
        // Title
        lblTitle = new JLabel();
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));
        lblTitle.setText("Performance Reports");
        
        // Select Course
        lblSelectCourse = new JLabel();
        lblSelectCourse.setText("Select Course:");
        
        cmbCourse = new JComboBox<>();
        
        // Semester
        lblSemester = new JLabel();
        lblSemester.setText("Semester:");
        
        cmbSemester = new JComboBox<>();
        cmbSemester.addActionListener(evt -> cmbSemesterActionPerformed(evt));
        
        // Generate Button
        btnGenerate = new JButton();
        btnGenerate.setBackground(new java.awt.Color(102, 153, 255));
        btnGenerate.setForeground(new java.awt.Color(255, 255, 255));
        btnGenerate.setText("Generate Report");
        btnGenerate.addActionListener(evt -> btnGenerateActionPerformed(evt));
        
        // Export Button
        btnExport = new JButton();
        btnExport.setBackground(new java.awt.Color(51, 153, 102));
        btnExport.setForeground(new java.awt.Color(255, 255, 255));
        btnExport.setText("Export to CSV");
        btnExport.addActionListener(evt -> btnExportActionPerformed(evt));
        btnExport.setEnabled(false);
        
        // Report Table (Grade Distribution)
        tblReport = new JTable();
        tblReport.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Letter Grade", "Number of Students", "Percentage"
            }
        ) {
            boolean[] canEdit = new boolean[] {false, false, false};
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(tblReport);
        
        // Summary Panel
        summaryPanel = new JPanel();
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Course Summary"));
        summaryPanel.setLayout(null);
        
        lblAverageGrade = new JLabel();
        lblAverageGrade.setText("Average Grade: N/A");
        
        lblEnrollmentCount = new JLabel();
        lblEnrollmentCount.setText("Enrollment Count: 0");
        
        lblTotalTuition = new JLabel();
        lblTotalTuition.setText("Total Tuition: $0");
        
        summaryPanel.add(lblAverageGrade);
        lblAverageGrade.setBounds(20, 25, 250, 25);
        
        summaryPanel.add(lblEnrollmentCount);
        lblEnrollmentCount.setBounds(20, 50, 250, 25);
        
        summaryPanel.add(lblTotalTuition);
        lblTotalTuition.setBounds(290, 25, 250, 25);
        
        // Back Button
        btnBack = new JButton();
        btnBack.setText("<< Back");
        btnBack.addActionListener(evt -> btnBackActionPerformed(evt));
        
        // Layout
        setLayout(null);
        
        add(lblTitle);
        lblTitle.setBounds(30, 20, 400, 30);
        
        add(lblSemester);
        lblSemester.setBounds(30, 70, 80, 25);
        
        add(cmbSemester);
        cmbSemester.setBounds(120, 70, 150, 25);
        
        add(lblSelectCourse);
        lblSelectCourse.setBounds(300, 70, 120, 25);
        
        add(cmbCourse);
        cmbCourse.setBounds(420, 70, 250, 25);
        
        add(btnGenerate);
        btnGenerate.setBounds(690, 70, 150, 25);
        
        add(btnExport);
        btnExport.setBounds(690, 105, 150, 25);
        
        add(scrollPane);
        scrollPane.setBounds(30, 120, 650, 300);
        
        add(summaryPanel);
        summaryPanel.setBounds(30, 430, 650, 100);
        
        add(btnBack);
        btnBack.setBounds(30, 550, 100, 35);
    }
    
    private void loadSemesters() {
        cmbSemester.removeAllItems();
        cmbSemester.addItem("Fall2025");
        // Add more semesters as needed
    }
    
    private void cmbSemesterActionPerformed(java.awt.event.ActionEvent evt) {
        loadFacultyCourses();
    }
    
    private void loadFacultyCourses() {
        cmbCourse.removeAllItems();
        
        String semester = (String) cmbSemester.getSelectedItem();
        if (semester == null) return;
        
        // Get faculty's courses for selected semester
        Department dept = business.getDepartment();
        CourseSchedule schedule = dept.getCourseSchedule(semester);
        
        if (schedule == null) return;
        
        ArrayList<CourseOffer> allOfferings = schedule.getCourseOffers();
        
        // Filter courses taught by this faculty
        for (CourseOffer offer : allOfferings) {
            try {
                info5100.university.example.Persona.Faculty.FacultyProfile assignedFaculty = 
                    offer.getFacultyProfile();
                
                if (assignedFaculty != null) {
                    info5100.university.example.Persona.Person assignedPerson = assignedFaculty.getPerson();
                    
                    if (assignedPerson != null && 
                        assignedPerson.getPersonId().equals(faculty.getPerson().getPersonId())) {
                        
                        Course course = offer.getSubjectCourse();
                        String display = course.getCOurseNumber() + " - " + course.getName();
                        cmbCourse.addItem(display);
                    }
                }
            } catch (NullPointerException e) {
                // Skip
            }
        }
    }
    
    private void btnGenerateActionPerformed(java.awt.event.ActionEvent evt) {
        if (cmbCourse.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a course!");
            return;
        }
        
        generatePerformanceReport();
        btnExport.setEnabled(true);
    }
    
    private void generatePerformanceReport() {
        DefaultTableModel model = (DefaultTableModel) tblReport.getModel();
        model.setRowCount(0);
        
        String courseDisplay = (String) cmbCourse.getSelectedItem();
        String courseNumber = courseDisplay.split(" - ")[0];
        String semester = (String) cmbSemester.getSelectedItem();
        
        // Get course offer
        Department dept = business.getDepartment();
        CourseSchedule schedule = dept.getCourseSchedule(semester);
        CourseOffer offer = schedule.getCourseOfferByNumber(courseNumber);
        
        if (offer == null) {
            JOptionPane.showMessageDialog(this, "Course not found!");
            return;
        }
        
        // Count grade distribution
        Map<String, Integer> gradeDistribution = new HashMap<>();
        gradeDistribution.put("A", 0);
        gradeDistribution.put("A-", 0);
        gradeDistribution.put("B+", 0);
        gradeDistribution.put("B", 0);
        gradeDistribution.put("B-", 0);
        gradeDistribution.put("C+", 0);
        gradeDistribution.put("C", 0);
        gradeDistribution.put("C-", 0);
        gradeDistribution.put("F", 0);
        gradeDistribution.put("Not Graded", 0);
        
        ArrayList<Seat> seats = offer.getSeatList();
        int totalStudents = 0;
        double totalGradePoints = 0;
        int gradedStudents = 0;
        int totalTuition = 0;
        
        for (Seat seat : seats) {
            if (seat.isOccupied()) {
                SeatAssignment sa = seat.getSeatAssignment();
                if (sa != null) {
                    totalStudents++;
                    
                    String grade = sa.getLetterGrade();
                    if (grade != null && !grade.isEmpty()) {
                        gradeDistribution.put(grade, gradeDistribution.get(grade) + 1);
                        totalGradePoints += sa.getGrade();
                        gradedStudents++;
                    } else {
                        gradeDistribution.put("Not Graded", gradeDistribution.get("Not Graded") + 1);
                    }
                    
                    // Calculate tuition
                    totalTuition += offer.getSubjectCourse().getCoursePrice();
                }
            }
        }
        
        // Populate grade distribution table
        String[] gradeOrder = {"A", "A-", "B+", "B", "B-", "C+", "C", "C-", "F", "Not Graded"};
        
        for (String grade : gradeOrder) {
            int count = gradeDistribution.get(grade);
            if (count > 0 || !grade.equals("Not Graded")) {
                Object[] row = new Object[3];
                row[0] = grade;
                row[1] = count;
                row[2] = totalStudents > 0 ? String.format("%.1f%%", (count * 100.0 / totalStudents)) : "0%";
                model.addRow(row);
            }
        }
        
        // Update summary
        double avgGrade = gradedStudents > 0 ? totalGradePoints / gradedStudents : 0;
        
        lblAverageGrade.setText(String.format("Average Grade: %.2f", avgGrade));
        lblEnrollmentCount.setText("Enrollment Count: " + totalStudents);
        lblTotalTuition.setText("Total Tuition: $" + String.format("%,d", totalTuition));
        
        System.out.println("✅ Generated performance report for " + courseNumber);
    }
    
    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {
        DefaultTableModel model = (DefaultTableModel) tblReport.getModel();
        
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Please generate a report first!");
            return;
        }
        
        String courseDisplay = (String) cmbCourse.getSelectedItem();
        String courseNumber = courseDisplay.split(" - ")[0];
        String semester = (String) cmbSemester.getSelectedItem();
        
        String filename = courseNumber + "_" + semester + "_PerformanceReport.csv";
        
        try {
            FileWriter writer = new FileWriter(filename);
            
            // Write header
            writer.append("Course Performance Report\n");
            writer.append("Course: " + courseDisplay + "\n");
            writer.append("Semester: " + semester + "\n");
            writer.append("Generated by: " + faculty.getPerson().getFullName() + "\n");
            writer.append("\n");
            
            // Write summary
            writer.append("Summary\n");
            writer.append(lblAverageGrade.getText() + "\n");
            writer.append(lblEnrollmentCount.getText() + "\n");
            writer.append(lblTotalTuition.getText() + "\n");
            writer.append("\n");
            
            // Write table headers
            writer.append("Letter Grade,Number of Students,Percentage\n");
            
            // Write table data
            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    writer.append(String.valueOf(model.getValueAt(i, j)));
                    if (j < model.getColumnCount() - 1) {
                        writer.append(",");
                    }
                }
                writer.append("\n");
            }
            
            writer.flush();
            writer.close();
            
            JOptionPane.showMessageDialog(this,
                "Report exported successfully!\n\nSaved as: " + filename,
                "Export Successful",
                JOptionPane.INFORMATION_MESSAGE);
            
            System.out.println("✅ Exported report to " + filename);
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                "Error exporting report: " + e.getMessage(),
                "Export Failed",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
}