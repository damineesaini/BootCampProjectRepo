package com.bootcamp.BootcampProject.dto.request;

import com.bootcamp.BootcampProject.utility.ValidationRegex;

import javax.validation.constraints.*;

public class NewAddress {
    @NotNull(message = "address line cannot be null")
    @Pattern(regexp = ValidationRegex.ADDRESSlINE,message = "Invalid format for address line")
    private String addressLine;
    @NotNull
    @Pattern(regexp = ValidationRegex.ISALPHA,message = "invalid city name")
    private String city;
    @Pattern(regexp = ValidationRegex.ISALPHA)
    @NotNull
    private String state;
    @Pattern(regexp = ValidationRegex.ISALPHA)
    @NotNull
    private String country;
    @Positive
    @Min(100000)
    @Max(999999)
    private int zipcode;
    @Pattern(regexp = ValidationRegex.ISALPHA)
    @NotNull
    private String label;

    public NewAddress(String addressLine, String city, String state, String country, int zipcode, String label) {
        this.addressLine = addressLine;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipcode = zipcode;
        this.label = label;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
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
