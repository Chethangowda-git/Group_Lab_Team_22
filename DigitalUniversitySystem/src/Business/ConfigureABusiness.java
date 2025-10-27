package Business;

import Business.Person.Person;
import Business.Person.PersonDirectory;
import Business.Profiles.*;
import Business.UserAccounts.*;

import info5100.university.example.Department.Department;
import info5100.university.example.CourseCatalog.Course;
import info5100.university.example.CourseSchedule.CourseSchedule;
import info5100.university.example.CourseSchedule.CourseOffer;

/**
 * ConfigureABusiness - Pre-populate system with clean test data
 * All students start with NO enrollments, NO grades, NO tuition
 * All courses are available but have 0 students enrolled
 *
 * @author chethan
 */
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

        System.out.println("\n========================================");
        System.out.println("   INITIALIZING UNIVERSITY SYSTEM");
        System.out.println("========================================\n");

        // ============================================
        // SECTION 1: CREATE PERSONS (30 total)
        // ============================================

        System.out.println("=== CREATING PERSONS ===");
        
        // 1 Admin
        Person adminPerson = pd.newPerson("admin001", "admin@neu.edu", "System", "Administrator", "617-123-0001");
        EmployeeProfile admin = ed.newEmployeeProfile(adminPerson);
        System.out.println("✅ Created Admin: System Administrator");

        // 1 Registrar
        Person regPerson = pd.newPerson("registrar001", "registrar@neu.edu", "Office", "Registrar", "617-123-0002");
        RegistrarProfile registrar = rd.newRegistrarProfile(regPerson);
        System.out.println("✅ Created Registrar: Office Registrar");

        // 10 Students - ALL START CLEAN (no enrollments, no grades, no tuition)
        String[][] studentData = {
            {"student001", "John", "Smith"},
            {"student002", "Emma", "Johnson"},
            {"student003", "Michael", "Williams"},
            {"student004", "Sophia", "Brown"},
            {"student005", "William", "Jones"},
            {"student006", "Olivia", "Garcia"},
            {"student007", "James", "Miller"},
            {"student008", "Ava", "Davis"},
            {"student009", "Robert", "Rodriguez"},
            {"student010", "Isabella", "Martinez"}
        };

        for (int i = 0; i < studentData.length; i++) {
            String id = studentData[i][0];
            String firstName = studentData[i][1];
            String lastName = studentData[i][2];
            String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@northeastern.edu";
            String phone = "617-555-" + String.format("%04d", 1001 + i);

            Person p = pd.newPerson(id, email, firstName, lastName, phone);
            StudentProfile sp = sd.newStudentProfile(p);

            // Create university profile but NO enrollments
            sp.getUniversityProfile();

            System.out.println("  ✅ Student: " + firstName + " " + lastName + " (" + id + ")");
        }
        System.out.println("✅ Created 10 Students - All start with 0 credits, no enrollments\n");

        // 10 Faculty - ALL START WITH NO COURSE ASSIGNMENTS
        String[][] facultyData = {
            {"faculty001", "David", "Wilson"},
            {"faculty002", "Sarah", "Moore"},
            {"faculty003", "Thomas", "Taylor"},
            {"faculty004", "Jennifer", "Anderson"},
            {"faculty005", "Christopher", "Thomas"},
            {"faculty006", "Linda", "Jackson"},
            {"faculty007", "Daniel", "White"},
            {"faculty008", "Patricia", "Harris"},
            {"faculty009", "Matthew", "Martin"},
            {"faculty010", "Barbara", "Thompson"}
        };

        for (int i = 0; i < facultyData.length; i++) {
            String id = facultyData[i][0];
            String firstName = facultyData[i][1];
            String lastName = facultyData[i][2];
            String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@northeastern.edu";
            String phone = "617-555-" + String.format("%04d", 2001 + i);

            Person p = pd.newPerson(id, email, firstName, lastName, phone);
            FacultyProfile fp = fd.newFacultyProfile(p);

            System.out.println("  ✅ Faculty: " + firstName + " " + lastName + " (" + id + ")");
        }
        System.out.println("✅ Created 10 Faculty - All start with 0 course assignments\n");

        // 8 Additional persons (to make 30 total - these are generic placeholder persons)
        for (int i = 1; i <= 8; i++) {
            String id = "person" + String.format("%03d", i);
            String email = "person" + i + "@northeastern.edu";
            pd.newPerson(id, email, "Person", String.valueOf(i), "617-555-" + String.format("%04d", 3001 + i));
        }
        System.out.println("✅ Created 8 additional persons (total: 30 persons)\n");

        // ============================================
        // SECTION 2: CREATE USER ACCOUNTS
        // ============================================

        System.out.println("=== CREATING USER ACCOUNTS ===");
        
        uad.newUserAccount(admin, "admin", "admin");
        System.out.println("  ✅ Admin account: admin / admin");

        uad.newUserAccount(registrar, "registrar", "registrar");
        System.out.println("  ✅ Registrar account: registrar / registrar");

        for (int i = 1; i <= 10; i++) {
            StudentProfile sp = sd.findStudent("student" + String.format("%03d", i));
            if (sp != null) {
                uad.newUserAccount(sp, "student" + i, "student" + i);
                System.out.println("  ✅ Student account: student" + i + " / student" + i);
            }
        }

        for (int i = 1; i <= 10; i++) {
            FacultyProfile fp = fd.findFaculty("faculty" + String.format("%03d", i));
            if (fp != null) {
                uad.newUserAccount(fp, "faculty" + i, "faculty" + i);
                System.out.println("  ✅ Faculty account: faculty" + i + " / faculty" + i);
            }
        }

        System.out.println("✅ Created 22 user accounts\n");

        // ============================================
        // SECTION 3: CREATE COURSES (CATALOG)
        // ============================================

        System.out.println("=== CREATING COURSE CATALOG ===");
        
        Course c1 = dept.newCourse("Application Engineering", "INFO5100", 4);
        Course c2 = dept.newCourse("Web Design", "INFO6150", 4);
        Course c3 = dept.newCourse("Data Structures", "INFO6205", 4);
        Course c4 = dept.newCourse("Database Management", "DAMG6210", 4);
        Course c5 = dept.newCourse("Big Data", "CSYE7200", 4);
        Course c6 = dept.newCourse("Machine Learning", "INFO6350", 4);
        Course c7 = dept.newCourse("Network Structures", "INFO6250", 4);
        Course c8 = dept.newCourse("Software Engineering", "CSYE6200", 4);

        dept.addCoreCourse(c1); // INFO 5100 is REQUIRED core course
        System.out.println("  ✅ Core Course: " + c1.getName() + " (" + c1.getCOurseNumber() + ") - REQUIRED");

        dept.addElectiveCourse(c2);
        dept.addElectiveCourse(c3);
        dept.addElectiveCourse(c4);
        dept.addElectiveCourse(c5);
        dept.addElectiveCourse(c6);
        dept.addElectiveCourse(c7);
        dept.addElectiveCourse(c8);

        System.out.println("  ✅ Elective Courses: 7 courses");
        System.out.println("✅ Created 8 courses total (1 core + 7 electives)\n");

        // ============================================
        // SECTION 4: CREATE COURSE OFFERINGS (Fall 2025)
        // ============================================

        System.out.println("=== CREATING COURSE OFFERINGS (Fall 2025) ===");

        CourseSchedule fall2025 = dept.newCourseSchedule("Fall2025");
        System.out.println("  ✅ Created semester: Fall2025");

        // COURSE OFFERING 1: INFO5100 (CORE - Required)
        CourseOffer co1 = fall2025.newCourseOffer("INFO5100");
        if (co1 != null) {
            co1.generatSeats(30);
            co1.setRoom("Richards Hall 301");
            co1.setScheduleTime("Mon/Wed 10:00 AM - 11:30 AM");
            System.out.println("  ✅ " + c1.getCOurseNumber() + " - Capacity: 30, Room: Richards Hall 301");
        }

        // COURSE OFFERING 2: INFO6150
        CourseOffer co2 = fall2025.newCourseOffer("INFO6150");
        if (co2 != null) {
            co2.generatSeats(25);
            co2.setRoom("West Village 210");
            co2.setScheduleTime("Tue/Thu 2:00 PM - 3:30 PM");
            System.out.println("  ✅ " + c2.getCOurseNumber() + " - Capacity: 25, Room: West Village 210");
        }

        // COURSE OFFERING 3: INFO6205
        CourseOffer co3 = fall2025.newCourseOffer("INFO6205");
        if (co3 != null) {
            co3.generatSeats(30);
            co3.setRoom("Shillman 305");
            co3.setScheduleTime("Mon/Wed 2:00 PM - 3:30 PM");
            System.out.println("  ✅ " + c3.getCOurseNumber() + " - Capacity: 30, Room: Shillman 305");
        }

        // COURSE OFFERING 4: DAMG6210
        CourseOffer co4 = fall2025.newCourseOffer("DAMG6210");
        if (co4 != null) {
            co4.generatSeats(28);
            co4.setRoom("Dodge Hall 150");
            co4.setScheduleTime("Tue/Thu 10:00 AM - 11:30 AM");
            System.out.println("  ✅ " + c4.getCOurseNumber() + " - Capacity: 28, Room: Dodge Hall 150");
        }

        // COURSE OFFERING 5: CSYE7200
        CourseOffer co5 = fall2025.newCourseOffer("CSYE7200");
        if (co5 != null) {
            co5.generatSeats(20);
            co5.setRoom("Forsyth 115");
            co5.setScheduleTime("Wed 6:00 PM - 9:00 PM");
            System.out.println("  ✅ " + c5.getCOurseNumber() + " - Capacity: 20, Room: Forsyth 115");
        }
        
        System.out.println("✅ Created 5 course offerings - All have 0 students enrolled\n");

        // ============================================
        // SECTION 5: ASSIGN FACULTY TO COURSES
        // ============================================
        
        System.out.println("=== ASSIGNING FACULTY TO COURSES ===");
        
        // Faculty 1 teaches 2 courses
        FacultyProfile faculty1 = fd.findFaculty("faculty001");
        if (faculty1 != null && co1 != null) {
            co1.AssignAsTeacher(faculty1.getUniversityProfile());
            co1.setSyllabus("Introduction to Java application development using Model-View-Controller architecture. Students will build full-stack applications.");
            System.out.println("  ✅ David Wilson assigned to INFO5100");
        }

        if (faculty1 != null && co2 != null) {
            co2.AssignAsTeacher(faculty1.getUniversityProfile());
            co2.setSyllabus("Modern web design principles including HTML5, CSS3, JavaScript, and responsive design frameworks.");
            System.out.println("  ✅ David Wilson assigned to INFO6150");
        }

        // Faculty 2 teaches 1 course
        FacultyProfile faculty2 = fd.findFaculty("faculty002");
        if (faculty2 != null && co3 != null) {
            co3.AssignAsTeacher(faculty2.getUniversityProfile());
            co3.setSyllabus("Fundamental data structures and algorithms with Java implementation. Topics include trees, graphs, sorting, and searching.");
            System.out.println("  ✅ Sarah Moore assigned to INFO6205");
        }

        // Faculty 3 teaches 1 course
        FacultyProfile faculty3 = fd.findFaculty("faculty003");
        if (faculty3 != null && co4 != null) {
            co4.AssignAsTeacher(faculty3.getUniversityProfile());
            co4.setSyllabus("Database design, SQL, normalization, and database management systems. Hands-on experience with MySQL and PostgreSQL.");
            System.out.println("  ✅ Thomas Taylor assigned to DAMG6210");
        }

        // Faculty 4 teaches 1 course
        FacultyProfile faculty4 = fd.findFaculty("faculty004");
        if (faculty4 != null && co5 != null) {
            co5.AssignAsTeacher(faculty4.getUniversityProfile());
            co5.setSyllabus("Big data technologies including Hadoop, Spark, MapReduce, and distributed systems for large-scale data processing.");
            System.out.println("  ✅ Jennifer Anderson assigned to CSYE7200");
        }

        System.out.println("✅ Assigned 4 faculty to 5 courses\n");
        System.out.println("⚠️  Faculty 5-10 have NO course assignments (available for Registrar to assign)\n");

        // ============================================
        // SECTION 6: SUMMARY
        // ============================================
        
        System.out.println("========================================");
        System.out.println("   INITIALIZATION COMPLETE");
        System.out.println("========================================");
        System.out.println("SUMMARY:");
        System.out.println("  • 30 Persons created");
        System.out.println("  • 10 Students (0 credits each, no enrollments)");
        System.out.println("  • 10 Faculty (4 assigned, 6 unassigned)");
        System.out.println("  • 1 Admin, 1 Registrar");
        System.out.println("  • 8 Courses in catalog");
        System.out.println("  • 5 Course offerings (Fall2025) - All empty");
        System.out.println("  • 22 User accounts created");
        System.out.println("\nREADY FOR TESTING!");
        System.out.println("  → Students can enroll in courses");
        System.out.println("  → Faculty can grade assignments");
        System.out.println("  → Registrar can manage offerings");
        System.out.println("  → Admin can manage all users");
        System.out.println("========================================\n");

        return business;
    }
}