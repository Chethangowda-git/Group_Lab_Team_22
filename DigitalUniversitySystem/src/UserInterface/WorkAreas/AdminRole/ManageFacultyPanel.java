package UserInterface.WorkAreas.AdminRole;

import Business.Business;
import Business.Profiles.FacultyDirectory;
import Business.Profiles.FacultyProfile;
import Business.Person.Person;

import info5100.university.example.Department.Department;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * Manage Faculty Panel
 * Admin can view, search, edit, and delete faculty records
 */
public class ManageFacultyPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    
    FacultyProfile selectedFaculty = null;
    
    private JLabel lblTitle;
    private JLabel lblSearch;
    private JComboBox<String> cmbSearchType;
    private JTextField txtSearch;
    private JButton btnSearch;
    private JButton btnShowAll;
    private JScrollPane scrollPane;
    private JTable tblFaculty;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnRefresh;
    private JButton btnBack;
    
    public ManageFacultyPanel(Business b, JPanel clp) {
        business = b;
        CardSequencePanel = clp;
        
        initComponents();
        loadAllFaculty();
    }
    
    private void initComponents() {
        lblTitle = new JLabel();
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));
        lblTitle.setText("Manage Faculty Records");
        
        lblSearch = new JLabel("Search By:");
        
        cmbSearchType = new JComboBox<>();
        cmbSearchType.addItem("Name");
        cmbSearchType.addItem("ID");
        cmbSearchType.addItem("Department");
        
        txtSearch = new JTextField();
        
        btnSearch = new JButton("Search");
        btnSearch.addActionListener(evt -> btnSearchActionPerformed(evt));
        
        btnShowAll = new JButton("Show All");
        btnShowAll.addActionListener(evt -> loadAllFaculty());
        
        tblFaculty = new JTable();
        tblFaculty.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Faculty ID", "Name", "Email", "Phone", "Department", "Courses Teaching"
            }
        ) {
            boolean[] canEdit = new boolean[] {false, false, false, false, false, false};
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        
        tblFaculty.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                tblFacultySelectionChanged();
            }
        });
        
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(tblFaculty);
        
        btnEdit = new JButton("Edit Faculty");
        btnEdit.addActionListener(evt -> btnEditActionPerformed(evt));
        
        btnDelete = new JButton("Delete Faculty");
        btnDelete.setBackground(new java.awt.Color(255, 102, 102));
        btnDelete.setForeground(java.awt.Color.WHITE);
        btnDelete.addActionListener(evt -> btnDeleteActionPerformed(evt));
        
        btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(evt -> loadAllFaculty());
        
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
        scrollPane.setBounds(30, 105, 780, 350);
        
        add(btnEdit);
        btnEdit.setBounds(490, 475, 120, 35);
        
        add(btnDelete);
        btnDelete.setBounds(620, 475, 120, 35);
        
        add(btnRefresh);
        btnRefresh.setBounds(360, 475, 120, 35);
        
        add(btnBack);
        btnBack.setBounds(30, 475, 100, 35);
    }
    
    private void loadAllFaculty() {
        DefaultTableModel model = (DefaultTableModel) tblFaculty.getModel();
        model.setRowCount(0);
        
        FacultyDirectory fd = business.getFacultyDirectory();
        ArrayList<FacultyProfile> facultyList = fd.getFacultyList();
        
        for (FacultyProfile fp : facultyList) {
            addFacultyToTable(model, fp);
        }
        
        System.out.println("✅ Loaded " + facultyList.size() + " faculty members");
    }
    
    private void addFacultyToTable(DefaultTableModel model, FacultyProfile fp) {
        Object[] row = new Object[6];
        
        Person person = fp.getPerson();
        row[0] = person.getPersonId();
        row[1] = person.getFullName();
        row[2] = person.getEmail();
        row[3] = person.getPhone() != null ? person.getPhone() : "";
        
        Department dept = business.getDepartment();
        row[4] = dept.getName();
        
        // Count courses teaching
        info5100.university.example.Persona.Faculty.FacultyProfile univFaculty = fp.getUniversityProfile();
        int courseCount = univFaculty.getFacultyAssignments().size();
        row[5] = courseCount;
        
        model.addRow(row);
    }
    
private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {
    String searchType = (String) cmbSearchType.getSelectedItem();
    String searchText = txtSearch.getText().trim();
    
    if (searchText.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter search text!");
        return;
    }
    
    DefaultTableModel model = (DefaultTableModel) tblFaculty.getModel();
    model.setRowCount(0);
    
    FacultyDirectory fd = business.getFacultyDirectory();
    ArrayList<FacultyProfile> facultyList = fd.getFacultyList();
    Department dept = business.getDepartment();
    
    int found = 0;
    
    for (FacultyProfile fp : facultyList) {
        boolean matches = false;
        
        // SEARCH METHOD 1: By Name
        if ("Name".equals(searchType)) {
            String fullName = fp.getPerson().getFullName();
            matches = fullName.toLowerCase().contains(searchText.toLowerCase());
        }
        // SEARCH METHOD 2: By ID
        else if ("ID".equals(searchType)) {
            String id = fp.getPerson().getPersonId();
            matches = id.toLowerCase().contains(searchText.toLowerCase());
        }
        // SEARCH METHOD 3: By Department
        else if ("Department".equals(searchType)) {
            String deptName = dept.getName();
            matches = deptName.toLowerCase().contains(searchText.toLowerCase());
        }
        
        if (matches) {
            addFacultyToTable(model, fp);
            found++;
        }
    }
    
    System.out.println("🔍 Search by " + searchType + " for '" + searchText + "' found " + found + " results");
    
    if (found == 0) {
        JOptionPane.showMessageDialog(this, 
            "No faculty found matching: '" + searchText + "'\n\n" +
            "Search Type: " + searchType);
    }
}
    
    private void tblFacultySelectionChanged() {
        int selectedRow = tblFaculty.getSelectedRow();
        
        if (selectedRow < 0) {
            selectedFaculty = null;
            return;
        }
        
        String facultyId = (String) tblFaculty.getValueAt(selectedRow, 0);
        FacultyDirectory fd = business.getFacultyDirectory();
        selectedFaculty = fd.findFaculty(facultyId);
    }
    
    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {
         if (selectedFaculty == null) {
        JOptionPane.showMessageDialog(this, "Please select a faculty member first!");
        return;
    }
    
    EditFacultyPanel panel = new EditFacultyPanel(business, selectedFaculty, CardSequencePanel);
    CardSequencePanel.add("EditFaculty", panel);
    ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
    
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        if (selectedFaculty == null) {
            JOptionPane.showMessageDialog(this, "Please select a faculty member first!");
            return;
        }
        JOptionPane.showMessageDialog(this, "Delete faculty - Coming in next step!");
    }
    
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
}