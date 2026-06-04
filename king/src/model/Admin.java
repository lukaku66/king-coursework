package model;

public class Admin extends Person {

    public Admin(String id, String name, String password) {
        super(id, name, password);
    }

    @Override
    public boolean login(String inputPassword) { return false; }

    @Override
    public String getRole() { return null; }

    @Override
    public String toString() { return null; }
}