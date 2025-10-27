/*
 * StudentWorkAreaJPanel.java
 * Main dashboard for Student role
 */
package UserInterface.WorkAreas.StudentRole;

import Business.Business;
import Business.Profiles.StudentProfile;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

/**
 * Student Work Area - Main Dashboard
 */
public class StudentWorkAreaJPanel extends javax.swing.JPanel {

    javax.swing.JPanel CardSequencePanel;
    Business business;
    StudentProfile student;

    public StudentWorkAreaJPanel(Business b, StudentProfile spp, JPanel clp) {
        business = b;
        this.CardSequencePanel = clp;
        student = spp;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        btnRegistration = new javax.swing.JButton();
        btnCourseWork = new javax.swing.JButton();
        btnTranscript = new javax.swing.JButton();
        btnGraduationAudit = new javax.swing.JButton();
        btnPayTuition = new javax.swing.JButton();
        btnManageProfile = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setForeground(new java.awt.Color(51, 51, 51));

        // Title
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24));
        jLabel1.setText("My Student Portal - " + student.getPerson().getFirstName());

        // Registration Button
        btnRegistration.setBackground(new java.awt.Color(102, 153, 255));
        btnRegistration.setFont(getFont());
        btnRegistration.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistration.setText("Course Registration");
        btnRegistration.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRegistration.setMaximumSize(new java.awt.Dimension(200, 40));
        btnRegistration.setMinimumSize(new java.awt.Dimension(20, 20));
        btnRegistration.setPreferredSize(new java.awt.Dimension(240, 25));
        btnRegistration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrationActionPerformed(evt);
            }
        });

        // Course Work Button
        btnCourseWork.setBackground(new java.awt.Color(102, 153, 255));
        btnCourseWork.setFont(getFont());
        btnCourseWork.setForeground(new java.awt.Color(255, 255, 255));
        btnCourseWork.setText("Course Work & Progress");
        btnCourseWork.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCourseWork.setMaximumSize(new java.awt.Dimension(200, 40));
        btnCourseWork.setMinimumSize(new java.awt.Dimension(20, 23));
        btnCourseWork.setPreferredSize(new java.awt.Dimension(240, 30));
        btnCourseWork.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCourseWorkActionPerformed(evt);
            }
        });

        // Transcript Button
        btnTranscript.setBackground(new java.awt.Color(102, 153, 255));
        btnTranscript.setFont(getFont());
        btnTranscript.setForeground(new java.awt.Color(255, 255, 255));
        btnTranscript.setText("View Transcript");
        btnTranscript.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnTranscript.setMaximumSize(new java.awt.Dimension(200, 40));
        btnTranscript.setMinimumSize(new java.awt.Dimension(20, 20));
        btnTranscript.setPreferredSize(new java.awt.Dimension(240, 25));
        btnTranscript.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTranscriptActionPerformed(evt);
            }
        });

        // Graduation Audit Button
        btnGraduationAudit.setBackground(new java.awt.Color(102, 153, 255));
        btnGraduationAudit.setFont(getFont());
        btnGraduationAudit.setForeground(new java.awt.Color(255, 255, 255));
        btnGraduationAudit.setText("Graduation Audit");
        btnGraduationAudit.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnGraduationAudit.setMaximumSize(new java.awt.Dimension(200, 40));
        btnGraduationAudit.setMinimumSize(new java.awt.Dimension(20, 20));
        btnGraduationAudit.setPreferredSize(new java.awt.Dimension(240, 25));
        btnGraduationAudit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGraduationAuditActionPerformed(evt);
            }
        });

        // Pay Tuition Button
        btnPayTuition.setBackground(new java.awt.Color(102, 153, 255));
        btnPayTuition.setFont(getFont());
        btnPayTuition.setForeground(new java.awt.Color(255, 255, 255));
        btnPayTuition.setText("Pay Tuition");
        btnPayTuition.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPayTuition.setMaximumSize(new java.awt.Dimension(200, 40));
        btnPayTuition.setMinimumSize(new java.awt.Dimension(20, 23));
        btnPayTuition.setPreferredSize(new java.awt.Dimension(240, 30));
        btnPayTuition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPayTuitionActionPerformed(evt);
            }
        });

        // Profile Button
        btnManageProfile.setBackground(new java.awt.Color(102, 153, 255));
        btnManageProfile.setFont(getFont());
        btnManageProfile.setForeground(new java.awt.Color(255, 255, 255));
        btnManageProfile.setText("My Profile");
        btnManageProfile.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnManageProfile.setMaximumSize(new java.awt.Dimension(200, 40));
        btnManageProfile.setMinimumSize(new java.awt.Dimension(20, 20));
        btnManageProfile.setPreferredSize(new java.awt.Dimension(240, 25));
        btnManageProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManageProfileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnRegistration, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                            .addComponent(btnGraduationAudit, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                            .addComponent(btnManageProfile, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE))
                        .addGap(75, 75, 75)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCourseWork, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTranscript, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPayTuition, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(548, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel1)
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistration, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCourseWork, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGraduationAudit, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTranscript, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnManageProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPayTuition, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(150, Short.MAX_VALUE))
        );
    }

    private void btnRegistrationActionPerformed(java.awt.event.ActionEvent evt) {
        CourseRegistrationPanel panel = new CourseRegistrationPanel(business, student, CardSequencePanel);
        CardSequencePanel.add("CourseRegistration", panel);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }

    private void btnCourseWorkActionPerformed(java.awt.event.ActionEvent evt) {
        CourseWorkPanel panel = new CourseWorkPanel(business, student, CardSequencePanel);
        CardSequencePanel.add("CourseWork", panel);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }

    private void btnTranscriptActionPerformed(java.awt.event.ActionEvent evt) {
        // TUITION CHECK BEFORE VIEWING TRANSCRIPT
        if (!student.isTuitionPaid()) {
            double balance = student.getTuitionBalance();
            
            int response = JOptionPane.showConfirmDialog(this,
                "TUITION PAYMENT REQUIRED\n\n" +
                "You cannot view your transcript until tuition is paid.\n\n" +
                "Current Balance: $" + String.format("%,d", (int)balance) + "\n\n" +
                "Would you like to pay now?",
                "Payment Required",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (response == JOptionPane.YES_OPTION) {
                // Navigate to Pay Tuition instead
                btnPayTuitionActionPerformed(null);
            }
            return;
        }
        
        // Tuition is paid, show transcript
        TranscriptPanel panel = new TranscriptPanel(business, student, CardSequencePanel);
        CardSequencePanel.add("Transcript", panel);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }

    private void btnGraduationAuditActionPerformed(java.awt.event.ActionEvent evt) {
        GraduationAuditPanel panel = new GraduationAuditPanel(business, student, CardSequencePanel);
        CardSequencePanel.add("GraduationAudit", panel);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }

    private void btnPayTuitionActionPerformed(java.awt.event.ActionEvent evt) {
        PayTuitionPanel panel = new PayTuitionPanel(business, student, CardSequencePanel);
        CardSequencePanel.add("PayTuition", panel);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }

    private void btnManageProfileActionPerformed(java.awt.event.ActionEvent evt) {
        StudentProfilePanel panel = new StudentProfilePanel(business, student, CardSequencePanel);
        CardSequencePanel.add("StudentProfile", panel);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }

    // Variables declaration
    private javax.swing.JButton btnRegistration;
    private javax.swing.JButton btnCourseWork;
    private javax.swing.JButton btnTranscript;
    private javax.swing.JButton btnGraduationAudit;
    private javax.swing.JButton btnPayTuition;
    private javax.swing.JButton btnManageProfile;
    private javax.swing.JLabel jLabel1;
}