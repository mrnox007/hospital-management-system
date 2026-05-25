import java.util.List;

public class Appointment {
    private String id;
    private String patientId; 
    private String doctorId; 
    private String date; 
    private String time;
    private String status;

    public Appointment(String id, String patientId, String doctorId, String date, String time) {
        if (id == null || id.isEmpty()) throw new IllegalArgumentException("ID required");
        if (date == null || date.isEmpty()) throw new IllegalArgumentException("Date required");
        if (time == null || time.isEmpty()) throw new IllegalArgumentException("Time required");

        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
        this.status = "Pending";
    }

    public String getId() { return id; }
    public String getPatientId() { return patientId; }
    public String getDoctorId() { return doctorId; }
    public String getStatus() { return status; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    
    public void setStatus(String status) { 
        this.status = status; 
    }

    public void confirm() {
        if (status.equalsIgnoreCase("Cancelled")) {
            System.out.println("Error: Cannot confirm a cancelled appointment.");
            return;
        }
        status = "Confirmed";
    }

    public void complete() {
        if (status.equalsIgnoreCase("Cancelled")) {
            System.out.println("Error: A cancelled appointment cannot be marked as completed.");
            return;
        }
        status = "Completed";
    }

    public void cancel() {
        status = "Cancelled";
    }

    public void display() {
        System.out.println("Appointment ID: " + id); // [cite: 61]
        System.out.println("Patient ID: " + patientId + " | Doctor ID: " + doctorId);
        System.out.println("Date: " + date + " | Time: " + time);
        System.out.println("Status: " + status);
    }

    public static void viewAllAppointments(List<Appointment> appointmentsList) {
        System.out.println("\n===== Global Appointments List =====");
        if (appointmentsList.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }
        for (Appointment a : appointmentsList) {
            a.display();
            System.out.println("----------------");
        }
    }

    public void updateStatus(int choice) {
        switch (choice) {
            case 1 -> confirm();
            case 2 -> complete();
            case 3 -> cancel();
            default -> System.out.println("Invalid status choice!");
        }
    }
}