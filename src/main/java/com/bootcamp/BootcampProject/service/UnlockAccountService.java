package com.bootcamp.BootcampProject.service;

import com.bootcamp.BootcampProject.entity.token.UnlockAccountToken;
import com.bootcamp.BootcampProject.entity.user.User;
import com.bootcamp.BootcampProject.repository.UnlockAccountTokenRepository;
import com.bootcamp.BootcampProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class UnlockAccountService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailSendService emailSendService;
    @Autowired
    UnlockAccountTokenRepository unlockAccountTokenRepository;

    public String unlockAccount(String email){
        if (userRepository.findByEmail(email)!=null){
            User user = userRepository.findByEmail(email);
            UnlockAccountToken unlockAccountToken;
            if(unlockAccountTokenRepository.findByUser(user)!=null){
                unlockAccountToken =unlockAccountTokenRepository.findByUser(user);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Timestamp(calendar.getTime().getTime()));
                unlockAccountToken.setCreateDate(new Date(calendar.getTime().getTime()));
                calendar.add(Calendar.HOUR,2);
                unlockAccountToken.setExpiryDate(new Date(calendar.getTime().getTime()));
                unlockAccountToken.setUnlockAccountToken(UUID.randomUUID().toString());
            }
            else {
                unlockAccountToken = new UnlockAccountToken(user);
            }
            unlockAccountTokenRepository.save(unlockAccountToken);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setFrom("damineesaini1111@gmail.com");
            message.setSubject("Account Locked");
            message.setText("To unlock your account, please click here:"+"http://localhost:8080/unlock-account?token="+unlockAccountToken.getUnlockAccountToken());
            emailSendService.sendEmail(message);
            return "Mail has been sent to you. Click on the link to unlock your account";
        }
        else {
            throw new UsernameNotFoundException("invalid email");
        }
    }

    @Transactional
    @Modifying
    public String setUnlockAccount(String unlockToken) throws Exception {
        if (unlockAccountTokenRepository.findByUnlockAccountToken(unlockToken) == null){
            throw new Exception("invalid token");
        }
        else {
            UnlockAccountToken unlockAccountToken = unlockAccountTokenRepository.findByUnlockAccountToken(unlockToken);
            Date presentDate = new Date();
            if (unlockAccountToken.getExpiryDate().getTime() - presentDate.getTime() <=0){
                throw new Exception("token is expired, request for new token using forgot password link");
            }
            else {
                User user = userRepository.findByEmail(unlockAccountToken.getUser().getEmail());
                    user.setLocked(false);
                    user.setLoginAttempts(0);
                    userRepository.save(user);
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setTo(user.getEmail());
                message.setFrom("damineesaini11@gmail.com");
                message.setSubject("Account Unlocked");
                message.setText("Your account is successfully unlocked");
                    emailSendService.sendEmail(message);
                    return "Account Unlocked";
                }
            }
        }

}
