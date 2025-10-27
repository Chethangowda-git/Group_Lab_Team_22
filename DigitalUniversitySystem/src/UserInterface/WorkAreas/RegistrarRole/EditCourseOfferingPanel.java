package UserInterface.WorkAreas.RegistrarRole;

import Business.Business;
import Business.Profiles.RegistrarProfile;
import Business.Profiles.FacultyProfile;
import Business.Profiles.FacultyDirectory;

import info5100.university.example.CourseSchedule.CourseOffer;
import info5100.university.example.CourseCatalog.Course;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Edit Course Offering Panel
 * Registrar can edit capacity, room, schedule time, and reassign faculty
 * 
 * @author chethan
 */
public class EditCourseOfferingPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    RegistrarProfile registrar;
    CourseOffer courseOffer;
    
    private JLabel lblTitle;
    private JLabel lblCourse;
    private JTextField txtCourse;
    private JLabel lblSemester;
    private JTextField txtSemester;
    private JLabel lblCurrentCapacity;
    private JTextField txtCurrentCapacity;
    private JLabel lblNewCapacity;
    private JTextField txtNewCapacity;
    private JLabel lblRoom;
    private JTextField txtRoom;
    private JLabel lblScheduleTime;
    private JTextField txtScheduleTime;
    private JLabel lblCurrentFaculty;
    private JTextField txtCurrentFaculty;
    private JLabel lblNewFaculty;
    private JComboBox<String> cmbFaculty;
    private JLabel lblEnrolled;
    private JTextField txtEnrolled;
    private JLabel lblMessage;
    private JButton btnSave;
    private JButton btnCancel;
    
    public EditCourseOfferingPanel(Business b, RegistrarProfile rp, CourseOffer co, JPanel clp) {
        business = b;
        registrar = rp;
        courseOffer = co;
        CardSequencePanel = clp;
        
        initComponents();
        loadFaculty();
        loadOfferingData();
    }
    
    private void initComponents() {
        lblTitle = new JLabel();
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));
        lblTitle.setText("Edit Course Offering");
        
        lblCourse = new JLabel("Course:");
        txtCourse = new JTextField();
        txtCourse.setEditable(false);
        txtCourse.setBackground(java.awt.Color.LIGHT_GRAY);
        
        lblSemester = new JLabel("Semester:");
        txtSemester = new JTextField();
        txtSemester.setEditable(false);
        txtSemester.setBackground(java.awt.Color.LIGHT_GRAY);
        
        lblCurrentCapacity = new JLabel("Current Capacity:");
        txtCurrentCapacity = new JTextField();
        txtCurrentCapacity.setEditable(false);
        txtCurrentCapacity.setBackground(java.awt.Color.LIGHT_GRAY);
        
        lblNewCapacity = new JLabel("New Capacity: *");
        txtNewCapacity = new JTextField();
        
        lblEnrolled = new JLabel("Currently Enrolled:");
        txtEnrolled = new JTextField();
        txtEnrolled.setEditable(false);
        txtEnrolled.setBackground(java.awt.Color.LIGHT_GRAY);
        
        lblRoom = new JLabel("Room:");
        txtRoom = new JTextField();
        
        lblScheduleTime = new JLabel("Schedule Time:");
        txtScheduleTime = new JTextField();
        
        lblCurrentFaculty = new JLabel("Current Faculty:");
        txtCurrentFaculty = new JTextField();
        txtCurrentFaculty.setEditable(false);
        txtCurrentFaculty.setBackground(java.awt.Color.LIGHT_GRAY);
        
        lblNewFaculty = new JLabel("Reassign Faculty:");
        cmbFaculty = new JComboBox<>();
        cmbFaculty.addItem("-- Keep Current --");
        
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
        
        add(lblCourse);
        lblCourse.setBounds(30, 70, 150, 25);
        add(txtCourse);
        txtCourse.setBounds(200, 70, 350, 30);
        
        add(lblSemester);
        lblSemester.setBounds(30, 110, 150, 25);
        add(txtSemester);
        txtSemester.setBounds(200, 110, 150, 30);
        
        add(lblCurrentCapacity);
        lblCurrentCapacity.setBounds(30, 150, 150, 25);
        add(txtCurrentCapacity);
        txtCurrentCapacity.setBounds(200, 150, 100, 30);
        
        add(lblEnrolled);
        lblEnrolled.setBounds(320, 150, 150, 25);
        add(txtEnrolled);
        txtEnrolled.setBounds(480, 150, 100, 30);
        
        add(lblNewCapacity);
        lblNewCapacity.setBounds(30, 190, 150, 25);
        add(txtNewCapacity);
        txtNewCapacity.setBounds(200, 190, 100, 30);
        
        add(lblRoom);
        lblRoom.setBounds(30, 230, 150, 25);
        add(txtRoom);
        txtRoom.setBounds(200, 230, 350, 30);
        
        add(lblScheduleTime);
        lblScheduleTime.setBounds(30, 270, 150, 25);
        add(txtScheduleTime);
        txtScheduleTime.setBounds(200, 270, 350, 30);
        
        add(lblCurrentFaculty);
        lblCurrentFaculty.setBounds(30, 310, 150, 25);
        add(txtCurrentFaculty);
        txtCurrentFaculty.setBounds(200, 310, 350, 30);
        
        add(lblNewFaculty);
        lblNewFaculty.setBounds(30, 350, 150, 25);
        add(cmbFaculty);
        cmbFaculty.setBounds(200, 350, 350, 30);
        
        add(lblMessage);
        lblMessage.setBounds(30, 390, 550, 25);
        
        add(btnSave);
        btnSave.setBounds(350, 430, 150, 35);
        
        add(btnCancel);
        btnCancel.setBounds(30, 430, 100, 35);
    }
    
    private void loadOfferingData() {
        Course course = courseOffer.getSubjectCourse();
        
        txtCourse.setText(course.getCOurseNumber() + " - " + course.getName());
        txtSemester.setText("Fall2025"); // Get from course offer if available
        txtCurrentCapacity.setText(String.valueOf(courseOffer.getCapacity()));
        txtNewCapacity.setText(String.valueOf(courseOffer.getCapacity()));
        txtEnrolled.setText(String.valueOf(courseOffer.getEnrolledCount()));
        txtRoom.setText(courseOffer.getRoom() != null ? courseOffer.getRoom() : "");
        txtScheduleTime.setText(courseOffer.getScheduleTime() != null ? courseOffer.getScheduleTime() : "");
        
        // Current faculty
        info5100.university.example.Persona.Faculty.FacultyProfile teacher = courseOffer.getFacultyProfile();
        if (teacher != null && teacher.getPerson() != null) {
            txtCurrentFaculty.setText(teacher.getPerson().getFullName());
        } else {
            txtCurrentFaculty.setText("Not Assigned");
        }
    }
    
    private void loadFaculty() {
        FacultyDirectory fd = business.getFacultyDirectory();
        ArrayList<FacultyProfile> facultyList = fd.getFacultyList();
        
        for (FacultyProfile fp : facultyList) {
            if (fp.getPerson() != null) {
                String displayName = fp.getPerson().getFullName() + " (" + fp.getPerson().getPersonId() + ")";
                cmbFaculty.addItem(displayName);
            }
        }
    }
    
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {
        lblMessage.setText("");
        
        // VALIDATION
        String newCapacityText = txtNewCapacity.getText().trim();
        if (newCapacityText.isEmpty()) {
            lblMessage.setText("Capacity is required!");
            lblMessage.setForeground(java.awt.Color.RED);
            return;
        }
        
        int newCapacity;
        try {
            newCapacity = Integer.parseInt(newCapacityText);
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
        
        // Check if new capacity is less than currently enrolled
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
        
        // UPDATE CAPACITY
        int currentCapacity = courseOffer.getCapacity();
        if (newCapacity != currentCapacity) {
            // Regenerate seats with new capacity
            courseOffer.generatSeats(newCapacity);
            System.out.println("Updated capacity: " + currentCapacity + " -> " + newCapacity);
        }
        
        // UPDATE ROOM
        String room = txtRoom.getText().trim();
        courseOffer.setRoom(room.isEmpty() ? "TBD" : room);
        
        // UPDATE SCHEDULE TIME
        String scheduleTime = txtScheduleTime.getText().trim();
        courseOffer.setScheduleTime(scheduleTime.isEmpty() ? "TBD" : scheduleTime);
        
        // UPDATE FACULTY (if changed)
        int facultyIndex = cmbFaculty.getSelectedIndex();
        if (facultyIndex > 0) { // 0 is "-- Keep Current --"
            String selectedText = (String) cmbFaculty.getSelectedItem();
            String facultyId = selectedText.substring(selectedText.indexOf("(") + 1, selectedText.indexOf(")"));
            
            FacultyDirectory fd = business.getFacultyDirectory();
            FacultyProfile faculty = fd.findFaculty(facultyId);
            
            if (faculty != null) {
                courseOffer.AssignAsTeacher(faculty.getUniversityProfile());
                System.out.println("Reassigned faculty: " + faculty.getPerson().getFullName());
            }
        }
        
        JOptionPane.showMessageDialog(this,
            "Course offering updated successfully!\n\n" +
            "Course: " + courseOffer.getSubjectCourse().getCOurseNumber() + "\n" +
            "Capacity: " + newCapacity + "\n" +
            "Room: " + (room.isEmpty() ? "TBD" : room) + "\n" +
            "Schedule: " + (scheduleTime.isEmpty() ? "TBD" : scheduleTime),
            "Success",
            JOptionPane.INFORMATION_MESSAGE);
        
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
    
    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
}