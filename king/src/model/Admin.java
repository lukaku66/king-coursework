package model;

public class Admin extends Person {
    private static final long serialVersionUID = 1L;

    public Admin(String id, String name, String password) {
        super(id, name, password);
    }

    @Override
    public boolean login(String inputPassword) {
        return getPassword().equals(inputPassword);
    }

    @Override
    public String getRole() {
        return "Admin";
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - Admin (Full Access)", getId(), getName());
    }
}
