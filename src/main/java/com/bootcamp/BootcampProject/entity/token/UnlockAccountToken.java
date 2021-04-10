package com.bootcamp.BootcampProject.entity.token;

import com.bootcamp.BootcampProject.entity.user.User;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity
public class UnlockAccountToken {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    private String unlockAccountToken;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expiryDate;

    @OneToOne(targetEntity = User.class,fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(nullable = false,name = "user_id")
    private User user;

    public UnlockAccountToken() {
    }

    public UnlockAccountToken(User user) {
        this.user = user;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        createDate=new Date(calendar.getTime().getTime());
        calendar.add(Calendar.HOUR,2);
        expiryDate=new Date(calendar.getTime().getTime());
        unlockAccountToken=UUID.randomUUID().toString();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUnlockAccountToken() {
        return unlockAccountToken;
    }

    public void setUnlockAccountToken(String unlockAccountToken) {
        this.unlockAccountToken = unlockAccountToken;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
