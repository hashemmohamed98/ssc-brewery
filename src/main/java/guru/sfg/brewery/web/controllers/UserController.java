/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guru.sfg.brewery.web.controllers;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.UserRepository;
import guru.sfg.brewery.security.google.GoogleCredentialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 *
 * @author #EM
 */
@Slf4j
@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class UserController {
    
    private final UserRepository userRepository;
    private final GoogleAuthenticator googleAuthenticator;
    
    @GetMapping("/register2fa")
    public String  register2fa( Model model){
    User user= getUser();
    String url= GoogleAuthenticatorQRGenerator.getOtpAuthURL("SFG", user.getUsername(),
            googleAuthenticator.createCredentials(user.getUsername()));
    
    log.debug("Google URL = "+ url);
    model.addAttribute("googleurl", url);
    return  "user/register2fa";
    }
    
    @PostMapping("/register2fa")
    public String confirm2fa(@RequestParam Integer verifyCode ) {
        User user= getUser();
        log.debug("The Verification Code is "+ verifyCode);
 if(googleAuthenticator.authorizeUser(user.getUsername(), verifyCode)){
        User savedUser=userRepository.findById(user.getId()).orElseThrow();
        savedUser.setUseGoogle2fa(true);
        userRepository.save(savedUser);
        return "/index";
                                 }
    
else{
   return "user/register2fa"; }
   } 
    
    @GetMapping("/verify2fa")
    public String verifty2fa(){
    return "user/verify2fa";
    }
    
    @PostMapping("/verify2fa")
    public String verifyPostOf2fa(@RequestParam Integer verifyCode){
    User user=getUser();
    
     if(googleAuthenticator.authorizeUser(user.getUsername(), verifyCode)){
     
     ((User)  SecurityContextHolder.getContext().getAuthentication().getPrincipal()).setGoogle2faRequired(false);
     
     return "/index";
     }
     
     
     else{
     return "/user/verify2fa";
     }
    }
    
    
    private User getUser() {
return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
