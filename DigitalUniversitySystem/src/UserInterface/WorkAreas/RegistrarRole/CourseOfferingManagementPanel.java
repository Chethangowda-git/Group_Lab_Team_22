package UserInterface.WorkAreas.RegistrarRole;

import Business.Business;
import Business.Profiles.RegistrarProfile;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import info5100.university.example.Department.Department;
import info5100.university.example.CourseSchedule.CourseSchedule;
import info5100.university.example.CourseSchedule.CourseOffer;
import info5100.university.example.CourseCatalog.Course;
import info5100.university.example.Persona.Faculty.FacultyProfile;
import info5100.university.example.Persona.Person;
import java.util.ArrayList;

/**
 * Course Offering Management Panel Registrar can view, create, and manage
 * course offerings
 */
public class CourseOfferingManagementPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    RegistrarProfile registrar;

    // UI Components
    private JLabel lblTitle;
    private JLabel lblSemester;
    private JComboBox<String> cmbSemester;
    private JButton btnRefresh;
    private JScrollPane scrollPane;
    private JTable tblCourseOfferings;
    private JButton btnCreateNew;
    private JButton btnBack;

    public CourseOfferingManagementPanel(Business b, RegistrarProfile rp, JPanel clp) {
        business = b;
        registrar = rp;
        CardSequencePanel = clp;

        initComponents();
        loadSemesters();
        loadCourseOfferings();
    }

    private void initComponents() {
        // Title Label
        lblTitle = new JLabel();
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));
        lblTitle.setText("Course Offering Management");

        // Semester Label and ComboBox
        lblSemester = new JLabel();
        lblSemester.setText("Semester:");

        cmbSemester = new JComboBox<>();
        cmbSemester.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSemesterActionPerformed(evt);
            }
        });

        // Refresh Button
        btnRefresh = new JButton();
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        // Create Table
        tblCourseOfferings = new JTable();
        tblCourseOfferings.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Course Number", "Course Name", "Credits", "Faculty", "Capacity", "Enrolled"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        scrollPane = new JScrollPane();
        scrollPane.setViewportView(tblCourseOfferings);

        // Create New Button
        btnCreateNew = new JButton();
        btnCreateNew.setBackground(new java.awt.Color(102, 153, 255));
        btnCreateNew.setForeground(new java.awt.Color(255, 255, 255));
        btnCreateNew.setText("Create New Offering");
        btnCreateNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateNewActionPerformed(evt);
            }
        });

        // Back Button
        btnBack = new JButton();
        btnBack.setText("<< Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        // Layout
        setLayout(null);

        add(lblTitle);
        lblTitle.setBounds(30, 20, 400, 30);

        add(lblSemester);
        lblSemester.setBounds(30, 70, 80, 25);

        add(cmbSemester);
        cmbSemester.setBounds(120, 70, 150, 25);

        add(btnRefresh);
        btnRefresh.setBounds(280, 70, 100, 25);

        add(scrollPane);
        scrollPane.setBounds(30, 110, 700, 300);

        add(btnCreateNew);
        btnCreateNew.setBounds(550, 430, 180, 35);

        add(btnBack);
        btnBack.setBounds(30, 430, 100, 35);
    }

    private void cmbSemesterActionPerformed(java.awt.event.ActionEvent evt) {
        // When semester changes, reload course offerings
        loadCourseOfferings();
    }

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {
        // Refresh the table
        loadCourseOfferings();
    }

    private void btnCreateNewActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO: Open create new course offering dialog
        javax.swing.JOptionPane.showMessageDialog(this, "Create New Offering - Coming in next step!");
    }

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        // Go back to Registrar main menu
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }

    private void loadSemesters() {
        // Clear existing items
        cmbSemester.removeAllItems();

        // Get Department from Business
        info5100.university.example.Department.Department dept = business.getDepartment();

        // Get all semesters (from mastercoursecatalog HashMap)
        // The Department has: HashMap<String, CourseSchedule> mastercoursecatalog
        // For now, we'll add the semesters we know exist
        // In a full implementation, you'd iterate through dept.mastercoursecatalog.keySet()
        cmbSemester.addItem("Fall2025");
        // Add more semesters if they exist

        System.out.println("✅ Loaded semesters into dropdown");
    }

    private void loadCourseOfferings() {
        // Get selected semester
        String selectedSemester = (String) cmbSemester.getSelectedItem();

        if (selectedSemester == null || selectedSemester.isEmpty()) {
            System.out.println("No semester selected");
            return;
        }

        // Clear the table
        DefaultTableModel model = (DefaultTableModel) tblCourseOfferings.getModel();
        model.setRowCount(0); // Remove all rows

        // Get Department from Business
        Department dept = business.getDepartment();

        // Get CourseSchedule for selected semester
        CourseSchedule schedule = dept.getCourseSchedule(selectedSemester);

        if (schedule == null) {
            System.out.println("⚠️ No schedule found for semester: " + selectedSemester);
            javax.swing.JOptionPane.showMessageDialog(this,
                    "No course schedule found for " + selectedSemester);
            return;
        }

        // Get all course offerings
        ArrayList<CourseOffer> offerings = schedule.getCourseOffers();

        if (offerings == null || offerings.isEmpty()) {
            System.out.println("⚠️ No course offerings in this semester");
            javax.swing.JOptionPane.showMessageDialog(this,
                    "No course offerings found for " + selectedSemester);
            return;
        }

        System.out.println("✅ Found " + offerings.size() + " course offerings");

        // Populate table with real data
        for (CourseOffer offering : offerings) {
            Object[] row = new Object[6];

            // Get course details
            Course course = offering.getSubjectCourse();
            row[0] = course.getCOurseNumber();      // Course Number
            row[1] = course.getName();      // Course Name (we'll use number for now)
            row[2] = course.getCredits();           // Credits

            // Get faculty (if assigned)
            // Get faculty (if assigned) - with safe null checking
            try {
                info5100.university.example.Persona.Faculty.FacultyProfile universityFaculty = offering.getFacultyProfile();
                if (universityFaculty != null) {
                    Person facultyPerson = universityFaculty.getPerson();
                    if (facultyPerson != null) {
                        row[3] = facultyPerson.getFullName();
                    } else {
                        row[3] = "Not Assigned";
                    }
                } else {
                    row[3] = "Not Assigned";
                }
            } catch (NullPointerException e) {
                // Faculty assignment is null - this is expected for new course offerings
                row[3] = "Not Assigned";
            }

            // Get capacity and enrolled count
            // We need to count seats
            int capacity = 0;
            int enrolled = 0;

            // Access seats through offering
            // offering has: ArrayList<Seat> seatlist (private)
            // We'll need another getter or calculate differently
            // For now, use placeholder
            // Get capacity and enrolled count
            row[4] = offering.getCapacity();
            row[5] = offering.getEnrolledCount();

            model.addRow(row);
        }

        System.out.println("✅ Loaded " + offerings.size() + " course offerings into table");
    }
}
