package Business.Person;

/**
 * Person class for Business layer
 * Simplified wrapper - just stores ID
 * The University Model Person has more details
 */
public class Person {
    
    String id;
    String email;
    String firstName;
    String lastName;
    String phone;
    
    // Constructor with all fields
    public Person(String id, String email, String firstName, String lastName, String phone) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }
    
    // Legacy constructor for backward compatibility (if needed)
    public Person(String id) {
        this.id = id;
        this.email = null;
        this.firstName = null;
        this.lastName = null;
        this.phone = null;
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
        if (getPersonId().equals(id)) {
            return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return getFullName() + " (" + id + ")";
    }
}