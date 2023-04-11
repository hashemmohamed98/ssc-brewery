/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guru.sfg.brewery.security;

import guru.sfg.brewery.domain.security.User;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
/**
 *
 * @author #EM
 */
@Slf4j
@Component
public class BeerOrderAuthenticationManager {
    
    public boolean customerIdMatches(Authentication auth , UUID customerId){
    
    User user = (User) auth.getPrincipal();
    return user.getCustomer().getId().equals(customerId);
    }
    
}
