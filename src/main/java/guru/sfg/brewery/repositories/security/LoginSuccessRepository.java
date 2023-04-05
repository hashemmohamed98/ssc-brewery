/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package guru.sfg.brewery.repositories.security;

import guru.sfg.brewery.domain.security.LoginSuccess;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author #EM
 */
public interface LoginSuccessRepository extends JpaRepository<LoginSuccess, Integer>{
    
}
