/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author #EM
 */
public class BeerRestControllerIT extends BaseIT {
    
   @Test
   void findBeers() throws Exception{
   mockMvc.perform(get("/api/v1/beer"))
           .andExpect(status().isOk());
   }
void findBeerById() throws Exception{
   mockMvc.perform(get("/api/v1/beer/a9d09feb-8579-4cec-9d7a-6bd5b96a0944"))
           .andExpect(status().isOk());
   }   

@Test
    void findBeerByUpc() throws Exception{
   mockMvc.perform(get("/api/v1/beerUpc/a9d09feb-8579-4cec-9d7a-6bd5b96a0944"))
           .andExpect(status().isOk());
   }   
}
