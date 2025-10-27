/*
 * EditUserAccountPanel.java
 * Admin can modify username and password for user accounts
 *
 * @author chethan
 */
package UserInterface.WorkAreas.AdminRole;

import Business.Business;
import Business.UserAccounts.UserAccount;
import Business.UserAccounts.UserAccountDirectory;
import Business.Profiles.Profile;

import javax.swing.*;

/**
 * Edit User Account Panel
 * Admin can modify username and password
 */
public class EditUserAccountPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    UserAccount account;

    private JLabel lblTitle, lblPersonName, lblRole, lblUsername, lblPassword, lblMessage;
    private JTextField txtPersonName, txtRole, txtUsername;
    private JPasswordField txtPassword;
    private JButton btnSave, btnCancel;

    public EditUserAccountPanel(Business b, UserAccount ua, JPanel clp) {
        business = b;
        account = ua;
        CardSequencePanel = clp;
        initComponents();
        loadData();
    }

    /**
     * Initialize UI components
     */
    private void initComponents() {
        lblTitle = new JLabel("Edit User Account");
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));

        lblPersonName = new JLabel("Person:");
        txtPersonName = new JTextField();
        txtPersonName.setEditable(false);
        txtPersonName.setBackground(java.awt.Color.LIGHT_GRAY);

        lblRole = new JLabel("Role:");
        txtRole = new JTextField();
        txtRole.setEditable(false);
        txtRole.setBackground(java.awt.Color.LIGHT_GRAY);

        lblUsername = new JLabel("Username: *");
        txtUsername = new JTextField();

        lblPassword = new JLabel("New Password: *");
        txtPassword = new JPasswordField();

        lblMessage = new JLabel();

        btnSave = new JButton("Save Changes");
        btnSave.setBackground(new java.awt.Color(102, 153, 255));
        btnSave.setForeground(java.awt.Color.WHITE);
        btnSave.addActionListener(evt -> btnSaveActionPerformed(evt));

        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(evt -> btnCancelActionPerformed(evt));

        setLayout(null);

        add(lblTitle);
        lblTitle.setBounds(30, 20, 400, 30);
        add(lblPersonName);
        lblPersonName.setBounds(30, 70, 150, 25);
        add(txtPersonName);
        txtPersonName.setBounds(200, 70, 250, 30);
        add(lblRole);
        lblRole.setBounds(30, 110, 150, 25);
        add(txtRole);
        txtRole.setBounds(200, 110, 250, 30);
        add(lblUsername);
        lblUsername.setBounds(30, 150, 150, 25);
        add(txtUsername);
        txtUsername.setBounds(200, 150, 250, 30);
        add(lblPassword);
        lblPassword.setBounds(30, 190, 150, 25);
        add(txtPassword);
        txtPassword.setBounds(200, 190, 250, 30);
        add(lblMessage);
        lblMessage.setBounds(30, 230, 450, 25);
        add(btnSave);
        btnSave.setBounds(300, 270, 150, 35);
        add(btnCancel);
        btnCancel.setBounds(30, 270, 100, 35);
    }

    /**
     * Load current account data
     */
    private void loadData() {
        Profile profile = account.getAssociatedPersonProfile();
        txtPersonName.setText(profile.getPerson().getFullName());
        txtRole.setText(account.getRole());
        txtUsername.setText(account.getUserLoginName());
    }

    /**
     * Save changes to username and password
     */
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {
        lblMessage.setText("");

        // Validation
        if (txtUsername.getText().trim().isEmpty()) {
            lblMessage.setText("⚠️ Username is required!");
            lblMessage.setForeground(java.awt.Color.RED);
            return;
        }

        String newPassword = new String(txtPassword.getPassword()).trim();
        if (newPassword.isEmpty()) {
            lblMessage.setText("⚠️ Password is required!");
            lblMessage.setForeground(java.awt.Color.RED);
            return;
        }

        // Check if username already exists (if changed)
        String newUsername = txtUsername.getText().trim();
        if (!newUsername.equals(account.getUserLoginName())) {
            UserAccountDirectory uad = business.getUserAccountDirectory();
            for (UserAccount ua : uad.getUserAccountList()) {
                if (ua.getUserLoginName().equalsIgnoreCase(newUsername) && ua != account) {
                    lblMessage.setText("⚠️ Username already exists! Choose a different username.");
                    lblMessage.setForeground(java.awt.Color.RED);
                    return;
                }
            }
        }

        // Update account (using setters)
        account.setUsername(newUsername);
        account.setPassword(newPassword);

        JOptionPane.showMessageDialog(this,
            "✅ User account updated successfully!\n\n" +
            "Username: " + newUsername);

        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }

    /**
     * Cancel editing and go back
     */
    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
}