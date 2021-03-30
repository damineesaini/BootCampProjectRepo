package com.bootcamp.BootcampProject.dto.request;

public class CustomerUpdate {
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private long contactNo;

    public CustomerUpdate(String firstName, String middleName, String lastName, String email, long contactNo) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.contactNo = contactNo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getContactNo() {
        return contactNo;
    }

    public void setContactNo(long contactNo) {
        this.contactNo = contactNo;
    }
}
