/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.util.DigestUtils;

/**
 *
 * @author #EM
 */
public class PasswordEncodingTest {
    static final String PASSWORD="password";
    
    @Test
    void noOpEncoder(){
    PasswordEncoder noop = NoOpPasswordEncoder.getInstance() ;
    
        System.out.println(noop.encode(PASSWORD));
    
    }
    
        @Test
    void LDAPEncoder(){
    PasswordEncoder ldap= new  LdapShaPasswordEncoder();
    String encodedPassword =ldap.encode(PASSWORD);
    
        System.out.println(encodedPassword);
    System.out.println(ldap.encode("tiger"));
    
    }
            @Test
    void testSHA256(){
    PasswordEncoder sha256= new  StandardPasswordEncoder();
   
    
        System.out.println(sha256.encode(PASSWORD));
    System.out.println(sha256.encode("hashhh98"));
    }
              @Test
    void testBCrypt(){
    PasswordEncoder bCrypt= new  BCryptPasswordEncoder(15);
   
        System.out.println(bCrypt.encode(PASSWORD));
        System.out.println(bCrypt.encode("tiger"));
    } 
    @Test
    public void testPassword(){
        System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes()));
    String Salted=PASSWORD+"ThisIsMySaltedValue";
        System.out.println(DigestUtils.md5DigestAsHex(Salted.getBytes()));
        System.out.println(DigestUtils.md5DigestAsHex(Salted.getBytes()));

    }
}
