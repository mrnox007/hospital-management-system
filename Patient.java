import java.util.ArrayList;
import java.util.List;

public class Patient extends User {
    private int age; // 
    private String gender; 
    private String assignedDoctorId; 
    private List<String> appointments; 

    public Patient(String id, String name, String username, String password, String phoneNumber, int age, String gender) {
        super(id, name, username, password, phoneNumber); 
        if (age <= 0) {
        throw new IllegalArgumentException("Invalid age: Age must be positive.");
    }
        this.age = age;
        this.gender = gender;
        this.appointments = new ArrayList<>();
    }

    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getAssignedDoctorId() { return assignedDoctorId; }
    public List<String> getAppointments() { return appointments; }

    public void setAssignedDoctorId(String doctorId) { 
        this.assignedDoctorId = doctorId;
    }

    public void addAppointment(String appointmentId) { 
        appointments.add(appointmentId);
    }

    @Override
    public void displayInfo() {
        System.out.println("Patient ID: " + id + " | Name: " + name);
        System.out.println("Age: " + age + " | Gender: " + gender); 
        System.out.println("Phone: " + phoneNumber);
    }

    public static void viewAllPatients(List<Patient> patientsList) { 
        System.out.println("\n===== Patients List =====");
        if (patientsList.isEmpty()) {
            System.out.println("No patients found.");
            return;
        }
        for (Patient p : patientsList) {
            p.displayInfo();
            System.out.println("----------------");
        }
    }

    public void viewDoctor(List<Doctor> doctorsList) {
        System.out.println("\n===== My Doctor =====");
        if (assignedDoctorId == null) {
            System.out.println("No doctor assigned.");
            return;
        }
        for (Doctor d : doctorsList) {
            if (d.getId().equals(assignedDoctorId)) {
                d.displayInfo(); // [cite: 29]
                return;
            }
        }
        System.out.println("Doctor details not found.");
    }

    public void viewAppointments(List<Appointment> appointmentsList) { 
        System.out.println("\n===== My Appointments =====");
        boolean found = false;
        for (Appointment a : appointmentsList) {
            if (a.getPatientId().equals(this.id)) { 
                a.display(); // [cite: 11]
                System.out.println("----------------");
                found = true;
            }
        }
        if (!found) System.out.println("No appointments found.");
    }
}