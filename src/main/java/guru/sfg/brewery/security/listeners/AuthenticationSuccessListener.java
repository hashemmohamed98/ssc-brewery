/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guru.sfg.brewery.security.listeners;
import guru.sfg.brewery.domain.security.LoginSuccess;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.LoginSuccessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
/**
 *
 * @author #EM
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class AuthenticationSuccessListener {
    private final LoginSuccessRepository loginSuccessRepository;
@EventListener
public void listen( AuthenticationSuccessEvent event){

log.debug("User Logged In Ok ! ");

if( event.getSource() instanceof UsernamePasswordAuthenticationToken){
UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) event.getSource();
 LoginSuccess.LoginSuccessBuilder builder= LoginSuccess.builder() ;
if(token.getPrincipal() instanceof User ){
    User user= (User) token.getPrincipal();
   builder.user(user);
log.debug("Username With Following Creds Is Now LoggedIn "+user.getUsername() +" " +user.getPassword());
    }

if(token.getDetails()instanceof WebAuthenticationDetails ){
    WebAuthenticationDetails web= (WebAuthenticationDetails) token.getDetails();
    builder.SourceIp(web.getRemoteAddress());
log.debug("Source IP : "+web.getRemoteAddress());
    }

        LoginSuccess loginSuccess= loginSuccessRepository.save(builder.build());
        log.debug("Login saved successfully. Id: "+ loginSuccess.getId());
}
    
}
}
