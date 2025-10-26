package UserInterface.WorkAreas.FacultyRole;

import Business.Business;
import Business.Profiles.FacultyProfile;
import Business.Person.Person;

import javax.swing.*;

public class FacultyProfilePanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    FacultyProfile faculty;
    
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
    private JLabel lblOfficeHours;
    private JTextField txtOfficeHours;
    private JLabel lblMessage;
    private JButton btnSave;
    private JButton btnCancel;
    
    public FacultyProfilePanel(Business b, FacultyProfile fp, JPanel clp) {
        business = b;
        faculty = fp;
        CardSequencePanel = clp;
        
        initComponents();
        loadProfileData();
    }
    
    private void initComponents() {
        lblTitle = new JLabel();
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));
        lblTitle.setText("My Faculty Profile");
        
        lblPersonId = new JLabel();
        lblPersonId.setText("Person ID:");
        txtPersonId = new JTextField();
        txtPersonId.setEditable(false);
        txtPersonId.setBackground(java.awt.Color.LIGHT_GRAY);
        
        lblFirstName = new JLabel();
        lblFirstName.setText("First Name:");
        txtFirstName = new JTextField();
        
        lblLastName = new JLabel();
        lblLastName.setText("Last Name:");
        txtLastName = new JTextField();
        
        lblEmail = new JLabel();
        lblEmail.setText("Email: *");
        txtEmail = new JTextField();
        
        lblPhone = new JLabel();
        lblPhone.setText("Phone:");
        txtPhone = new JTextField();
        
        lblOfficeHours = new JLabel();
        lblOfficeHours.setText("Office Hours:");
        txtOfficeHours = new JTextField();
        txtOfficeHours.setText("By Appointment");
        
        lblMessage = new JLabel();
        lblMessage.setForeground(java.awt.Color.BLUE);
        
        btnSave = new JButton();
        btnSave.setBackground(new java.awt.Color(102, 153, 255));
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("Save Changes");
        btnSave.addActionListener(evt -> btnSaveActionPerformed(evt));
        
        btnCancel = new JButton();
        btnCancel.setText("Cancel");
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
        
        add(lblOfficeHours);
        lblOfficeHours.setBounds(30, 280, 150, 25);
        add(txtOfficeHours);
        txtOfficeHours.setBounds(200, 280, 250, 30);
        
        add(lblMessage);
        lblMessage.setBounds(30, 330, 450, 25);
        
        add(btnSave);
        btnSave.setBounds(300, 370, 150, 35);
        
        add(btnCancel);
        btnCancel.setBounds(30, 370, 100, 35);
    }
    
    private void loadProfileData() {
        Person person = faculty.getPerson();
        
        txtPersonId.setText(person.getPersonId());
        txtFirstName.setText(person.getFirstName() != null ? person.getFirstName() : "");
        txtLastName.setText(person.getLastName() != null ? person.getLastName() : "");
        txtEmail.setText(person.getEmail() != null ? person.getEmail() : "");
        txtPhone.setText(person.getPhone() != null ? person.getPhone() : "");
    }
    
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {
        lblMessage.setText("");
        
        if (txtEmail.getText().trim().isEmpty() || !txtEmail.getText().contains("@")) {
            lblMessage.setText("⚠️ Please enter a valid email!");
            lblMessage.setForeground(java.awt.Color.RED);
            return;
        }
        
        Person person = faculty.getPerson();
        person.setFirstName(txtFirstName.getText().trim());
        person.setLastName(txtLastName.getText().trim());
        person.setEmail(txtEmail.getText().trim());
        person.setPhone(txtPhone.getText().trim());
        
        lblMessage.setText("✅ Profile updated successfully!");
        lblMessage.setForeground(new java.awt.Color(0, 128, 0));
        
        JOptionPane.showMessageDialog(this, "Profile updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
}