/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guru.sfg.brewery.web.controllers;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *
 * @author #EM
 */
@SpringBootTest
public class BeerControllerIT extends BaseIT{  

    
 @Test
 void initCreationUser() throws Exception{
 mockMvc.perform(get("/beers/new").with(httpBasic("user", "password")))
         .andExpect(status().isForbidden());
 }
 @Test
 void initCreationCustomer() throws Exception{
 mockMvc.perform(get("/beers/new").with(httpBasic("scott", "tiger")))
         .andExpect(status().isForbidden());
    
 }
  @Test
 void initCreationAdmin() throws Exception{
 mockMvc.perform(get("/beers/new").with(httpBasic("spring", "guru")))
         .andExpect(status().isOk())
        .andExpect(view().name("beers/createBeer"))
        .andExpect(model().attributeExists("beer"));
 }
@Test
void findbeers() throws Exception{
mockMvc.perform(get("/beers/find").with(httpBasic("user", "password"))).andExpect(status().isOk())
        .andExpect(view().name("beers/findBeers"))
        
        .andExpect(model().attributeExists("beer"));
        
}
    
@Test
void findbeersWithAnonymous() throws Exception{
mockMvc.perform(get("/beers/find").with(anonymous())).andExpect(status().isUnauthorized());
        
}
            }
