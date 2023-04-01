/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guru.sfg.brewery.config;
import guru.sfg.brewery.security.RestHeaderAuthFilter;
import guru.sfg.brewery.security.RestUrlParamsAuthfilter;
import guru.sfg.brewery.security.SfgPasswordEncoderFactories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


/**
 *
 * @author #EM
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//        public RestHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager auth){
//RestHeaderAuthFilter filter = new RestHeaderAuthFilter( new AntPathRequestMatcher("/api/**"));   
//filter.setAuthenticationManager(auth);
//return filter;
//    }
//   
//        
//   public RestUrlParamsAuthfilter restUrlParamsAuthfilter (AuthenticationManager auth){
//       
//       RestUrlParamsAuthfilter authfilter = new  RestUrlParamsAuthfilter( new AntPathRequestMatcher("/api/**"));
//       authfilter.setAuthenticationManager(auth);
//       return authfilter;
//   }     
    
    //needed for use with spring Data JPA sPEL  
    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension(){
    return new SecurityEvaluationContextExtension();
    
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
//        http.addFilterBefore(restUrlParamsAuthfilter(authenticationManager()),
//                UsernamePasswordAuthenticationFilter.class).
//                csrf().
//                disable();
        
//        http.addFilterBefore(restHeaderAuthFilter(authenticationManager()),
//                UsernamePasswordAuthenticationFilter.class)
//                .csrf().disable();
http
        .authorizeRequests(authorize -> {
                    authorize
                    .antMatchers("/h2-console/**").permitAll() //do not use in production!
                    .antMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll();
//                    .antMatchers("/beers/find", "/beers*").hasAnyRole("ADMIN","CUSTOMER","USER")
//                    .antMatchers(HttpMethod.GET, "/api/v1/beer/**").hasAnyRole("ADMIN","CUSTOMER","USER")
////                    .mvcMatchers(HttpMethod.DELETE, "/api/v1/beer/**").hasRole("ADMIN")
//                    .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").hasAnyRole("ADMIN","CUSTOMER","USER")
//                    .mvcMatchers("/brewery/breweries")
//                      .hasAnyRole("ADMIN", "CUSTOMER")
//                  .mvcMatchers(HttpMethod.GET, "/brewery/api/v1/breweries")
//                      .hasAnyRole("ADMIN", "CUSTOMER");
                    
                 
                        })
        .authorizeRequests()
        .anyRequest()
        .authenticated()
        .and()
        .formLogin(loginConfigurer  ->{
        loginConfigurer.loginProcessingUrl("/login")
        .loginPage("/").permitAll()
        .successForwardUrl("/")
        .defaultSuccessUrl("/")
        .failureUrl("/?error")        ;
        })
        .logout((logoutConfigurer) -> {
         logoutConfigurer
                 .logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET"))
         .logoutSuccessUrl("/?logout")
         .permitAll();
        })
        .httpBasic()
        .and().csrf().ignoringAntMatchers("/h2-console/**","/api/**");
http.headers().frameOptions().sameOrigin();
    }
  
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//        .withUser("user")
//        .password("{noop}password")
//        .roles("USER")
//        .and()
//        .withUser("admin").password("{sha256}a214f64613d15b7533849dc05dd4a9a8106540cb6e20936cdd0cb0392c1ac3beb7888ccd37e2adb0").roles("ADMIN")
//        .and()
//        .withUser("scott").password("{bcrypt15}$2a$15$MoH/SF2H1PPZFM7zeq02yeV7RZ//7V/uoAttjjfUmJifjVaXPQure").roles("CUSTOMER");
//    }
    
    
    @Bean
    PasswordEncoder passwordEncoder(){
    return  SfgPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    
   
}
