/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guru.sfg.brewery.domain.security;
//
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;


/**
 *
 * @author #EM
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {
    
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Integer id;
private String password;
private  String username;
@Singular
@ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST},fetch = FetchType.EAGER)
@JoinTable(name = "user_role",joinColumns = {@JoinColumn(name = "USER_ID",referencedColumnName = "ID")},inverseJoinColumns ={@JoinColumn(name = "ROLE_ID",referencedColumnName = "ID")} )
private Set<Role>  roles;

@Transient
private Set<Authority> authorities;


    public Set<Authority> getAuthorities() {
        return this.roles.stream()
                .map(Role::getAuthorities)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }
@Builder.Default
private  Boolean accountNonExpired= true;

@Builder.Default
private  Boolean accountNonLocked= true;

@Builder.Default
private  Boolean credentialsNonExpired= true;
    
@Builder.Default
private  Boolean enabled= true;

}
