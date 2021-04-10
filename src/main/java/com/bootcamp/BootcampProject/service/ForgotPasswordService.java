package com.bootcamp.BootcampProject.service;

import com.bootcamp.BootcampProject.dto.request.ForgotPassword;
import com.bootcamp.BootcampProject.entity.token.ResetPasswordToken;
import com.bootcamp.BootcampProject.entity.user.User;
import com.bootcamp.BootcampProject.exception.DoesNotExistException;
import com.bootcamp.BootcampProject.exception.PasswordDoesNotMatchException;
import com.bootcamp.BootcampProject.exception.TokenExpiredException;
import com.bootcamp.BootcampProject.exception.UserNotFoundException;
import com.bootcamp.BootcampProject.repository.ResetPasswordRepository;
import com.bootcamp.BootcampProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class ForgotPasswordService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ResetPasswordRepository resetPasswordRepository;

    @Autowired
    EmailSendService emailSendService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public String resetPassword(String email) throws UserNotFoundException {
        if(userRepository.findByEmail(email) == null){
            throw new UserNotFoundException("user not found.invalid email");
        }
        else {
            User user = userRepository.findByEmail(email);
            ResetPasswordToken resetPasswordToken;
            if(resetPasswordRepository.findByUser(user)!=null){
                resetPasswordToken =resetPasswordRepository.findByUser(user);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Timestamp(calendar.getTime().getTime()));
                resetPasswordToken.setCreateDate(new Date(calendar.getTime().getTime()));
                calendar.add(Calendar.HOUR,2);
                resetPasswordToken.setExpiryDate(new Date(calendar.getTime().getTime()));
                resetPasswordToken.setResetToken(UUID.randomUUID().toString());
            }
            else {
                resetPasswordToken = new ResetPasswordToken(user);
            }
            resetPasswordRepository.save(resetPasswordToken);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setFrom("damineesaini11@gmail.com");
            message.setSubject("Reset Password");
            message.setText("To reset your account password, please lick here:"+"http://localhost:8080/reset-password?token="+resetPasswordToken.getResetToken());
            emailSendService.sendEmail(message);
            return "A link has been sent to your mail for password reset";
        }
    }

    public String updatePassword(String resetToken, ForgotPassword forgotPassword) throws TokenExpiredException, DoesNotExistException, PasswordDoesNotMatchException {
        if (resetPasswordRepository.findByResetToken(resetToken) == null){
            throw new DoesNotExistException("invalid token");
        }
        else {
            ResetPasswordToken resetPasswordToken = resetPasswordRepository.findByResetToken(resetToken);
            Date presentDate = new Date();
            if (resetPasswordToken.getExpiryDate().getTime() - presentDate.getTime() <=0){
                throw new TokenExpiredException("token is expired, request for new token using forgot password link");
            }
            else {
             User user = userRepository.findByEmail(resetPasswordToken.getUser().getEmail());
             if (forgotPassword.getPassword().matches(forgotPassword.getConfirmPassword())){
                 String password = forgotPassword.getPassword();
                 user.setPassword(bCryptPasswordEncoder.encode(password));
                 user.setActive(true);
                 userRepository.save(user);
                 SimpleMailMessage message = new SimpleMailMessage();
                 message.setTo(user.getEmail());
                 message.setFrom("damineesaini11@gmail.com");
                 message.setSubject("Password Updated!!");
                 message.setText("Your password is updated successfully");
                 emailSendService.sendEmail(message);
                 return "Password Updated";
             }
             else {
                 throw new PasswordDoesNotMatchException("password and confirm password does not match");
             }

            }
        }
    }
}
