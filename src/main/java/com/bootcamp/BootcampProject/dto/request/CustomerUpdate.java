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
    @Email(message = "Email should be valid")
    private String email;
    @Pattern(regexp = "^(\\\\+\\\\d{1,3}( )?)?((\\\\(\\\\d{1,3}\\\\))|\\\\d{1,3})[- .]?\\\\d{3,4}[- .]?\\\\d{4}$")
    @NotNull
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
