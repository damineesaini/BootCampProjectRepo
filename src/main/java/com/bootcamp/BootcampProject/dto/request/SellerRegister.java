package com.bootcamp.BootcampProject.dto.request;

import com.bootcamp.BootcampProject.utility.ValidationRegex;

import javax.validation.constraints.*;

public class SellerRegister {
    @Pattern(regexp = ValidationRegex.ISALPHA)
    @NotNull
    private String firstName;
    @Pattern(regexp = ValidationRegex.ISALPHA)
    private String middleName;
    @Pattern(regexp = ValidationRegex.ISALPHA)
    @NotNull
    private String lastName;
    @Email(message = "Email should be valid")
    private String email;
    @Pattern(regexp = ValidationRegex.PASSWORD)
    private String password;
    @Pattern(regexp = ValidationRegex.PASSWORD)
    private String confirmPassword;
    @Pattern(regexp = ValidationRegex.ISALPHA)
    @NotNull
    private String companyName;
    @Pattern(regexp = ValidationRegex.PHONE)
    @NotNull
    private String companyContactNo;
    @NotNull
    @Pattern(regexp = ValidationRegex.GST)
    private String gst;
    @Pattern(regexp = ValidationRegex.ISALPHA)
    @NotNull
    private String city;
    @Pattern(regexp = ValidationRegex.ISALPHA)
    @NotNull
    private String state;
    @Pattern(regexp = ValidationRegex.ISALPHA)
    @NotNull
    private String country;
    @NotNull
    @Pattern(regexp =ValidationRegex.ADDRESSlINE)
    private String addressLine;
    @Positive
    @Min(100000)
    @Max(999999)
    private int zipcode;
    @Pattern(regexp = ValidationRegex.ISALPHA)
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
        this.confirmPassword = confirmPassword;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyContactNo() {
        return companyContactNo;
    }

    public void setCompanyContactNo(String companyContactNo) {
        this.companyContactNo = companyContactNo;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
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
