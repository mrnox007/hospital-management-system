import java.io.*;
import java.util.*;

public class FileManager {

    public void saveDoctors(List<Doctor> doctors) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("doctors.txt"))) {
            for (Doctor d : doctors) { 
                pw.println(d.getId() + "," + d.getName() + "," + d.getUsername() + "," +
                           d.getPassword() + "," + d.getSpecialization() + "," +
                           d.getDepartment() + "," + d.getPhoneNumber());
            }
        } catch (IOException e) {
            System.out.println("Error saving doctors file ");
        }
    }

    public List<Doctor> loadDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        File file = new File("doctors.txt");
        if (!file.exists()) return doctors;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                if (d.length >= 7) {
                    doctors.add(new Doctor(d[0], d[1], d[2], d[3], d[6], d[4], d[5]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading doctors ");
        }
        return doctors;
    }

    public void savePatients(List<Patient> patients) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("patients.txt"))) {
            for (Patient p : patients) {
                pw.println(p.getId() + "," + p.getName() + "," + p.getUsername() + "," +
                           p.getPassword() + "," + p.getAge() + "," + p.getGender() + "," +
                           p.getPhoneNumber() + "," + p.getAssignedDoctorId());
            }
        } catch (IOException e) {
            System.out.println("Error saving patients file ");
        }
    }

    public List<Patient> loadPatients() {
        List<Patient> patients = new ArrayList<>();
        File file = new File("patients.txt");
        if (!file.exists()) return patients;  

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                if (d.length >= 7) {
                    Patient p = new Patient(d[0], d[1], d[2], d[3], d[6], Integer.parseInt(d[4]), d[5]);
                    if (d.length > 7 && !d[7].equals("null")) p.setAssignedDoctorId(d[7]);
                    patients.add(p);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading patients ");
        }
        return patients;
    }
    
    public void saveAppointments(List<Appointment> apps) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("appointments.txt"))) {
            for (Appointment a : apps) {
                pw.println(a.getId() + "," + a.getPatientId() + "," + a.getDoctorId() + "," +
                           a.getDate() + "," + a.getTime() + "," + a.getStatus());
            }
        } catch (IOException e) {
            System.out.println("Error saving appointments file ");
        }
    }

    public List<Appointment> loadAppointments() {
        List<Appointment> apps = new ArrayList<>();
        File file = new File("appointments.txt");
        if (!file.exists()) return apps;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                if (d.length >= 6) {
                    Appointment a = new Appointment(d[0], d[1], d[2], d[3], d[4]);
                    a.setStatus(d[5]);
                    apps.add(a);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading appointments ");
        }
        return apps;
    }

    public boolean checkAdminLogin(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(username) && data[1].equals(password) && data[2].equals("admin")) {
                    return true;
                }
            }
        } catch (IOException e) { return false; }
        return false;
    }

    public void saveUser(String user, String pass, String role) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("users.txt", true))) {
            pw.println(user + "," + pass + "," + role);
        } catch (IOException e) {
            System.out.println("Error saving login details ");
        }
    }
}