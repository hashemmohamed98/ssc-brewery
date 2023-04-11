/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guru.sfg.brewery.security.listeners;
import guru.sfg.brewery.domain.security.LoginFailure;
import guru.sfg.brewery.domain.security.LoginSuccess;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.LoginFailureRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 *
 * @author #EM
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class AuthenticationFailureListener {
    final UserRepository userRepository;
    final LoginFailureRepository loginFailureRepository;
 @EventListener
    public void  listen( AuthenticationFailureBadCredentialsEvent event){
    
        if(event.getSource() instanceof UsernamePasswordAuthenticationToken){
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) event.getSource();
                         LoginFailure.LoginFailureBuilder builder= LoginFailure.builder();
            log.debug("Not Authenticated");
            if( !(token.getPrincipal()  instanceof User) ){
    
                  builder.username(token.getPrincipal().toString());
                  userRepository.findByUsername((String) token.getPrincipal()).ifPresent(builder::user);
//            User user= (User) token.getPrincipal();
           log.debug("Username with the provided Creds Is not valid " + token.getPrincipal() +" "+token.getCredentials());
                
            }
            if(token.getDetails() instanceof WebAuthenticationDetails){
            WebAuthenticationDetails details= (WebAuthenticationDetails) token.getDetails();
            builder.sourceIp( details.getRemoteAddress());
            log.debug("Source IP : "+ details.getRemoteAddress());
            }
            LoginFailure loginFailure=loginFailureRepository.save(builder.build());
    log.debug("Login Failure Attempt is Saved . id: "+loginFailure.getId());
    if(loginFailure.getUser() != null){
    lockUserAccount(loginFailure.getUser());
    }
        }
    }

    private void lockUserAccount(User user) {
List <LoginFailure> failuers = loginFailureRepository.findAllByUserAndCreatedDateIsAfter(user, Timestamp.valueOf(LocalDateTime.now().minusDays(1)));

if(failuers.size()>3){
    log.debug("Locking User Account");
user.setAccountNonLocked(false);
userRepository.save(user);
}
    }
}
