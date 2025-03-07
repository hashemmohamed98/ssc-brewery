/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guru.sfg.brewery.web.controllers;
import guru.sfg.brewery.repositories.BeerInventoryRepository;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.repositories.CustomerRepository;
import guru.sfg.brewery.services.BeerService;
import guru.sfg.brewery.services.BreweryService;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;


/**
 *
 * @author #EM
 */
public abstract class BaseIT {
     @Autowired   
    WebApplicationContext wac;
    
   protected MockMvc mockMvc;
    
//    @MockBean
//    BeerRepository beerRepository;
//    
//    @MockBean
//    BeerInventoryRepository beerInventoryRepository;
//    
//    @MockBean
//    BreweryService breweryService;
//    
//    @MockBean
//    CustomerRepository customerRepository;
//    
//    @MockBean
//   BeerService beerService;
    @BeforeEach 
void setup(){
mockMvc= MockMvcBuilders
        .webAppContextSetup(wac)
        .apply(springSecurity())
        .build();

}
public static Stream <Arguments> getStreamAdminCustomer(){

    return Stream.of(Arguments.of("spring","guru"),Arguments.of("scott","tiger"));
}

public static Stream <Arguments> getStreamAllUsers(){

    return Stream.of(Arguments.of("spring","guru"),Arguments.of("scott","tiger"),Arguments.of("user","password"));
}

public static Stream <Arguments> getStreamNotAdmin(){

    return Stream.of(Arguments.of("scott","tiger"),Arguments.of("user","password"));
}

}
