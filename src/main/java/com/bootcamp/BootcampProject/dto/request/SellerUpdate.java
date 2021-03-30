package com.bootcamp.BootcampProject.dto.request;

public class SellerUpdate {
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String companyName;
    private long companyContactNo;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public long getCompanyContactNo() {
        return companyContactNo;
    }

    public void setCompanyContactNo(long companyContactNo) {
        this.companyContactNo = companyContactNo;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }
}
