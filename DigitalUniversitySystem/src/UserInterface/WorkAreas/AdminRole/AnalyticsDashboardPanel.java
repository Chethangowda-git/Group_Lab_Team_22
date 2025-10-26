package UserInterface.WorkAreas.AdminRole;

import Business.Business;
import Business.Profiles.*;
import Business.UserAccounts.UserAccountDirectory;

import info5100.university.example.Department.Department;
import info5100.university.example.CourseSchedule.CourseSchedule;
import info5100.university.example.CourseSchedule.CourseOffer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * Analytics Dashboard Panel
 * Display university-level statistics and reports
 */
public class AnalyticsDashboardPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    
    private JLabel lblTitle;
    private JLabel lblSemester;
    private JComboBox<String> cmbSemester;
    private JButton btnRefresh;
    
    // Summary metrics
    private JPanel metricsPanel;
    private JLabel lblTotalUsers;
    private JLabel lblTotalStudents;
    private JLabel lblTotalFaculty;
    private JLabel lblTotalAdmin;
    private JLabel lblTotalCourses;
    private JLabel lblTotalEnrollments;
    private JLabel lblTuitionRevenue;
    
    // Tables
    private JLabel lblCourseEnrollment;
    private JScrollPane scrollCourses;
    private JTable tblCourseEnrollment;
    
    private JLabel lblUsersByRole;
    private JScrollPane scrollUsers;
    private JTable tblUsersByRole;
    
    private JButton btnBack;
    
    public AnalyticsDashboardPanel(Business b, JPanel clp) {
        business = b;
        CardSequencePanel = clp;
        
        initComponents();
        loadAnalytics();
    }
    
    private void initComponents() {
        lblTitle = new JLabel();
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));
        lblTitle.setText("University Analytics Dashboard");
        
        lblSemester = new JLabel("Semester:");
        cmbSemester = new JComboBox<>();
        cmbSemester.addItem("Fall2025");
        cmbSemester.addActionListener(evt -> loadAnalytics());
        
        btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(evt -> loadAnalytics());
        
        // Metrics Panel
        metricsPanel = new JPanel();
        metricsPanel.setBorder(BorderFactory.createTitledBorder("Summary Metrics"));
        metricsPanel.setLayout(null);
        
        lblTotalUsers = new JLabel("Total Users: 0");
        lblTotalStudents = new JLabel("Students: 0");
        lblTotalFaculty = new JLabel("Faculty: 0");
        lblTotalAdmin = new JLabel("Admin: 0");
        lblTotalCourses = new JLabel("Courses Offered: 0");
        lblTotalEnrollments = new JLabel("Total Enrollments: 0");
        lblTuitionRevenue = new JLabel("Tuition Revenue: $0");
        
        metricsPanel.add(lblTotalUsers);
        lblTotalUsers.setBounds(20, 25, 200, 25);
        metricsPanel.add(lblTotalStudents);
        lblTotalStudents.setBounds(20, 50, 200, 25);
        metricsPanel.add(lblTotalFaculty);
        lblTotalFaculty.setBounds(20, 75, 200, 25);
        metricsPanel.add(lblTotalAdmin);
        lblTotalAdmin.setBounds(240, 25, 200, 25);
        
        metricsPanel.add(lblTotalCourses);
        lblTotalCourses.setBounds(240, 50, 200, 25);
        metricsPanel.add(lblTotalEnrollments);
        lblTotalEnrollments.setBounds(240, 75, 200, 25);
        
        metricsPanel.add(lblTuitionRevenue);
        lblTuitionRevenue.setBounds(460, 25, 250, 25);
        
        // Course Enrollment Table
        lblCourseEnrollment = new JLabel();
        lblCourseEnrollment.setFont(new java.awt.Font("Dialog", 1, 14));
        lblCourseEnrollment.setText("Enrolled Students Per Course:");
        
        tblCourseEnrollment = new JTable();
        tblCourseEnrollment.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Course Number", "Course Name", "Capacity", "Enrolled", "Enrollment %"
            }
        ) {
            boolean[] canEdit = new boolean[] {false, false, false, false, false};
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        
        scrollCourses = new JScrollPane();
        scrollCourses.setViewportView(tblCourseEnrollment);
        
        // Users by Role Table
        lblUsersByRole = new JLabel();
        lblUsersByRole.setFont(new java.awt.Font("Dialog", 1, 14));
        lblUsersByRole.setText("Active Users by Role:");
        
        tblUsersByRole = new JTable();
        tblUsersByRole.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Role", "User Count", "Percentage"
            }
        ) {
            boolean[] canEdit = new boolean[] {false, false, false};
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        
        scrollUsers = new JScrollPane();
        scrollUsers.setViewportView(tblUsersByRole);
        
        btnBack = new JButton("<< Back");
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
        
        add(metricsPanel);
        metricsPanel.setBounds(30, 105, 850, 120);
        
        add(lblCourseEnrollment);
        lblCourseEnrollment.setBounds(30, 240, 400, 25);
        add(scrollCourses);
        scrollCourses.setBounds(30, 270, 550, 200);
        
        add(lblUsersByRole);
        lblUsersByRole.setBounds(600, 240, 280, 25);
        add(scrollUsers);
        scrollUsers.setBounds(600, 270, 280, 200);
        
        add(btnBack);
        btnBack.setBounds(30, 490, 100, 35);
    }
    
    private void loadAnalytics() {
        String semester = (String) cmbSemester.getSelectedItem();
        
        // Get directories
        StudentDirectory sd = business.getStudentDirectory();
        FacultyDirectory fd = business.getFacultyDirectory();
        EmployeeDirectory ed = business.getEmployeeDirectory();
        RegistrarDirectory rd = business.getRegistrarDirectory();
        
        // COUNT USERS BY ROLE
        int studentCount = sd.getStudentList().size();
        int facultyCount = fd.getFacultyList().size();
        int adminCount = ed.getEmployeeList().size();
int registrarCount = rd.getRegistrarList().size();
        int totalUsers = studentCount + facultyCount + adminCount + registrarCount;
        
        lblTotalUsers.setText("Total Users: " + totalUsers);
        lblTotalStudents.setText("Students: " + studentCount);
        lblTotalFaculty.setText("Faculty: " + facultyCount);
        lblTotalAdmin.setText("Admin/Registrar: " + (adminCount + registrarCount));
        
        // POPULATE USERS BY ROLE TABLE
        DefaultTableModel userModel = (DefaultTableModel) tblUsersByRole.getModel();
        userModel.setRowCount(0);
        
        addRoleRow(userModel, "Students", studentCount, totalUsers);
        addRoleRow(userModel, "Faculty", facultyCount, totalUsers);
        addRoleRow(userModel, "Admin", adminCount, totalUsers);
        addRoleRow(userModel, "Registrar", registrarCount, totalUsers);
        
        // GET COURSE DATA
        Department dept = business.getDepartment();
        CourseSchedule schedule = dept.getCourseSchedule(semester);
        
        if (schedule == null) return;
        
        ArrayList<CourseOffer> offerings = schedule.getCourseOffers();
        
        lblTotalCourses.setText("Courses Offered: " + offerings.size());
        
        // CALCULATE ENROLLMENTS AND TUITION
        int totalEnrollments = 0;
        double totalTuitionCollected = 0;
        
        // Calculate tuition collected from students who paid
        ArrayList<StudentProfile> allStudents = sd.getStudentList();
        for (StudentProfile sp : allStudents) {
            double owed = sp.calculateTotalTuitionOwed();
            double balance = sp.getTuitionBalance();
            double paid = owed - balance;
            if (paid > 0) {
                totalTuitionCollected += paid;
            }
        }
        
        // POPULATE COURSE ENROLLMENT TABLE
        DefaultTableModel courseModel = (DefaultTableModel) tblCourseEnrollment.getModel();
        courseModel.setRowCount(0);
        
        for (CourseOffer offer : offerings) {
            Object[] row = new Object[5];
            
            info5100.university.example.CourseCatalog.Course course = offer.getSubjectCourse();
            int capacity = offer.getCapacity();
            int enrolled = offer.getEnrolledCount();
            double enrollPercent = capacity > 0 ? (enrolled * 100.0 / capacity) : 0;
            
            row[0] = course.getCOurseNumber();
            row[1] = course.getName();
            row[2] = capacity;
            row[3] = enrolled;
            row[4] = String.format("%.1f%%", enrollPercent);
            
            courseModel.addRow(row);
            totalEnrollments += enrolled;
        }
        
        lblTotalEnrollments.setText("Total Enrollments: " + totalEnrollments);
        lblTuitionRevenue.setText("Tuition Collected: $" + String.format("%,d", (int)totalTuitionCollected));
        
        System.out.println("✅ Analytics loaded: " + totalUsers + " users, " + 
                          offerings.size() + " courses, " + totalEnrollments + " enrollments");
    }
    
    private void addRoleRow(DefaultTableModel model, String role, int count, int total) {
        Object[] row = new Object[3];
        row[0] = role;
        row[1] = count;
        row[2] = total > 0 ? String.format("%.1f%%", (count * 100.0 / total)) : "0%";
        model.addRow(row);
    }
    
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
}