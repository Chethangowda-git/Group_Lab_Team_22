package UserInterface.WorkAreas.RegistrarRole;

import Business.Business;
import Business.Profiles.RegistrarProfile;
import Business.Profiles.FacultyProfile;
import Business.Profiles.FacultyDirectory;

import info5100.university.example.Department.Department;
import info5100.university.example.CourseCatalog.Course;
import info5100.university.example.CourseCatalog.CourseCatalog;
import info5100.university.example.CourseSchedule.CourseSchedule;
import info5100.university.example.CourseSchedule.CourseOffer;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Panel to create a new course offering
 * Registrar can select course, faculty, set capacity, room, and schedule time
 * 
 * @author chethan
 */
public class CreateCourseOfferingPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    RegistrarProfile registrar;
    String selectedSemester;
    
    private JLabel lblTitle;
    private JLabel lblSemester;
    private JLabel lblSemesterValue;
    private JLabel lblCourse;
    private JComboBox<String> cmbCourse;
    private JLabel lblFaculty;
    private JComboBox<String> cmbFaculty;
    private JLabel lblCapacity;
    private JTextField txtCapacity;
    private JLabel lblRoom;
    private JTextField txtRoom;
    private JLabel lblScheduleTime;
    private JTextField txtScheduleTime;
    private JButton btnCreate;
    private JButton btnCancel;
    private JLabel lblMessage;
    
    public CreateCourseOfferingPanel(Business b, RegistrarProfile rp, JPanel clp, String semester) {
        business = b;
        registrar = rp;
        CardSequencePanel = clp;
        selectedSemester = semester;
        
        initComponents();
        loadCourses();
        loadFaculty();
    }
    
    private void initComponents() {
        lblTitle = new JLabel();
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));
        lblTitle.setText("Create New Course Offering");
        
        lblSemester = new JLabel();
        lblSemester.setFont(new java.awt.Font("Dialog", 1, 14));
        lblSemester.setText("Semester:");
        
        lblSemesterValue = new JLabel();
        lblSemesterValue.setFont(new java.awt.Font("Dialog", 0, 14));
        lblSemesterValue.setText(selectedSemester);
        
        lblCourse = new JLabel();
        lblCourse.setText("Select Course: *");
        
        cmbCourse = new JComboBox<>();
        
        lblFaculty = new JLabel();
        lblFaculty.setText("Assign Faculty (Optional):");
        
        cmbFaculty = new JComboBox<>();
        cmbFaculty.addItem("-- Not Assigned --");
        
        lblCapacity = new JLabel();
        lblCapacity.setText("Capacity (Seats): *");
        
        txtCapacity = new JTextField();
        txtCapacity.setText("30");
        
        lblRoom = new JLabel();
        lblRoom.setText("Room:");
        
        txtRoom = new JTextField();
        txtRoom.setText("TBD");
        
        lblScheduleTime = new JLabel();
        lblScheduleTime.setText("Schedule Time:");
        
        txtScheduleTime = new JTextField();
        txtScheduleTime.setText("TBD");
        
        lblMessage = new JLabel();
        lblMessage.setForeground(java.awt.Color.RED);
        
        btnCreate = new JButton();
        btnCreate.setBackground(new java.awt.Color(102, 153, 255));
        btnCreate.setForeground(new java.awt.Color(255, 255, 255));
        btnCreate.setText("Create Offering");
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });
        
        btnCancel = new JButton();
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        
        setLayout(null);
        
        add(lblTitle);
        lblTitle.setBounds(30, 20, 400, 30);
        
        add(lblSemester);
        lblSemester.setBounds(30, 70, 100, 25);
        
        add(lblSemesterValue);
        lblSemesterValue.setBounds(140, 70, 200, 25);
        
        add(lblCourse);
        lblCourse.setBounds(30, 110, 200, 25);
        
        add(cmbCourse);
        cmbCourse.setBounds(30, 140, 300, 30);
        
        add(lblFaculty);
        lblFaculty.setBounds(30, 180, 200, 25);
        
        add(cmbFaculty);
        cmbFaculty.setBounds(30, 210, 300, 30);
        
        add(lblCapacity);
        lblCapacity.setBounds(30, 250, 200, 25);
        
        add(txtCapacity);
        txtCapacity.setBounds(30, 280, 100, 30);
        
        add(lblRoom);
        lblRoom.setBounds(30, 320, 200, 25);
        
        add(txtRoom);
        txtRoom.setBounds(30, 350, 300, 30);
        
        add(lblScheduleTime);
        lblScheduleTime.setBounds(30, 390, 200, 25);
        
        add(txtScheduleTime);
        txtScheduleTime.setBounds(30, 420, 300, 30);
        
        add(lblMessage);
        lblMessage.setBounds(30, 460, 500, 25);
        
        add(btnCreate);
        btnCreate.setBounds(200, 510, 150, 35);
        
        add(btnCancel);
        btnCancel.setBounds(30, 510, 100, 35);
    }
    
    private void loadCourses() {
        Department dept = business.getDepartment();
        CourseCatalog catalog = dept.getCourseCatalog();
        ArrayList<Course> courses = catalog.getCourseList();
        
        cmbCourse.removeAllItems();
        
        for (Course course : courses) {
            String display = course.getCOurseNumber() + " - " + course.getName();
            cmbCourse.addItem(display);
        }
        
        System.out.println("Loaded " + courses.size() + " courses");
    }
    
    private void loadFaculty() {
        FacultyDirectory fd = business.getFacultyDirectory();
        ArrayList<FacultyProfile> facultyList = fd.getFacultyList();
        
        for (FacultyProfile fp : facultyList) {
            String display = fp.getPerson().getFullName();
            cmbFaculty.addItem(display);
        }
        
        System.out.println("Loaded " + facultyList.size() + " faculty");
    }
    
    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {
        lblMessage.setText("");
        
        // VALIDATE
        if (cmbCourse.getSelectedItem() == null) {
            lblMessage.setText("Please select a course!");
            return;
        }
        
        String capacityText = txtCapacity.getText().trim();
        if (capacityText.isEmpty()) {
            lblMessage.setText("Please enter capacity!");
            return;
        }
        
        int capacity;
        try {
            capacity = Integer.parseInt(capacityText);
            if (capacity <= 0) {
                lblMessage.setText("Capacity must be greater than 0!");
                return;
            }
            if (capacity > 100) {
                lblMessage.setText("Capacity cannot exceed 100!");
                return;
            }
        } catch (NumberFormatException e) {
            lblMessage.setText("Capacity must be a valid number!");
            return;
        }
        
        String room = txtRoom.getText().trim();
        String scheduleTime = txtScheduleTime.getText().trim();
        
        if (room.isEmpty()) room = "TBD";
        if (scheduleTime.isEmpty()) scheduleTime = "TBD";
        
        // EXTRACT VALUES
        String selectedCourseDisplay = (String) cmbCourse.getSelectedItem();
        String courseNumber = selectedCourseDisplay.split(" - ")[0];
        
        String selectedFacultyDisplay = (String) cmbFaculty.getSelectedItem();
        boolean hasFaculty = !selectedFacultyDisplay.equals("-- Not Assigned --");
        
        // CREATE OFFERING
        Department dept = business.getDepartment();
        CourseSchedule schedule = dept.getCourseSchedule(selectedSemester);
        
        if (schedule == null) {
            lblMessage.setText("Error: Semester schedule not found!");
            return;
        }
        
        // Check if already exists
        CourseOffer existingOffer = schedule.getCourseOfferByNumber(courseNumber);
        if (existingOffer != null) {
            lblMessage.setText("Course offering already exists for " + courseNumber + "!");
            return;
        }
        
        CourseOffer newOffering = schedule.newCourseOffer(courseNumber);
        
        if (newOffering == null) {
            lblMessage.setText("Error: Could not create offering!");
            return;
        }
        
        newOffering.generatSeats(capacity);
        newOffering.setRoom(room);
        newOffering.setScheduleTime(scheduleTime);
        
        // ASSIGN FACULTY
        if (hasFaculty) {
            FacultyDirectory fd = business.getFacultyDirectory();
            ArrayList<FacultyProfile> facultyList = fd.getFacultyList();
            
            for (FacultyProfile fp : facultyList) {
                if (fp.getPerson().getFullName().equals(selectedFacultyDisplay)) {
                    newOffering.AssignAsTeacher(fp.getUniversityProfile());
                    System.out.println("Assigned faculty: " + selectedFacultyDisplay);
                    break;
                }
            }
        }
        
        System.out.println("Created offering: " + courseNumber + " | Capacity: " + capacity);
        
        JOptionPane.showMessageDialog(this, 
            "Course offering created successfully!\n\n" +
            "Course: " + courseNumber + "\n" +
            "Capacity: " + capacity + "\n" +
            "Room: " + room + "\n" +
            "Schedule: " + scheduleTime + "\n" +
            "Faculty: " + (hasFaculty ? selectedFacultyDisplay : "Not Assigned"),
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