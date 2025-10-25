/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info5100.university.example.Persona;

/**
 *
 * @author kal bugrara
 */
public class Person {
    
    String id;
    String email;           
    String firstName;       
    String lastName;       
    String phone;           
      
    public Person(String id, String email, String firstName, String lastName, String phone) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }
    
    public String getPersonId() {
        return id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public String getFullName() {
        if (firstName != null && lastName != null) {
            return firstName + " " + lastName;
        }
        return id;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public boolean isMatch(String id) {
        if (getPersonId().equals(id)) return true;
        return false;
    }
    
    @Override
    public String toString() {
        return getFullName() + " (" + id + ")";
    }
}