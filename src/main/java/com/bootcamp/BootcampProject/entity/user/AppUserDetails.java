package com.bootcamp.BootcampProject.entity.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class AppUserDetails implements UserDetails {

    private String username;
    private String password;
    private boolean isActive;
    private boolean isDeleted;
    private int loginAttempts;
    private boolean isLocked;
    Collection<? extends GrantedAuthority> authorities;

    public AppUserDetails(User byUsername){
        this.username = byUsername.getEmail();
        this.password=byUsername.getPassword();
        List<GrantedAuthority> auths = new ArrayList<>();
        for (Role role:byUsername.getRoles()) {
            auths.add((new SimpleGrantedAuthority(role.getAuthority().toUpperCase(Locale.ROOT))));
        }
        this.authorities=auths;
        this.isActive= byUsername.isActive();
        this.isDeleted = byUsername.isNotDeleted();
        this.isLocked = byUsername.isNotLocked();
        this.loginAttempts=byUsername.getLoginAttempts();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isDeleted;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public int getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }
}
