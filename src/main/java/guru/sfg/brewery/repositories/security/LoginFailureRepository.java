/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package guru.sfg.brewery.repositories.security;

import guru.sfg.brewery.domain.security.LoginFailure;
import guru.sfg.brewery.domain.security.User;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author #EM
 */
public interface LoginFailureRepository extends JpaRepository<LoginFailure, Integer> {
    List <LoginFailure> findAllByUserAndCreatedDateIsAfter(User user,Timestamp timestamp);
}
