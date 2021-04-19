package com.bootcamp.BootcampProject.controller;

import com.bootcamp.BootcampProject.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LogoutController {
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private TokenStore tokenStore;

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws UserNotFoundException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            try {
                String tokenValue = authHeader.replace("bearer", "").trim();
                OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
                tokenStore.removeAccessToken(accessToken);
            } catch (Exception e) {
                throw new UserNotFoundException("user not found");
            }
        }
        return "Logged out successfully";
    }


    @GetMapping("/welcome")
    public String welcome(){
        return messageSource.getMessage("good.morning.message",null, LocaleContextHolder.getLocale());
    }

}
