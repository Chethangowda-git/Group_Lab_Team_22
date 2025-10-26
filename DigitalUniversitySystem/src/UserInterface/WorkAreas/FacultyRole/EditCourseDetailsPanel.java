package UserInterface.WorkAreas.FacultyRole;

import Business.Business;
import Business.Profiles.FacultyProfile;

import info5100.university.example.CourseSchedule.CourseOffer;
import info5100.university.example.CourseCatalog.Course;

import javax.swing.*;

/**
 * Edit Course Details Panel
 * Faculty can update syllabus, open/close enrollment, edit course info
 */
public class EditCourseDetailsPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    FacultyProfile faculty;
    CourseOffer courseOffer;
    
    // UI Components
    private JLabel lblTitle;
    private JLabel lblCourseNumber;
    private JTextField txtCourseNumber;
    private JLabel lblCourseName;
    private JTextField txtCourseName;
    private JLabel lblCredits;
    private JTextField txtCredits;
    private JLabel lblCapacity;
    private JTextField txtCapacity;
    private JLabel lblRoom;
    private JTextField txtRoom;
    private JLabel lblScheduleTime;
    private JTextField txtScheduleTime;
    private JLabel lblSyllabus;
    private JTextArea txtSyllabus;
    private JScrollPane scrollSyllabus;
    private JLabel lblEnrollmentStatus;
    private JCheckBox chkEnrollmentOpen;
    private JLabel lblMessage;
    private JButton btnSave;
    private JButton btnCancel;
    
    public EditCourseDetailsPanel(Business b, FacultyProfile fp, CourseOffer co, JPanel clp) {
        business = b;
        faculty = fp;
        courseOffer = co;
        CardSequencePanel = clp;
        
        initComponents();
        loadCourseData();
    }
    
    private void initComponents() {
        // Title
        lblTitle = new JLabel();
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));
        lblTitle.setText("Edit Course Details");
        
        // Course Number (Read-only)
        lblCourseNumber = new JLabel();
        lblCourseNumber.setText("Course Number:");
        txtCourseNumber = new JTextField();
        txtCourseNumber.setEditable(false);
        txtCourseNumber.setBackground(java.awt.Color.LIGHT_GRAY);
        
        // Course Name (Read-only for now)
        lblCourseName = new JLabel();
        lblCourseName.setText("Course Name:");
        txtCourseName = new JTextField();
        txtCourseName.setEditable(false);
        txtCourseName.setBackground(java.awt.Color.LIGHT_GRAY);
        
        // Credits (Read-only)
        lblCredits = new JLabel();
        lblCredits.setText("Credits:");
        txtCredits = new JTextField();
        txtCredits.setEditable(false);
        txtCredits.setBackground(java.awt.Color.LIGHT_GRAY);
        
        // Capacity (Read-only - set by Registrar)
        lblCapacity = new JLabel();
        lblCapacity.setText("Capacity:");
        txtCapacity = new JTextField();
        txtCapacity.setEditable(false);
        txtCapacity.setBackground(java.awt.Color.LIGHT_GRAY);
        
        // Room
        lblRoom = new JLabel();
        lblRoom.setText("Room:");
        txtRoom = new JTextField();
        
        // Schedule Time
        lblScheduleTime = new JLabel();
        lblScheduleTime.setText("Schedule Time:");
        txtScheduleTime = new JTextField();
        
        // Syllabus
        lblSyllabus = new JLabel();
        lblSyllabus.setText("Course Syllabus:");
        txtSyllabus = new JTextArea(5, 30);
        txtSyllabus.setLineWrap(true);
        txtSyllabus.setWrapStyleWord(true);
        scrollSyllabus = new JScrollPane(txtSyllabus);
        
        // Enrollment Status
        lblEnrollmentStatus = new JLabel();
        lblEnrollmentStatus.setText("Enrollment Control:");
        chkEnrollmentOpen = new JCheckBox("Enrollment Open for Students");
        chkEnrollmentOpen.setSelected(true);
        
        // Message
        lblMessage = new JLabel();
        lblMessage.setForeground(java.awt.Color.BLUE);
        
        // Buttons
        btnSave = new JButton();
        btnSave.setBackground(new java.awt.Color(102, 153, 255));
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("Save Changes");
        btnSave.addActionListener(evt -> btnSaveActionPerformed(evt));
        
        btnCancel = new JButton();
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(evt -> btnCancelActionPerformed(evt));
        
        // Layout
        setLayout(null);
        
        add(lblTitle);
        lblTitle.setBounds(30, 20, 400, 30);
        
        add(lblCourseNumber);
        lblCourseNumber.setBounds(30, 70, 150, 25);
        add(txtCourseNumber);
        txtCourseNumber.setBounds(200, 70, 250, 30);
        
        add(lblCourseName);
        lblCourseName.setBounds(30, 110, 150, 25);
        add(txtCourseName);
        txtCourseName.setBounds(200, 110, 400, 30);
        
        add(lblCredits);
        lblCredits.setBounds(30, 150, 150, 25);
        add(txtCredits);
        txtCredits.setBounds(200, 150, 100, 30);
        
        add(lblCapacity);
        lblCapacity.setBounds(330, 150, 100, 25);
        add(txtCapacity);
        txtCapacity.setBounds(430, 150, 100, 30);
        
        add(lblRoom);
        lblRoom.setBounds(30, 190, 150, 25);
        add(txtRoom);
        txtRoom.setBounds(200, 190, 250, 30);
        
        add(lblScheduleTime);
        lblScheduleTime.setBounds(30, 230, 150, 25);
        add(txtScheduleTime);
        txtScheduleTime.setBounds(200, 230, 400, 30);
        
        add(lblSyllabus);
        lblSyllabus.setBounds(30, 270, 200, 25);
        add(scrollSyllabus);
        scrollSyllabus.setBounds(30, 300, 600, 120);
        
        add(lblEnrollmentStatus);
        lblEnrollmentStatus.setBounds(30, 430, 150, 25);
        add(chkEnrollmentOpen);
        chkEnrollmentOpen.setBounds(200, 430, 250, 25);
        
        add(lblMessage);
        lblMessage.setBounds(30, 465, 600, 25);
        
        add(btnSave);
        btnSave.setBounds(430, 500, 150, 35);
        
        add(btnCancel);
        btnCancel.setBounds(30, 500, 100, 35);
    }
    
    private void loadCourseData() {
        Course course = courseOffer.getSubjectCourse();
        
        txtCourseNumber.setText(course.getCOurseNumber());
        txtCourseName.setText(course.getName());
        txtCredits.setText(String.valueOf(course.getCredits()));
        txtCapacity.setText(String.valueOf(courseOffer.getCapacity()));
        txtRoom.setText(courseOffer.getRoom() != null ? courseOffer.getRoom() : "");
        txtScheduleTime.setText(courseOffer.getScheduleTime() != null ? courseOffer.getScheduleTime() : "");
        txtSyllabus.setText(courseOffer.getSyllabus() != null ? courseOffer.getSyllabus() : "");
        chkEnrollmentOpen.setSelected(courseOffer.isEnrollmentOpen());
    }
    
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {
        lblMessage.setText("");
        
        // Validate syllabus
        String syllabus = txtSyllabus.getText().trim();
        if (syllabus.isEmpty()) {
            lblMessage.setText("⚠️ Syllabus cannot be empty!");
            lblMessage.setForeground(java.awt.Color.RED);
            return;
        }
        
        // Save changes
        courseOffer.setRoom(txtRoom.getText().trim());
        courseOffer.setScheduleTime(txtScheduleTime.getText().trim());
        courseOffer.setSyllabus(syllabus);
        courseOffer.setEnrollmentOpen(chkEnrollmentOpen.isSelected());
        
        System.out.println("✅ Updated course: " + courseOffer.getCourseNumber());
        System.out.println("   Syllabus: " + syllabus.substring(0, Math.min(50, syllabus.length())) + "...");
        System.out.println("   Enrollment: " + (chkEnrollmentOpen.isSelected() ? "Open" : "Closed"));
        
        lblMessage.setText("✅ Course details updated successfully!");
        lblMessage.setForeground(new java.awt.Color(0, 128, 0));
        
        JOptionPane.showMessageDialog(this,
            "Course details updated successfully!\n\n" +
            "Syllabus updated\n" +
            "Enrollment: " + (chkEnrollmentOpen.isSelected() ? "Open" : "Closed"),
            "Success",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
}