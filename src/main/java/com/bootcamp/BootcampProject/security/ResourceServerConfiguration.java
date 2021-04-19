package com.bootcamp.BootcampProject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    AppUserDetailsService userDetailsService;

    public ResourceServerConfiguration() {
        super();
    }

    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authenticationProvider;
    }

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/welcome").permitAll()
                .antMatchers("/cart/customer/**").hasAnyRole("CUSTOMER")
                .antMatchers("/order/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/order/customer/**").hasAnyRole("CUSTOMER")
                .antMatchers("/order/seller/**").hasAnyRole("SELLER")
                .antMatchers("/category/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/category/customer/**").hasAnyRole("CUSTOMER")
                .antMatchers("/category/seller/**").hasAnyRole("SELLER")
                .antMatchers("/product/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/product/customer/**").hasAnyRole("CUSTOMER")
                .antMatchers("/product/seller/**").hasAnyRole("SELLER")
                .antMatchers("/image/customer/**").hasAnyRole("CUSTOMER")
                .antMatchers("/image/seller/**").hasAnyRole("SELLER")
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/customer/**").hasAnyRole("CUSTOMER")
                .antMatchers("/seller/**").hasAnyRole("SELLER")
                .antMatchers("/register/**").anonymous()
                .antMatchers("/password/**").anonymous()
                .antMatchers("/unlock/**").anonymous()
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/welcome")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .csrf().disable();
    }

}