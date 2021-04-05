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
    @Pattern(regexp = "^(\\\\+\\\\d{1,3}( )?)?((\\\\(\\\\d{1,3}\\\\))|\\\\d{1,3})[- .]?\\\\d{3,4}[- .]?\\\\d{4}$")
    @NotNull
    private long contact;
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
    @Size(min = 6,max = 6)
    private int zipcode;
    @Pattern(regexp = "[a-zA-Z][a-zA-Z ]*")
    @NotNull
    private String label;

    public CustomerRegister(String firstName, String middleName, String lastName, String email, long contact, String password, String confirmPassword, String city, String state, String country, String addressLine, int zipcode, String label) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.contact = contact;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.city = city;
        this.state = state;
        this.country = country;
        this.addressLine = addressLine;
        this.zipcode = zipcode;
        this.label = label;
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

    public long getContact() {
        return contact;
    }

    public void setContact(long contact) {
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
