package com.bootcamp.BootcampProject.dto.request;

import com.bootcamp.BootcampProject.utility.ValidationRegex;

import javax.validation.constraints.Pattern;

public class CustomerUpdate {
    @Pattern(regexp = ValidationRegex.ISALPHA)
    private String firstName;
    @Pattern(regexp = ValidationRegex.ISALPHA)
    private String middleName;
    @Pattern(regexp = ValidationRegex.ISALPHA)
    private String lastName;
    @Pattern(regexp = ValidationRegex.PHONE)
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
