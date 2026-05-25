import java.util.*;

public class HospitalSystem {
    private Scanner scanner = new Scanner(System.in);
    private systemManager manager = new systemManager(); 
    private FileManager fileManager = new FileManager();

    public void startSystem() {
        int choice = -1;
        while (choice != 4) {
            System.out.println("\n===== Hospital Management System =====");
            System.out.println("1. Login as Admin"); 
            System.out.println("2. Login as Doctor"); 
            System.out.println("3. Login as Patient"); 
            System.out.println("4. Exit"); 
            System.out.print("Enter your choice: ");
            choice = getIntInput();

            switch (choice) {
                case 1 -> adminLogin();
                case 2 -> doctorLogin();
                case 3 -> patientLogin();
                case 4 -> {
                    manager.saveAllData();
                    System.out.println("Data saved. System closed.");
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private void adminLogin() {
        System.out.println("\n===== Admin Login =====");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (fileManager.checkAdminLogin(username, password)) { 
            System.out.println("Login successful!");
            adminMenu();
        } else {
            System.out.println("Invalid username or password!");
        }
    }

    private void doctorLogin() {
        System.out.println("\n===== Doctor Login =====");
        System.out.print("Enter Username: ");
        String user = scanner.nextLine();
        System.out.print("Enter Password: ");
        String pass = scanner.nextLine();

        for (Doctor d : manager.getDoctorsList()) { 
            if (d.checkLogin(user, pass)) {
                System.out.println("Login successful!");
                doctorMenu(d);
                return;
            }
        }
        System.out.println("Invalid credentials!");
    }

    private void patientLogin() {
        System.out.println("\n===== Patient Login =====");
        System.out.print("Enter Username: ");
        String user = scanner.nextLine();
        System.out.print("Enter Password: ");
        String pass = scanner.nextLine();

        for (Patient p : manager.getPatientsList()) { 
            if (p.checkLogin(user, pass)) {
                System.out.println("Login successful!");
                patientMenu(p);
                return;
            }
        }
        System.out.println("Invalid credentials!");
    }

    private void adminMenu() {
        int choice = -1;
        while (choice != 12) {
            System.out.println("\n===== Admin Menu =====");
            System.out.println("1. Add Doctor\n2. Register Patient\n3. Assign Patient to Doctor");
            System.out.println("4. Create Appointment\n5. View All Doctors\n6. View All Patients"); 
            System.out.println("7. View All Appointments\n8. Search Patient by ID\n9. Search Doctor by ID"); 
            System.out.println("10. Generate Reports\n11. Save Data\n12. Logout"); 
            System.out.print("Choice: ");
            choice = getIntInput();

            switch (choice) {
                case 1 -> addDoctor();
                case 2 -> registerPatient();
                case 3 -> assignPatientToDoctor();
                case 4 -> createAppointment();
                case 5 -> Doctor.viewAllDoctors(manager.getDoctorsList()); 
                case 6 -> Patient.viewAllPatients(manager.getPatientsList()); 
                case 7 -> Appointment.viewAllAppointments(manager.getAppointmentsList()); 
                case 8 -> searchByID(manager.getPatientsList());
                case 9 -> searchByID(manager.getDoctorsList()); 
                case 10 -> manager.generateReports();
                case 11 -> {
                    manager.saveAllData();
                    System.out.println("Data saved successfully!");
                }
            }
        }
    }

    private void doctorMenu(Doctor d) {
        int choice = -1;
        while (choice != 5) {
            System.out.println("\n--- Welcome Dr. " + d.getName() + " ---");
            System.out.println("1. View Profile\n2. View Assigned Patients\n3. View My Appointments"); 
            System.out.println("4. Update Appointment Status\n5. Logout"); 
            System.out.print("Choice: ");
            choice = getIntInput();
            switch (choice) {
                case 1 -> d.displayInfo();
                case 2 -> d.viewPatients(manager.getPatientsList()); 
                case 3 -> d.viewAppointments(manager.getAppointmentsList()); 
                case 4 -> updateStatus(d); 
                case 5 -> System.out.println("Logging out...");
            }
        }
    }

    private void patientMenu(Patient p) {
        int choice = -1;
        while (choice != 6) {
            System.out.println("\n--- Welcome " + p.getName() + " ---");
            System.out.println("1. View Profile\n2. View Assigned Doctor\n3. View My Appointments");
            System.out.println("4. Book Appointment\n5. Cancel Appointment\n6. Logout");
            System.out.print("Choice: ");
            choice = getIntInput();
            switch (choice) {
                case 1 -> p.displayInfo(); 
                case 2 -> p.viewDoctor(manager.getDoctorsList()); 
                case 3 -> p.viewAppointments(manager.getAppointmentsList());
                case 4 -> bookForPatient(p);
                case 5 -> cancelForPatient(p); 
                case 6 -> System.out.println("Logging out...");
            }
        }
    }

    private void addDoctor() {
        System.out.println("\n--- Add New Doctor ---");
        System.out.print("ID: "); String id = scanner.nextLine();
        System.out.print("Name: "); String name = scanner.nextLine();
        System.out.print("Username: "); String user = scanner.nextLine();
        System.out.print("Password: "); String pass = scanner.nextLine();
        System.out.print("Phone: "); String ph = scanner.nextLine();
        System.out.print("Specialization: "); String sp = scanner.nextLine();
        System.out.print("Department: "); String dp = scanner.nextLine();
        
        manager.addDoctor(new Doctor(id, name, user, pass, ph, sp, dp)); 
        fileManager.saveUser(user, pass, "doctor"); 
        System.out.println("Doctor added successfully.");
    }

    private void registerPatient() {
        System.out.println("\n--- Register New Patient ---");
        System.out.print("ID: "); String id = scanner.nextLine();
        System.out.print("Name: "); String name = scanner.nextLine();
        System.out.print("Username: "); String user = scanner.nextLine();
        System.out.print("Password: "); String pass = scanner.nextLine();
        System.out.print("Phone: "); String ph = scanner.nextLine();
        System.out.print("Age: "); int age = getIntInput();
        System.out.print("Gender: "); String gen = scanner.nextLine();
        
        manager.registerPatient(new Patient(id, name, user, pass, ph, age, gen)); 
        fileManager.saveUser(user, pass, "patient");
        System.out.println("Patient registered successfully.");
    }

    private void assignPatientToDoctor() {
        System.out.print("Patient ID: "); String pid = scanner.nextLine();
        System.out.print("Doctor ID: "); String did = scanner.nextLine();
        Patient p = manager.findPatientById(pid);
        Doctor d = manager.findDoctorById(did);
        if (p != null && d != null) {
            manager.assignPatientToDoctor(p, d);
            System.out.println("Assigned Successfully!");
        } else {
            System.out.println("Error: One of the IDs not found.");
        }
    }

    private void createAppointment() { 
        System.out.print("Appointment ID: "); String aid = scanner.nextLine();
        System.out.print("Patient ID: "); String pid = scanner.nextLine();
        System.out.print("Doctor ID: "); String did = scanner.nextLine();
        
        System.out.print("Date (YYYY-MM-DD): "); String date = scanner.nextLine();
        System.out.print("Time (HH:MM): "); String time = scanner.nextLine();

        if (manager.isDoctorBusy(did, date, time)) { 
            System.out.println("Doctor is busy at this time!");
            return;
        }
        manager.addAppointment(new Appointment(aid, pid, did, date, time)); 
        System.out.println("Appointment created successfully.");
    }

    private void bookForPatient(Patient p) {
        if (p.getAssignedDoctorId() == null) { 
            System.out.println("You must be assigned to a doctor first!");
            return;
        }
        System.out.print("Appointment ID: "); String aid = scanner.nextLine();
        System.out.print("Date (YYYY-MM-DD): "); String date = scanner.nextLine();
        System.out.print("Time (HH:MM): "); String time = scanner.nextLine();

        if (manager.isDoctorBusy(p.getAssignedDoctorId(), date, time)) {
            System.out.println("Doctor is busy at this time!");
            return;
        }
        manager.addAppointment(new Appointment(aid, p.getId(), p.getAssignedDoctorId(), date, time));
        System.out.println("Appointment booked.");
    }

    private void cancelForPatient(Patient p) { 
        System.out.print("Enter Appointment ID to cancel: ");
        String aid = scanner.nextLine();
        for (Appointment a : manager.getAppointmentsList()) {
            if (a.getId().equals(aid) && a.getPatientId().equals(p.getId())) {
                a.cancel();
                System.out.println("Appointment cancelled.");
                return;
            }
        }
        System.out.println("Appointment not found.");
    }

    private void updateStatus(Doctor d) { 
        System.out.print("Enter Appointment ID: ");
        String aid = scanner.nextLine();
        for (Appointment a : manager.getAppointmentsList()) {
            if (a.getId().equals(aid) && a.getDoctorId().equals(d.getId())) {
                System.out.println("Choose Status: 1. Confirm 2. Complete 3. Cancel");
                a.updateStatus(getIntInput());
                System.out.println("Status updated.");
                return;
            }
        }
        System.out.println("Appointment not found or not assigned to you.");
    }

    private void searchByID(List<? extends User> list) {
        System.out.print("Enter ID: ");
        String id = scanner.nextLine();
        for (User u : list) {
            if (u.getId().equals(id)) {
                u.displayInfo();
                return;
            }
        }
        System.out.println("ID not found.");
    }

    private int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input! Please enter a number: ");
            scanner.next();
        }
        int i = scanner.nextInt();
        scanner.nextLine(); 
        return i;
    }
}