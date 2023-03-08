/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guru.sfg.brewery.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/**
 *
 * @author #EM
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
http
        .authorizeRequests(authorize -> {
                    authorize.antMatchers("/","/resources/**","/login","/webjars/**").permitAll();
                    authorize.antMatchers("/beers/find","/beers*").permitAll();
                    authorize.antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll();
                    authorize.mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll();
                        })
        .authorizeRequests()
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .and()
        .httpBasic();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
        .withUser("user")
        .password("{noop}password")
        .roles("USER")
        .and()
        .withUser("admin").password("{noop}1234").roles("ADMIN")
        .and()
        .withUser("scott").password("{noop}tiger").roles("CUSTOMER");
    }
    
   
}
