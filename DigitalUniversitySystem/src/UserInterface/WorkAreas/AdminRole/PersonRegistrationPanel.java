package UserInterface.WorkAreas.AdminRole;

import Business.Business;
import Business.Person.Person;
import Business.Person.PersonDirectory;
import Business.Profiles.EmployeeDirectory;
import Business.Profiles.EmployeeProfile;
import Business.Profiles.FacultyDirectory;
import Business.Profiles.FacultyProfile;
import Business.Profiles.Profile;
import Business.Profiles.RegistrarDirectory;
import Business.Profiles.RegistrarProfile;
import Business.Profiles.StudentDirectory;
import Business.Profiles.StudentProfile;
import Business.UserAccounts.UserAccount;
import Business.UserAccounts.UserAccountDirectory;

import javax.swing.*;

/**
 * Person Registration Panel
 * Admin can register new persons (Student, Faculty, Registrar) with duplicate checking
 */
public class PersonRegistrationPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    
    private JLabel lblTitle;
    private JLabel lblPersonId;
    private JTextField txtPersonId;
    private JButton btnGenerateId;
    private JLabel lblFirstName;
    private JTextField txtFirstName;
    private JLabel lblLastName;
    private JTextField txtLastName;
    private JLabel lblEmail;
    private JTextField txtEmail;
    private JLabel lblPhone;
    private JTextField txtPhone;
    private JLabel lblRole;
    private JComboBox<String> cmbRole;
    private JLabel lblUsername;
    private JTextField txtUsername;
    private JLabel lblPassword;
    private JPasswordField txtPassword;
    private JLabel lblMessage;
    private JButton btnRegister;
    private JButton btnClear;
    private JButton btnBack;
    
    public PersonRegistrationPanel(Business b, JPanel clp) {
        business = b;
        CardSequencePanel = clp;
        
        initComponents();
        generateUniqueId();
    }
    
    private void initComponents() {
        lblTitle = new JLabel();
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));
        lblTitle.setText("Register New Person");
        
        lblPersonId = new JLabel("Person ID: *");
        txtPersonId = new JTextField();
        txtPersonId.setEditable(false);
        txtPersonId.setBackground(java.awt.Color.LIGHT_GRAY);
        
        btnGenerateId = new JButton("Generate New ID");
        btnGenerateId.addActionListener(evt -> generateUniqueId());
        
        lblFirstName = new JLabel("First Name: *");
        txtFirstName = new JTextField();
        
        lblLastName = new JLabel("Last Name: *");
        txtLastName = new JTextField();
        
        lblEmail = new JLabel("Email: *");
        txtEmail = new JTextField();
        
        lblPhone = new JLabel("Phone:");
        txtPhone = new JTextField();
        
        lblRole = new JLabel("Role: *");
        cmbRole = new JComboBox<>();
        cmbRole.addItem("Student");
        cmbRole.addItem("Faculty");
        cmbRole.addItem("Registrar");
        
        lblUsername = new JLabel("Username: *");
        txtUsername = new JTextField();
        
        lblPassword = new JLabel("Password: *");
        txtPassword = new JPasswordField();
        
        lblMessage = new JLabel();
        lblMessage.setForeground(java.awt.Color.RED);
        
        btnRegister = new JButton();
        btnRegister.setBackground(new java.awt.Color(102, 153, 255));
        btnRegister.setForeground(java.awt.Color.WHITE);
        btnRegister.setText("Register Person");
        btnRegister.addActionListener(evt -> btnRegisterActionPerformed(evt));
        
        btnClear = new JButton("Clear Form");
        btnClear.addActionListener(evt -> clearForm());
        
        btnBack = new JButton("<< Back");
        btnBack.addActionListener(evt -> btnBackActionPerformed(evt));
        
        setLayout(null);
        
        add(lblTitle);
        lblTitle.setBounds(30, 20, 400, 30);
        
        add(lblPersonId);
        lblPersonId.setBounds(30, 70, 150, 25);
        add(txtPersonId);
        txtPersonId.setBounds(200, 70, 200, 30);
        add(btnGenerateId);
        btnGenerateId.setBounds(410, 70, 150, 30);
        
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
        
        add(lblRole);
        lblRole.setBounds(30, 270, 150, 25);
        add(cmbRole);
        cmbRole.setBounds(200, 270, 150, 30);
        
        add(lblUsername);
        lblUsername.setBounds(30, 310, 150, 25);
        add(txtUsername);
        txtUsername.setBounds(200, 310, 200, 30);
        
        add(lblPassword);
        lblPassword.setBounds(30, 350, 150, 25);
        add(txtPassword);
        txtPassword.setBounds(200, 350, 200, 30);
        
        add(lblMessage);
        lblMessage.setBounds(30, 390, 600, 25);
        
        add(btnRegister);
        btnRegister.setBounds(350, 430, 150, 40);
        
        add(btnClear);
        btnClear.setBounds(220, 430, 120, 40);
        
        add(btnBack);
        btnBack.setBounds(30, 430, 100, 40);
    }
    
    private void generateUniqueId() {
    PersonDirectory pd = business.getPersonDirectory();
    int nextId = 1000 + pd.getPersonList().size();
    String newId = "P" + String.format("%06d", nextId);
    txtPersonId.setText(newId);
    System.out.println("✅ Generated ID: " + newId);
}
    
    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {
        lblMessage.setText("");
        
        // VALIDATION
        if (txtFirstName.getText().trim().isEmpty()) {
            lblMessage.setText("⚠️ First name is required!");
            return;
        }
        
        if (txtLastName.getText().trim().isEmpty()) {
            lblMessage.setText("⚠️ Last name is required!");
            return;
        }
        
        if (txtEmail.getText().trim().isEmpty() || !txtEmail.getText().contains("@")) {
            lblMessage.setText("⚠️ Valid email is required!");
            return;
        }
        
        if (txtUsername.getText().trim().isEmpty()) {
            lblMessage.setText("⚠️ Username is required!");
            return;
        }
        
        String password = new String(txtPassword.getPassword()).trim();
        if (password.isEmpty()) {
            lblMessage.setText("⚠️ Password is required!");
            return;
        }
        
        // CHECK FOR DUPLICATES
        PersonDirectory pd = business.getPersonDirectory();
        String email = txtEmail.getText().trim();
        String personId = txtPersonId.getText().trim();
        
        for (Person p : pd.getPersonList()) {
            if (email.equalsIgnoreCase(p.getEmail())) {
                lblMessage.setText("⚠️ Email already exists! Please use different email.");
                return;
            }
            if (personId.equals(p.getPersonId())) {
                lblMessage.setText("⚠️ Person ID already exists! Click 'Generate New ID'.");
                return;
            }
        }
        
        // CREATE PERSON
        Person newPerson = pd.newPerson(
            personId,
            email,
            txtFirstName.getText().trim(),
            txtLastName.getText().trim(),
            txtPhone.getText().trim()
        );
        
        // CREATE ROLE PROFILE
        String role = (String) cmbRole.getSelectedItem();
        Profile profile = null;
        
        if ("Student".equals(role)) {
            StudentDirectory sd = business.getStudentDirectory();
            profile = sd.newStudentProfile(newPerson);
        } else if ("Faculty".equals(role)) {
            FacultyDirectory fd = business.getFacultyDirectory();
            profile = fd.newFacultyProfile(newPerson);
        } else if ("Registrar".equals(role)) {
            RegistrarDirectory rd = business.getRegistrarDirectory();
            profile = rd.newRegistrarProfile(newPerson);
        }
        
        // CREATE USER ACCOUNT
        UserAccountDirectory uad = business.getUserAccountDirectory();
        uad.newUserAccount(profile, txtUsername.getText().trim(), password);
        
        System.out.println("✅ Registered: " + newPerson.getFullName() + " as " + role);
        
        JOptionPane.showMessageDialog(this,
            "✅ Person registered successfully!\n\n" +
            "Name: " + newPerson.getFullName() + "\n" +
            "Email: " + email + "\n" +
            "Role: " + role + "\n" +
            "Username: " + txtUsername.getText() + "\n" +
            "Person ID: " + personId,
            "Registration Complete",
            JOptionPane.INFORMATION_MESSAGE);
        
        clearForm();
        generateUniqueId();
    }
    
    private void clearForm() {
        txtFirstName.setText("");
        txtLastName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        cmbRole.setSelectedIndex(0);
        lblMessage.setText("");
    }
    
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
}