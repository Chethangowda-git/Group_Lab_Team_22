/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Profiles;
import Business.Person.Person;
import java.util.ArrayList;

public class StudentProfile extends Profile {
    
    private info5100.university.example.Persona.StudentProfile universityStudentProfile;
    
    // FINANCIAL FIELDS - NEW
    private double tuitionBalance = 0.0;
    private ArrayList<String> paymentHistory;
    
    public StudentProfile(Person p) {
        super(p);
        
        info5100.university.example.Persona.Person universityPerson = 
            new info5100.university.example.Persona.Person(
                p.getPersonId(), p.getEmail(), p.getFirstName(), p.getLastName(), p.getPhone()
            );
        
        this.universityStudentProfile = 
            new info5100.university.example.Persona.StudentProfile(universityPerson);
        
        // Initialize financial data
        this.paymentHistory = new ArrayList<>();
    }
    
    @Override
    public String getRole() {
        return "Student";
    }
    
    public info5100.university.example.Persona.StudentProfile getUniversityProfile() {
        return universityStudentProfile;
    }
    
    // FINANCIAL METHODS - NEW
    
    /**
     * Get current tuition balance owed
     */
    public double getTuitionBalance() {
        return tuitionBalance;
    }
    
    /**
     * Add to tuition balance (when enrolling in course)
     */
    public void addTuitionCharge(double amount) {
        tuitionBalance += amount;
    }
    
    /**
     * Pay tuition (full or partial payment)
     */
    public void payTuition(double amount) {
        if (amount <= 0) return;
        
        tuitionBalance -= amount;
        if (tuitionBalance < 0) tuitionBalance = 0; // No negative balance
        
        // Record payment
        String timestamp = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        String record = timestamp + " - Paid: $" + String.format("%.2f", amount);
        paymentHistory.add(record);
    }
    
    /**
     * Refund tuition (when dropping course)
     */
    public void refundTuition(double amount) {
        tuitionBalance -= amount;
        if (tuitionBalance < 0) tuitionBalance = 0;
        
        String timestamp = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        String record = timestamp + " - Refund: $" + String.format("%.2f", amount);
        paymentHistory.add(record);
    }
    
    /**
     * Get payment history
     */
    public ArrayList<String> getPaymentHistory() {
        return paymentHistory;
    }
    
    /**
     * Check if tuition is paid (balance is 0)
     */
    public boolean isTuitionPaid() {
        return tuitionBalance <= 0;
    }
    
    /**
     * Calculate total tuition owed based on enrollments
     */
    public double calculateTotalTuitionOwed() {
        double total = 0;
        
        info5100.university.example.Persona.Transcript transcript = 
            universityStudentProfile.getTranscript();
        
        ArrayList<info5100.university.example.CourseSchedule.SeatAssignment> enrollments = 
            transcript.getCourseList();
        
        if (enrollments != null) {
            for (info5100.university.example.CourseSchedule.SeatAssignment sa : enrollments) {
                total += sa.getAssociatedCourse().getCoursePrice();
            }
        }
        
        return total;
    }
}