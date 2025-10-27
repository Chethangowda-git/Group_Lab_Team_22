package UserInterface.WorkAreas.StudentRole;

import Business.Business;
import Business.Profiles.StudentProfile;
import Business.Person.Person;
import javax.swing.*;

/**
 * Student Profile Panel
 * Student can view and edit their profile
 * 
 * @author chethan
 */
public class StudentProfilePanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    StudentProfile student;
    
    private JLabel lblTitle, lblPersonId, lblFirstName, lblLastName, lblEmail, lblPhone, lblMessage;
    private JTextField txtPersonId, txtFirstName, txtLastName, txtEmail, txtPhone;
    private JButton btnSave, btnCancel;
    
    public StudentProfilePanel(Business b, StudentProfile sp, JPanel clp) {
        business = b;
        student = sp;
        CardSequencePanel = clp;
        initComponents();
        loadProfileData();
    }
    
    private void initComponents() {
        lblTitle = new JLabel();
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));
        lblTitle.setText("My Student Profile");
        
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
        lblPersonId.setBounds(30, 80, 150, 25);
        add(txtPersonId);
        txtPersonId.setBounds(200, 80, 250, 30);
        add(lblFirstName);
        lblFirstName.setBounds(30, 120, 150, 25);
        add(txtFirstName);
        txtFirstName.setBounds(200, 120, 250, 30);
        add(lblLastName);
        lblLastName.setBounds(30, 160, 150, 25);
        add(txtLastName);
        txtLastName.setBounds(200, 160, 250, 30);
        add(lblEmail);
        lblEmail.setBounds(30, 200, 150, 25);
        add(txtEmail);
        txtEmail.setBounds(200, 200, 250, 30);
        add(lblPhone);
        lblPhone.setBounds(30, 240, 150, 25);
        add(txtPhone);
        txtPhone.setBounds(200, 240, 250, 30);
        add(lblMessage);
        lblMessage.setBounds(30, 290, 450, 25);
        add(btnSave);
        btnSave.setBounds(300, 330, 150, 35);
        add(btnCancel);
        btnCancel.setBounds(30, 330, 100, 35);
    }
    
    private void loadProfileData() {
        Person person = student.getPerson();
        txtPersonId.setText(person.getPersonId());
        txtFirstName.setText(person.getFirstName() != null ? person.getFirstName() : "");
        txtLastName.setText(person.getLastName() != null ? person.getLastName() : "");
        txtEmail.setText(person.getEmail() != null ? person.getEmail() : "");
        txtPhone.setText(person.getPhone() != null ? person.getPhone() : "");
    }
    
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {
        if (txtEmail.getText().trim().isEmpty() || !txtEmail.getText().contains("@")) {
            lblMessage.setText("Valid email required!");
            lblMessage.setForeground(java.awt.Color.RED);
            return;
        }
        
        Person person = student.getPerson();
        person.setFirstName(txtFirstName.getText().trim());
        person.setLastName(txtLastName.getText().trim());
        person.setEmail(txtEmail.getText().trim());
        person.setPhone(txtPhone.getText().trim());
        
        lblMessage.setText("Profile updated!");
        lblMessage.setForeground(new java.awt.Color(0, 128, 0));
        JOptionPane.showMessageDialog(this, "Profile updated successfully!");
    }
    
    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
}