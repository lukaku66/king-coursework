package model;

public abstract class Person {
    private String id;
    private String name;
    private String password;

    public Person(String id, String name, String password) {
        // TODO: assign fields
    }

    public String getId() { return null; }
    public void setId(String id) { }
    public String getName() { return null; }
    public void setName(String name) { }
    public String getPassword() { return null; }
    public void setPassword(String password) { }

    public abstract boolean login(String inputPassword);
    public abstract String getRole();

    @Override
    public String toString() { return null; }
}