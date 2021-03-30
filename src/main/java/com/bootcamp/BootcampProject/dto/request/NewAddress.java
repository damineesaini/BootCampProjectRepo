package com.bootcamp.BootcampProject.dto.request;

public class NewAddress {
    private String addressLine;
    private String city;
    private String state;
    private String country;
    private int zipcode;
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
