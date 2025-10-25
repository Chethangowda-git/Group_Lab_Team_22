/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Auth;

import info5100.university.example.Persona.Person;

/**
 *
 * @author chethan
 */
public class UserAccount {
    
    private String username;
    private String password;
    private Person person;
    private String role; // "Admin", "Faculty", "Student", "Registrar"
    
    public UserAccount(String username, String password, Person person, String role) {
        this.username = username;
        this.password = password;
        this.person = person;
        this.role = role;
    }
    
    /**
     * Authenticates user credentials
     * @param un username
     * @param pw password
     * @return true if credentials match
     */
    public boolean authenticate(String un, String pw) {
        return this.username.equals(un) && this.password.equals(pw);
    }
    
    // Getters
    public String getUsername() {
        return username;
    }
    
    public String getRole() {
        return role;
    }
    
    public Person getPerson() {
        return person;
    }
    
    @Override
    public String toString() {
        return username + " (" + role + ")";
    }
}
