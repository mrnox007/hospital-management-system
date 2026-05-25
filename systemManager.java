import java.util.*;

public class systemManager {
    private List<Doctor> doctorsList;
    private List<Patient> patientsList;
    private List<Appointment> appointmentsList;
    private FileManager fileManager = new FileManager();

    public systemManager() {
     
        doctorsList = fileManager.loadDoctors();
        patientsList = fileManager.loadPatients();
        appointmentsList = fileManager.loadAppointments();
    }

    public void addDoctor(Doctor d) {
        doctorsList.add(d);
    }

    public void registerPatient(Patient p) {
        patientsList.add(p);
    }

    public void assignPatientToDoctor(Patient p, Doctor d) {
        p.setAssignedDoctorId(d.getId());
        d.addPatient(p.getId());
    }

    public Doctor findDoctorById(String id) {
        for (Doctor d : doctorsList) if (d.getId().equals(id)) return d;
        return null;
    }

    public Patient findPatientById(String id) {
        for (Patient p : patientsList) if (p.getId().equals(id)) return p;
        return null;
    }

    public boolean isDoctorBusy(String did, String date, String time) {
        for (Appointment a : appointmentsList) {
            if (a.getDoctorId().equals(did) && a.getDate().equals(date) && a.getTime().equals(time)) {
                return true;
            }
        }
        return false;
    }

    public void addAppointment(Appointment app) {
        appointmentsList.add(app);
    }

    public void generateReports() {
        System.out.println("\n--- Hospital Reports ---");
        System.out.println("Total Doctors: " + doctorsList.size());
        System.out.println("Total Patients: " + patientsList.size());
        int conf = 0, canc = 0, comp = 0;
        for (Appointment a : appointmentsList) {
            if (a.getStatus().equalsIgnoreCase("Confirmed")) conf++;
            else if (a.getStatus().equalsIgnoreCase("Cancelled")) canc++;
            else if (a.getStatus().equalsIgnoreCase("Completed")) comp++;
        }
        System.out.println("Appointments - Confirmed: " + conf + ", Cancelled: " + canc + ", Completed: " + comp);
    }

    public void saveAllData() {
        fileManager.saveDoctors(doctorsList);
        fileManager.savePatients(patientsList);
        fileManager.saveAppointments(appointmentsList);
    }

    public List<Doctor> getDoctorsList() { return doctorsList; }
    public List<Patient> getPatientsList() { return patientsList; }
    public List<Appointment> getAppointmentsList() { return appointmentsList; }
}