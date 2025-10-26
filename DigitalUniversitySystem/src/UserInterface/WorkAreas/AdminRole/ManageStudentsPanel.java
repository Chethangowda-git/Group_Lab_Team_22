package UserInterface.WorkAreas.AdminRole;

import Business.Business;
import Business.Profiles.StudentDirectory;
import Business.Profiles.StudentProfile;
import Business.Person.Person;
import info5100.university.example.Persona.Transcript;

import info5100.university.example.Department.Department;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * Manage Students Panel
 * Admin can view, search, edit, and delete student records
 */
public class ManageStudentsPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    
    StudentProfile selectedStudent = null;
    
    private JLabel lblTitle;
    private JLabel lblSearch;
    private JComboBox<String> cmbSearchType;
    private JTextField txtSearch;
    private JButton btnSearch;
    private JButton btnShowAll;
    private JScrollPane scrollPane;
    private JTable tblStudents;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnRefresh;
    private JButton btnBack;
    
    public ManageStudentsPanel(Business b, JPanel clp) {
        business = b;
        CardSequencePanel = clp;
        
        initComponents();
        loadAllStudents();
    }
    
    private void initComponents() {
        lblTitle = new JLabel();
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));
        lblTitle.setText("Manage Student Records");
        
        // Search controls
        lblSearch = new JLabel("Search By:");
        
        cmbSearchType = new JComboBox<>();
        cmbSearchType.addItem("Name");
        cmbSearchType.addItem("ID");
        cmbSearchType.addItem("Department");
        
        txtSearch = new JTextField();
        
        btnSearch = new JButton("Search");
        btnSearch.addActionListener(evt -> btnSearchActionPerformed(evt));
        
        btnShowAll = new JButton("Show All");
        btnShowAll.addActionListener(evt -> loadAllStudents());
        
        // Students Table
        tblStudents = new JTable();
        tblStudents.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Student ID", "Name", "Email", "Phone", "Program", "Total Credits", "GPA"
            }
        ) {
            boolean[] canEdit = new boolean[] {false, false, false, false, false, false, false};
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        
        tblStudents.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                tblStudentsSelectionChanged();
            }
        });
        
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(tblStudents);
        
        btnEdit = new JButton("Edit Student");
        btnEdit.addActionListener(evt -> btnEditActionPerformed(evt));
        
        btnDelete = new JButton("Delete Student");
        btnDelete.setBackground(new java.awt.Color(255, 102, 102));
        btnDelete.setForeground(java.awt.Color.WHITE);
        btnDelete.addActionListener(evt -> btnDeleteActionPerformed(evt));
        
        btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(evt -> loadAllStudents());
        
        btnBack = new JButton("<< Back");
        btnBack.addActionListener(evt -> btnBackActionPerformed(evt));
        
        setLayout(null);
        
        add(lblTitle);
        lblTitle.setBounds(30, 20, 400, 30);
        
        add(lblSearch);
        lblSearch.setBounds(30, 65, 80, 25);
        add(cmbSearchType);
        cmbSearchType.setBounds(120, 65, 120, 25);
        add(txtSearch);
        txtSearch.setBounds(250, 65, 200, 25);
        add(btnSearch);
        btnSearch.setBounds(460, 65, 100, 25);
        add(btnShowAll);
        btnShowAll.setBounds(570, 65, 100, 25);
        
        add(scrollPane);
        scrollPane.setBounds(30, 105, 850, 350);
        
        add(btnEdit);
        btnEdit.setBounds(550, 475, 120, 35);
        
        add(btnDelete);
        btnDelete.setBounds(680, 475, 120, 35);
        
        add(btnRefresh);
        btnRefresh.setBounds(420, 475, 120, 35);
        
        add(btnBack);
        btnBack.setBounds(30, 475, 100, 35);
    }
    
    private void loadAllStudents() {
        DefaultTableModel model = (DefaultTableModel) tblStudents.getModel();
        model.setRowCount(0);
        
        StudentDirectory sd = business.getStudentDirectory();
        ArrayList<StudentProfile> students = sd.getStudentList();
        
        for (StudentProfile sp : students) {
            addStudentToTable(model, sp);
        }
        
        System.out.println("✅ Loaded " + students.size() + " students");
    }
    
    private void addStudentToTable(DefaultTableModel model, StudentProfile sp) {
        Object[] row = new Object[7];
        
        Person person = sp.getPerson();
        row[0] = person.getPersonId();
        row[1] = person.getFullName();
        row[2] = person.getEmail();
        row[3] = person.getPhone() != null ? person.getPhone() : "";
        row[4] = "MSIS";  // Program
        
        // Get credits and GPA from university profile
        info5100.university.example.Persona.StudentProfile univStudent = sp.getUniversityProfile();
        info5100.university.example.Persona.Transcript transcript = univStudent.getTranscript();
        
        row[5] = transcript.getTotalCreditHours();
        row[6] = String.format("%.2f", transcript.calculateOverallGPA());
        
        model.addRow(row);
    }
    
    
    private void tblStudentsSelectionChanged() {
        int selectedRow = tblStudents.getSelectedRow();
        
        if (selectedRow < 0) {
            selectedStudent = null;
            return;
        }
        
        String studentId = (String) tblStudents.getValueAt(selectedRow, 0);
        StudentDirectory sd = business.getStudentDirectory();
        selectedStudent = sd.findStudent(studentId);
    }
    
private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {
    if (selectedStudent == null) {
        JOptionPane.showMessageDialog(this, "Please select a student first!");
        return;
    }
    
    EditStudentPanel panel = new EditStudentPanel(business, selectedStudent, CardSequencePanel);
    CardSequencePanel.add("EditStudent", panel);
    ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
}

private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
    if (selectedStudent == null) {
        JOptionPane.showMessageDialog(this, "Please select a student first!");
        return;
    }
    
    // Check if student has enrollments
    info5100.university.example.Persona.Transcript transcript = 
        selectedStudent.getUniversityProfile().getTranscript();
    
    if (transcript.getTotalCreditHours() > 0) {
        JOptionPane.showMessageDialog(this,
            "⚠️ Cannot delete student with active enrollments!\n\n" +
            "Student has " + transcript.getTotalCreditHours() + " credits enrolled.\n" +
            "Please drop all courses before deleting.",
            "Delete Not Allowed",
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    int confirm = JOptionPane.showConfirmDialog(this,
        "⚠️ Are you sure you want to DELETE:\n\n" +
        selectedStudent.getPerson().getFullName() + "\n" +
        "ID: " + selectedStudent.getPerson().getPersonId() + "\n\n" +
        "This action cannot be undone!",
        "Confirm Delete",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE);
    
    if (confirm != JOptionPane.YES_OPTION) return;
    
    // Remove from directory
    StudentDirectory sd = business.getStudentDirectory();
    sd.getStudentList().remove(selectedStudent);
    
    JOptionPane.showMessageDialog(this, "✅ Student deleted successfully!");
    
    loadAllStudents();
}
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {
    String searchType = (String) cmbSearchType.getSelectedItem();
    String searchText = txtSearch.getText().trim();
    
    if (searchText.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter search text!");
        return;
    }
    
    DefaultTableModel model = (DefaultTableModel) tblStudents.getModel();
    model.setRowCount(0);
    
    StudentDirectory sd = business.getStudentDirectory();
    ArrayList<StudentProfile> students = sd.getStudentList();
    Department dept = business.getDepartment();
    
    int found = 0;
    
    for (StudentProfile sp : students) {
        boolean matches = false;
        
        if ("Name".equals(searchType)) {
            String fullName = sp.getPerson().getFullName();
            matches = fullName.toLowerCase().contains(searchText.toLowerCase());
        }
        else if ("ID".equals(searchType)) {
            String id = sp.getPerson().getPersonId();
            matches = id.toLowerCase().contains(searchText.toLowerCase());
        }
        else if ("Department".equals(searchType)) {
            String deptName = dept.getName();
            matches = deptName.toLowerCase().contains(searchText.toLowerCase());
        }
        
        if (matches) {
            addStudentToTable(model, sp);
            found++;
        }
    }
    
    if (found == 0) {
        JOptionPane.showMessageDialog(this, "No students found matching: " + searchText);
    }
    
    System.out.println("✅ Search found " + found + " students");
}
}