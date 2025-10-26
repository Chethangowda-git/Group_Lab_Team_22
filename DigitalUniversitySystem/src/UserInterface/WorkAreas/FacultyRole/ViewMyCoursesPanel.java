package UserInterface.WorkAreas.FacultyRole;

import Business.Business;
import Business.Profiles.FacultyProfile;

import info5100.university.example.Persona.Faculty.FacultyAssignment;
import info5100.university.example.CourseSchedule.CourseOffer;
import info5100.university.example.CourseSchedule.CourseSchedule;
import info5100.university.example.CourseCatalog.Course;
import info5100.university.example.Department.Department;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * View My Courses Panel with Mode Support Mode: MANAGE_COURSES or
 * MANAGE_STUDENTS
 */
public class ViewMyCoursesPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    FacultyProfile faculty;
    String mode; // "MANAGE_COURSES" or "MANAGE_STUDENTS"

    // UI Components
    private JLabel lblTitle;
    private JLabel lblWelcome;
    private JLabel lblInstruction;
    private JScrollPane scrollPane;
    private JTable tblCourses;
    private JButton btnRefresh;
    private JButton btnBack;

    /**
     * Constructor with mode
     *
     * @param mode "MANAGE_COURSES" or "MANAGE_STUDENTS"
     */
    public ViewMyCoursesPanel(Business b, FacultyProfile fp, JPanel clp, String mode) {
        business = b;
        faculty = fp;
        CardSequencePanel = clp;
        this.mode = mode;

        initComponents();
        loadMyCourses();
    }

    private void initComponents() {
        // Title (changes based on mode)
        lblTitle = new JLabel();
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));

        if ("MANAGE_COURSES".equals(mode)) {
            lblTitle.setText("Manage Courses");
        } else {
            lblTitle.setText("Manage Students");
        }

        // Welcome message
        lblWelcome = new JLabel();
        lblWelcome.setFont(new java.awt.Font("Dialog", 0, 14));
        lblWelcome.setText("Courses taught by: " + faculty.getPerson().getFullName());

        // Instruction (changes based on mode)
        lblInstruction = new JLabel();
        lblInstruction.setFont(new java.awt.Font("Dialog", 2, 12));
        lblInstruction.setForeground(new java.awt.Color(102, 102, 102));

        if ("MANAGE_COURSES".equals(mode)) {
            lblInstruction.setText("Double-click a course to edit course settings, syllabus, and enrollment control");
        } else {
            lblInstruction.setText("Double-click a course to view students, assign grades, and view rankings");
        }

        // Courses Table
        tblCourses = new JTable();
        tblCourses.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Course Number", "Course Name", "Credits", "Enrolled", "Capacity", "Room", "Schedule"
                }
        ) {
            boolean[] canEdit = new boolean[]{false, false, false, false, false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        // Add double-click listener
        tblCourses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    tblCoursesDoubleClicked();
                }
            }
        });

        scrollPane = new JScrollPane();
        scrollPane.setViewportView(tblCourses);

        // Refresh Button
        btnRefresh = new JButton();
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(evt -> btnRefreshActionPerformed(evt));

        // Back Button
        btnBack = new JButton();
        btnBack.setText("<< Back");
        btnBack.addActionListener(evt -> btnBackActionPerformed(evt));

        // Layout
        setLayout(null);

        add(lblTitle);
        lblTitle.setBounds(30, 20, 400, 30);

        add(lblWelcome);
        lblWelcome.setBounds(30, 60, 500, 25);

        add(lblInstruction);
        lblInstruction.setBounds(30, 85, 700, 20);

        add(scrollPane);
        scrollPane.setBounds(30, 115, 850, 285);

        add(btnRefresh);
        btnRefresh.setBounds(680, 420, 100, 35);

        add(btnBack);
        btnBack.setBounds(30, 420, 100, 35);
    }

    private void loadMyCourses() {
        DefaultTableModel model = (DefaultTableModel) tblCourses.getModel();
        model.setRowCount(0);

        System.out.println("Loading courses for: " + faculty.getPerson().getFullName() + " (Mode: " + mode + ")");

        Department dept = business.getDepartment();
        CourseSchedule schedule = dept.getCourseSchedule("Fall2025");

        if (schedule == null) {
            System.out.println("⚠️ No schedule found");
            return;
        }

        ArrayList<CourseOffer> allOfferings = schedule.getCourseOffers();
        int coursesFound = 0;

        for (CourseOffer offer : allOfferings) {
            try {
                info5100.university.example.Persona.Faculty.FacultyProfile assignedFaculty
                        = offer.getFacultyProfile();

                if (assignedFaculty != null) {
                    info5100.university.example.Persona.Person assignedPerson = assignedFaculty.getPerson();

                    if (assignedPerson != null
                            && assignedPerson.getPersonId().equals(faculty.getPerson().getPersonId())) {

                        Object[] row = new Object[7];

                        Course course = offer.getSubjectCourse();
                        row[0] = course.getCOurseNumber();
                        row[1] = course.getName();
                        row[2] = course.getCredits();
                        row[3] = offer.getEnrolledCount();
                        row[4] = offer.getCapacity();
                        row[5] = offer.getRoom() != null ? offer.getRoom() : "TBD";
                        row[6] = offer.getScheduleTime() != null ? offer.getScheduleTime() : "TBD";

                        model.addRow(row);
                        coursesFound++;
                    }
                }
            } catch (NullPointerException e) {
                // Skip
            }
        }

        if (coursesFound == 0) {
            Object[] row = new Object[7];
            row[0] = "No courses assigned";
            row[1] = "This faculty member is not teaching any courses";
            model.addRow(row);
        }

        System.out.println("✅ Found " + coursesFound + " courses");
    }

    private void tblCoursesDoubleClicked() {
        int selectedRow = tblCourses.getSelectedRow();

        if (selectedRow < 0) {
            return;
        }

        String courseNumber = (String) tblCourses.getValueAt(selectedRow, 0);

        if ("No courses assigned".equals(courseNumber)) {
            return;
        }

        Department dept = business.getDepartment();
        CourseSchedule schedule = dept.getCourseSchedule("Fall2025");

        if (schedule == null) {
            return;
        }

        CourseOffer selectedOffer = schedule.getCourseOfferByNumber(courseNumber);

        if (selectedOffer == null) {
            JOptionPane.showMessageDialog(this, "Course offer not found!");
            return;
        }

        // DIFFERENT BEHAVIOR BASED ON MODE!
        if ("MANAGE_COURSES".equals(mode)) {
            // Open Edit Course Details Panel (to be created)
            EditCourseDetailsPanel panel = new EditCourseDetailsPanel(business, faculty, selectedOffer, CardSequencePanel);
            CardSequencePanel.add("EditCourse", panel);
            ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
        } else {
            // Open Manage Course Details Panel (student grading)
            ManageCourseDetailsPanel panel = new ManageCourseDetailsPanel(business, faculty, selectedOffer, CardSequencePanel);
            CardSequencePanel.add("ManageCourseDetails", panel);
            ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
        }
    }

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {
        loadMyCourses();
    }

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
}
