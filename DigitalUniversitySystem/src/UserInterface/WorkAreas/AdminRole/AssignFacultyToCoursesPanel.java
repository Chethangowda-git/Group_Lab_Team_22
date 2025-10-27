/*
 * AssignFacultyToCoursesPanel.java
 * Admin can assign or reassign faculty to course offerings
 * 
 * @author chethan
 */
package UserInterface.WorkAreas.AdminRole;

import Business.Business;
import Business.Profiles.FacultyDirectory;
import Business.Profiles.FacultyProfile;

import info5100.university.example.Department.Department;
import info5100.university.example.CourseSchedule.CourseSchedule;
import info5100.university.example.CourseSchedule.CourseOffer;
import info5100.university.example.CourseCatalog.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * Assign Faculty to Courses Panel
 * Admin can view all course offerings and assign/reassign faculty
 */
public class AssignFacultyToCoursesPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    
    CourseOffer selectedCourseOffer = null;
    
    private JLabel lblTitle;
    private JLabel lblSemester;
    private JComboBox<String> cmbSemester;
    private JButton btnRefresh;
    private JScrollPane scrollPane;
    private JTable tblCourseOfferings;
    private JLabel lblSelectedCourse;
    private JTextField txtSelectedCourse;
    private JLabel lblCurrentFaculty;
    private JTextField txtCurrentFaculty;
    private JLabel lblAssignFaculty;
    private JComboBox<String> cmbFaculty;
    private JButton btnAssign;
    private JButton btnUnassign;
    private JButton btnBack;
    
    public AssignFacultyToCoursesPanel(Business b, JPanel clp) {
        business = b;
        CardSequencePanel = clp;
        
        initComponents();
        loadSemesters();
        loadCourseOfferings();
        loadFacultyList();
    }
    
    /**
     * Initialize all UI components
     */
    private void initComponents() {
        lblTitle = new JLabel();
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));
        lblTitle.setText("Assign Faculty to Courses");
        
        lblSemester = new JLabel("Semester:");
        
        cmbSemester = new JComboBox<>();
        cmbSemester.addActionListener(evt -> loadCourseOfferings());
        
        btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(evt -> loadCourseOfferings());
        
        // Course Offerings Table
        tblCourseOfferings = new JTable();
        tblCourseOfferings.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Course Number", "Course Name", "Capacity", "Enrolled", "Room", "Schedule", "Faculty Assigned"
            }
        ) {
            boolean[] canEdit = new boolean[] {false, false, false, false, false, false, false};
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        
        tblCourseOfferings.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                tblCourseOfferingsSelectionChanged();
            }
        });
        
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(tblCourseOfferings);
        
        // Assignment Section
        lblSelectedCourse = new JLabel("Selected Course:");
        txtSelectedCourse = new JTextField();
        txtSelectedCourse.setEditable(false);
        txtSelectedCourse.setBackground(java.awt.Color.LIGHT_GRAY);
        
        lblCurrentFaculty = new JLabel("Current Faculty:");
        txtCurrentFaculty = new JTextField();
        txtCurrentFaculty.setEditable(false);
        txtCurrentFaculty.setBackground(java.awt.Color.LIGHT_GRAY);
        
        lblAssignFaculty = new JLabel("Assign Faculty: *");
        cmbFaculty = new JComboBox<>();
        
        btnAssign = new JButton("Assign Faculty");
        btnAssign.setBackground(new java.awt.Color(102, 153, 255));
        btnAssign.setForeground(java.awt.Color.WHITE);
        btnAssign.addActionListener(evt -> btnAssignActionPerformed(evt));
        
        btnUnassign = new JButton("Unassign Faculty");
        btnUnassign.setBackground(new java.awt.Color(255, 153, 51));
        btnUnassign.setForeground(java.awt.Color.WHITE);
        btnUnassign.addActionListener(evt -> btnUnassignActionPerformed(evt));
        
        btnBack = new JButton("<< Back");
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
        
        add(scrollPane);
        scrollPane.setBounds(30, 105, 950, 280);
        
        // Assignment controls
        add(lblSelectedCourse);
        lblSelectedCourse.setBounds(30, 400, 150, 25);
        add(txtSelectedCourse);
        txtSelectedCourse.setBounds(180, 400, 400, 30);
        
        add(lblCurrentFaculty);
        lblCurrentFaculty.setBounds(30, 440, 150, 25);
        add(txtCurrentFaculty);
        txtCurrentFaculty.setBounds(180, 440, 400, 30);
        
        add(lblAssignFaculty);
        lblAssignFaculty.setBounds(30, 480, 150, 25);
        add(cmbFaculty);
        cmbFaculty.setBounds(180, 480, 300, 30);
        
        add(btnAssign);
        btnAssign.setBounds(490, 480, 140, 30);
        
        add(btnUnassign);
        btnUnassign.setBounds(640, 480, 140, 30);
        
        add(btnBack);
        btnBack.setBounds(30, 530, 100, 35);
    }
    
    /**
     * Load available semesters into dropdown
     */
    private void loadSemesters() {
        cmbSemester.removeAllItems();
        cmbSemester.addItem("Fall2025");
        
        // Try to get the schedule to verify it exists
        Department dept = business.getDepartment();
        CourseSchedule schedule = dept.getCourseSchedule("Fall2025");
        
        if (schedule != null) {
            System.out.println("✅ Found semester: Fall2025");
        } else {
            System.out.println("⚠️ No course schedule found for Fall2025");
        }
    }
    
    /**
     * Load all course offerings for selected semester
     */
    private void loadCourseOfferings() {
        DefaultTableModel model = (DefaultTableModel) tblCourseOfferings.getModel();
        model.setRowCount(0);
        
        String semester = (String) cmbSemester.getSelectedItem();
        if (semester == null) {
            semester = "Fall2025";
        }
        
        Department dept = business.getDepartment();
        CourseSchedule schedule = dept.getCourseSchedule(semester);
        
        if (schedule == null) {
            System.out.println("⚠️ No course schedule found for " + semester);
            return;
        }
        
        ArrayList<CourseOffer> offerings = schedule.getCourseOffers();
        
        if (offerings == null || offerings.isEmpty()) {
            System.out.println("⚠️ No course offerings found");
            return;
        }
        
        for (CourseOffer offer : offerings) {
            addCourseOfferToTable(model, offer);
        }
        
        System.out.println("✅ Loaded " + offerings.size() + " course offerings");
    }
    
    /**
     * Add a single course offering to the table
     */
    private void addCourseOfferToTable(DefaultTableModel model, CourseOffer offer) {
        if (offer == null) return;
        
        Object[] row = new Object[7];
        
        Course course = offer.getSubjectCourse();
        if (course == null) return;
        
        row[0] = course.getCOurseNumber();
        row[1] = course.getName();
        row[2] = offer.getCapacity();
        row[3] = offer.getEnrolledCount();
        row[4] = offer.getRoom() != null ? offer.getRoom() : "TBA";
        row[5] = offer.getScheduleTime() != null ? offer.getScheduleTime() : "TBA";
        
        // Get assigned faculty - FIXED: Use getPersonName() instead of getName()
        info5100.university.example.Persona.Faculty.FacultyProfile teacher = offer.getFacultyProfile();
        if (teacher != null && teacher.getPerson() != null) {
            info5100.university.example.Persona.Person univPerson = teacher.getPerson();
//            row[6] = univPerson.getPersonName(); // FIXED: Changed from getName()
            row[6] = univPerson.getFullName(); // CORRECT
        } else {
            row[6] = "Not Assigned";
        }
        
        model.addRow(row);
    }
    
    /**
     * Load all available faculty into 
     */
    private void loadFacultyList() {
        cmbFaculty.removeAllItems();
        cmbFaculty.addItem("-- Select Faculty --");
        
        FacultyDirectory fd = business.getFacultyDirectory();
        ArrayList<FacultyProfile> facultyList = fd.getFacultyList();
        
        if (facultyList == null || facultyList.isEmpty()) {
            System.out.println("⚠️ No faculty found");
            return;
        }
        
        for (FacultyProfile fp : facultyList) {
            if (fp.getPerson() != null) {
                String displayName = fp.getPerson().getFullName() + " (" + fp.getPerson().getPersonId() + ")";
                cmbFaculty.addItem(displayName);
            }
        }
        
        System.out.println("✅ Loaded " + facultyList.size() + " faculty members");
    }
    
    /**
     * Handle table selection change
     */
    private void tblCourseOfferingsSelectionChanged() {
        int selectedRow = tblCourseOfferings.getSelectedRow();
        
        if (selectedRow < 0) {
            selectedCourseOffer = null;
            txtSelectedCourse.setText("");
            txtCurrentFaculty.setText("");
            return;
        }
        
        String courseNumber = (String) tblCourseOfferings.getValueAt(selectedRow, 0);
        String courseName = (String) tblCourseOfferings.getValueAt(selectedRow, 1);
        String currentFaculty = (String) tblCourseOfferings.getValueAt(selectedRow, 6);
        
        txtSelectedCourse.setText(courseNumber + " - " + courseName);
        txtCurrentFaculty.setText(currentFaculty);
        
        // Find the CourseOffer object
        String semester = (String) cmbSemester.getSelectedItem();
        if (semester == null) semester = "Fall2025";
        
        Department dept = business.getDepartment();
        CourseSchedule schedule = dept.getCourseSchedule(semester);
        
        if (schedule != null) {
            selectedCourseOffer = schedule.getCourseOfferByNumber(courseNumber);
        }
    }
    
    /**
     * Assign selected faculty to selected course
     */
    private void btnAssignActionPerformed(java.awt.event.ActionEvent evt) {
        if (selectedCourseOffer == null) {
            JOptionPane.showMessageDialog(this, 
                "⚠️ Please select a course offering first!",
                "No Course Selected",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int selectedIndex = cmbFaculty.getSelectedIndex();
        if (selectedIndex <= 0) {
            JOptionPane.showMessageDialog(this, 
                "⚠️ Please select a faculty member to assign!",
                "No Faculty Selected",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Get selected faculty
        String selectedText = (String) cmbFaculty.getSelectedItem();
        String facultyId = selectedText.substring(selectedText.indexOf("(") + 1, selectedText.indexOf(")"));
        
        FacultyDirectory fd = business.getFacultyDirectory();
        FacultyProfile faculty = fd.findFaculty(facultyId);
        
        if (faculty == null) {
            JOptionPane.showMessageDialog(this, 
                "⚠️ Faculty not found!",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if course already has this faculty assigned
        info5100.university.example.Persona.Faculty.FacultyProfile currentTeacher = 
            selectedCourseOffer.getFacultyProfile();
        
        if (currentTeacher != null && currentTeacher.getPerson() != null) {
            info5100.university.example.Persona.Person currentPerson = currentTeacher.getPerson();
            if (currentPerson.getPersonId().equals(facultyId)) {
                JOptionPane.showMessageDialog(this, 
                    "⚠️ This faculty is already assigned to this course!",
                    "Already Assigned",
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Confirm reassignment - FIXED: Use getPersonName()
            int confirm = JOptionPane.showConfirmDialog(this,
                "This course is currently assigned to:\n" +
                currentPerson.getFullName() + "\n\n" + // FIXED
                "Do you want to reassign to:\n" +
                faculty.getPerson().getFullName() + "?",
                "Confirm Reassignment",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
            
            if (confirm != JOptionPane.YES_OPTION) return;
        }
        
        // Assign faculty to course
        selectedCourseOffer.AssignAsTeacher(faculty.getUniversityProfile());
        
        JOptionPane.showMessageDialog(this, 
            "✅ Faculty assigned successfully!\n\n" +
            "Course: " + selectedCourseOffer.getSubjectCourse().getName() + "\n" +
            "Faculty: " + faculty.getPerson().getFullName(),
            "Success",
            JOptionPane.INFORMATION_MESSAGE);
        
        // Refresh table
        loadCourseOfferings();
        
        System.out.println("✅ Assigned " + faculty.getPerson().getFullName() + 
                          " to " + selectedCourseOffer.getSubjectCourse().getCOurseNumber());
    }
    
    /**
     * Unassign faculty from selected course
     */
    private void btnUnassignActionPerformed(java.awt.event.ActionEvent evt) {
        if (selectedCourseOffer == null) {
            JOptionPane.showMessageDialog(this, 
                "⚠️ Please select a course offering first!",
                "No Course Selected",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        info5100.university.example.Persona.Faculty.FacultyProfile currentTeacher = 
            selectedCourseOffer.getFacultyProfile();
        
        if (currentTeacher == null) {
            JOptionPane.showMessageDialog(this, 
                "⚠️ No faculty is currently assigned to this course!",
                "No Faculty Assigned",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Check if course has enrolled students
        int enrolledCount = selectedCourseOffer.getEnrolledCount();
        if (enrolledCount > 0) {
            JOptionPane.showMessageDialog(this,
                "⚠️ Cannot unassign faculty from a course with enrolled students!\n\n" +
                "Enrolled Students: " + enrolledCount + "\n" +
                "Please drop all students first.",
                "Unassign Not Allowed",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // FIXED: Use getPersonName()
        String teacherName = "Unknown";
        if (currentTeacher.getPerson() != null) {
            teacherName = currentTeacher.getPerson().getFullName(); // FIXED
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to unassign:\n" +
            teacherName + "\n\n" +
            "From course:\n" +
            selectedCourseOffer.getSubjectCourse().getName(),
            "Confirm Unassignment",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm != JOptionPane.YES_OPTION) return;
        
        // Unassign faculty
        selectedCourseOffer.AssignAsTeacher(null);
        
        JOptionPane.showMessageDialog(this, 
            "✅ Faculty unassigned successfully!",
            "Success",
            JOptionPane.INFORMATION_MESSAGE);
        
        // Refresh table
        loadCourseOfferings();
        
        System.out.println("✅ Unassigned faculty from " + 
                          selectedCourseOffer.getSubjectCourse().getCOurseNumber());
    }
    
    /**
     * Navigate back to admin work area
     */
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
}