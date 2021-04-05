package com.bootcamp.BootcampProject.dto.request;

import javax.validation.constraints.*;

public class CustomerRegister {
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
    @Pattern(regexp = "(0/91)?[7-9][0-9]{9}")
    @NotNull
    private String contact;
    @Pattern(regexp = "^(?=.*?[A-Z])(?=(.*[a-z]){1,})(?=(.*[\\d]){1,})(?=(.*[\\W]){1,})(?!.*\\s).{8,}$")
    private String password;
    @Pattern(regexp = "^(?=.*?[A-Z])(?=(.*[a-z]){1,})(?=(.*[\\d]){1,})(?=(.*[\\W]){1,})(?!.*\\s).{8,}$")
    private String confirmPassword;
    @Pattern(regexp = "[a-zA-Z][a-zA-Z ]*")
    @NotNull
    private String city;
    @Pattern(regexp = "[a-zA-Z][a-zA-Z ]*")
    @NotNull
    private String state;
    @Pattern(regexp = "[a-zA-Z][a-zA-Z ]*")
    @NotNull
    private String country;
    @NotNull
    @Pattern(regexp ="[A-Za-z0-9'\\.\\-\\s\\,]")
    private String addressLine;
    @Positive
    @Min(100000)
    @Max(999999)
    private int zipcode;
    @Pattern(regexp = "[a-zA-Z][a-zA-Z ]*")
    @NotNull
    private String label;

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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        confirmPassword = confirmPassword;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
