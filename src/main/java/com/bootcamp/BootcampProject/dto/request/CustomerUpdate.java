package com.bootcamp.BootcampProject.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CustomerUpdate {
    @Pattern(regexp = "[a-zA-Z][a-zA-Z ]*")
    @NotNull
    private String firstName;
    @Pattern(regexp = "[a-zA-Z][a-zA-Z ]*")
    private String middleName;
    @Pattern(regexp = "[a-zA-Z][a-zA-Z ]*")
    @NotNull
    private String lastName;
    @Pattern(regexp = "(0/91)?[7-9][0-9]{9}")
    @NotNull
    private String contactNo;

    public CustomerUpdate(String firstName, String middleName, String lastName, String contactNo) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
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

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
}
