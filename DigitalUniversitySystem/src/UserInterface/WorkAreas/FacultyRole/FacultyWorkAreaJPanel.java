/*
 * FacultyWorkAreaJPanel.java
 * Main dashboard for Faculty role
 */
package UserInterface.WorkAreas.FacultyRole;

import Business.Business;
import Business.Profiles.FacultyProfile;
import javax.swing.JPanel;

/**
 * Faculty Work Area - Main Dashboard
 * 
 * @author chethan
 */
public class FacultyWorkAreaJPanel extends javax.swing.JPanel {

    javax.swing.JPanel CardSequencePanel;
    Business business;
    FacultyProfile facultyProfile;
    
    public FacultyWorkAreaJPanel(Business b, FacultyProfile fpp, JPanel clp) {
        business = b;
        this.CardSequencePanel = clp;
        facultyProfile = fpp;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        btnManageCourse = new javax.swing.JButton();
        btnManageStudentProfiles = new javax.swing.JButton();
        btnMyProfile = new javax.swing.JButton();
        btnPerformanceReport = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setForeground(new java.awt.Color(51, 51, 51));

        btnManageCourse.setBackground(new java.awt.Color(102, 153, 255));
        btnManageCourse.setFont(getFont());
        btnManageCourse.setForeground(new java.awt.Color(255, 255, 255));
        btnManageCourse.setText("Manage Courses");
        btnManageCourse.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnManageCourse.setMaximumSize(new java.awt.Dimension(200, 40));
        btnManageCourse.setMinimumSize(new java.awt.Dimension(20, 23));
        btnManageCourse.setPreferredSize(new java.awt.Dimension(240, 30));
        btnManageCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManageCourseActionPerformed(evt);
            }
        });

        btnManageStudentProfiles.setBackground(new java.awt.Color(102, 153, 255));
        btnManageStudentProfiles.setFont(getFont());
        btnManageStudentProfiles.setForeground(new java.awt.Color(255, 255, 255));
        btnManageStudentProfiles.setText("Manage Students & Grading");
        btnManageStudentProfiles.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnManageStudentProfiles.setMaximumSize(new java.awt.Dimension(200, 40));
        btnManageStudentProfiles.setMinimumSize(new java.awt.Dimension(20, 20));
        btnManageStudentProfiles.setPreferredSize(new java.awt.Dimension(240, 25));
        btnManageStudentProfiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManageStudentProfilesActionPerformed(evt);
            }
        });

        btnMyProfile.setBackground(new java.awt.Color(102, 153, 255));
        btnMyProfile.setFont(getFont());
        btnMyProfile.setForeground(new java.awt.Color(255, 255, 255));
        btnMyProfile.setText("My Profile");
        btnMyProfile.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnMyProfile.setMaximumSize(new java.awt.Dimension(145, 40));
        btnMyProfile.setMinimumSize(new java.awt.Dimension(20, 20));
        btnMyProfile.setPreferredSize(new java.awt.Dimension(240, 25));
        btnMyProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMyProfileActionPerformed(evt);
            }
        });

        btnPerformanceReport.setBackground(new java.awt.Color(102, 153, 255));
        btnPerformanceReport.setFont(getFont());
        btnPerformanceReport.setForeground(new java.awt.Color(255, 255, 255));
        btnPerformanceReport.setText("Performance Reports");
        btnPerformanceReport.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPerformanceReport.setMaximumSize(new java.awt.Dimension(200, 40));
        btnPerformanceReport.setMinimumSize(new java.awt.Dimension(20, 20));
        btnPerformanceReport.setPreferredSize(new java.awt.Dimension(240, 25));
        btnPerformanceReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPerformanceReportActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24));
        jLabel1.setText("My Faculty Profile");  // FIXED TYPO

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnManageCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(75, 75, 75)
                            .addComponent(btnManageStudentProfiles, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnMyProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPerformanceReport, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(632, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addGap(76, 76, 76)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnManageCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnManageStudentProfiles, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMyProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPerformanceReport, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(221, Short.MAX_VALUE))
        );
    }

    private void btnManageCourseActionPerformed(java.awt.event.ActionEvent evt) {
        ViewMyCoursesPanel panel = new ViewMyCoursesPanel(business, facultyProfile, CardSequencePanel, "MANAGE_COURSES");
        CardSequencePanel.add("MyCourses", panel);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }

    private void btnManageStudentProfilesActionPerformed(java.awt.event.ActionEvent evt) {
        ViewMyCoursesPanel panel = new ViewMyCoursesPanel(business, facultyProfile, CardSequencePanel, "MANAGE_STUDENTS");
        CardSequencePanel.add("ManageStudents", panel);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }

    private void btnMyProfileActionPerformed(java.awt.event.ActionEvent evt) {
        FacultyProfilePanel panel = new FacultyProfilePanel(business, facultyProfile, CardSequencePanel);
        CardSequencePanel.add("FacultyProfile", panel);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }

    private void btnPerformanceReportActionPerformed(java.awt.event.ActionEvent evt) {
        PerformanceReportsPanel panel = new PerformanceReportsPanel(business, facultyProfile, CardSequencePanel);
        CardSequencePanel.add("PerformanceReports", panel);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }

    // Variables declaration
    private javax.swing.JButton btnManageCourse;
    private javax.swing.JButton btnManageStudentProfiles;
    private javax.swing.JButton btnMyProfile;
    private javax.swing.JButton btnPerformanceReport;
    private javax.swing.JLabel jLabel1;
}