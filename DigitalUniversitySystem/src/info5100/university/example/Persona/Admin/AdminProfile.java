/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info5100.university.example.Persona.Admin;

import info5100.university.example.Persona.Person;

/**
 *
 * @author chethan
 */
public class AdminProfile {
    
    Person person;
    
    public AdminProfile(Person p) {
        person = p;
    }
    
    public Person getPerson() {
        return person;
    }
    
    public boolean isMatch(String id) {
        return person.getPersonId().equals(id);
    }
    
    @Override
    public String toString() {
        return "Admin: " + person.getFullName();
    }
}
