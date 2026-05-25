public class Admin extends User {
  
    public Admin(String id, String name, String username, String password, String phoneNumber) {
        super(id, name, username, password, phoneNumber);
    }

    @Override
    public void displayInfo() {
        System.out.println("Admin Name: " + name);
        System.out.println("Admin ID: " + id);
    }
}