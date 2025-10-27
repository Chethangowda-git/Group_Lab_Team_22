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
import info5100.university.example.Persona.Person;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Course Offering Management Panel
 * Registrar can view, create, and edit course offerings
 * 
 * @author chethan
 */
public class CourseOfferingManagementPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    RegistrarProfile registrar;
    
    CourseOffer selectedCourseOffer = null;
    
    // UI Components
    private JLabel lblTitle;
    private JLabel lblSemester;
    private JComboBox<String> cmbSemester;
    private JButton btnRefresh;
    private JScrollPane scrollPane;
    private JTable tblCourseOfferings;
    private JButton btnEdit;
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
            new Object[][] {},
            new String[] {
                "Course Number", "Course Name", "Credits", "Faculty", "Capacity", "Enrolled", "Room", "Schedule Time"
            }
        ) {
            boolean[] canEdit = new boolean[] {
                false, false, false, false, false, false, false, false
            };
            
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        
        // Add selection listener
        tblCourseOfferings.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                tblCourseOfferingsSelectionChanged();
            }
        });
        
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(tblCourseOfferings);
        
        // Edit Button
        btnEdit = new JButton();
        btnEdit.setBackground(new java.awt.Color(255, 153, 51));
        btnEdit.setForeground(java.awt.Color.WHITE);
        btnEdit.setText("Edit Selected Offering");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        
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
        scrollPane.setBounds(30, 110, 900, 300);
        
        add(btnEdit);
        btnEdit.setBounds(550, 430, 180, 35);
        
        add(btnCreateNew);
        btnCreateNew.setBounds(750, 430, 180, 35);
        
        add(btnBack);
        btnBack.setBounds(30, 430, 100, 35);
    }
    
    private void loadSemesters() {
        cmbSemester.removeAllItems();
        cmbSemester.addItem("Fall2025");
        
        System.out.println("Loaded semesters into dropdown");
    }
    
    private void loadCourseOfferings() {
        String selectedSemester = (String) cmbSemester.getSelectedItem();
        
        if (selectedSemester == null || selectedSemester.isEmpty()) {
            System.out.println("No semester selected");
            return;
        }
        
        // Clear the table
        DefaultTableModel model = (DefaultTableModel) tblCourseOfferings.getModel();
        model.setRowCount(0);
        
        Department dept = business.getDepartment();
        CourseSchedule schedule = dept.getCourseSchedule(selectedSemester);
        
        if (schedule == null) {
            System.out.println("No schedule found for semester: " + selectedSemester);
            javax.swing.JOptionPane.showMessageDialog(this, 
                "No course schedule found for " + selectedSemester);
            return;
        }
        
        ArrayList<CourseOffer> offerings = schedule.getCourseOffers();
        
        if (offerings == null || offerings.isEmpty()) {
            System.out.println("No course offerings in this semester");
            return;
        }
        
        System.out.println("Found " + offerings.size() + " course offerings");
        
        // Populate table
        for (CourseOffer offering : offerings) {
            Object[] row = new Object[8];
            
            Course course = offering.getSubjectCourse();
            if (course == null) continue;
            
            row[0] = course.getCOurseNumber();
            row[1] = course.getName();
            row[2] = course.getCredits();
            
            // Get faculty
            try {
                info5100.university.example.Persona.Faculty.FacultyProfile universityFaculty = offering.getFacultyProfile();
                if (universityFaculty != null && universityFaculty.getPerson() != null) {
                    Person facultyPerson = universityFaculty.getPerson();
                    row[3] = facultyPerson.getFullName();
                } else {
                    row[3] = "Not Assigned";
                }
            } catch (NullPointerException e) {
                row[3] = "Not Assigned";
            }
            
            row[4] = offering.getCapacity();
            row[5] = offering.getEnrolledCount();
            row[6] = offering.getRoom() != null ? offering.getRoom() : "TBD";
            row[7] = offering.getScheduleTime() != null ? offering.getScheduleTime() : "TBD";
            
            model.addRow(row);
        }
        
        System.out.println("Loaded " + offerings.size() + " course offerings into table");
    }
    
    private void cmbSemesterActionPerformed(java.awt.event.ActionEvent evt) {
        loadCourseOfferings();
    }

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {
        loadCourseOfferings();
    }
    
    /**
     * Handle table selection change
     */
    private void tblCourseOfferingsSelectionChanged() {
        int selectedRow = tblCourseOfferings.getSelectedRow();
        
        if (selectedRow < 0) {
            selectedCourseOffer = null;
            return;
        }
        
        String courseNumber = (String) tblCourseOfferings.getValueAt(selectedRow, 0);
        
        // Find the CourseOffer object
        String semester = (String) cmbSemester.getSelectedItem();
        if (semester == null) return;
        
        Department dept = business.getDepartment();
        CourseSchedule schedule = dept.getCourseSchedule(semester);
        
        if (schedule != null) {
            ArrayList<CourseOffer> offerings = schedule.getCourseOffers();
            
            for (CourseOffer offer : offerings) {
                Course course = offer.getSubjectCourse();
                if (course != null && course.getCOurseNumber().equals(courseNumber)) {
                    selectedCourseOffer = offer;
                    System.out.println("Selected: " + courseNumber);
                    break;
                }
            }
        }
    }
    
    /**
     * Open Edit panel for selected course offering
     */
    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {
        if (selectedCourseOffer == null) {
            JOptionPane.showMessageDialog(this,
                "Please select a course offering from the table first!",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        EditCourseOfferingPanel panel = new EditCourseOfferingPanel(
            business, registrar, selectedCourseOffer, CardSequencePanel);
        CardSequencePanel.add("EditOffering", panel);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }

    private void btnCreateNewActionPerformed(java.awt.event.ActionEvent evt) {
        String semester = (String) cmbSemester.getSelectedItem();
        
        if (semester == null || semester.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please select a semester first!");
            return;
        }
        
        CreateCourseOfferingPanel panel = new CreateCourseOfferingPanel(business, registrar, CardSequencePanel, semester);
        CardSequencePanel.add("CreateOffering", panel);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
}