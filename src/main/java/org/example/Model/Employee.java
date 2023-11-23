package org.example.Model;

public class Employee {
    private int id;
    private String firstName;
    private int identityNumber;

    public Employee() {
    }

    public Employee(String firstName, int identityNumber) {
        this.firstName = firstName;
        this.identityNumber = identityNumber;
    }

    public Employee(int id, String firstName, int identityNumber) {
        this.id = id;
        this.firstName = firstName;
        this.identityNumber = identityNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(int identityNumber) {
        this.identityNumber = identityNumber;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", identityNumber=" + identityNumber +
                '}';
    }
}
