package UserInterface.WorkAreas.RegistrarRole;

import Business.Business;
import Business.Profiles.StudentProfile;

import info5100.university.example.CourseSchedule.SeatAssignment;
import info5100.university.example.CourseSchedule.CourseLoad;
import info5100.university.example.Persona.Transcript;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * Panel to drop a student from a course
 * Registrar can drop students and auto-refund tuition
 * 
 * @author chethan
 */
public class DropStudentPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    StudentProfile student;
    
    SeatAssignment selectedEnrollment = null;
    
    private JLabel lblTitle;
    private JLabel lblStudent;
    private JLabel lblStudentName;
    private JLabel lblInstructions;
    private JScrollPane scrollPane;
    private JTable tblEnrollments;
    private JLabel lblMessage;
    private JButton btnDrop;
    private JButton btnCancel;
    
    public DropStudentPanel(Business b, StudentProfile sp, JPanel clp) {
        business = b;
        student = sp;
        CardSequencePanel = clp;
        
        initComponents();
        loadEnrollments();
    }
    
    private void initComponents() {
        lblTitle = new JLabel();
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));
        lblTitle.setText("Drop Student from Course");
        
        lblStudent = new JLabel();
        lblStudent.setFont(new java.awt.Font("Dialog", 1, 14));
        lblStudent.setText("Student:");
        
        lblStudentName = new JLabel();
        lblStudentName.setFont(new java.awt.Font("Dialog", 0, 14));
        lblStudentName.setText(student.getPerson().getFullName());
        
        lblInstructions = new JLabel();
        lblInstructions.setText("Select a course to drop:");
        
        tblEnrollments = new JTable();
        tblEnrollments.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"Course Number", "Course Name", "Semester", "Tuition"}
        ) {
            boolean[] canEdit = new boolean[] {false, false, false, false};
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        
        tblEnrollments.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                tblEnrollmentsSelectionChanged();
            }
        });
        
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(tblEnrollments);
        
        lblMessage = new JLabel();
        lblMessage.setForeground(java.awt.Color.RED);
        
        btnDrop = new JButton();
        btnDrop.setBackground(new java.awt.Color(255, 102, 102));
        btnDrop.setForeground(new java.awt.Color(255, 255, 255));
        btnDrop.setText("Drop from Course");
        btnDrop.addActionListener(evt -> btnDropActionPerformed(evt));
        
        btnCancel = new JButton();
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(evt -> btnCancelActionPerformed(evt));
        
        setLayout(null);
        
        add(lblTitle);
        lblTitle.setBounds(30, 20, 400, 30);
        
        add(lblStudent);
        lblStudent.setBounds(30, 70, 100, 25);
        
        add(lblStudentName);
        lblStudentName.setBounds(140, 70, 300, 25);
        
        add(lblInstructions);
        lblInstructions.setBounds(30, 110, 300, 25);
        
        add(scrollPane);
        scrollPane.setBounds(30, 140, 650, 200);
        
        add(lblMessage);
        lblMessage.setBounds(30, 350, 500, 25);
        
        add(btnDrop);
        btnDrop.setBounds(400, 390, 180, 35);
        
        add(btnCancel);
        btnCancel.setBounds(30, 390, 100, 35);
    }
    
    private void loadEnrollments() {
        DefaultTableModel model = (DefaultTableModel) tblEnrollments.getModel();
        model.setRowCount(0);
        
        info5100.university.example.Persona.StudentProfile universityStudent = 
            student.getUniversityProfile();
        
        Transcript transcript = universityStudent.getTranscript();
        ArrayList<SeatAssignment> enrollments = transcript.getCourseList();
        
        if (enrollments == null || enrollments.isEmpty()) {
            Object[] row = new Object[4];
            row[0] = "No enrollments";
            row[1] = "Student is not enrolled in any courses";
            row[2] = "";
            row[3] = "";
            model.addRow(row);
            return;
        }
        
        for (SeatAssignment sa : enrollments) {
            Object[] row = new Object[4];
            
            info5100.university.example.CourseCatalog.Course course = sa.getAssociatedCourse();
            row[0] = course.getCOurseNumber();
            row[1] = course.getName();
            
            CourseLoad courseLoad = sa.getCourseLoad();
            row[2] = courseLoad != null ? courseLoad.getSemester() : "Unknown";
            
            // Show tuition for this course
            row[3] = "$" + String.format("%,d", course.getCoursePrice());
            
            model.addRow(row);
        }
        
        System.out.println("Loaded " + enrollments.size() + " enrollments");
    }
    
    private void tblEnrollmentsSelectionChanged() {
        int selectedRow = tblEnrollments.getSelectedRow();
        
        if (selectedRow < 0) {
            selectedEnrollment = null;
            return;
        }
        
        // Get the seat assignment from transcript
        info5100.university.example.Persona.StudentProfile universityStudent = 
            student.getUniversityProfile();
        
        Transcript transcript = universityStudent.getTranscript();
        ArrayList<SeatAssignment> enrollments = transcript.getCourseList();
        
        if (selectedRow < enrollments.size()) {
            selectedEnrollment = enrollments.get(selectedRow);
        }
    }
    
    /**
     * Drop student from selected course
     * UPDATED: Now includes automatic tuition refund
     */
    private void btnDropActionPerformed(java.awt.event.ActionEvent evt) {
        if (selectedEnrollment == null) {
            JOptionPane.showMessageDialog(this, "Please select a course to drop!");
            return;
        }
        
        // Get course info for confirmation
        String courseNumber = selectedEnrollment.getAssociatedCourse().getCOurseNumber();
        String courseName = selectedEnrollment.getAssociatedCourse().getName();
        double coursePrice = selectedEnrollment.getAssociatedCourse().getCoursePrice();
        
        // Confirm with user
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to drop:\n\n" +
            "Student: " + student.getPerson().getFullName() + "\n" +
            "From: " + courseNumber + " - " + courseName + "\n\n" +
            "Tuition will be refunded: $" + String.format("%,d", (int)coursePrice),
            "Confirm Drop",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        
        // Drop the student by removing from course load
        CourseLoad courseLoad = selectedEnrollment.getCourseLoad();
        
        if (courseLoad == null) {
            JOptionPane.showMessageDialog(this, "Error: Could not find course load!");
            return;
        }
        
        // Remove from course load's seat assignments
        courseLoad.getSeatAssignments().remove(selectedEnrollment);
        
        // Mark seat as unoccupied
        selectedEnrollment.getSeat().vacate();
        
        // REFUND TUITION (NEW - CRITICAL FOR DATA COMPATIBILITY)
        student.refundTuition(coursePrice);
        
        System.out.println("Dropped " + student.getPerson().getFullName() + 
                          " from " + courseNumber);
        System.out.println("Refunded tuition: $" + coursePrice + 
                          " | New balance: $" + student.getTuitionBalance());
        
        JOptionPane.showMessageDialog(this,
            "Student dropped successfully!\n\n" +
            "Student: " + student.getPerson().getFullName() + "\n" +
            "Course: " + courseNumber + " - " + courseName + "\n\n" +
            "Tuition Refunded: $" + String.format("%,d", (int)coursePrice) + "\n" +
            "New Balance: $" + String.format("%,d", (int)student.getTuitionBalance()),
            "Drop Complete",
            JOptionPane.INFORMATION_MESSAGE);
        
        // Go back
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
    
    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
}