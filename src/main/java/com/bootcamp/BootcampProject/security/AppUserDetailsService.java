package com.bootcamp.BootcampProject.security;

import com.bootcamp.BootcampProject.entity.user.AppUserDetails;
import com.bootcamp.BootcampProject.service.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
   private UserDaoService userDaoService;

    private AppUserDetails appUserDetails;

    @Override
    public UserDetails loadUserByUsername(String s){
            if(userDaoService.loadUserByUsername(s)==null){
                   throw new UsernameNotFoundException("invalid Username or password entered entered");
                }
            else {
                appUserDetails = userDaoService.loadUserByUsername(s);
                return appUserDetails;
            }
    }

}