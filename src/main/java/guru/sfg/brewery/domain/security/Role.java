/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guru.sfg.brewery.domain.security;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Role {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Integer id;
private String name;
@ManyToMany(mappedBy = "roles")
private Set<User> users;


@Singular
@ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST},fetch = FetchType.EAGER)
@JoinTable(name = "role_authority",
        joinColumns = {@JoinColumn(name = "ROLE_ID",referencedColumnName = "ID")},
        inverseJoinColumns ={@JoinColumn(name = "AUTHORITY_ID",referencedColumnName = "ID")} )
private Set<Authority> authorities;
}
