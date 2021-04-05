package com.bootcamp.BootcampProject.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class SellerUpdate {
    @Pattern(regexp = "[a-zA-Z][a-zA-Z ]*")
    @NotNull
    private String firstName;
    @Pattern(regexp = "[a-zA-Z][a-zA-Z ]*")
    private String middleName;
    @Pattern(regexp = "[a-zA-Z][a-zA-Z ]*")
    @NotNull
    private String lastName;
    @Pattern(regexp = "[a-zA-Z][a-zA-Z ]*")
    @NotNull
    private String companyName;
    @Pattern(regexp = "(0/91)?[7-9][0-9]{9}")
    @NotNull
    private String companyContactNo;
    @NotNull
    @Pattern(regexp = "\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[A-Z\\d]{1}[Z]{1}[A-Z\\d]{1}")
    private String gst;

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
}
