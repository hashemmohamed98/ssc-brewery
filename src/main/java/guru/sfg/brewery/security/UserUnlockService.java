/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guru.sfg.brewery.security;

import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.UserRepository;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
/**
 *
 * @author #EM
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserUnlockService {
    private final UserRepository userRepository;
    @Scheduled(fixedRate = 300000)
    public void unlockAccounts(){
        log.debug("Running Unlock Accounts Service ");
        List <User> lockedUsers= userRepository
                .findAllByAccountNonLockedAndLastModifiedDateIsBefore(false, 
                        Timestamp.valueOf(LocalDateTime.now().minusSeconds(30)));
       
        if(lockedUsers.size()>0){
        log.debug("Found Locked Accounts And Unlocking Them ");
        lockedUsers.forEach(user -> user.setAccountNonLocked(true));
            
        userRepository.saveAll(lockedUsers);
        }
    }
}
