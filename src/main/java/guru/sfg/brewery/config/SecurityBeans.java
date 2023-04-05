/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guru.sfg.brewery.config;

import javax.sql.DataSource;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/**
 *
 * @author #EM
 */
@Configuration
public class SecurityBeans {
    
    @Bean
    public AuthenticationEventPublisher authenticationEventPublisher( ApplicationEventPublisher applicationEventPublisher){
        
   return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
    }
    @Bean
    public PersistentTokenRepository persistentTokenRepository( DataSource dataSource){
        JdbcTokenRepositoryImpl tokenRepository=new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
    return tokenRepository;
    
    }
}
