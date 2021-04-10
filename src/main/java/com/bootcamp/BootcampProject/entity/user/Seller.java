package com.bootcamp.BootcampProject.entity.user;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "seller")
public class Seller{
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @OneToOne(targetEntity = User.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User userId;
    private String gst;
    @Column(name = "company_contact")
    private String companyContactNo;
    @Column(name = "company_name")
    private String companyName;

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getCompanyContactNo() {
        return companyContactNo;
    }

    public void setCompanyContactNo(String companyContactNo) {
        this.companyContactNo = companyContactNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
