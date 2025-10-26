package UserInterface.WorkAreas.RegistrarRole;

import Business.Business;
import Business.Profiles.RegistrarProfile;
import Business.Profiles.StudentProfile;
import Business.Profiles.StudentDirectory;

import info5100.university.example.Department.Department;
import info5100.university.example.CourseSchedule.CourseSchedule;
import info5100.university.example.CourseSchedule.SeatAssignment;
import info5100.university.example.CourseCatalog.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * Financial Reconciliation Panel
 * Monitor tuition payment status and generate financial reports
 */
public class FinancialReconciliationPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    RegistrarProfile registrar;
    
    // UI Components
    private JLabel lblTitle;
    private JLabel lblSemester;
    private JComboBox<String> cmbSemester;
    private JButton btnRefresh;
    private JScrollPane scrollPane;
    private JTable tblFinancial;
    private JPanel summaryPanel;
    private JLabel lblSummaryTitle;
    private JLabel lblTotalCollected;
    private JLabel lblTotalOutstanding;
    private JLabel lblStudentsPaid;
    private JLabel lblStudentsUnpaid;
    private JButton btnBack;
    
    public FinancialReconciliationPanel(Business b, RegistrarProfile rp, JPanel clp) {
        business = b;
        registrar = rp;
        CardSequencePanel = clp;
        
        initComponents();
        loadSemesters();
        loadFinancialData();
    }
    
    private void initComponents() {
        // Title
        lblTitle = new JLabel();
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));
        lblTitle.setText("Financial Reconciliation");
        
        // Semester filter
        lblSemester = new JLabel();
        lblSemester.setText("Semester:");
        
        cmbSemester = new JComboBox<>();
        cmbSemester.addActionListener(evt -> cmbSemesterActionPerformed(evt));
        
        btnRefresh = new JButton();
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(evt -> loadFinancialData());
        
        // Financial Table
        tblFinancial = new JTable();
        tblFinancial.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Student Name", "Courses Enrolled", "Total Owed", "Amount Paid", "Balance", "Status"
            }
        ) {
            boolean[] canEdit = new boolean[] {false, false, false, false, false, false};
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(tblFinancial);
        
        // Summary Panel
        summaryPanel = new JPanel();
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Financial Summary"));
        summaryPanel.setLayout(null);
        
        lblSummaryTitle = new JLabel();
        lblSummaryTitle.setFont(new java.awt.Font("Dialog", 1, 14));
        lblSummaryTitle.setText("Summary for Selected Semester:");
        
        lblTotalCollected = new JLabel();
        lblTotalCollected.setText("Total Collected: $0");
        
        lblTotalOutstanding = new JLabel();
        lblTotalOutstanding.setText("Total Outstanding: $0");
        
        lblStudentsPaid = new JLabel();
        lblStudentsPaid.setText("Students Paid: 0");
        
        lblStudentsUnpaid = new JLabel();
        lblStudentsUnpaid.setText("Students Unpaid: 0");
        
        summaryPanel.add(lblSummaryTitle);
        lblSummaryTitle.setBounds(10, 20, 300, 25);
        
        summaryPanel.add(lblTotalCollected);
        lblTotalCollected.setBounds(10, 50, 250, 25);
        
        summaryPanel.add(lblTotalOutstanding);
        lblTotalOutstanding.setBounds(10, 75, 250, 25);
        
        summaryPanel.add(lblStudentsPaid);
        lblStudentsPaid.setBounds(270, 50, 200, 25);
        
        summaryPanel.add(lblStudentsUnpaid);
        lblStudentsUnpaid.setBounds(270, 75, 200, 25);
        
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
        
        add(btnRefresh);
        btnRefresh.setBounds(280, 70, 100, 25);
        
        add(scrollPane);
        scrollPane.setBounds(30, 110, 700, 250);
        
        add(summaryPanel);
        summaryPanel.setBounds(30, 370, 700, 120);
        
        add(btnBack);
        btnBack.setBounds(30, 510, 100, 35);
    }
    
    private void loadSemesters() {
        cmbSemester.removeAllItems();
        cmbSemester.addItem("All Semesters");
        cmbSemester.addItem("Fall2025");
        // Add more semesters as needed
    }
    
    private void cmbSemesterActionPerformed(java.awt.event.ActionEvent evt) {
        loadFinancialData();
    }
    
    private void loadFinancialData() {
        // Clear table
        DefaultTableModel model = (DefaultTableModel) tblFinancial.getModel();
        model.setRowCount(0);
        
        String selectedSemester = (String) cmbSemester.getSelectedItem();
        boolean allSemesters = "All Semesters".equals(selectedSemester);
        
        // Get all students
        StudentDirectory sd = business.getStudentDirectory();
        ArrayList<StudentProfile> students = sd.getStudentList();
        
        int totalCollected = 0;
        int totalOutstanding = 0;
        int studentsPaid = 0;
        int studentsUnpaid = 0;
        
        for (StudentProfile sp : students) {
            // Get student's enrollments
            info5100.university.example.Persona.StudentProfile universityStudent = 
                sp.getUniversityProfile();
            
            ArrayList<SeatAssignment> enrollments = 
                universityStudent.getTranscript().getCourseList();
            
            if (enrollments == null || enrollments.isEmpty()) {
                continue; // Skip students with no enrollments
            }
            
            // Calculate tuition for this student
            int studentTuition = 0;
            int coursesCount = 0;
            
            for (SeatAssignment sa : enrollments) {
                // Filter by semester if needed
                if (!allSemesters) {
                    String courseSemester = sa.getCourseLoad().getSemester();
                    if (!selectedSemester.equals(courseSemester)) {
                        continue; // Skip this course
                    }
                }
                
                Course course = sa.getAssociatedCourse();
                int courseCost = course.getCoursePrice(); // $1500 * credits
                studentTuition += courseCost;
                coursesCount++;
            }
            
            if (coursesCount == 0) {
                continue; // Skip if no courses in selected semester
            }
            
            // For now, assume all tuition is unpaid (no payment system in model)
            int amountPaid = 0;
            int balance = studentTuition;
            String status = balance > 0 ? "Unpaid" : "Paid";
            
            // Add to table
            Object[] row = new Object[6];
            row[0] = sp.getPerson().getFullName();
            row[1] = coursesCount;
            row[2] = "$" + String.format("%,d", studentTuition);
            row[3] = "$" + String.format("%,d", amountPaid);
            row[4] = "$" + String.format("%,d", balance);
            row[5] = status;
            
            model.addRow(row);
            
            // Update summary
            totalCollected += amountPaid;
            totalOutstanding += balance;
            
            if (balance == 0) {
                studentsPaid++;
            } else {
                studentsUnpaid++;
            }
        }
        
        // Update summary labels
        lblTotalCollected.setText("Total Collected: $" + String.format("%,d", totalCollected));
        lblTotalOutstanding.setText("Total Outstanding: $" + String.format("%,d", totalOutstanding));
        lblStudentsPaid.setText("Students Paid: " + studentsPaid);
        lblStudentsUnpaid.setText("Students Unpaid: " + studentsUnpaid);
        
        System.out.println("✅ Loaded financial data for " + model.getRowCount() + " students");
    }
    
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
}