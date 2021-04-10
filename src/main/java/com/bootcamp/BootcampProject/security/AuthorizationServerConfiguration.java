package com.bootcamp.BootcampProject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;


@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;


    public AuthorizationServerConfiguration() {
        super();
    }

    @Bean
    @Primary
    DefaultTokenServices tokenServices(){
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore()).userDetailsService(userDetailsService)
                .authenticationManager(authenticationManager);
    }

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("live-test")
                .secret(passwordEncoder.encode("abcde"))
                .authorizedGrantTypes("password","refresh_token")
                .refreshTokenValiditySeconds(30 * 24 * 3600)
                .scopes("app")
                .accessTokenValiditySeconds(7*24*60);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer authorizationServerSecurityConfigurer) throws Exception {
        authorizationServerSecurityConfigurer.allowFormAuthenticationForClients();
    }

//    @EventListener
//    public void authFailedEventListener(AbstractAuthenticationFailureEvent abstractAuthenticationFailureEvent){
////            String email = abstractAuthenticationFailureEvent.getAuthentication().getPrincipal().toString();
////            if (userRepository.findByEmail(email)!=null) {
////                User user = userRepository.findByEmail(email);
////                if (user.getLoginAttempts()>=2){
////                    user.setLocked(true);
////                    SimpleMailMessage message = new SimpleMailMessage();
////                    message.setTo(user.getEmail());
////                    message.setFrom("damineesaini1111@gmail.com");
////                    message.setSubject("Account Locked");
////                    message.setText("To unlock your account, please click here:"+"http://localhost:8080/unlock-account/"+user.getEmail());
////                    emailSendService.sendEmail(message);
////                }
////                else {
////                    user.setLoginAttempts(user.getLoginAttempts()+1);
////                }
////                userRepository.save(user);
////            }
////            else {
////                throw new UsernameNotFoundException("invalid email");
////            }
//        System.out.println(abstractAuthenticationFailureEvent.getAuthentication().getPrincipal());
//        }
}
