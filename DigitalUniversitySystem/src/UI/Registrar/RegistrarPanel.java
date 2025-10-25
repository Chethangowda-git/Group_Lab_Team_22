/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.Registrar;

import info5100.university.example.Department.Department;
import UI.MainJFrame;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author chethan
 */
public class RegistrarPanel extends javax.swing.JPanel {
    
    private Department department;
    private MainJFrame mainFrame;
    
    private CardLayout cardLayout;
    private JPanel cardPanel;
    
    private JPanel menuPanel;
    
    public RegistrarPanel(Department dept, MainJFrame mf) {
        this.department = dept;
        this.mainFrame = mf;
        
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        menuPanel = createMenuPanel();
        
        cardPanel.add(menuPanel, "MENU");
        
        add(cardPanel, BorderLayout.CENTER);
        
        cardLayout.show(cardPanel, "MENU");
    }
    
    private JPanel createMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel("Registrar Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(50, 30, 400, 30);
        panel.add(titleLabel);
        
        JButton courseOfferingsBtn = createMenuButton("Manage Course Offerings", 50, 100);
        courseOfferingsBtn.addActionListener(e -> showCourseOfferingManagement());
        panel.add(courseOfferingsBtn);
        
        JButton enrollmentBtn = createMenuButton("Student Enrollment (Admin)", 50, 160);
        enrollmentBtn.addActionListener(e -> showStudentEnrollment());
        panel.add(enrollmentBtn);
        
        JButton financialBtn = createMenuButton("Financial Reports", 50, 220);
        financialBtn.addActionListener(e -> showFinancialReports());
        panel.add(financialBtn);
        
        JButton institutionalBtn = createMenuButton("Institutional Reports", 50, 280);
        institutionalBtn.addActionListener(e -> showInstitutionalReports());
        panel.add(institutionalBtn);
        
        JButton profileBtn = createMenuButton("My Profile", 50, 340);
        profileBtn.addActionListener(e -> showProfile());
        panel.add(profileBtn);
        
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(800, 30, 150, 40);
        logoutBtn.setBackground(new Color(220, 53, 69));
        logoutBtn.setForeground(Color.BLACK);
        logoutBtn.setFocusPainted(false);
        logoutBtn.addActionListener(e -> mainFrame.logout());
        panel.add(logoutBtn);
        
        return panel;
    }
    
    private JButton createMenuButton(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 400, 45);
        btn.setFont(new Font("Arial", Font.PLAIN, 16));
        btn.setBackground(new Color(111, 66, 193));
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        return btn;
    }
    
    private void showCourseOfferingManagement() {
        // Check if panel already exists
        boolean exists = false;
        for (Component comp : cardPanel.getComponents()) {
            if (comp instanceof CourseOfferingManagementPanel) {
                exists = true;
                break;
            }
        }
        
        if (!exists) {
//            CourseOfferingManagementPanel panel = new CourseOfferingManagementPanel(department, this);
            CourseOfferingManagementPanel panel = new CourseOfferingManagementPanel();

            cardPanel.add(panel, "COURSE_OFFERINGS");
        }
        
        cardLayout.show(cardPanel, "COURSE_OFFERINGS");
    }
    
    private void showStudentEnrollment() {
        boolean exists = false;
        for (Component comp : cardPanel.getComponents()) {
            if (comp instanceof StudentEnrollmentPanel) {
                exists = true;
                break;
            }
        }
        
        if (!exists) {
//            StudentEnrollmentPanel panel = new StudentEnrollmentPanel(department, this);
            StudentEnrollmentPanel panel = new StudentEnrollmentPanel();

            cardPanel.add(panel, "ENROLLMENT");
        }
        
        cardLayout.show(cardPanel, "ENROLLMENT");
    }
    
    private void showFinancialReports() {
        boolean exists = false;
        for (Component comp : cardPanel.getComponents()) {
            if (comp instanceof FinancialReportsPanel) {
                exists = true;
                break;
            }
        }
        
        if (!exists) {
//            FinancialReportsPanel panel = new FinancialReportsPanel(department, this);
            FinancialReportsPanel panel = new FinancialReportsPanel();

            cardPanel.add(panel, "FINANCIAL");
        }
        
        cardLayout.show(cardPanel, "FINANCIAL");
    }
    
    private void showInstitutionalReports() {
        boolean exists = false;
        for (Component comp : cardPanel.getComponents()) {
            if (comp instanceof InstitutionalReportsPanel) {
                exists = true;
                break;
            }
        }
        
        if (!exists) {
//            InstitutionalReportsPanel panel = new InstitutionalReportsPanel(department, this);
                        InstitutionalReportsPanel panel = new InstitutionalReportsPanel();

            cardPanel.add(panel, "INSTITUTIONAL");
        }
        
        cardLayout.show(cardPanel, "INSTITUTIONAL");
    }
    
    private void showProfile() {
        boolean exists = false;
        for (Component comp : cardPanel.getComponents()) {
            if (comp instanceof ProfileManagementPanel) {
                exists = true;
                break;
            }
        }
        
        if (!exists) {
//            ProfileManagementPanel panel = new ProfileManagementPanel(department, this);
            ProfileManagementPanel panel = new ProfileManagementPanel();

            cardPanel.add(panel, "PROFILE");
        }
        
        cardLayout.show(cardPanel, "PROFILE");
    }
    
    public void showMenu() {
        cardLayout.show(cardPanel, "MENU");
    }
}