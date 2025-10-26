package UserInterface.WorkAreas.StudentRole;

import Business.Business;
import Business.Profiles.StudentProfile;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * Pay Tuition Panel
 * Student can view balance and make payments
 */
public class PayTuitionPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    StudentProfile student;
    
    private JLabel lblTitle;
    private JLabel lblStudentName;
    private JLabel lblCurrentBalance;
    private JLabel lblTotalOwed;
    private JButton btnPayFull;
    private JButton btnPayPartial;
    private JLabel lblPaymentHistory;
    private JScrollPane scrollHistory;
    private JTable tblPaymentHistory;
    private JButton btnBack;
    
    public PayTuitionPanel(Business b, StudentProfile sp, JPanel clp) {
        business = b;
        student = sp;
        CardSequencePanel = clp;
        
        initComponents();
        loadPaymentData();
    }
    
    private void initComponents() {
        lblTitle = new JLabel();
        lblTitle.setFont(new java.awt.Font("Arial", 1, 24));
        lblTitle.setText("Pay Tuition");
        
        lblStudentName = new JLabel();
        lblStudentName.setFont(new java.awt.Font("Dialog", 1, 16));
        lblStudentName.setText("Student: " + student.getPerson().getFullName());
        
        lblCurrentBalance = new JLabel();
        lblCurrentBalance.setFont(new java.awt.Font("Dialog", 1, 18));
        lblCurrentBalance.setForeground(java.awt.Color.RED);
        lblCurrentBalance.setText("Current Balance: $0");
        
        lblTotalOwed = new JLabel();
        lblTotalOwed.setText("Total Tuition Owed: $0");
        
        btnPayFull = new JButton();
        btnPayFull.setBackground(new java.awt.Color(102, 153, 255));
        btnPayFull.setForeground(java.awt.Color.WHITE);
        btnPayFull.setText("Pay Full Balance");
        btnPayFull.addActionListener(evt -> btnPayFullActionPerformed(evt));
        
        btnPayPartial = new JButton();
        btnPayPartial.setText("Pay Custom Amount");
        btnPayPartial.addActionListener(evt -> btnPayPartialActionPerformed(evt));
        
        lblPaymentHistory = new JLabel();
        lblPaymentHistory.setFont(new java.awt.Font("Dialog", 1, 14));
        lblPaymentHistory.setText("Payment History:");
        
        tblPaymentHistory = new JTable();
        tblPaymentHistory.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"Date & Time", "Transaction"}
        ) {
            boolean[] canEdit = new boolean[] {false, false};
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        
        scrollHistory = new JScrollPane();
        scrollHistory.setViewportView(tblPaymentHistory);
        
        btnBack = new JButton();
        btnBack.setText("<< Back");
        btnBack.addActionListener(evt -> btnBackActionPerformed(evt));
        
        setLayout(null);
        
        add(lblTitle);
        lblTitle.setBounds(30, 20, 400, 30);
        
        add(lblStudentName);
        lblStudentName.setBounds(30, 60, 500, 25);
        
        add(lblCurrentBalance);
        lblCurrentBalance.setBounds(30, 100, 400, 30);
        
        add(lblTotalOwed);
        lblTotalOwed.setBounds(30, 140, 400, 25);
        
        add(btnPayFull);
        btnPayFull.setBounds(30, 180, 180, 40);
        
        add(btnPayPartial);
        btnPayPartial.setBounds(230, 180, 180, 40);
        
        add(lblPaymentHistory);
        lblPaymentHistory.setBounds(30, 240, 300, 25);
        
        add(scrollHistory);
        scrollHistory.setBounds(30, 270, 650, 200);
        
        add(btnBack);
        btnBack.setBounds(30, 490, 100, 35);
    }
    
    private void loadPaymentData() {
        // Calculate total owed
        double totalOwed = student.calculateTotalTuitionOwed();
        double currentBalance = student.getTuitionBalance();
        
        lblTotalOwed.setText("Total Tuition Owed: $" + String.format("%,d", (int)totalOwed));
        lblCurrentBalance.setText("Current Balance: $" + String.format("%,d", (int)currentBalance));
        
        if (currentBalance <= 0) {
            lblCurrentBalance.setForeground(new java.awt.Color(0, 128, 0));
        } else {
            lblCurrentBalance.setForeground(java.awt.Color.RED);
        }
        
        // Load payment history
        DefaultTableModel model = (DefaultTableModel) tblPaymentHistory.getModel();
        model.setRowCount(0);
        
        ArrayList<String> history = student.getPaymentHistory();
        
        if (history.isEmpty()) {
            Object[] row = new Object[2];
            row[0] = "No payment history";
            row[1] = "";
            model.addRow(row);
        } else {
            for (String record : history) {
                String[] parts = record.split(" - ");
                Object[] row = new Object[2];
                row[0] = parts.length > 0 ? parts[0] : "";
                row[1] = parts.length > 1 ? parts[1] : record;
                model.addRow(row);
            }
        }
    }
    
    private void btnPayFullActionPerformed(java.awt.event.ActionEvent evt) {
        double balance = student.getTuitionBalance();
        
        if (balance <= 0) {
            JOptionPane.showMessageDialog(this, 
                "No balance to pay!\n\nYour account is paid in full.",
                "No Payment Needed",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Pay full balance of $" + String.format("%,d", (int)balance) + "?",
            "Confirm Payment",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm != JOptionPane.YES_OPTION) return;
        
        student.payTuition(balance);
        
        JOptionPane.showMessageDialog(this,
            "✅ Payment successful!\n\n" +
            "Amount paid: $" + String.format("%,d", (int)balance) + "\n" +
            "New balance: $0",
            "Payment Complete",
            JOptionPane.INFORMATION_MESSAGE);
        
        loadPaymentData();
    }
    
    private void btnPayPartialActionPerformed(java.awt.event.ActionEvent evt) {
        double balance = student.getTuitionBalance();
        
        if (balance <= 0) {
            JOptionPane.showMessageDialog(this, "No balance to pay!");
            return;
        }
        
        String input = JOptionPane.showInputDialog(this,
            "Current balance: $" + String.format("%,d", (int)balance) + "\n\n" +
            "Enter amount to pay:",
            "Partial Payment",
            JOptionPane.QUESTION_MESSAGE);
        
        if (input == null || input.trim().isEmpty()) return;
        
        try {
            double amount = Double.parseDouble(input);
            
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Amount must be greater than 0!");
                return;
            }
            
            if (amount > balance) {
                JOptionPane.showMessageDialog(this, "Amount cannot exceed balance!");
                return;
            }
            
            student.payTuition(amount);
            
            JOptionPane.showMessageDialog(this,
                "✅ Payment successful!\n\n" +
                "Amount paid: $" + String.format("%,d", (int)amount) + "\n" +
                "Remaining balance: $" + String.format("%,d", (int)student.getTuitionBalance()),
                "Payment Complete",
                JOptionPane.INFORMATION_MESSAGE);
            
            loadPaymentData();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount entered!");
        }
    }
    
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
}