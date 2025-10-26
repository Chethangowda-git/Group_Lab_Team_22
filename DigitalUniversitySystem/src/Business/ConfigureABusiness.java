package Business;

import Business.Person.Person;
import Business.Person.PersonDirectory;
import Business.Profiles.*;
import Business.UserAccounts.*;

import info5100.university.example.Department.Department;
import info5100.university.example.CourseCatalog.Course;
import info5100.university.example.CourseSchedule.CourseSchedule;
import info5100.university.example.CourseSchedule.CourseOffer;

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
        if (co1 != null) {
            co1.generatSeats(30);
        }

        CourseOffer co2 = fall2025.newCourseOffer("INFO6150");
        if (co2 != null) {
            co2.generatSeats(25);
        }

        CourseOffer co3 = fall2025.newCourseOffer("INFO6205");
        if (co3 != null) {
            co3.generatSeats(30);
        }

        CourseOffer co4 = fall2025.newCourseOffer("DAMG6210");
        if (co4 != null) {
            co4.generatSeats(28);
        }

        CourseOffer co5 = fall2025.newCourseOffer("CSYE7200");
        if (co5 != null) {
            co5.generatSeats(20);
        }

        System.out.println("✅ System Initialized!");
        System.out.println("📊 30 persons, 10 students, 10 faculty, 1 admin, 1 registrar");

        return business;
    }
}
