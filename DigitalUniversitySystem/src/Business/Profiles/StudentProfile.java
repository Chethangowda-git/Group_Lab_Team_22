/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Profiles;
import Business.Person.Person;

public class StudentProfile extends Profile {
    
    // Link to University Model Student Profile
    private info5100.university.example.Persona.StudentProfile universityStudentProfile;
    
    public StudentProfile(Person p) {
        super(p);
        
        // Create University Model Person and StudentProfile
        info5100.university.example.Persona.Person universityPerson = 
            new info5100.university.example.Persona.Person(
                p.getPersonId(), 
                p.getEmail(), 
                p.getFirstName(), 
                p.getLastName(), 
                p.getPhone()
            );
        
        this.universityStudentProfile = 
            new info5100.university.example.Persona.StudentProfile(universityPerson);
    }
    
    @Override
    public String getRole() {
        return "Student";
    }
    
    /**
     * Get the University Model StudentProfile for advanced operations
     */
    public info5100.university.example.Persona.StudentProfile getUniversityProfile() {
        return universityStudentProfile;
    }
}