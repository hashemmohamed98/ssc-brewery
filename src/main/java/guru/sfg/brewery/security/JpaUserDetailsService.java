/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guru.sfg.brewery.security;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import  guru.sfg.brewery.domain.security.User;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
/**
 *
 * @author #EM
 */
@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class JpaUserDetailsService implements UserDetailsService {

private final  UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.debug("Loading User Credintials Via JPA");
        User user= userRepository.findByUsername(username).orElseThrow(() ->{
        return new  UsernameNotFoundException("User name: "+username +" does not exist");
        });
        return new  org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),user.getEnabled(),user.getAccountNonExpired(),user.getAccountNonLocked(),user.getCredentialsNonExpired(),
                convertToSpringAuthority(user.getAuthorities()));
    }

    private Collection<? extends GrantedAuthority> convertToSpringAuthority(Set<Authority> authorities) {
   
        if(authorities!=null && authorities.size()>0){
        
        return authorities.stream().map(Authority::getRole)
                .map(SimpleGrantedAuthority:: new)
                .collect(Collectors.toSet());
        }
        else{
        return new HashSet<>();
        }
    }
    
}
