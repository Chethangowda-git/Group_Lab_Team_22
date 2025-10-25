/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package UI;

import Auth.*;
import Data.TestDataSetup;
import info5100.university.example.Department.Department;
import UI.Admin.AdminPanel;
import UI.Faculty.FacultyPanel;
import UI.Student.StudentPanel;
import UI.Registrar.RegistrarPanel;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author chethan
 */
public class MainJFrame extends javax.swing.JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;

    private Department department;
    private UserAccountDirectory userAccountDirectory;

    private LoginPanel loginPanel;
    private AdminPanel adminPanel;
    private FacultyPanel facultyPanel;
    private StudentPanel studentPanel;
    private RegistrarPanel registrarPanel;

    public MainJFrame() {
        initializeData();
        initComponents();

        setTitle("University Management System");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initializeData() {
        // System.out.println("Initializing University Management System...");

        department = TestDataSetup.configure();
        userAccountDirectory = TestDataSetup.setupUserAccounts(department);

        // System.out.println("System ready!");
    }

    private void initComponents() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        loginPanel = new LoginPanel(userAccountDirectory, this);
        cardPanel.add(loginPanel, "LOGIN");

        add(cardPanel);

        cardLayout.show(cardPanel, "LOGIN");
    }

    public void onLoginSuccess(UserAccount userAccount) {
        String role = userAccount.getRole();
        String username = userAccount.getUsername();

        // System.out.println("Login successful: " + username + " (" + role + ")");
        switch (role) {
            case "Admin":
                showAdminPanel();
                break;
            case "Faculty":
                showFacultyPanel(userAccount);
                break;
            case "Student":
                showStudentPanel(userAccount);
                break;
            case "Registrar":
                showRegistrarPanel();
                break;
            default:
                JOptionPane.showMessageDialog(this,
                        "Unknown role: " + role,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
        }
    }

private void showAdminPanel() {
    System.out.println("showAdminPanel called");
    
    if (adminPanel == null) {
//        System.out.println("Creating new AdminPanel (no-arg)");
        adminPanel = new AdminPanel();  // NO PARAMETERS
        cardPanel.add(adminPanel, "ADMIN");
//        System.out.println("AdminPanel added to cardPanel");
    }
    
//    System.out.println("Showing ADMIN card");
    cardLayout.show(cardPanel, "ADMIN");
//    cardPanel.revalidate();
//    cardPanel.repaint();
}

    private void showFacultyPanel(UserAccount userAccount) {
        if (facultyPanel == null) {
//            facultyPanel = new FacultyPanel(department, userAccount, this);
            facultyPanel = new FacultyPanel();
            cardPanel.add(facultyPanel, "FACULTY");
        }
        cardLayout.show(cardPanel, "FACULTY");
    }

    private void showStudentPanel(UserAccount userAccount) {
        if (studentPanel == null) {
//            studentPanel = new StudentPanel(department, userAccount, this);
            studentPanel = new StudentPanel();
            cardPanel.add(studentPanel, "STUDENT");
        }
        cardLayout.show(cardPanel, "STUDENT");
    }

    private void showRegistrarPanel() {
        if (registrarPanel == null) {
            registrarPanel = new RegistrarPanel(department, this);
//            registrarPanel = new RegistrarPanel();
            cardPanel.add(registrarPanel, "REGISTRAR");
        }
        cardLayout.show(cardPanel, "REGISTRAR");
    }

    public void logout() {
        loginPanel.clearForm();

        if (adminPanel != null) {
            cardPanel.remove(adminPanel);
            adminPanel = null;
        }
        if (facultyPanel != null) {
            cardPanel.remove(facultyPanel);
            facultyPanel = null;
        }
        if (studentPanel != null) {
            cardPanel.remove(studentPanel);
            studentPanel = null;
        }
        if (registrarPanel != null) {
            cardPanel.remove(registrarPanel);
            registrarPanel = null;
        }

        cardLayout.show(cardPanel, "LOGIN");
        // System.out.println("Logged out");
    }

    public Department getDepartment() {
        return department;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            MainJFrame mainFrame = new MainJFrame();
            mainFrame.setVisible(true);
        });
    }
}
