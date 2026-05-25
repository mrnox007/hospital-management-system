import java.util.ArrayList;
import java.util.List;

public class Doctor extends User {
    private String specialization; 
    private String department;  
    private List<String> assignedPatients; 
    private List<String> appointments;  

    public Doctor(String id, String name, String username, String password,  String phoneNumber, String specialization, String department) {
        super(id, name, username, password, phoneNumber); 
        this.specialization = specialization;
        this.department = department;
        this.assignedPatients = new ArrayList<>();
        this.appointments = new ArrayList<>();
    }

    public String getSpecialization() { return specialization; }
    public String getDepartment() { return department; }
    public List<String> getAssignedPatients() { return assignedPatients; }
    public List<String> getAppointments() { return appointments; }

    public void addPatient(String patientId) {
        assignedPatients.add(patientId);
    }

    public void addAppointment(String appointmentId) {
        appointments.add(appointmentId);
    }

    @Override
    public void displayInfo() {
        System.out.println("Doctor ID: " + id + " | Name: " + name);
        System.out.println("Specialization: " + specialization + " | Dept: " + department);
        System.out.println("Phone: " + phoneNumber); // [cite: 25]
    }

    public static void viewAllDoctors(List<Doctor> doctorsList) { 
        System.out.println("\n===== Doctors List =====");
        if (doctorsList.isEmpty()) {
            System.out.println("No doctors found.");
            return;
        }
        for (Doctor d : doctorsList) {
            d.displayInfo();
            System.out.println("----------------");
        }
    }

    public void viewPatients(List<Patient> patientsList) { 
        System.out.println("\n===== My Assigned Patients =====");
        boolean found = false;
        for (Patient p : patientsList) {
            if (p.getAssignedDoctorId() != null && p.getAssignedDoctorId().equals(this.id)) {
                p.displayInfo();
                System.out.println("----------------");
                found = true;
            }
        }
        if (!found) System.out.println("No patients assigned.");
    }

    public void viewAppointments(List<Appointment> appointmentsList) {
        System.out.println("\n===== My Appointments =====");
        boolean found = false;
        for (Appointment a : appointmentsList) {
            if (a.getDoctorId().equals(this.id)) {
                a.display();
                System.out.println("----------------");
                found = true;
            }
        }
        if (!found) System.out.println("No appointments found.");
    }
}