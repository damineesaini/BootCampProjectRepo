package com.bootcamp.BootcampProject.security;

import com.bootcamp.BootcampProject.entity.user.AppUserDetails;
import com.bootcamp.BootcampProject.exception.UserNotFoundException;
import com.bootcamp.BootcampProject.service.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    UserDaoService userDaoService;

    AppUserDetails appUserDetails;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        try {
            if(userDaoService.loadUserByUsername(s)==null){
                   throw new UsernameNotFoundException("Invalid Username Entered");
                }
            else {
                appUserDetails = userDaoService.loadUserByUsername(s);
            }
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        return appUserDetails;
    }
}