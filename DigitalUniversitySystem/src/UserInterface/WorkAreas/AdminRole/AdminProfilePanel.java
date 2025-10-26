package UserInterface.WorkAreas.AdminRole;

import Business.Business;
import Business.Profiles.EmployeeProfile;
import Business.Person.Person;

import javax.swing.*;

/**
 * Admin Profile Panel
 * Admin can view and edit their own profile
 */
public class AdminProfilePanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    EmployeeProfile admin;
    
    private JLabel lblTitle;
    private JLabel lblPersonId;
    private JTextField txtPersonId;
    private JLabel lblFirstName;
    private JTextField txtFirstName;
    private JLabel lblLastName;
    private JTextField txtLastName;
    private JLabel lblEmail;
    private JTextField txtEmail;
    private JLabel lblPhone;
    private JTextField txtPhone;
    private JLabel lblMessage;
    private JButton btnSave;
    private JButton btnCancel;
    
    public AdminProfilePanel(Business b, EmployeeProfile ep, JPanel clp) {
        business = b;
        admin = ep;
        CardSequencePanel = clp;
        
        initComponents();
        loadData();
    }
    
    private void initComponents() {
        lblTitle = new JLabel();
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));
        lblTitle.setText("My Admin Profile");
        
        lblPersonId = new JLabel("Person ID:");
        txtPersonId = new JTextField();
        txtPersonId.setEditable(false);
        txtPersonId.setBackground(java.awt.Color.LIGHT_GRAY);
        
        lblFirstName = new JLabel("First Name:");
        txtFirstName = new JTextField();
        
        lblLastName = new JLabel("Last Name:");
        txtLastName = new JTextField();
        
        lblEmail = new JLabel("Email: *");
        txtEmail = new JTextField();
        
        lblPhone = new JLabel("Phone:");
        txtPhone = new JTextField();
        
        lblMessage = new JLabel();
        lblMessage.setForeground(java.awt.Color.BLUE);
        
        btnSave = new JButton();
        btnSave.setBackground(new java.awt.Color(102, 153, 255));
        btnSave.setForeground(java.awt.Color.WHITE);
        btnSave.setText("Save Changes");
        btnSave.addActionListener(evt -> btnSaveActionPerformed(evt));
        
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(evt -> btnCancelActionPerformed(evt));
        
        setLayout(null);
        
        add(lblTitle);
        lblTitle.setBounds(30, 20, 400, 30);
        
        add(lblPersonId);
        lblPersonId.setBounds(30, 70, 150, 25);
        add(txtPersonId);
        txtPersonId.setBounds(200, 70, 250, 30);
        
        add(lblFirstName);
        lblFirstName.setBounds(30, 110, 150, 25);
        add(txtFirstName);
        txtFirstName.setBounds(200, 110, 250, 30);
        
        add(lblLastName);
        lblLastName.setBounds(30, 150, 150, 25);
        add(txtLastName);
        txtLastName.setBounds(200, 150, 250, 30);
        
        add(lblEmail);
        lblEmail.setBounds(30, 190, 150, 25);
        add(txtEmail);
        txtEmail.setBounds(200, 190, 250, 30);
        
        add(lblPhone);
        lblPhone.setBounds(30, 230, 150, 25);
        add(txtPhone);
        txtPhone.setBounds(200, 230, 250, 30);
        
        add(lblMessage);
        lblMessage.setBounds(30, 270, 450, 25);
        
        add(btnSave);
        btnSave.setBounds(300, 310, 150, 35);
        
        add(btnCancel);
        btnCancel.setBounds(30, 310, 100, 35);
    }
    
    private void loadData() {
        Person person = admin.getPerson();
        txtPersonId.setText(person.getPersonId());
        txtFirstName.setText(person.getFirstName() != null ? person.getFirstName() : "");
        txtLastName.setText(person.getLastName() != null ? person.getLastName() : "");
        txtEmail.setText(person.getEmail() != null ? person.getEmail() : "");
        txtPhone.setText(person.getPhone() != null ? person.getPhone() : "");
    }
    
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {
        lblMessage.setText("");
        
        if (txtEmail.getText().trim().isEmpty() || !txtEmail.getText().contains("@")) {
            lblMessage.setText("⚠️ Valid email is required!");
            lblMessage.setForeground(java.awt.Color.RED);
            return;
        }
        
        Person person = admin.getPerson();
        person.setFirstName(txtFirstName.getText().trim());
        person.setLastName(txtLastName.getText().trim());
        person.setEmail(txtEmail.getText().trim());
        person.setPhone(txtPhone.getText().trim());
        
        lblMessage.setText("✅ Profile updated successfully!");
        lblMessage.setForeground(new java.awt.Color(0, 128, 0));
        
        JOptionPane.showMessageDialog(this, "Profile updated successfully!");
    }
    
    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
}