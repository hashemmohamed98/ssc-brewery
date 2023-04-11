/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/AnnotationType.java to edit this template
 */
package guru.sfg.brewery.security.perms;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 *
 * @author #EM
 */
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('brewery.create')")
public @interface BreweryCreatePermission {
    
}
