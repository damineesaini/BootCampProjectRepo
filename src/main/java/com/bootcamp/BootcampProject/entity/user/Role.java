package com.bootcamp.BootcampProject.entity.user;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    private String authority;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}

/*
* create table role(
*   id integer primary_key auto_increment,
*   authority varchar(10)
* );
*
* create table users_roles(
*   user_id int,
*   role_id int,
*   foreign key (user_id)
    references user(id),
    * foreign key (role_id)
    references role(id),
* );
* */