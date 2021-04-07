package com.bootcamp.BootcampProject.security;

import com.bootcamp.BootcampProject.entity.user.User;
import com.bootcamp.BootcampProject.repository.UserRepository;
import com.bootcamp.BootcampProject.service.EmailSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class LockAccountAuthManager implements ApplicationListener<AbstractAuthenticationEvent> {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailSendService emailSendService;

    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent abstractAuthenticationEvent){
        if (abstractAuthenticationEvent instanceof AuthenticationSuccessEvent){
            AuthenticationSuccessEvent event = (AuthenticationSuccessEvent) abstractAuthenticationEvent;
            String email = (String) event.getAuthentication().getPrincipal();
            if (userRepository.findByEmail(email)!=null){
                User user = userRepository.findByEmail(email);
                user.setLoginAttempts(0);
                user.setLocked(false);
                userRepository.save(user);
            }
            else {
                throw new UsernameNotFoundException("invalid email");
            }
        }

        if (abstractAuthenticationEvent instanceof AuthenticationFailureBadCredentialsEvent){
            AuthenticationFailureBadCredentialsEvent event = (AuthenticationFailureBadCredentialsEvent) abstractAuthenticationEvent;
            String email = (String) event.getAuthentication().getPrincipal();
            if (userRepository.findByEmail(email)!=null) {
                User user = userRepository.findByEmail(email);
                if (user.getLoginAttempts()>=2){
                    user.setLocked(true);
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setTo(user.getEmail());
                    message.setFrom("damineesaini1111@gmail.com");
                    message.setSubject("Account Locked");
                    message.setText("To unlock your account, please click here:"+"http://localhost:8080/unlock-account/"+user.getEmail());
                    emailSendService.sendEmail(message);
                }
                else {
                    user.setLoginAttempts(user.getLoginAttempts()+1);
                }
                userRepository.save(user);
            }
            else {
                throw new UsernameNotFoundException("invalid email");
            }
        }
    }


}
