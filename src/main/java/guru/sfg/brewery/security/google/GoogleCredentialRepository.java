/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guru.sfg.brewery.security.google;

import com.warrenstrange.googleauth.ICredentialRepository;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
/**
 *
 * @author #EM
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class GoogleCredentialRepository implements ICredentialRepository  {

    private final UserRepository userRepository;
    @Override
    public String getSecretKey(String username) {
User user = userRepository.findByUsername(username).orElseThrow();

return user.getGoogle2faSecret();
    }

    @Override
    public void saveUserCredentials(String username, String secretKey, int i, List<Integer> list) {
       User user = userRepository.findByUsername(username).orElseThrow();
         user.setGoogle2faSecret(secretKey);
         user.setUseGoogle2fa(true);
         userRepository.save(user);
    }
    
}
