package Business;

import Business.Person.Person;
import Business.Person.PersonDirectory;
import Business.Profiles.*;
import Business.UserAccounts.*;

import info5100.university.example.Department.Department;
import info5100.university.example.CourseCatalog.Course;
import info5100.university.example.CourseSchedule.CourseSchedule;
import info5100.university.example.CourseSchedule.CourseOffer;
import info5100.university.example.CourseSchedule.CourseLoad;
import info5100.university.example.CourseSchedule.SeatAssignment;

class ConfigureABusiness {

    static Business initialize() {
        Business business = new Business("Information Systems");

        PersonDirectory pd = business.getPersonDirectory();
        EmployeeDirectory ed = business.getEmployeeDirectory();
        StudentDirectory sd = business.getStudentDirectory();
        FacultyDirectory fd = business.getFacultyDirectory();
        RegistrarDirectory rd = business.getRegistrarDirectory();
        UserAccountDirectory uad = business.getUserAccountDirectory();
        Department dept = business.getDepartment();

        // ============ CREATE 30 PERSONS WITH FULL DETAILS ============
        
        // 1 Admin
        Person adminPerson = pd.newPerson("admin001", "admin@neu.edu", "System", "Administrator", "617-123-0001");
        EmployeeProfile admin = ed.newEmployeeProfile(adminPerson);

        // 1 Registrar
        Person regPerson = pd.newPerson("registrar001", "registrar@neu.edu", "Office", "Registrar", "617-123-0002");
        RegistrarProfile registrar = rd.newRegistrarProfile(regPerson);

        // 10 Students with names
        String[] studentFirstNames = {"John", "Emma", "Michael", "Sophia", "William", "Olivia", "James", "Ava", "Robert", "Isabella"};
        String[] studentLastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez"};

        for (int i = 1; i <= 10; i++) {
            String id = "student" + String.format("%03d", i);
            String email = "student" + i + "@neu.edu";
            String firstName = studentFirstNames[i - 1];
            String lastName = studentLastNames[i - 1];
            String phone = "617-555-" + String.format("%04d", 1000 + i);

            Person p = pd.newPerson(id, email, firstName, lastName, phone);
            sd.newStudentProfile(p);
        }

        // 10 Faculty with names
        String[] facultyFirstNames = {"David", "Sarah", "Thomas", "Jennifer", "Christopher", "Linda", "Daniel", "Patricia", "Matthew", "Barbara"};
        String[] facultyLastNames = {"Wilson", "Moore", "Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson"};

        for (int i = 1; i <= 10; i++) {
            String id = "faculty" + String.format("%03d", i);
            String email = "faculty" + i + "@neu.edu";
            String firstName = facultyFirstNames[i - 1];
            String lastName = facultyLastNames[i - 1];
            String phone = "617-555-" + String.format("%04d", 2000 + i);

            Person p = pd.newPerson(id, email, firstName, lastName, phone);
            fd.newFacultyProfile(p);
        }

        // 8 Additional persons (to make 30 total)
        for (int i = 1; i <= 8; i++) {
            String id = "person" + String.format("%03d", i);
            String email = "person" + i + "@neu.edu";
            pd.newPerson(id, email, "Person", String.valueOf(i), "617-555-" + String.format("%04d", 3000 + i));
        }

        // ============ CREATE USER ACCOUNTS ============
        
        uad.newUserAccount(admin, "admin", "admin");
        uad.newUserAccount(registrar, "registrar", "registrar");

        for (int i = 1; i <= 10; i++) {
            StudentProfile sp = sd.findStudent("student" + String.format("%03d", i));
            if (sp != null) {
                uad.newUserAccount(sp, "student" + i, "student" + i);
            }
        }

        for (int i = 1; i <= 10; i++) {
            FacultyProfile fp = fd.findFaculty("faculty" + String.format("%03d", i));
            if (fp != null) {
                uad.newUserAccount(fp, "faculty" + i, "faculty" + i);
            }
        }

        // ============ CREATE UNIVERSITY MODEL DATA ============
        
        Course c1 = dept.newCourse("Application Engineering", "INFO5100", 4);
        Course c2 = dept.newCourse("Web Design", "INFO6150", 4);
        Course c3 = dept.newCourse("Data Structures", "INFO6205", 4);
        Course c4 = dept.newCourse("Database Management", "DAMG6210", 4);
        Course c5 = dept.newCourse("Big Data", "CSYE7200", 4);
        
        // Add more courses to catalog
        Course c6 = dept.newCourse("Machine Learning", "INFO6350", 4);
        Course c7 = dept.newCourse("Network Structures", "INFO6250", 4);
        Course c8 = dept.newCourse("Software Engineering", "CSYE6200", 4);

        dept.addCoreCourse(c1);
        dept.addElectiveCourse(c2);
        dept.addElectiveCourse(c3);
        dept.addElectiveCourse(c4);
        dept.addElectiveCourse(c5);
        dept.addElectiveCourse(c6);
        dept.addElectiveCourse(c7);
        dept.addElectiveCourse(c8);

        CourseSchedule fall2025 = dept.newCourseSchedule("Fall2025");

        CourseOffer co1 = fall2025.newCourseOffer("INFO5100");
        if (co1 != null) co1.generatSeats(30);

        CourseOffer co2 = fall2025.newCourseOffer("INFO6150");
        if (co2 != null) co2.generatSeats(25);

        CourseOffer co3 = fall2025.newCourseOffer("INFO6205");
        if (co3 != null) co3.generatSeats(30);

        CourseOffer co4 = fall2025.newCourseOffer("DAMG6210");
        if (co4 != null) co4.generatSeats(28);

        CourseOffer co5 = fall2025.newCourseOffer("CSYE7200");
        if (co5 != null) co5.generatSeats(20);

        // ============ ASSIGN SAMPLE GRADES FOR GPA TESTING ============
        
        // Student 1: John Smith - Excellent student (GPA ~3.85)
        StudentProfile student1 = sd.findStudent("student001");
        if (student1 != null) {
            info5100.university.example.Persona.StudentProfile univStudent1 = student1.getUniversityProfile();
            CourseLoad cl1 = univStudent1.newCourseLoad("Fall2025");
            
            SeatAssignment sa1 = co1.assignEmptySeat(cl1);
            if (sa1 != null) sa1.setLetterGrade("A");  // 4.0
            
            SeatAssignment sa2 = co2.assignEmptySeat(cl1);
            if (sa2 != null) sa2.setLetterGrade("A-"); // 3.7
        }

        // Student 2: Emma Johnson - Good student (GPA ~3.15)
        StudentProfile student2 = sd.findStudent("student002");
        if (student2 != null) {
            info5100.university.example.Persona.StudentProfile univStudent2 = student2.getUniversityProfile();
            CourseLoad cl2 = univStudent2.newCourseLoad("Fall2025");
            
            SeatAssignment sa3 = co3.assignEmptySeat(cl2);
            if (sa3 != null) sa3.setLetterGrade("B+"); // 3.3
            
            SeatAssignment sa4 = co4.assignEmptySeat(cl2);
            if (sa4 != null) sa4.setLetterGrade("B");  // 3.0
        }

        // Student 3: Michael Williams - At-risk student (GPA 2.3)
        StudentProfile student3 = sd.findStudent("student003");
        if (student3 != null) {
            info5100.university.example.Persona.StudentProfile univStudent3 = student3.getUniversityProfile();
            CourseLoad cl3 = univStudent3.newCourseLoad("Fall2025");
            
            SeatAssignment sa5 = co5.assignEmptySeat(cl3);
            if (sa5 != null) sa5.setLetterGrade("C+"); // 2.3
        }
        
        // Student 4: Sophia Brown - Perfect student (GPA 4.0)
        StudentProfile student4 = sd.findStudent("student004");
        if (student4 != null) {
            info5100.university.example.Persona.StudentProfile univStudent4 = student4.getUniversityProfile();
            CourseLoad cl4 = univStudent4.newCourseLoad("Fall2025");
            
            SeatAssignment sa6 = co1.assignEmptySeat(cl4);
            if (sa6 != null) sa6.setLetterGrade("A");  // 4.0
            
            SeatAssignment sa7 = co3.assignEmptySeat(cl4);
            if (sa7 != null) sa7.setLetterGrade("A");  // 4.0
        }
        
        // Student 5: William Jones - Struggling student (GPA 1.7)
        StudentProfile student5 = sd.findStudent("student005");
        if (student5 != null) {
            info5100.university.example.Persona.StudentProfile univStudent5 = student5.getUniversityProfile();
            CourseLoad cl5 = univStudent5.newCourseLoad("Fall2025");
            
            SeatAssignment sa8 = co2.assignEmptySeat(cl5);
            if (sa8 != null) sa8.setLetterGrade("C-"); // 1.7
        }

        System.out.println("✅ System Initialized!");
        System.out.println("📊 30 persons, 10 students, 10 faculty, 1 admin, 1 registrar");
        System.out.println("📚 Assigned sample grades to 5 students for GPA testing");
        System.out.println("   - Student 1 (John Smith): A, A- → GPA 3.85");
        System.out.println("   - Student 2 (Emma Johnson): B+, B → GPA 3.15");
        System.out.println("   - Student 3 (Michael Williams): C+ → GPA 2.3");
        System.out.println("   - Student 4 (Sophia Brown): A, A → GPA 4.0");
        System.out.println("   - Student 5 (William Jones): C- → GPA 1.7");

        return business;
    }
}