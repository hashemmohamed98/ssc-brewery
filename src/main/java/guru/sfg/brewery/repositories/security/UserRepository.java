/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package guru.sfg.brewery.repositories.security;

import org.springframework.data.jpa.repository.JpaRepository;
import guru.sfg.brewery.domain.security.User;
import java.util.Optional;

/**
 *
 * @author #EM
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

}
