package UserInterface.WorkAreas.FacultyRole;

import Business.Business;
import Business.Profiles.FacultyProfile;

import info5100.university.example.CourseSchedule.CourseOffer;
import info5100.university.example.CourseCatalog.Course;

import javax.swing.*;

/**
 * Edit Course Details Panel
 * Faculty can update course title, description, schedule, capacity, syllabus, and enrollment
 * 
 * @author chethan
 */
public class EditCourseDetailsPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    FacultyProfile faculty;
    CourseOffer courseOffer;
    
    private JLabel lblTitle;
    private JLabel lblCourseNumber;
    private JTextField txtCourseNumber;
    private JLabel lblCourseName;
    private JTextField txtCourseName;
    private JLabel lblCredits;
    private JTextField txtCredits;
    private JLabel lblEnrolled;
    private JTextField txtEnrolled;
    private JLabel lblCapacity;
    private JTextField txtCapacity;
    private JLabel lblRoom;
    private JTextField txtRoom;
    private JLabel lblScheduleTime;
    private JTextField txtScheduleTime;
    private JLabel lblDescription;
    private JTextArea txtDescription;
    private JScrollPane scrollDescription;
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
        lblTitle = new JLabel();
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));
        lblTitle.setText("Edit Course Details");
        
        lblCourseNumber = new JLabel();
        lblCourseNumber.setText("Course Number:");
        txtCourseNumber = new JTextField();
        txtCourseNumber.setEditable(false);
        txtCourseNumber.setBackground(java.awt.Color.LIGHT_GRAY);
        
        lblCourseName = new JLabel();
        lblCourseName.setText("Course Title:");
        txtCourseName = new JTextField();
        txtCourseName.setEditable(false);
        txtCourseName.setBackground(java.awt.Color.LIGHT_GRAY);
        txtCourseName.setToolTipText("Course title is defined in the catalog and cannot be changed here");
        
        lblCredits = new JLabel();
        lblCredits.setText("Credits:");
        txtCredits = new JTextField();
        txtCredits.setEditable(false);
        txtCredits.setBackground(java.awt.Color.LIGHT_GRAY);
        
        lblEnrolled = new JLabel();
        lblEnrolled.setText("Currently Enrolled:");
        txtEnrolled = new JTextField();
        txtEnrolled.setEditable(false);
        txtEnrolled.setBackground(java.awt.Color.LIGHT_GRAY);
        
        lblCapacity = new JLabel();
        lblCapacity.setText("Capacity: *");
        txtCapacity = new JTextField();
        
        lblRoom = new JLabel();
        lblRoom.setText("Room:");
        txtRoom = new JTextField();
        
        lblScheduleTime = new JLabel();
        lblScheduleTime.setText("Schedule Time: *");
        txtScheduleTime = new JTextField();
        
        lblDescription = new JLabel();
        lblDescription.setText("Course Description: *");
        txtDescription = new JTextArea(3, 30);
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        scrollDescription = new JScrollPane(txtDescription);
        
        lblSyllabus = new JLabel();
        lblSyllabus.setText("Detailed Syllabus:");
        txtSyllabus = new JTextArea(5, 30);
        txtSyllabus.setLineWrap(true);
        txtSyllabus.setWrapStyleWord(true);
        scrollSyllabus = new JScrollPane(txtSyllabus);
        
        lblEnrollmentStatus = new JLabel();
        lblEnrollmentStatus.setText("Enrollment Control:");
        chkEnrollmentOpen = new JCheckBox("Enrollment Open for Students");
        chkEnrollmentOpen.setSelected(true);
        
        lblMessage = new JLabel();
        lblMessage.setForeground(java.awt.Color.BLUE);
        
        btnSave = new JButton();
        btnSave.setBackground(new java.awt.Color(102, 153, 255));
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("Save Changes");
        btnSave.addActionListener(evt -> btnSaveActionPerformed(evt));
        
        btnCancel = new JButton();
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(evt -> btnCancelActionPerformed(evt));
        
        setLayout(null);
        
        add(lblTitle);
        lblTitle.setBounds(30, 20, 400, 30);
        
        add(lblCourseNumber);
        lblCourseNumber.setBounds(30, 70, 150, 25);
        add(txtCourseNumber);
        txtCourseNumber.setBounds(200, 70, 200, 30);
        
        add(lblCourseName);
        lblCourseName.setBounds(30, 110, 150, 25);
        add(txtCourseName);
        txtCourseName.setBounds(200, 110, 400, 30);
        
        add(lblCredits);
        lblCredits.setBounds(30, 150, 100, 25);
        add(txtCredits);
        txtCredits.setBounds(200, 150, 80, 30);
        
        add(lblEnrolled);
        lblEnrolled.setBounds(310, 150, 150, 25);
        add(txtEnrolled);
        txtEnrolled.setBounds(480, 150, 80, 30);
        
        add(lblCapacity);
        lblCapacity.setBounds(30, 190, 150, 25);
        add(txtCapacity);
        txtCapacity.setBounds(200, 190, 100, 30);
        
        add(lblRoom);
        lblRoom.setBounds(30, 230, 150, 25);
        add(txtRoom);
        txtRoom.setBounds(200, 230, 300, 30);
        
        add(lblScheduleTime);
        lblScheduleTime.setBounds(30, 270, 150, 25);
        add(txtScheduleTime);
        txtScheduleTime.setBounds(200, 270, 400, 30);
        
        add(lblDescription);
        lblDescription.setBounds(30, 310, 200, 25);
        add(scrollDescription);
        scrollDescription.setBounds(30, 340, 600, 80);
        
        add(lblSyllabus);
        lblSyllabus.setBounds(30, 430, 200, 25);
        add(scrollSyllabus);
        scrollSyllabus.setBounds(30, 460, 600, 120);
        
        add(lblEnrollmentStatus);
        lblEnrollmentStatus.setBounds(30, 590, 150, 25);
        add(chkEnrollmentOpen);
        chkEnrollmentOpen.setBounds(200, 590, 250, 25);
        
        add(lblMessage);
        lblMessage.setBounds(30, 625, 600, 25);
        
        add(btnSave);
        btnSave.setBounds(430, 660, 150, 35);
        
        add(btnCancel);
        btnCancel.setBounds(30, 660, 100, 35);
    }
    
    private void loadCourseData() {
        Course course = courseOffer.getSubjectCourse();
        
        txtCourseNumber.setText(course.getCOurseNumber());
        txtCourseName.setText(course.getName());
        txtCredits.setText(String.valueOf(course.getCredits()));
        txtEnrolled.setText(String.valueOf(courseOffer.getEnrolledCount()));
        txtCapacity.setText(String.valueOf(courseOffer.getCapacity()));
        txtRoom.setText(courseOffer.getRoom() != null ? courseOffer.getRoom() : "");
        txtScheduleTime.setText(courseOffer.getScheduleTime() != null ? courseOffer.getScheduleTime() : "");
        
        // Load description (from syllabus or create default)
        String syllabus = courseOffer.getSyllabus();
        if (syllabus != null && !syllabus.isEmpty()) {
            // Try to extract description (first 200 chars) and full syllabus
            if (syllabus.length() > 200) {
                txtDescription.setText(syllabus.substring(0, 200));
                txtSyllabus.setText(syllabus);
            } else {
                txtDescription.setText(syllabus);
                txtSyllabus.setText(syllabus);
            }
        } else {
            txtDescription.setText("Course description not yet provided.");
            txtSyllabus.setText("");
        }
        
        chkEnrollmentOpen.setSelected(courseOffer.isEnrollmentOpen());
    }
    
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {
        lblMessage.setText("");
        
        // VALIDATION
        
        // 1. Validate Description
        String description = txtDescription.getText().trim();
        if (description.isEmpty()) {
            lblMessage.setText("Course description is required!");
            lblMessage.setForeground(java.awt.Color.RED);
            return;
        }
        
        // 2. Validate Schedule
        String scheduleTime = txtScheduleTime.getText().trim();
        if (scheduleTime.isEmpty()) {
            lblMessage.setText("Schedule time is required!");
            lblMessage.setForeground(java.awt.Color.RED);
            return;
        }
        
        // 3. Validate Capacity
        String capacityText = txtCapacity.getText().trim();
        if (capacityText.isEmpty()) {
            lblMessage.setText("Capacity is required!");
            lblMessage.setForeground(java.awt.Color.RED);
            return;
        }
        
        int newCapacity;
        try {
            newCapacity = Integer.parseInt(capacityText);
            if (newCapacity <= 0) {
                lblMessage.setText("Capacity must be greater than 0!");
                lblMessage.setForeground(java.awt.Color.RED);
                return;
            }
        } catch (NumberFormatException e) {
            lblMessage.setText("Capacity must be a valid number!");
            lblMessage.setForeground(java.awt.Color.RED);
            return;
        }
        
        // 4. Check if new capacity is less than currently enrolled
        int enrolled = courseOffer.getEnrolledCount();
        if (newCapacity < enrolled) {
            lblMessage.setText("Cannot reduce capacity below enrolled count (" + enrolled + ")!");
            lblMessage.setForeground(java.awt.Color.RED);
            JOptionPane.showMessageDialog(this,
                "Cannot reduce capacity!\n\n" +
                "Current Enrolled: " + enrolled + "\n" +
                "New Capacity: " + newCapacity + "\n\n" +
                "New capacity must be >= enrolled students.",
                "Invalid Capacity",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // SAVE CHANGES
        
        // Update capacity if changed
        int currentCapacity = courseOffer.getCapacity();
        if (newCapacity != currentCapacity) {
            courseOffer.generatSeats(newCapacity);
            System.out.println("Updated capacity: " + currentCapacity + " -> " + newCapacity);
        }
        
        // Update room and schedule
        courseOffer.setRoom(txtRoom.getText().trim());
        courseOffer.setScheduleTime(scheduleTime);
        
        // Update syllabus (combine description + detailed syllabus)
        String syllabus = txtSyllabus.getText().trim();
        String fullSyllabus;
        
        if (syllabus.isEmpty()) {
            // Just description if no detailed syllabus
            fullSyllabus = description;
        } else {
            // Combine description + detailed syllabus
            fullSyllabus = "COURSE DESCRIPTION:\n" + description + "\n\n" +
                          "DETAILED SYLLABUS:\n" + syllabus;
        }
        
        courseOffer.setSyllabus(fullSyllabus);
        
        // Update enrollment status
        courseOffer.setEnrollmentOpen(chkEnrollmentOpen.isSelected());
        
        System.out.println("Updated course: " + courseOffer.getCourseNumber());
        System.out.println("   Description: " + description.substring(0, Math.min(50, description.length())) + "...");
        System.out.println("   Capacity: " + newCapacity);
        System.out.println("   Schedule: " + scheduleTime);
        System.out.println("   Enrollment: " + (chkEnrollmentOpen.isSelected() ? "Open" : "Closed"));
        
        lblMessage.setText("Course details updated successfully!");
        lblMessage.setForeground(new java.awt.Color(0, 128, 0));
        
        JOptionPane.showMessageDialog(this,
            "Course details updated successfully!\n\n" +
            "Title: " + txtCourseName.getText() + " (view-only)\n" +
            "Description: Updated\n" +
            "Schedule: " + scheduleTime + "\n" +
            "Capacity: " + newCapacity + "\n" +
            "Syllabus: Updated\n" +
            "Enrollment: " + (chkEnrollmentOpen.isSelected() ? "Open" : "Closed"),
            "Success",
            JOptionPane.INFORMATION_MESSAGE);
        
        // Go back
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
    
    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
}
