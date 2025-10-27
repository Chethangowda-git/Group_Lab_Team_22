package UserInterface.WorkAreas.FacultyRole;

import Business.Business;
import Business.Profiles.FacultyProfile;
import Business.Profiles.StudentDirectory;
import Business.Profiles.StudentProfile;

import info5100.university.example.CourseSchedule.CourseOffer;
import info5100.university.example.CourseSchedule.Seat;
import info5100.university.example.CourseSchedule.SeatAssignment;
import info5100.university.example.CourseCatalog.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * Manage Course Details Panel 
 * Faculty can view enrolled students and assign grades
 */
public class ManageCourseDetailsPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    FacultyProfile faculty;
    CourseOffer courseOffer;

    SeatAssignment selectedSeatAssignment = null;
    StudentProfile selectedStudent = null;

    // UI Components
    private JLabel lblTitle;
    private JLabel lblCourseInfo;
    private JLabel lblClassGPA;
    private JLabel lblEnrolledStudents;
    private JScrollPane scrollPane;
    private JTable tblStudents;
    private JButton btnGradeStudent;
    private JButton btnViewProgress;
    private JButton btnViewTranscript;
    private JButton btnViewRankings;
    private JButton btnRefresh;
    private JButton btnBack;

    public ManageCourseDetailsPanel(Business b, FacultyProfile fp, CourseOffer co, JPanel clp) {
        business = b;
        faculty = fp;
        courseOffer = co;
        CardSequencePanel = clp;

        initComponents();
        loadEnrolledStudents();
    }

    private void initComponents() {
        // Title
        lblTitle = new JLabel();
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));
        Course course = courseOffer.getSubjectCourse();
        lblTitle.setText("Manage: " + course.getCOurseNumber() + " - " + course.getName());

        // Course Info
        lblCourseInfo = new JLabel();
        lblCourseInfo.setFont(new java.awt.Font("Dialog", 0, 14));
        lblCourseInfo.setText(String.format("Credits: %d | Capacity: %d | Enrolled: %d",
                course.getCredits(), courseOffer.getCapacity(), courseOffer.getEnrolledCount()));

        // Class GPA
        lblClassGPA = new JLabel();
        lblClassGPA.setFont(new java.awt.Font("Dialog", 1, 14));
        lblClassGPA.setForeground(new java.awt.Color(0, 102, 204));
        lblClassGPA.setText("Class GPA: N/A");

        // Enrolled Students Label
        lblEnrolledStudents = new JLabel();
        lblEnrolledStudents.setFont(new java.awt.Font("Dialog", 1, 14));
        lblEnrolledStudents.setText("Enrolled Students:");

        // Students Table
        tblStudents = new JTable();
        tblStudents.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Student Name", "Student ID", "Letter Grade", "Grade Points", "Quality Points", "Rank"
                }
        ) {
            boolean[] canEdit = new boolean[]{false, false, false, false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        tblStudents.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                tblStudentsSelectionChanged();
            }
        });

        scrollPane = new JScrollPane();
        scrollPane.setViewportView(tblStudents);

        // Buttons
        btnGradeStudent = new JButton();
        btnGradeStudent.setBackground(new java.awt.Color(102, 153, 255));
        btnGradeStudent.setForeground(new java.awt.Color(255, 255, 255));
        btnGradeStudent.setText("Assign Grade");
        btnGradeStudent.addActionListener(evt -> btnGradeStudentActionPerformed(evt));

        btnViewProgress = new JButton();
        btnViewProgress.setBackground(new java.awt.Color(51, 153, 102));
        btnViewProgress.setForeground(new java.awt.Color(255, 255, 255));
        btnViewProgress.setText("View Progress");
        btnViewProgress.addActionListener(evt -> btnViewProgressActionPerformed(evt));

        btnViewTranscript = new JButton();
        btnViewTranscript.setBackground(new java.awt.Color(51, 102, 153));
        btnViewTranscript.setForeground(new java.awt.Color(255, 255, 255));
        btnViewTranscript.setText("View Transcript");
        btnViewTranscript.addActionListener(evt -> btnViewTranscriptActionPerformed(evt));

        btnViewRankings = new JButton();
        btnViewRankings.setText("View Rankings");
        btnViewRankings.addActionListener(evt -> btnViewRankingsActionPerformed(evt));

        btnRefresh = new JButton();
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(evt -> btnRefreshActionPerformed(evt));

        btnBack = new JButton();
        btnBack.setText("<< Back");
        btnBack.addActionListener(evt -> btnBackActionPerformed(evt));

        // Layout
        setLayout(null);

        add(lblTitle);
        lblTitle.setBounds(30, 20, 700, 30);

        add(lblCourseInfo);
        lblCourseInfo.setBounds(30, 60, 500, 25);

        add(lblClassGPA);
        lblClassGPA.setBounds(550, 60, 250, 25);

        add(lblEnrolledStudents);
        lblEnrolledStudents.setBounds(30, 100, 300, 25);

        add(scrollPane);
        scrollPane.setBounds(30, 130, 850, 300);

        add(btnGradeStudent);
        btnGradeStudent.setBounds(280, 450, 130, 35);

        add(btnViewProgress);
        btnViewProgress.setBounds(420, 450, 130, 35);

        add(btnViewTranscript);
        btnViewTranscript.setBounds(560, 450, 140, 35);

        add(btnViewRankings);
        btnViewRankings.setBounds(710, 450, 130, 35);

        add(btnRefresh);
        btnRefresh.setBounds(150, 450, 100, 35);

        add(btnBack);
        btnBack.setBounds(30, 450, 100, 35);
    }

    private void loadEnrolledStudents() {
        DefaultTableModel model = (DefaultTableModel) tblStudents.getModel();
        model.setRowCount(0);

        ArrayList<Seat> seats = courseOffer.getSeatList();

        if (seats == null || seats.isEmpty()) {
            System.out.println("⚠️ No seats in this course");
            return;
        }

        // Get all students to match with course loads
        StudentDirectory sd = business.getStudentDirectory();
        ArrayList<StudentProfile> allStudents = sd.getStudentList();

        int enrolledCount = 0;
        double totalGradePoints = 0;
        int gradedCount = 0;

        for (Seat seat : seats) {
            if (seat.isOccupied()) {
                SeatAssignment sa = seat.getSeatAssignment();

                if (sa == null) continue;

                Object[] row = new Object[6];

                // Get course load from seat assignment
                info5100.university.example.CourseSchedule.CourseLoad courseLoad = sa.getCourseLoad();

                // Find student by searching through all students and checking their transcripts
                String studentName = "Unknown Student";
                String studentId = "Unknown";

                if (courseLoad != null) {
                    // Search through all students
                    boolean found = false;
                    for (StudentProfile sp : allStudents) {
                        info5100.university.example.Persona.StudentProfile univStudent = sp.getUniversityProfile();
                        info5100.university.example.Persona.Transcript transcript = univStudent.getTranscript();

                        // Get all seat assignments for this student
                        ArrayList<info5100.university.example.CourseSchedule.SeatAssignment> studentAssignments =
                                transcript.getCourseList();

                        // Check if this student has this specific seat assignment
                        if (studentAssignments != null) {
                            for (info5100.university.example.CourseSchedule.SeatAssignment studentSa : studentAssignments) {
                                // Match by object reference
                                if (studentSa == sa) {
                                    studentName = sp.getPerson().getFullName();
                                    studentId = sp.getPerson().getPersonId();
                                    found = true;
                                    break;
                                }
                            }
                        }

                        if (found) break;
                    }
                }

                row[0] = studentName;
                row[1] = studentId;

                String letterGrade = sa.getLetterGrade();
                row[2] = letterGrade != null ? letterGrade : "Not Graded";

                float gradePoints = sa.getGrade();
                row[3] = gradePoints > 0 ? String.format("%.2f", gradePoints) : "N/A";

                int credits = sa.getCreditHours();
                float qualityPoints = gradePoints * credits;
                row[4] = gradePoints > 0 ? String.format("%.2f", qualityPoints) : "N/A";

                row[5] = "-";  // Rank - will calculate after all loaded

                model.addRow(row);
                enrolledCount++;

                if (gradePoints > 0) {
                    totalGradePoints += gradePoints;
                    gradedCount++;
                }
            }
        }

        // Calculate and display class GPA
        if (gradedCount > 0) {
            double classGPA = totalGradePoints / gradedCount;
            lblClassGPA.setText(String.format("Class GPA: %.2f", classGPA));
        } else {
            lblClassGPA.setText("Class GPA: N/A (No grades assigned)");
        }

        System.out.println("✅ Loaded " + enrolledCount + " enrolled students");
    }

    private void tblStudentsSelectionChanged() {
        int selectedRow = tblStudents.getSelectedRow();

        if (selectedRow < 0) {
            selectedSeatAssignment = null;
            selectedStudent = null;
            return;
        }

        // Get student ID from table
        String studentId = (String) tblStudents.getValueAt(selectedRow, 1);

        // Find the student profile
        StudentDirectory sd = business.getStudentDirectory();
        selectedStudent = sd.findStudent(studentId);

        // Get the seat assignment for selected row
        ArrayList<Seat> seats = courseOffer.getSeatList();
        int currentRow = 0;

        for (Seat seat : seats) {
            if (seat.isOccupied()) {
                if (currentRow == selectedRow) {
                    selectedSeatAssignment = seat.getSeatAssignment();
                    break;
                }
                currentRow++;
            }
        }
    }

    private void btnGradeStudentActionPerformed(java.awt.event.ActionEvent evt) {
        if (selectedSeatAssignment == null) {
            JOptionPane.showMessageDialog(this, "Please select a student first!");
            return;
        }

        // Get current grade
        String currentGrade = selectedSeatAssignment.getLetterGrade();
        String currentGradeDisplay = currentGrade != null ? currentGrade : "Not Graded";

        // Show grade selection dialog
        String[] gradeOptions = {
            "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "F"
        };

        String selectedGrade = (String) JOptionPane.showInputDialog(
                this,
                "Current Grade: " + currentGradeDisplay + "\n\nSelect new letter grade:",
                "Assign Grade",
                JOptionPane.QUESTION_MESSAGE,
                null,
                gradeOptions,
                currentGrade != null ? currentGrade : "B"
        );

        // If user cancelled or didn't select anything
        if (selectedGrade == null) {
            return;
        }

        // Assign the grade
        selectedSeatAssignment.setLetterGrade(selectedGrade);

        System.out.println("✅ Assigned grade " + selectedGrade + " to student");

        // Show confirmation
        JOptionPane.showMessageDialog(this,
                "Grade assigned successfully!\n\n"
                + "Letter Grade: " + selectedGrade + "\n"
                + "Grade Points: " + String.format("%.2f", selectedSeatAssignment.getGrade()),
                "Success",
                JOptionPane.INFORMATION_MESSAGE);

        // Refresh the table to show new grade
        loadEnrolledStudents();
    }

    private void btnViewProgressActionPerformed(java.awt.event.ActionEvent evt) {
        if (selectedStudent == null) {
            JOptionPane.showMessageDialog(this, "Please select a student first!");
            return;
        }

        StudentProgressPanel panel = new StudentProgressPanel(business, faculty, selectedStudent, CardSequencePanel);
        CardSequencePanel.add("StudentProgress", panel);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }

    private void btnViewTranscriptActionPerformed(java.awt.event.ActionEvent evt) {
        if (selectedStudent == null) {
            JOptionPane.showMessageDialog(this, "Please select a student first!");
            return;
        }

        StudentTranscriptPanel panel = new StudentTranscriptPanel(business, faculty, selectedStudent, CardSequencePanel);
        CardSequencePanel.add("StudentTranscript", panel);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }

    private void btnViewRankingsActionPerformed(java.awt.event.ActionEvent evt) {
        // Calculate rankings and update table
        DefaultTableModel model = (DefaultTableModel) tblStudents.getModel();

        // Get all rows and sort by grade points (descending)
        int rowCount = model.getRowCount();

        if (rowCount == 0) {
            JOptionPane.showMessageDialog(this, "No students enrolled in this course!");
            return;
        }

        // Create array to store student data with grades
        ArrayList<StudentGradeData> studentGrades = new ArrayList<>();

        for (int i = 0; i < rowCount; i++) {
            String name = (String) model.getValueAt(i, 0);
            String id = (String) model.getValueAt(i, 1);
            String grade = (String) model.getValueAt(i, 2);
            String gradePointsStr = (String) model.getValueAt(i, 3);

            if (!"N/A".equals(gradePointsStr)) {
                float gradePoints = Float.parseFloat(gradePointsStr);
                studentGrades.add(new StudentGradeData(name, id, grade, gradePoints));
            }
        }

        // Sort by grade points (descending)
        studentGrades.sort((s1, s2) -> Float.compare(s2.gradePoints, s1.gradePoints));

        // Clear and repopulate table with rankings
        model.setRowCount(0);

        int rank = 1;
        double totalGradePoints = 0;

        for (StudentGradeData data : studentGrades) {
            Object[] row = new Object[6];
            row[0] = data.name;
            row[1] = data.id;
            row[2] = data.letterGrade;
            row[3] = String.format("%.2f", data.gradePoints);

            int credits = courseOffer.getSubjectCourse().getCredits();
            row[4] = String.format("%.2f", data.gradePoints * credits);
            row[5] = "#" + rank;  // Rank

            model.addRow(row);

            totalGradePoints += data.gradePoints;
            rank++;
        }

        // Update class GPA
        if (studentGrades.size() > 0) {
            double classGPA = totalGradePoints / studentGrades.size();
            lblClassGPA.setText(String.format("Class GPA: %.2f (Ranked by Performance)", classGPA));
        }

        JOptionPane.showMessageDialog(this,
                "Students ranked by grade!\n\n"
                + "Total Students: " + studentGrades.size() + "\n"
                + "Class Average: " + String.format("%.2f", totalGradePoints / studentGrades.size()),
                "Rankings Generated",
                JOptionPane.INFORMATION_MESSAGE);

        System.out.println("✅ Generated rankings for " + studentGrades.size() + " students");
    }

    // Inner class to help with sorting
    private class StudentGradeData {
        String name;
        String id;
        String letterGrade;
        float gradePoints;

        StudentGradeData(String n, String i, String lg, float gp) {
            name = n;
            id = i;
            letterGrade = lg;
            gradePoints = gp;
        }
    }

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {
        loadEnrolledStudents();
    }

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
}