package UserInterface.WorkAreas.StudentRole;

import Business.Business;
import Business.Profiles.StudentProfile;

import info5100.university.example.Department.Department;
import info5100.university.example.CourseSchedule.CourseSchedule;
import info5100.university.example.CourseSchedule.CourseOffer;
import info5100.university.example.CourseSchedule.CourseLoad;
import info5100.university.example.CourseSchedule.SeatAssignment;
import info5100.university.example.CourseCatalog.Course;
import info5100.university.example.Persona.Transcript;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * Course Registration Panel
 * Student can search, enroll, and drop courses with 8-credit validation
 * 
 * @author chethan
 */
public class CourseRegistrationPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    StudentProfile student;
    
    private JLabel lblTitle;
    private JLabel lblSemester;
    private JComboBox<String> cmbSemester;
    private JLabel lblSearch;
    private JComboBox<String> cmbSearchType;
    private JTextField txtSearch;
    private JButton btnSearch;
    private JButton btnShowAll;
    private JLabel lblAvailableCourses;
    private JScrollPane scrollAvailable;
    private JTable tblAvailableCourses;
    private JLabel lblMyEnrollments;
    private JScrollPane scrollEnrollments;
    private JTable tblMyEnrollments;
    private JButton btnEnroll;
    private JButton btnDrop;
    private JButton btnBack;
    
    public CourseRegistrationPanel(Business b, StudentProfile sp, JPanel clp) {
        business = b;
        student = sp;
        CardSequencePanel = clp;
        
        initComponents();
        loadAvailableCourses();
        loadMyEnrollments();
    }
    
    private void initComponents() {
        lblTitle = new JLabel();
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));
        lblTitle.setText("Course Registration");
        
        lblSemester = new JLabel();
        lblSemester.setText("Semester:");
        
        cmbSemester = new JComboBox<>();
        cmbSemester.addItem("Fall2025");
        cmbSemester.addActionListener(evt -> cmbSemesterActionPerformed(evt));
        
        lblSearch = new JLabel();
        lblSearch.setText("Search By:");
        
        cmbSearchType = new JComboBox<>();
        cmbSearchType.addItem("Course ID");
        cmbSearchType.addItem("Teacher Name");
        cmbSearchType.addItem("Department");
        
        txtSearch = new JTextField();
        
        btnSearch = new JButton();
        btnSearch.setText("Search");
        btnSearch.addActionListener(evt -> btnSearchActionPerformed(evt));
        
        btnShowAll = new JButton();
        btnShowAll.setText("Show All");
        btnShowAll.addActionListener(evt -> loadAvailableCourses());
        
        lblAvailableCourses = new JLabel();
        lblAvailableCourses.setFont(new java.awt.Font("Dialog", 1, 14));
        lblAvailableCourses.setText("Available Courses:");
        
        tblAvailableCourses = new JTable();
        tblAvailableCourses.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"Course Number", "Course Name", "Credits", "Faculty", "Enrolled", "Capacity", "Status"}
        ) {
            boolean[] canEdit = new boolean[] {false, false, false, false, false, false, false};
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        
        scrollAvailable = new JScrollPane();
        scrollAvailable.setViewportView(tblAvailableCourses);
        
        lblMyEnrollments = new JLabel();
        lblMyEnrollments.setFont(new java.awt.Font("Dialog", 1, 14));
        lblMyEnrollments.setText("My Current Enrollments (0 credits / 8 max):");
        
        tblMyEnrollments = new JTable();
        tblMyEnrollments.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"Course Number", "Course Name", "Credits", "Semester"}
        ) {
            boolean[] canEdit = new boolean[] {false, false, false, false};
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        
        scrollEnrollments = new JScrollPane();
        scrollEnrollments.setViewportView(tblMyEnrollments);
        
        btnEnroll = new JButton();
        btnEnroll.setBackground(new java.awt.Color(102, 153, 255));
        btnEnroll.setForeground(new java.awt.Color(255, 255, 255));
        btnEnroll.setText("Enroll in Selected Course");
        btnEnroll.addActionListener(evt -> btnEnrollActionPerformed(evt));
        
        btnDrop = new JButton();
        btnDrop.setBackground(new java.awt.Color(255, 102, 102));
        btnDrop.setForeground(new java.awt.Color(255, 255, 255));
        btnDrop.setText("Drop Selected Course");
        btnDrop.addActionListener(evt -> btnDropActionPerformed(evt));
        
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
        
        add(lblSearch);
        lblSearch.setBounds(300, 65, 80, 25);
        add(cmbSearchType);
        cmbSearchType.setBounds(380, 65, 120, 25);
        add(txtSearch);
        txtSearch.setBounds(510, 65, 150, 25);
        add(btnSearch);
        btnSearch.setBounds(670, 65, 80, 25);
        add(btnShowAll);
        btnShowAll.setBounds(760, 65, 100, 25);
        
        add(lblAvailableCourses);
        lblAvailableCourses.setBounds(30, 100, 300, 25);
        add(scrollAvailable);
        scrollAvailable.setBounds(30, 130, 830, 180);
        
        add(lblMyEnrollments);
        lblMyEnrollments.setBounds(30, 320, 500, 25);
        add(scrollEnrollments);
        scrollEnrollments.setBounds(30, 350, 830, 120);
        
        add(btnEnroll);
        btnEnroll.setBounds(550, 485, 200, 35);
        
        add(btnDrop);
        btnDrop.setBounds(760, 485, 180, 35);
        
        add(btnBack);
        btnBack.setBounds(30, 485, 100, 35);
    }
    
    private void cmbSemesterActionPerformed(java.awt.event.ActionEvent evt) {
        loadAvailableCourses();
        loadMyEnrollments();
    }
    
    private void loadAvailableCourses() {
        DefaultTableModel model = (DefaultTableModel) tblAvailableCourses.getModel();
        model.setRowCount(0);
        
        String semester = (String) cmbSemester.getSelectedItem();
        if (semester == null) return;
        
        Department dept = business.getDepartment();
        CourseSchedule schedule = dept.getCourseSchedule(semester);
        
        if (schedule == null) return;
        
        ArrayList<CourseOffer> offerings = schedule.getCourseOffers();
        
        for (CourseOffer offer : offerings) {
            addCourseToTable(model, offer);
        }
        
        System.out.println("Loaded " + offerings.size() + " available courses");
    }

    private void addCourseToTable(DefaultTableModel model, CourseOffer offer) {
        Object[] row = new Object[7];
        
        Course course = offer.getSubjectCourse();
        row[0] = course.getCOurseNumber();
        row[1] = course.getName();
        row[2] = course.getCredits();
        
        // Faculty
        try {
            info5100.university.example.Persona.Faculty.FacultyProfile faculty = offer.getFacultyProfile();
            row[3] = faculty != null && faculty.getPerson() != null ? 
                     faculty.getPerson().getFullName() : "TBD";
        } catch (Exception e) {
            row[3] = "TBD";
        }
        
        row[4] = offer.getEnrolledCount();
        row[5] = offer.getCapacity();
        
        // Status
        boolean isFull = offer.getEnrolledCount() >= offer.getCapacity();
        boolean isOpen = offer.isEnrollmentOpen();
        
        if (!isOpen) {
            row[6] = "Closed";
        } else if (isFull) {
            row[6] = "Full";
        } else {
            row[6] = "Open";
        }
        
        model.addRow(row);
    }
    
    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {
        String searchType = (String) cmbSearchType.getSelectedItem();
        String searchText = txtSearch.getText().trim().toLowerCase();
        
        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter search text!");
            return;
        }
        
        DefaultTableModel model = (DefaultTableModel) tblAvailableCourses.getModel();
        model.setRowCount(0);
        
        String semester = (String) cmbSemester.getSelectedItem();
        Department dept = business.getDepartment();
        CourseSchedule schedule = dept.getCourseSchedule(semester);
        
        if (schedule == null) return;
        
        ArrayList<CourseOffer> offerings = schedule.getCourseOffers();
        int resultsFound = 0;
        
        for (CourseOffer offer : offerings) {
            boolean matches = false;
            
            Course course = offer.getSubjectCourse();
            
            // SEARCH METHOD 1: By Course ID
            if ("Course ID".equals(searchType)) {
                matches = course.getCOurseNumber().toLowerCase().contains(searchText);
            }
            // SEARCH METHOD 2: By Teacher Name
            else if ("Teacher Name".equals(searchType)) {
                try {
                    info5100.university.example.Persona.Faculty.FacultyProfile faculty = offer.getFacultyProfile();
                    if (faculty != null && faculty.getPerson() != null) {
                        String facultyName = faculty.getPerson().getFullName().toLowerCase();
                        matches = facultyName.contains(searchText);
                    }
                } catch (Exception e) {
                    matches = false;
                }
            }
            // SEARCH METHOD 3: By Department
            else if ("Department".equals(searchType)) {
                String deptName = dept.getName().toLowerCase();
                matches = deptName.contains(searchText);
            }
            
            if (matches) {
                addCourseToTable(model, offer);
                resultsFound++;
            }
        }
        
        System.out.println("Search completed: " + resultsFound + " results found");
        
        if (resultsFound == 0) {
            JOptionPane.showMessageDialog(this, "No courses found matching: " + searchText);
        }
    }
    
    private void loadMyEnrollments() {
        DefaultTableModel model = (DefaultTableModel) tblMyEnrollments.getModel();
        model.setRowCount(0);
        
        String semester = (String) cmbSemester.getSelectedItem();
        if (semester == null) return;
        
        // Get student's university profile
        info5100.university.example.Persona.StudentProfile univStudent = student.getUniversityProfile();
        Transcript transcript = univStudent.getTranscript();
        
        // Get course load for selected semester
        CourseLoad courseLoad = transcript.getCourseLoadBySemester(semester);
        
        int totalCredits = 0;
        
        if (courseLoad != null) {
            ArrayList<SeatAssignment> enrollments = courseLoad.getSeatAssignments();
            
            for (SeatAssignment sa : enrollments) {
                Object[] row = new Object[4];
                
                Course course = sa.getAssociatedCourse();
                row[0] = course.getCOurseNumber();
                row[1] = course.getName();
                row[2] = course.getCredits();
                row[3] = semester;
                
                model.addRow(row);
                totalCredits += course.getCredits();
            }
        }
        
        // Update label with credit count
        lblMyEnrollments.setText(String.format("My Current Enrollments (%d credits / 8 max):", totalCredits));
        
        System.out.println("Student has " + totalCredits + " credits enrolled");
    }
    
    private void btnEnrollActionPerformed(java.awt.event.ActionEvent evt) {
        // Get selected course from available courses table
        int selectedRow = tblAvailableCourses.getSelectedRow();
        
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a course to enroll!");
            return;
        }
        
        String courseNumber = (String) tblAvailableCourses.getValueAt(selectedRow, 0);
        String status = (String) tblAvailableCourses.getValueAt(selectedRow, 6);
        
        // Check if course is open and not full
        if ("Closed".equals(status)) {
            JOptionPane.showMessageDialog(this, "This course is closed for enrollment!");
            return;
        }
        
        if ("Full".equals(status)) {
            JOptionPane.showMessageDialog(this, "This course is full!");
            return;
        }
        
        // Get course offer
        String semester = (String) cmbSemester.getSelectedItem();
        Department dept = business.getDepartment();
        CourseSchedule schedule = dept.getCourseSchedule(semester);
        CourseOffer offer = schedule.getCourseOfferByNumber(courseNumber);
        
        if (offer == null) {
            JOptionPane.showMessageDialog(this, "Course not found!");
            return;
        }
        
        // Get student's course load
        info5100.university.example.Persona.StudentProfile univStudent = student.getUniversityProfile();
        Transcript transcript = univStudent.getTranscript();
        CourseLoad courseLoad = transcript.getCourseLoadBySemester(semester);
        
        if (courseLoad == null) {
            courseLoad = univStudent.newCourseLoad(semester);
        }
        
        // CHECK 1: 8-CREDIT LIMIT VALIDATION
        int currentCredits = courseLoad.getTotalCreditHours();
        int courseCredits = offer.getSubjectCourse().getCredits();
        
        if (currentCredits + courseCredits > 8) {
            JOptionPane.showMessageDialog(this,
                "Cannot enroll! Exceeds 8-credit limit.\n\n" +
                "Current credits: " + currentCredits + "\n" +
                "Course credits: " + courseCredits + "\n" +
                "Total would be: " + (currentCredits + courseCredits) + "\n" +
                "Maximum allowed: 8",
                "Credit Limit Exceeded",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // CHECK 2: Already enrolled check
        ArrayList<SeatAssignment> currentEnrollments = courseLoad.getSeatAssignments();
        for (SeatAssignment sa : currentEnrollments) {
            if (sa.getAssociatedCourse().getCOurseNumber().equals(courseNumber)) {
                JOptionPane.showMessageDialog(this, "You are already enrolled in this course!");
                return;
            }
        }
        
        // ENROLL THE STUDENT
        SeatAssignment sa = offer.assignEmptySeat(courseLoad);
        
        if (sa == null) {
            JOptionPane.showMessageDialog(this, "Enrollment failed! Course may be full.");
            return;
        }
        
        System.out.println("Enrolled in " + courseNumber + " | Credits: " + (currentCredits + courseCredits) + "/8");
        
        // AUTO-BILL TUITION FOR THIS COURSE
        double courseTuition = offer.getSubjectCourse().getCoursePrice();
        student.addTuitionCharge(courseTuition);

        System.out.println("Billed tuition: $" + courseTuition + " | New balance: $" + student.getTuitionBalance());
        
        JOptionPane.showMessageDialog(this,
        "Enrolled successfully!\n\n" +
        "Course: " + courseNumber + "\n" +
        "Credits: " + courseCredits + "\n" +
        "Total credits: " + (currentCredits + courseCredits) + "/8\n\n" +
        "Tuition billed: $" + String.format("%,d", (int)courseTuition) + "\n" +
        "New balance: $" + String.format("%,d", (int)student.getTuitionBalance()),
        "Enrolled & Billed",
        JOptionPane.INFORMATION_MESSAGE);
        
        // Refresh both tables
        loadAvailableCourses();
        loadMyEnrollments();
    }
    
    private void btnDropActionPerformed(java.awt.event.ActionEvent evt) {
        // Get selected course from MY ENROLLMENTS table
        int selectedRow = tblMyEnrollments.getSelectedRow();
        
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a course from your enrollments to drop!");
            return;
        }
        
        String courseNumber = (String) tblMyEnrollments.getValueAt(selectedRow, 0);
        String courseName = (String) tblMyEnrollments.getValueAt(selectedRow, 1);
        
        // Confirm drop
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to drop:\n\n" +
            courseNumber + " - " + courseName + "?",
            "Confirm Drop",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        
        // Get student's course load
        String semester = (String) cmbSemester.getSelectedItem();
        info5100.university.example.Persona.StudentProfile univStudent = student.getUniversityProfile();
        Transcript transcript = univStudent.getTranscript();
        CourseLoad courseLoad = transcript.getCourseLoadBySemester(semester);
        
        if (courseLoad == null) {
            JOptionPane.showMessageDialog(this, "Error: Course load not found!");
            return;
        }
        
        // Find and remove the seat assignment
        ArrayList<SeatAssignment> enrollments = courseLoad.getSeatAssignments();
        SeatAssignment toRemove = null;
        
        for (SeatAssignment sa : enrollments) {
            if (sa.getAssociatedCourse().getCOurseNumber().equals(courseNumber)) {
                toRemove = sa;
                break;
            }
        }
        
        if (toRemove == null) {
            JOptionPane.showMessageDialog(this, "Error: Enrollment not found!");
            return;
        }
        
        // Remove from course load
        enrollments.remove(toRemove);
        
        // Vacate the seat
        toRemove.getSeat().vacate();
        
        System.out.println("Dropped course: " + courseNumber);
        
        // REFUND TUITION FOR DROPPED COURSE
        double coursePrice = toRemove.getAssociatedCourse().getCoursePrice();
        student.refundTuition(coursePrice);

        System.out.println("Refunded tuition: $" + coursePrice + " | New balance: $" + student.getTuitionBalance());
        
        JOptionPane.showMessageDialog(this,
        "Course dropped successfully!\n\n" +
        "Course: " + courseNumber + " - " + courseName + "\n\n" +
        "Tuition refunded: $" + String.format("%,d", (int)coursePrice) + "\n" +
        "New balance: $" + String.format("%,d", (int)student.getTuitionBalance()),
        "Dropped & Refunded",
        JOptionPane.INFORMATION_MESSAGE);
        
        // Refresh both tables
        loadAvailableCourses();
        loadMyEnrollments();
    }
    
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
}