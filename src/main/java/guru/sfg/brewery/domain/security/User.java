/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guru.sfg.brewery.domain.security;
//
import guru.sfg.brewery.domain.Customer;
import java.sql.Timestamp;
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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


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
public class User implements UserDetails,CredentialsContainer {
  
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Integer id;
private String password;
private  String username;
@Singular
@ManyToMany(cascade = {CascadeType.MERGE},fetch = FetchType.EAGER)
@JoinTable(name = "user_role",joinColumns = {@JoinColumn(name = "USER_ID",referencedColumnName = "ID")},inverseJoinColumns ={@JoinColumn(name = "ROLE_ID",referencedColumnName = "ID")} )
private Set<Role>  roles;
@Transient
    public Set<SimpleGrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(Role::getAuthorities)
                .flatMap(Set::stream)
                .map(authortiy ->{ 
                    return new SimpleGrantedAuthority(authortiy.getPermission());})
                .collect(Collectors.toSet());
    }
    
 @ManyToOne(fetch = FetchType.EAGER)
 private Customer customer;
 
 
@Builder.Default
private  Boolean accountNonExpired= true;

@Builder.Default
private  Boolean accountNonLocked= true;

@Builder.Default
private  Boolean credentialsNonExpired= true;
    
@Builder.Default
private  Boolean enabled= true;

@Builder.Default
private Boolean useGoogle2fa= false;

private String google2faSecret;

@Transient
private Boolean google2faRequired = true ;

    @Override
    public boolean isAccountNonExpired() {
                return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
            return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
            return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {

            return this.enabled;
    }

    @Override
    public void eraseCredentials() {
            this.password=null;
    }

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;
    
    @UpdateTimestamp
    private Timestamp lastModifiedDate;

}
