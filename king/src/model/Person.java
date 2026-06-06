package model;

public abstract class Person implements Searchable {
    private String id;
    private String name;
    private String password;

    public Person(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public abstract boolean login(String inputPassword);
    public abstract String getRole();

    @Override
    public String toString() {
        return String.format("[%s] %s - %s", id, name, getRole());
    }
}