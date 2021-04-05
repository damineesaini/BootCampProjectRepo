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
    private long companyContactNo;
    @Column(name = "company_name")
    private String companyName;

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public long getCompanyContactNo() {
        return companyContactNo;
    }

    public void setCompanyContactNo(long companyContactNo) {
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
}

/*
 * create table seller(
 *   user_id int,
 *   gst varchar(12),
 *   company_contact_no varchar(12),
 *   company_name varchar(30),
 * foreign key (user_id)
    references user(id),
 * );*/