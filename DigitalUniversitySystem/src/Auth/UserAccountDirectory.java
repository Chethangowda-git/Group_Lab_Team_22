/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Auth;

import info5100.university.example.Persona.Person;
import java.util.ArrayList;

/**
 *
 * @author chethan
 */
public class UserAccountDirectory {
    
    private ArrayList<UserAccount> userAccountList;
    
    public UserAccountDirectory() {
        userAccountList = new ArrayList();
    }
    
    /**
     * Creates a new user account
     */
    public UserAccount newUserAccount(String username, String password, Person person, String role) {
        UserAccount ua = new UserAccount(username, password, person, role);
        userAccountList.add(ua);
        return ua;
    }
    
    /**
     * Authenticates a user
     * @param username
     * @param password
     * @return UserAccount if valid, null otherwise
     */
    public UserAccount authenticateUser(String username, String password) {
        for (UserAccount ua : userAccountList) {
            if (ua.authenticate(username, password)) {
                return ua;
            }
        }
        return null; // Authentication failed
    }
    
    /**
     * Find user account by username
     */
    public UserAccount findUserAccount(String username) {
        for (UserAccount ua : userAccountList) {
            if (ua.getUsername().equals(username)) {
                return ua;
            }
        }
        return null;
    }
    
    public ArrayList<UserAccount> getUserAccountList() {
        return userAccountList;
    }
}
