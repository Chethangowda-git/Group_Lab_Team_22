package UserInterface.WorkAreas.RegistrarRole;

import Business.Business;
import Business.Profiles.RegistrarProfile;
import javax.swing.JPanel;

/**
 * Registrar Work Area - Main dashboard for Registrar role
 */
public class RegistrarWorkAreaJPanel extends javax.swing.JPanel {

    javax.swing.JPanel CardSequencePanel;
    Business business;
    RegistrarProfile registrar;

    public RegistrarWorkAreaJPanel(Business b, RegistrarProfile rp, JPanel clp) {
        business = b;
        this.CardSequencePanel = clp;
        registrar = rp;
        initComponents();
    }

    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnCourseOfferings = new javax.swing.JButton();
        btnStudentRegistration = new javax.swing.JButton();
        btnFinancialReports = new javax.swing.JButton();
        btnInstitutionalReports = new javax.swing.JButton();
        btnMyProfile = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24));
        jLabel1.setText("Registrar Portal");

        btnCourseOfferings.setBackground(new java.awt.Color(102, 153, 255));
        btnCourseOfferings.setFont(new java.awt.Font("Dialog", 0, 14));
        btnCourseOfferings.setForeground(new java.awt.Color(255, 255, 255));
        btnCourseOfferings.setText("Course Offerings Management");
        btnCourseOfferings.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCourseOfferings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCourseOfferingsActionPerformed(evt);
            }
        });

        btnStudentRegistration.setBackground(new java.awt.Color(102, 153, 255));
        btnStudentRegistration.setFont(new java.awt.Font("Dialog", 0, 14));
        btnStudentRegistration.setForeground(new java.awt.Color(255, 255, 255));
        btnStudentRegistration.setText("Student Registration (Admin-Side)");
        btnStudentRegistration.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnStudentRegistration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStudentRegistrationActionPerformed(evt);
            }
        });

        btnFinancialReports.setBackground(new java.awt.Color(102, 153, 255));
        btnFinancialReports.setFont(new java.awt.Font("Dialog", 0, 14));
        btnFinancialReports.setForeground(new java.awt.Color(255, 255, 255));
        btnFinancialReports.setText("Financial Reconciliation");
        btnFinancialReports.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnFinancialReports.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinancialReportsActionPerformed(evt);
            }
        });

        btnInstitutionalReports.setBackground(new java.awt.Color(102, 153, 255));
        btnInstitutionalReports.setFont(new java.awt.Font("Dialog", 0, 14));
        btnInstitutionalReports.setForeground(new java.awt.Color(255, 255, 255));
        btnInstitutionalReports.setText("Institutional Reports");
        btnInstitutionalReports.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnInstitutionalReports.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInstitutionalReportsActionPerformed(evt);
            }
        });

        btnMyProfile.setBackground(new java.awt.Color(102, 153, 255));
        btnMyProfile.setFont(new java.awt.Font("Dialog", 0, 14));
        btnMyProfile.setForeground(new java.awt.Color(255, 255, 255));
        btnMyProfile.setText("My Profile");
        btnMyProfile.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnMyProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMyProfileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnMyProfile, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCourseOfferings, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                            .addComponent(btnStudentRegistration, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(75, 75, 75)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnFinancialReports, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnInstitutionalReports, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(344, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabel1)
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCourseOfferings, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFinancialReports, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnStudentRegistration, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInstitutionalReports, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(btnMyProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(141, Short.MAX_VALUE))
        );
    }

    private void btnCourseOfferingsActionPerformed(java.awt.event.ActionEvent evt) {
        // Open Course Offering Management Panel
        CourseOfferingManagementPanel panel = new CourseOfferingManagementPanel(business, registrar, CardSequencePanel);
        CardSequencePanel.add("CourseOfferings", panel);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }

  private void btnStudentRegistrationActionPerformed(java.awt.event.ActionEvent evt) {
    // Open Student Registration Panel
    StudentRegistrationPanel panel = new StudentRegistrationPanel(business, registrar, CardSequencePanel);
    CardSequencePanel.add("StudentRegistration", panel);
    ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
}

    private void btnFinancialReportsActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO: Financial Reconciliation panel
        javax.swing.JOptionPane.showMessageDialog(this, "Financial Reconciliation - Coming soon!");
    }

    private void btnInstitutionalReportsActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO: Institutional Reports panel
        javax.swing.JOptionPane.showMessageDialog(this, "Institutional Reports - Coming soon!");
    }

    private void btnMyProfileActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO: My Profile panel
        javax.swing.JOptionPane.showMessageDialog(this, "My Profile - Coming soon!");
    }

    // Variables declaration
    private javax.swing.JButton btnCourseOfferings;
    private javax.swing.JButton btnFinancialReports;
    private javax.swing.JButton btnInstitutionalReports;
    private javax.swing.JButton btnMyProfile;
    private javax.swing.JButton btnStudentRegistration;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration
}