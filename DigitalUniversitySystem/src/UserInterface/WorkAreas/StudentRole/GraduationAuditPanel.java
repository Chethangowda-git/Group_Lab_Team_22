package UserInterface.WorkAreas.StudentRole;

import Business.Business;
import Business.Profiles.StudentProfile;

import info5100.university.example.Persona.Transcript;
import info5100.university.example.CourseSchedule.SeatAssignment;
import info5100.university.example.CourseCatalog.Course;
import info5100.university.example.Department.Department;
import info5100.university.example.Degree.Degree;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Graduation Audit Panel
 * Track 32 credits, INFO 5100 requirement, graduation eligibility
 */
public class GraduationAuditPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    StudentProfile student;
    
    private JLabel lblTitle;
    private JLabel lblStudentName;
    private JLabel lblProgram;
    private JLabel lblTotalCredits;
    private JLabel lblCoreStatus;
    private JLabel lblElectiveStatus;
    private JLabel lblGraduationStatus;
    private JTextArea txtDetails;
    private JScrollPane scrollDetails;
    private JButton btnBack;
    
    public GraduationAuditPanel(Business b, StudentProfile sp, JPanel clp) {
        business = b;
        student = sp;
        CardSequencePanel = clp;
        
        initComponents();
        performAudit();
    }
    
    private void initComponents() {
        lblTitle = new JLabel();
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));
        lblTitle.setText("Graduation Audit");
        
        lblStudentName = new JLabel();
        lblStudentName.setFont(new java.awt.Font("Dialog", 1, 16));
        lblStudentName.setText("Student: " + student.getPerson().getFullName());
        
        lblProgram = new JLabel();
        lblProgram.setText("Program: Master of Science in Information Systems (MSIS)");
        
        lblTotalCredits = new JLabel();
        lblTotalCredits.setFont(new java.awt.Font("Dialog", 1, 14));
        lblTotalCredits.setText("Total Credits: 0 / 32 required");
        
        lblCoreStatus = new JLabel();
        lblCoreStatus.setText("Core Requirement (INFO 5100): Not Completed");
        
        lblElectiveStatus = new JLabel();
        lblElectiveStatus.setText("Elective Credits: 0 / 28 required");
        
        lblGraduationStatus = new JLabel();
        lblGraduationStatus.setFont(new java.awt.Font("Dialog", 1, 18));
        lblGraduationStatus.setText("Graduation Status: Not Eligible");
        
        txtDetails = new JTextArea(10, 50);
        txtDetails.setEditable(false);
        txtDetails.setLineWrap(true);
        txtDetails.setWrapStyleWord(true);
        scrollDetails = new JScrollPane(txtDetails);
        
        btnBack = new JButton();
        btnBack.setText("<< Back");
        btnBack.addActionListener(evt -> btnBackActionPerformed(evt));
        
        setLayout(null);
        
        add(lblTitle);
        lblTitle.setBounds(30, 20, 400, 30);
        
        add(lblStudentName);
        lblStudentName.setBounds(30, 60, 500, 25);
        
        add(lblProgram);
        lblProgram.setBounds(30, 90, 600, 25);
        
        add(lblTotalCredits);
        lblTotalCredits.setBounds(30, 130, 400, 25);
        
        add(lblCoreStatus);
        lblCoreStatus.setBounds(30, 160, 500, 25);
        
        add(lblElectiveStatus);
        lblElectiveStatus.setBounds(30, 190, 500, 25);
        
        add(lblGraduationStatus);
        lblGraduationStatus.setBounds(30, 230, 600, 30);
        
        add(scrollDetails);
        scrollDetails.setBounds(30, 270, 700, 200);
        
        add(btnBack);
        btnBack.setBounds(30, 490, 100, 35);
    }
    
    private void loadSemesters() {
        // Not needed for audit, but can be added if you want semester-by-semester view
    }
    
    private void performAudit() {
        info5100.university.example.Persona.StudentProfile univStudent = student.getUniversityProfile();
        Transcript transcript = univStudent.getTranscript();
        
        // Get total credits
        int totalCredits = transcript.getTotalCreditHours();
        lblTotalCredits.setText(String.format("Total Credits: %d / 32 required", totalCredits));
        
        // Check for INFO 5100 (core course)
        boolean hasInfo5100 = false;
        ArrayList<SeatAssignment> allCourses = transcript.getCourseList();
        
        for (SeatAssignment sa : allCourses) {
            if ("INFO5100".equals(sa.getAssociatedCourse().getCOurseNumber())) {
                hasInfo5100 = true;
                break;
            }
        }
        
        if (hasInfo5100) {
            lblCoreStatus.setText("✅ Core Requirement (INFO 5100): Completed");
            lblCoreStatus.setForeground(new java.awt.Color(0, 128, 0));
        } else {
            lblCoreStatus.setText("❌ Core Requirement (INFO 5100): Not Completed");
            lblCoreStatus.setForeground(java.awt.Color.RED);
        }
        
        // Calculate elective credits (total - 4 for INFO 5100 if taken)
        int electiveCredits = hasInfo5100 ? totalCredits - 4 : totalCredits;
        lblElectiveStatus.setText(String.format("Elective Credits: %d / 28 required", electiveCredits));
        
        // Check graduation eligibility
        boolean eligible = (totalCredits >= 32) && hasInfo5100;
        
        if (eligible) {
            lblGraduationStatus.setText("✅ ELIGIBLE TO GRADUATE!");
            lblGraduationStatus.setForeground(new java.awt.Color(0, 128, 0));
        } else {
            int creditsNeeded = 32 - totalCredits;
            lblGraduationStatus.setText("❌ NOT ELIGIBLE - " + creditsNeeded + " more credits needed");
            lblGraduationStatus.setForeground(java.awt.Color.RED);
        }
        
        // Build detailed report
        StringBuilder details = new StringBuilder();
        details.append("GRADUATION REQUIREMENTS FOR MSIS PROGRAM\n");
        details.append("=" .repeat(60)).append("\n\n");
        
        details.append("Total Credits Required: 32\n");
        details.append("Your Total Credits: ").append(totalCredits).append("\n");
        details.append("Credits Remaining: ").append(Math.max(0, 32 - totalCredits)).append("\n\n");
        
        details.append("Core Course Requirement:\n");
        details.append("- INFO 5100 (4 credits): ").append(hasInfo5100 ? "✅ COMPLETED" : "❌ NOT COMPLETED").append("\n\n");
        
        details.append("Elective Requirements:\n");
        details.append("- Elective Credits: ").append(electiveCredits).append(" / 28\n");
        details.append("- Electives Remaining: ").append(Math.max(0, 28 - electiveCredits)).append("\n\n");
        
        details.append("Overall Status:\n");
        if (eligible) {
            details.append("✅ You have met all graduation requirements!\n");
            details.append("You may apply for graduation.");
        } else {
            details.append("❌ Requirements not yet met.\n");
            if (!hasInfo5100) {
                details.append("- Please complete INFO 5100 (core requirement)\n");
            }
            if (totalCredits < 32) {
                details.append("- Please complete ").append(32 - totalCredits).append(" more credits\n");
            }
        }
        
        txtDetails.setText(details.toString());
        
        System.out.println("✅ Graduation audit: " + (eligible ? "ELIGIBLE" : "NOT ELIGIBLE"));
    }
    
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
}