package com.bootcamp.BootcampProject.service;

import com.bootcamp.BootcampProject.entity.user.AppUserDetails;
import com.bootcamp.BootcampProject.entity.user.User;
import com.bootcamp.BootcampProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDaoService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSendService emailSendService;

    public AppUserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user=userRepository.findByEmail(username);
        System.out.println(username);
        if(user!=null) {
                return new AppUserDetails(user);
        }
        else {
            throw new UsernameNotFoundException("Invalid username entered");
        }
    }

    public void manageAttempts(String username) {
        User user = userRepository.findByEmail(username);
        if(user!=null) {
            System.out.println("inside if");
            if (user.getLoginAttempts() > 2) {
                if(user.isNotLocked()){
                    user.setNotLocked(false);
                    SimpleMailMessage mail = new SimpleMailMessage();
                    mail.setTo(user.getEmail());
                    mail.setSubject("Account locked");
                    mail.setText("To unlock your account, please click here:"+"http://localhost:8080/unlock/unlock-account?email="+user.getEmail());
                    emailSendService.sendEmail(mail);
                }
            }
            user.setLoginAttempts(user.getLoginAttempts() + 1);
            userRepository.save(user);
        }
    }
}