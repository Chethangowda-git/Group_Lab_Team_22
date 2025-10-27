/*
 * ManageUserAccountsJPanel.java
 * Admin can view, search, edit, and delete user accounts
 *
 * @author chethan
 */
package UserInterface.WorkAreas.AdminRole;

import Business.Business;
import Business.UserAccounts.UserAccount;
import Business.UserAccounts.UserAccountDirectory;
import Business.Profiles.Profile;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * Manage User Accounts Panel
 * Admin can view, edit, and delete user accounts
 */
public class ManageUserAccountsJPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;

    UserAccount selectedAccount = null;

    private JLabel lblTitle;
    private JLabel lblSearch;
    private JComboBox<String> cmbSearchType;
    private JTextField txtSearch;
    private JButton btnSearch;
    private JButton btnShowAll;
    private JScrollPane scrollPane;
    private JTable tblAccounts;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnRefresh;
    private JButton btnBack;

    public ManageUserAccountsJPanel(Business b, JPanel clp) {
        business = b;
        CardSequencePanel = clp;

        initComponents();
        loadAllAccounts();
    }

    /**
     * Initialize all UI components
     */
    private void initComponents() {
        lblTitle = new JLabel();
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));
        lblTitle.setText("Manage User Accounts");

        lblSearch = new JLabel("Search By:");

        cmbSearchType = new JComboBox<>();
        cmbSearchType.addItem("Username");
        cmbSearchType.addItem("Role");
        cmbSearchType.addItem("Person Name");

        txtSearch = new JTextField();

        btnSearch = new JButton("Search");
        btnSearch.addActionListener(evt -> btnSearchActionPerformed(evt));

        btnShowAll = new JButton("Show All");
        btnShowAll.addActionListener(evt -> loadAllAccounts());

        tblAccounts = new JTable();
        tblAccounts.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Username", "Role", "Person ID", "Person Name", "Email"
            }
        ) {
            boolean[] canEdit = new boolean[] {false, false, false, false, false};
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        tblAccounts.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                tblAccountsSelectionChanged();
            }
        });

        scrollPane = new JScrollPane();
        scrollPane.setViewportView(tblAccounts);

        btnEdit = new JButton("Edit Account");
        btnEdit.setBackground(new java.awt.Color(102, 153, 255));
        btnEdit.setForeground(java.awt.Color.WHITE);
        btnEdit.addActionListener(evt -> btnEditActionPerformed(evt));

        btnDelete = new JButton("Delete Account");
        btnDelete.setBackground(new java.awt.Color(255, 102, 102));
        btnDelete.setForeground(java.awt.Color.WHITE);
        btnDelete.addActionListener(evt -> btnDeleteActionPerformed(evt));

        btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(evt -> loadAllAccounts());

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

    /**
     * Load all user accounts and populate table
     */
    private void loadAllAccounts() {
        DefaultTableModel model = (DefaultTableModel) tblAccounts.getModel();
        model.setRowCount(0);

        UserAccountDirectory uad = business.getUserAccountDirectory();
        ArrayList<UserAccount> accounts = uad.getUserAccountList();

        for (UserAccount ua : accounts) {
            addAccountToTable(model, ua);
        }

        System.out.println("✅ Loaded " + accounts.size() + " user accounts");
    }

    /**
     * Add a single user account to the table
     */
    private void addAccountToTable(DefaultTableModel model, UserAccount ua) {
        Object[] row = new Object[5];

        Profile profile = ua.getAssociatedPersonProfile();

        row[0] = ua.getUserLoginName();
        row[1] = ua.getRole();
        row[2] = ua.getPersonId();
        row[3] = profile.getPerson().getFullName();
        row[4] = profile.getPerson().getEmail();

        model.addRow(row);
    }

    /**
     * Search user accounts by username, role, or person name
     */
    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {
        String searchType = (String) cmbSearchType.getSelectedItem();
        String searchText = txtSearch.getText().trim();

        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter search text!");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tblAccounts.getModel();
        model.setRowCount(0);

        UserAccountDirectory uad = business.getUserAccountDirectory();
        ArrayList<UserAccount> accounts = uad.getUserAccountList();

        int found = 0;

        for (UserAccount ua : accounts) {
            boolean matches = false;

            // SEARCH METHOD 1: By Username
            if ("Username".equals(searchType)) {
                String username = ua.getUserLoginName();
                matches = username.toLowerCase().contains(searchText.toLowerCase());
            }
            // SEARCH METHOD 2: By Role
            else if ("Role".equals(searchType)) {
                String role = ua.getRole();
                matches = role.toLowerCase().contains(searchText.toLowerCase());
            }
            // SEARCH METHOD 3: By Person Name
            else if ("Person Name".equals(searchType)) {
                Profile profile = ua.getAssociatedPersonProfile();
                String fullName = profile.getPerson().getFullName();
                matches = fullName.toLowerCase().contains(searchText.toLowerCase());
            }

            if (matches) {
                addAccountToTable(model, ua);
                found++;
            }
        }

        System.out.println("🔍 Search by " + searchType + " for '" + searchText + "' found " + found + " results");

        if (found == 0) {
            JOptionPane.showMessageDialog(this,
                "No user accounts found matching: '" + searchText + "'\n\n" +
                "Search Type: " + searchType);
        }
    }

    /**
     * Handle table selection change
     */
    private void tblAccountsSelectionChanged() {
        int selectedRow = tblAccounts.getSelectedRow();

        if (selectedRow < 0) {
            selectedAccount = null;
            return;
        }

        String username = (String) tblAccounts.getValueAt(selectedRow, 0);
        UserAccountDirectory uad = business.getUserAccountDirectory();

        // Find account by username
        for (UserAccount ua : uad.getUserAccountList()) {
            if (ua.getUserLoginName().equals(username)) {
                selectedAccount = ua;
                break;
            }
        }
    }

    /**
     * Edit selected user account (username/password)
     */
    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {
        if (selectedAccount == null) {
            JOptionPane.showMessageDialog(this, "Please select a user account first!");
            return;
        }

        EditUserAccountPanel panel = new EditUserAccountPanel(business, selectedAccount, CardSequencePanel);
        CardSequencePanel.add("EditUserAccount", panel);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }

    /**
     * Delete selected user account with validation
     */
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        if (selectedAccount == null) {
            JOptionPane.showMessageDialog(this, "Please select a user account first!");
            return;
        }

        Profile profile = selectedAccount.getAssociatedPersonProfile();
        String personName = profile.getPerson().getFullName();
        String username = selectedAccount.getUserLoginName();
        String role = selectedAccount.getRole();

        // Prevent deleting admin account (optional safety check)
        if ("admin".equalsIgnoreCase(username)) {
            JOptionPane.showMessageDialog(this,
                "⚠️ Cannot delete the main admin account!",
                "Delete Not Allowed",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "⚠️ Are you sure you want to DELETE this user account?\n\n" +
            "Username: " + username + "\n" +
            "Role: " + role + "\n" +
            "Person: " + personName + "\n\n" +
            "This will prevent this user from logging in.\n" +
            "This action cannot be undone!",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (confirm != JOptionPane.YES_OPTION) return;

        // Remove from directory
        UserAccountDirectory uad = business.getUserAccountDirectory();
        uad.getUserAccountList().remove(selectedAccount);

        JOptionPane.showMessageDialog(this,
            "✅ User account deleted successfully!\n\n" +
            "Username: " + username + " has been removed.");

        selectedAccount = null;
        loadAllAccounts();
    }

    /**
     * Navigate back to admin work area
     */
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
}