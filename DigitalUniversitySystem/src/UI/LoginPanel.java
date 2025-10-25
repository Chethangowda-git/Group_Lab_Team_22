/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI;

import Auth.UserAccount;
import Auth.UserAccountDirectory;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.*;
import javax.swing.SwingConstants;

/**
 *
 * @author chethan
 */
public class LoginPanel extends javax.swing.JPanel {
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel messageLabel;
    
    private UserAccountDirectory userAccountDirectory;
    private MainJFrame mainFrame;
    
    public LoginPanel(UserAccountDirectory uad, MainJFrame mf) {
        this.userAccountDirectory = uad;
        this.mainFrame = mf;
        
        initComponents();
    }
    
    private void initComponents() {
        setLayout(null);
        setBackground(new Color(240, 248, 255));
        
        JLabel titleLabel = new JLabel("University Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(0, 102, 204));
        titleLabel.setBounds(200, 80, 500, 40);
        add(titleLabel);
        
        JLabel subtitleLabel = new JLabel("Please login to continue");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setForeground(Color.GRAY);
        subtitleLabel.setBounds(280, 125, 300, 25);
        add(subtitleLabel);
        
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userLabel.setBounds(250, 180, 100, 25);
        add(userLabel);
        
        usernameField = new JTextField("admin");
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBounds(250, 210, 300, 35);
        add(usernameField);
        
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passLabel.setBounds(250, 260, 100, 25);
        add(passLabel);
        
        passwordField = new JPasswordField("admin");
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBounds(250, 290, 300, 35);
        add(passwordField);
        
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(51, 153, 255));
        loginButton.setForeground(Color.BLACK);
        loginButton.setFocusPainted(false);
        loginButton.setBounds(250, 350, 300, 40);
        loginButton.addActionListener(e -> performLogin());
        add(loginButton);
        
        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        messageLabel.setForeground(Color.RED);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBounds(250, 400, 300, 25);
        add(messageLabel);
        
        JLabel infoLabel = new JLabel("<html><center>Test Credentials:<br>" +
                "admin/admin | student1/student1<br>" +
                "faculty1/faculty1 | registrar/registrar</center></html>");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        infoLabel.setForeground(Color.GRAY);
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setBounds(200, 450, 400, 60);
        add(infoLabel);
        
        passwordField.addActionListener(e -> performLogin());
    }
    
    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        
        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please enter both username and password");
            return;
        }
        
        UserAccount userAccount = userAccountDirectory.authenticateUser(username, password);
        
        if (userAccount == null) {
            messageLabel.setText("Invalid username or password");
            passwordField.setText("");
            return;
        }
        
        messageLabel.setText("");
        mainFrame.onLoginSuccess(userAccount);
    }
    
    public void clearForm() {
        usernameField.setText("");
        passwordField.setText("");
        messageLabel.setText("");
    }
}