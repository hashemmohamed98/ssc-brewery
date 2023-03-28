/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import org.springframework.test.annotation.Rollback;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


/**
 *
 * @author #EM
 */
@SpringBootTest
public class CustomerControllerIT extends BaseIT {
    
  @ParameterizedTest(name = "#{index} with [{arguments}]")
  @MethodSource("guru.sfg.brewery.web.controllers.BaseIT#getStreamAdminCustomer")
   void testListCustomerAuth(String user,String pass) throws Exception{
       mockMvc.perform(get("/customers").with(httpBasic(user,pass))).andExpect(status().isOk());
   
   }
   @Test
   void testListCustomerNotAuth() throws Exception{
       mockMvc.perform(get("/customers").with(httpBasic("user","password"))).andExpect(status().isForbidden());
   
   }    
   
      @Test
   void testListCustomerNotLoggedIn() throws Exception{
       mockMvc.perform(get("/customers")).andExpect(status().isUnauthorized());
   
   }    
   
   @Rollback
   @Test
void processCreationForm() throws Exception {
mockMvc.perform(post("/customers/new")
        .param("Customer Name", " Foo Customer2")
        .with(httpBasic("spring","guru")))
        .andExpect(status().is3xxRedirection());

} 
   @Rollback
  @ParameterizedTest(name = "#{index} with [{arguments}]")
  @MethodSource("guru.sfg.brewery.web.controllers.BaseIT#getStreamNotAdmin")
void processCreationFormNoAuth(String user,String password) throws Exception {
mockMvc.perform(post("/customers/new")
        .param("Customer Name", " Foo Customer2")
        .with(httpBasic(user,password)))
        .andExpect(status().isForbidden());

} 
@Test
void processCreationFormNoAuth() throws Exception {
mockMvc.perform(post("/customers/new")
        .param("Customer Name", " Foo Customer2"))
        .andExpect(status().isUnauthorized());

} 
}
