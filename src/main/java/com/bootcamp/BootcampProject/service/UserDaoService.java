package com.bootcamp.BootcampProject.service;

import com.bootcamp.BootcampProject.entity.user.AppUserDetails;
import com.bootcamp.BootcampProject.entity.user.User;
import com.bootcamp.BootcampProject.exception.InactiveException;
import com.bootcamp.BootcampProject.exception.UserNotFoundException;
import com.bootcamp.BootcampProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDaoService {

    @Autowired
    private UserRepository userRepository;

    public AppUserDetails loadUserByUsername(String username) throws Exception, UserNotFoundException, InactiveException {
        User user=userRepository.findByEmail(username);
        System.out.println(user);
        if(user!=null){
            if(username!=null){
                if(user.isActive()){
                    if (!user.isLocked()){
                        System.out.println("inside is locked"+user.isLocked());
                        user.setLocked(false);
                        return new AppUserDetails(user);
                    }
                    else {
                        throw new InactiveException("Account is locked");
                    }
                }
                else{
                    throw new InactiveException("Account is not activated");
                }
            }
            else {
                throw new UsernameNotFoundException("username cannot not be blank");
            }
        }
        else {
            throw new UserNotFoundException("Invalid username entered");
        }
    }
}