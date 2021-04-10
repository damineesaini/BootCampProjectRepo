package com.bootcamp.BootcampProject.dto.request;

import com.bootcamp.BootcampProject.utility.ValidationRegex;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class SellerUpdate {
    @Pattern(regexp = ValidationRegex.ISALPHA)
    @NotNull
    private String firstName;
    @Pattern(regexp = ValidationRegex.ISALPHA)
    private String middleName;
    @Pattern(regexp = ValidationRegex.ISALPHA)
    @NotNull
    private String lastName;
    @Pattern(regexp = ValidationRegex.ISALPHA)
    @NotNull
    private String companyName;
    @Pattern(regexp = ValidationRegex.PHONE)
    @NotNull
    private String companyContactNo;
    @NotNull
    @Pattern(regexp = ValidationRegex.GST)
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
