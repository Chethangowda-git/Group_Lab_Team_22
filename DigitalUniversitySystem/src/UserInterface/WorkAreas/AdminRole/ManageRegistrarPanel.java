/*
 * ManageRegistrarPanel.java
 * Admin can view, search, edit, and delete registrar records
 *
 * @author chethan
 */
package UserInterface.WorkAreas.AdminRole;

import Business.Business;
import Business.Profiles.RegistrarDirectory;
import Business.Profiles.RegistrarProfile;
import Business.Person.Person;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * Manage Registrar Panel
 * Admin can view, search, edit, and delete registrar records
 */
public class ManageRegistrarPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;

    RegistrarProfile selectedRegistrar = null;

    private JLabel lblTitle;
    private JLabel lblSearch;
    private JComboBox<String> cmbSearchType;
    private JTextField txtSearch;
    private JButton btnSearch;
    private JButton btnShowAll;
    private JScrollPane scrollPane;
    private JTable tblRegistrars;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnRefresh;
    private JButton btnBack;

    public ManageRegistrarPanel(Business b, JPanel clp) {
        business = b;
        CardSequencePanel = clp;

        initComponents();
        loadAllRegistrars();
    }

    /**
     * Initialize all UI components
     */
    private void initComponents() {
        lblTitle = new JLabel();
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));
        lblTitle.setText("Manage Registrar Records");

        lblSearch = new JLabel("Search By:");

        cmbSearchType = new JComboBox<>();
        cmbSearchType.addItem("Name");
        cmbSearchType.addItem("ID");
        cmbSearchType.addItem("Email");

        txtSearch = new JTextField();

        btnSearch = new JButton("Search");
        btnSearch.addActionListener(evt -> btnSearchActionPerformed(evt));

        btnShowAll = new JButton("Show All");
        btnShowAll.addActionListener(evt -> loadAllRegistrars());

        tblRegistrars = new JTable();
        tblRegistrars.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Registrar ID", "Name", "Email", "Phone"
            }
        ) {
            boolean[] canEdit = new boolean[] {false, false, false, false};
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        tblRegistrars.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                tblRegistrarsSelectionChanged();
            }
        });

        scrollPane = new JScrollPane();
        scrollPane.setViewportView(tblRegistrars);

        btnEdit = new JButton("Edit Registrar");
        btnEdit.setBackground(new java.awt.Color(102, 153, 255));
        btnEdit.setForeground(java.awt.Color.WHITE);
        btnEdit.addActionListener(evt -> btnEditActionPerformed(evt));

        btnDelete = new JButton("Delete Registrar");
        btnDelete.setBackground(new java.awt.Color(255, 102, 102));
        btnDelete.setForeground(java.awt.Color.WHITE);
        btnDelete.addActionListener(evt -> btnDeleteActionPerformed(evt));

        btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(evt -> loadAllRegistrars());

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

    /**
     * Load all registrars and populate table
     */
    private void loadAllRegistrars() {
        DefaultTableModel model = (DefaultTableModel) tblRegistrars.getModel();
        model.setRowCount(0);

        RegistrarDirectory rd = business.getRegistrarDirectory();
        ArrayList<RegistrarProfile> registrarList = rd.getRegistrarList();

        for (RegistrarProfile rp : registrarList) {
            addRegistrarToTable(model, rp);
        }

        System.out.println("✅ Loaded " + registrarList.size() + " registrars");
    }

    /**
     * Add a single registrar to the table
     */
    private void addRegistrarToTable(DefaultTableModel model, RegistrarProfile rp) {
        Object[] row = new Object[4];

        Person person = rp.getPerson();
        row[0] = person.getPersonId();
        row[1] = person.getFullName();
        row[2] = person.getEmail();
        row[3] = person.getPhone() != null ? person.getPhone() : "";

        model.addRow(row);
    }

    /**
     * Search registrars by name, ID, or email
     */
    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {
        String searchType = (String) cmbSearchType.getSelectedItem();
        String searchText = txtSearch.getText().trim();

        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter search text!");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tblRegistrars.getModel();
        model.setRowCount(0);

        RegistrarDirectory rd = business.getRegistrarDirectory();
        ArrayList<RegistrarProfile> registrarList = rd.getRegistrarList();

        int found = 0;

        for (RegistrarProfile rp : registrarList) {
            boolean matches = false;

            // SEARCH METHOD 1: By Name
            if ("Name".equals(searchType)) {
                String fullName = rp.getPerson().getFullName();
                matches = fullName.toLowerCase().contains(searchText.toLowerCase());
            }
            // SEARCH METHOD 2: By ID
            else if ("ID".equals(searchType)) {
                String id = rp.getPerson().getPersonId();
                matches = id.toLowerCase().contains(searchText.toLowerCase());
            }
            // SEARCH METHOD 3: By Email
            else if ("Email".equals(searchType)) {
                String email = rp.getPerson().getEmail();
                matches = email.toLowerCase().contains(searchText.toLowerCase());
            }

            if (matches) {
                addRegistrarToTable(model, rp);
                found++;
            }
        }

        System.out.println("🔍 Search by " + searchType + " for '" + searchText + "' found " + found + " results");

        if (found == 0) {
            JOptionPane.showMessageDialog(this,
                "No registrars found matching: '" + searchText + "'\n\n" +
                "Search Type: " + searchType);
        }
    }

    /**
     * Handle table selection change
     */
    private void tblRegistrarsSelectionChanged() {
        int selectedRow = tblRegistrars.getSelectedRow();

        if (selectedRow < 0) {
            selectedRegistrar = null;
            return;
        }

        String registrarId = (String) tblRegistrars.getValueAt(selectedRow, 0);
        RegistrarDirectory rd = business.getRegistrarDirectory();
        selectedRegistrar = rd.findRegistrar(registrarId);
    }

    /**
     * Edit selected registrar
     */
    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {
        if (selectedRegistrar == null) {
            JOptionPane.showMessageDialog(this, "Please select a registrar first!");
            return;
        }

        EditRegistrarPanel panel = new EditRegistrarPanel(business, selectedRegistrar, CardSequencePanel);
        CardSequencePanel.add("EditRegistrar", panel);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }

    /**
     * Delete selected registrar with validation
     */
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        if (selectedRegistrar == null) {
            JOptionPane.showMessageDialog(this, "Please select a registrar first!");
            return;
        }

        // Check if this is the only registrar
        RegistrarDirectory rd = business.getRegistrarDirectory();
        if (rd.getRegistrarList().size() <= 1) {
            JOptionPane.showMessageDialog(this,
                "⚠️ Cannot delete the last registrar!\n\n" +
                "The system must have at least one registrar.",
                "Delete Not Allowed",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "⚠️ Are you sure you want to DELETE:\n\n" +
            selectedRegistrar.getPerson().getFullName() + "\n" +
            "ID: " + selectedRegistrar.getPerson().getPersonId() + "\n\n" +
            "This action cannot be undone!",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (confirm != JOptionPane.YES_OPTION) return;

        // Remove from directory
        rd.getRegistrarList().remove(selectedRegistrar);

        JOptionPane.showMessageDialog(this, "✅ Registrar deleted successfully!");

        selectedRegistrar = null;
        loadAllRegistrars();
    }

    /**
     * Navigate back to admin work area
     */
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
}