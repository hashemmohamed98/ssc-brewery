/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.Role;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.AuthorityRepository;
import guru.sfg.brewery.repositories.security.RoleRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
/**
 *
 * @author #EM
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class DefaultUserLoader implements CommandLineRunner {
     private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private void loadSecurityData() {
Authority createBeer = authorityRepository.save(Authority.builder().permission("beer.create").build());
Authority readBeer = authorityRepository.save(Authority.builder().permission("beer.read").build());
Authority updateBeer = authorityRepository.save(Authority.builder().permission("beer.update").build());
Authority deleteBeer = authorityRepository.save(Authority.builder().permission("beer.delete").build());

Authority createCustomer= authorityRepository.save(Authority.builder().permission("customer.create").build());
Authority readCustomer = authorityRepository.save(Authority.builder().permission("customer.read").build());
Authority updateCustomer = authorityRepository.save(Authority.builder().permission("customer.update").build());
Authority deleteCustomer = authorityRepository.save(Authority.builder().permission("customer.delete").build());


Authority createBrewery= authorityRepository.save(Authority.builder().permission("brewery.create").build());
Authority readBrewery = authorityRepository.save(Authority.builder().permission("brewery.read").build());
Authority updateBrewery = authorityRepository.save(Authority.builder().permission("brewery.update").build());
Authority deleteBrewery= authorityRepository.save(Authority.builder().permission("brewery.delete").build());

Role adminRole= roleRepository.save(Role.builder().name("ADMIN").build());
Role userRole= roleRepository.save(Role.builder().name("USER").build());
Role customerRole= roleRepository.save(Role.builder().name("CUSTOMER").build());


adminRole.setAuthorities(new HashSet<>(Set.of(createBeer, readBeer, updateBeer,deleteBeer,createBrewery,readBrewery,updateBrewery,deleteBrewery,createCustomer,readCustomer,updateCustomer,deleteCustomer)));
userRole.setAuthorities(new HashSet<>(Set.of(readBeer)));
customerRole.setAuthorities(new HashSet<>(Set.of(readBeer,readCustomer,readBrewery)));

roleRepository.saveAll(Arrays.asList(adminRole , userRole,customerRole));

        userRepository.save(User.builder()
                .username("spring")
                .password(passwordEncoder.encode("guru"))
                .role(adminRole)
                .build());

        userRepository.save(User.builder()
                .username("user")
                .password(passwordEncoder.encode("password"))
                .role(userRole)
                .build());

        User user= userRepository.save(User.builder()
                .username("scott")
                .password(passwordEncoder.encode("tiger"))
                .role(customerRole)
                .build());

        log.debug("Users Loaded: " + userRepository.count());
        user.getAuthorities().forEach(authority ->{
            System.out.println("Customer Authorities "+authority.getPermission());
        });
    }
    @Transactional
    @Override
    public void run(String... args) throws Exception {
        if (authorityRepository.count() == 0) {
            loadSecurityData();
        }
    }


}
