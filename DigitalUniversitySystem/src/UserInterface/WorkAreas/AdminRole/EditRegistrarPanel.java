/*
 * EditRegistrarPanel.java
 * Admin can edit registrar details
 *
 * @author chethan
 */
package UserInterface.WorkAreas.AdminRole;

import Business.Business;
import Business.Profiles.RegistrarProfile;
import Business.Person.Person;

import javax.swing.*;

/**
 * Edit Registrar Panel
 * Admin can modify registrar contact information
 */
public class EditRegistrarPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    RegistrarProfile registrar;

    private JLabel lblTitle, lblPersonId, lblFirstName, lblLastName, lblEmail, lblPhone, lblMessage;
    private JTextField txtPersonId, txtFirstName, txtLastName, txtEmail, txtPhone;
    private JButton btnSave, btnCancel;

    public EditRegistrarPanel(Business b, RegistrarProfile rp, JPanel clp) {
        business = b;
        registrar = rp;
        CardSequencePanel = clp;
        initComponents();
        loadData();
    }

    /**
     * Initialize UI components
     */
    private void initComponents() {
        lblTitle = new JLabel("Edit Registrar Record");
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));

        lblPersonId = new JLabel("Person ID:");
        txtPersonId = new JTextField();
        txtPersonId.setEditable(false);
        txtPersonId.setBackground(java.awt.Color.LIGHT_GRAY);

        lblFirstName = new JLabel("First Name: *");
        txtFirstName = new JTextField();

        lblLastName = new JLabel("Last Name: *");
        txtLastName = new JTextField();

        lblEmail = new JLabel("Email: *");
        txtEmail = new JTextField();

        lblPhone = new JLabel("Phone:");
        txtPhone = new JTextField();

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

    /**
     * Load current registrar data
     */
    private void loadData() {
        Person person = registrar.getPerson();
        txtPersonId.setText(person.getPersonId());
        txtFirstName.setText(person.getFirstName());
        txtLastName.setText(person.getLastName());
        txtEmail.setText(person.getEmail());
        txtPhone.setText(person.getPhone() != null ? person.getPhone() : "");
    }

    /**
     * Save changes to registrar record
     */
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {
        lblMessage.setText("");

        // Validation
        if (txtFirstName.getText().trim().isEmpty() ||
            txtLastName.getText().trim().isEmpty() ||
            txtEmail.getText().trim().isEmpty() ||
            !txtEmail.getText().contains("@")) {
            lblMessage.setText("⚠️ All required fields must be filled!");
            lblMessage.setForeground(java.awt.Color.RED);
            return;
        }

        // Update person data
        Person person = registrar.getPerson();
        person.setFirstName(txtFirstName.getText().trim());
        person.setLastName(txtLastName.getText().trim());
        person.setEmail(txtEmail.getText().trim());
        person.setPhone(txtPhone.getText().trim());

        JOptionPane.showMessageDialog(this, "✅ Registrar record updated!");

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