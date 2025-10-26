/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Profiles;

import Business.Person.Person;

public class FacultyProfile extends Profile {
    
    // Link to University Model Faculty Profile
    private info5100.university.example.Persona.Faculty.FacultyProfile universityFacultyProfile;
    
    public FacultyProfile(Person p) {
        super(p);
        
        // Create University Model Person and FacultyProfile
        info5100.university.example.Persona.Person universityPerson = 
            new info5100.university.example.Persona.Person(
                p.getPersonId(), 
                p.getEmail(), 
                p.getFirstName(), 
                p.getLastName(), 
                p.getPhone()
            );
        
        this.universityFacultyProfile = 
            new info5100.university.example.Persona.Faculty.FacultyProfile(universityPerson);
    }
    
    @Override
    public String getRole() {
        return "Faculty";
    }
    
    /**
     * Get the University Model FacultyProfile for advanced operations
     */
    public info5100.university.example.Persona.Faculty.FacultyProfile getUniversityProfile() {
        return universityFacultyProfile;
    }
}
