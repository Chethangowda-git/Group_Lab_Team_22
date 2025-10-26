package UserInterface.WorkAreas.RegistrarRole;

import Business.Business;
import Business.Profiles.RegistrarProfile;
import Business.Profiles.StudentProfile;
import Business.Profiles.StudentDirectory;

import info5100.university.example.Department.Department;
import info5100.university.example.CourseSchedule.CourseSchedule;
import info5100.university.example.CourseSchedule.CourseOffer;
import info5100.university.example.CourseSchedule.CourseLoad;
import info5100.university.example.CourseSchedule.SeatAssignment;
import info5100.university.example.Persona.Transcript;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * Student Registration Panel (Admin-Side) Registrar can enroll/drop students in
 * courses
 */
public class StudentRegistrationPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    RegistrarProfile registrar;

    StudentProfile selectedStudent = null;

    // UI Components
    private JLabel lblTitle;
    private JLabel lblStudents;
    private JScrollPane scrollPaneStudents;
    private JTable tblStudents;
    private JLabel lblEnrollments;
    private JScrollPane scrollPaneEnrollments;
    private JTable tblEnrollments;
    private JButton btnEnrollStudent;
    private JButton btnDropStudent;
    private JButton btnRefresh;
    private JButton btnBack;

    public StudentRegistrationPanel(Business b, RegistrarProfile rp, JPanel clp) {
        business = b;
        registrar = rp;
        CardSequencePanel = clp;

        initComponents();
        loadStudents();
    }

    private void initComponents() {
        // Title
        lblTitle = new JLabel();
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));
        lblTitle.setText("Student Registration (Admin-Side)");

        // Students Label
        lblStudents = new JLabel();
        lblStudents.setFont(new java.awt.Font("Dialog", 1, 14));
        lblStudents.setText("All Students:");

        // Students Table
        tblStudents = new JTable();
        tblStudents.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Student ID", "Name", "Email"}
        ) {
            boolean[] canEdit = new boolean[]{false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        tblStudents.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                tblStudentsSelectionChanged();
            }
        });

        scrollPaneStudents = new JScrollPane();
        scrollPaneStudents.setViewportView(tblStudents);

        // Enrollments Label
        lblEnrollments = new JLabel();
        lblEnrollments.setFont(new java.awt.Font("Dialog", 1, 14));
        lblEnrollments.setText("Current Enrollments: (Select a student)");

        // Enrollments Table
        tblEnrollments = new JTable();
        tblEnrollments.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Course Number", "Course Name", "Semester"}
        ) {
            boolean[] canEdit = new boolean[]{false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        scrollPaneEnrollments = new JScrollPane();
        scrollPaneEnrollments.setViewportView(tblEnrollments);

        // Buttons
        btnEnrollStudent = new JButton();
        btnEnrollStudent.setBackground(new java.awt.Color(102, 153, 255));
        btnEnrollStudent.setForeground(new java.awt.Color(255, 255, 255));
        btnEnrollStudent.setText("Enroll in Course");
        btnEnrollStudent.addActionListener(evt -> btnEnrollStudentActionPerformed(evt));

        btnDropStudent = new JButton();
        btnDropStudent.setBackground(new java.awt.Color(255, 102, 102));
        btnDropStudent.setForeground(new java.awt.Color(255, 255, 255));
        btnDropStudent.setText("Drop from Course");
        btnDropStudent.addActionListener(evt -> btnDropStudentActionPerformed(evt));

        btnRefresh = new JButton();
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(evt -> btnRefreshActionPerformed(evt));

        btnBack = new JButton();
        btnBack.setText("<< Back");
        btnBack.addActionListener(evt -> btnBackActionPerformed(evt));

        // Layout
        setLayout(null);

        add(lblTitle);
        lblTitle.setBounds(30, 20, 500, 30);

        add(lblStudents);
        lblStudents.setBounds(30, 70, 200, 25);

        add(scrollPaneStudents);
        scrollPaneStudents.setBounds(30, 100, 700, 150);

        add(lblEnrollments);
        lblEnrollments.setBounds(30, 260, 400, 25);

        add(scrollPaneEnrollments);
        scrollPaneEnrollments.setBounds(30, 290, 700, 150);

        add(btnEnrollStudent);
        btnEnrollStudent.setBounds(400, 460, 150, 35);

        add(btnDropStudent);
        btnDropStudent.setBounds(570, 460, 160, 35);

        add(btnRefresh);
        btnRefresh.setBounds(200, 460, 100, 35);

        add(btnBack);
        btnBack.setBounds(30, 460, 100, 35);
    }

    private void loadStudents() {
        // Clear table
        DefaultTableModel model = (DefaultTableModel) tblStudents.getModel();
        model.setRowCount(0);

        // Get all students
        StudentDirectory sd = business.getStudentDirectory();
        ArrayList<StudentProfile> students = sd.getStudentList();

        System.out.println("✅ Loading " + students.size() + " students...");

        // Populate table
        for (StudentProfile sp : students) {
            Object[] row = new Object[3];
            row[0] = sp.getPerson().getPersonId();        // Student ID
            row[1] = sp.getPerson().getFullName();        // Name
            row[2] = sp.getPerson().getEmail();           // Email

            model.addRow(row);
        }

        System.out.println("✅ Loaded " + students.size() + " students into table");
    }

    private void tblStudentsSelectionChanged() {
        // When a student is selected, load their enrollments
        int selectedRow = tblStudents.getSelectedRow();

        if (selectedRow < 0) {
            selectedStudent = null;
            lblEnrollments.setText("Current Enrollments: (Select a student)");
            return;
        }

        // Get student from table
        String studentId = (String) tblStudents.getValueAt(selectedRow, 0);
        StudentDirectory sd = business.getStudentDirectory();
        selectedStudent = sd.findStudent(studentId);

        if (selectedStudent != null) {
            lblEnrollments.setText("Current Enrollments: " + selectedStudent.getPerson().getFullName());
            loadStudentEnrollments();
        }
    }

    private void loadStudentEnrollments() {
        // Clear table
        DefaultTableModel model = (DefaultTableModel) tblEnrollments.getModel();
        model.setRowCount(0);

        if (selectedStudent == null) {
            return;
        }

        System.out.println("Loading enrollments for: " + selectedStudent.getPerson().getFullName());

        // Get University Model StudentProfile
        info5100.university.example.Persona.StudentProfile universityStudent
                = selectedStudent.getUniversityProfile();

        // Get Transcript
        info5100.university.example.Persona.Transcript transcript
                = universityStudent.getTranscript();

        // Get all seat assignments (all courses across all semesters)
        ArrayList<SeatAssignment> enrollments = transcript.getCourseList();

        if (enrollments == null || enrollments.isEmpty()) {
            Object[] row = new Object[3];
            row[0] = "No enrollments";
            row[1] = "Student is not enrolled in any courses";
            row[2] = "";
            model.addRow(row);
            System.out.println("⚠️ No enrollments found");
            return;
        }

        // Populate table with enrollments
        for (SeatAssignment sa : enrollments) {
            Object[] row = new Object[3];

            // Get course info
            info5100.university.example.CourseCatalog.Course course = sa.getAssociatedCourse();
            row[0] = course.getCOurseNumber();   // Course Number
            row[1] = course.getName();           // Course Name

            // Get semester info
            CourseLoad courseLoad = sa.getCourseLoad();
            if (courseLoad != null) {
                row[2] = courseLoad.getSemester();     // Use getter
            } else {
                row[2] = "Unknown";
            }

            model.addRow(row);
        }

        System.out.println("✅ Loaded " + enrollments.size() + " enrollments");
    }

   private void btnEnrollStudentActionPerformed(java.awt.event.ActionEvent evt) {
    if (selectedStudent == null) {
        JOptionPane.showMessageDialog(this, "Please select a student first!");
        return;
    }
    
    // Open Enroll Student Panel
    EnrollStudentPanel panel = new EnrollStudentPanel(business, selectedStudent, CardSequencePanel);
    CardSequencePanel.add("EnrollStudent", panel);
    ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
}

   private void btnDropStudentActionPerformed(java.awt.event.ActionEvent evt) {
    if (selectedStudent == null) {
        JOptionPane.showMessageDialog(this, "Please select a student first!");
        return;
    }
    
    // Check if student has any enrollments
    info5100.university.example.Persona.StudentProfile universityStudent = 
        selectedStudent.getUniversityProfile();
    
    ArrayList<SeatAssignment> enrollments = 
        universityStudent.getTranscript().getCourseList();
    
    if (enrollments == null || enrollments.isEmpty()) {
        JOptionPane.showMessageDialog(this, 
            "This student is not enrolled in any courses!");
        return;
    }
    
    // Open Drop Student Panel
    DropStudentPanel panel = new DropStudentPanel(business, selectedStudent, CardSequencePanel);
    CardSequencePanel.add("DropStudent", panel);
    ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
}

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {
        loadStudents();
        if (selectedStudent != null) {
            loadStudentEnrollments();
        }
    }

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
}
