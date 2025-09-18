package com.flipkart.ecomsystem;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

//Only load the web layer (controllers, validation, 
//etc.) — don’t load the entire application context.”
@WebMvcTest(ProductController.class)
class ProductControllerTest {
	
	//MockMvc is a Spring-provided testing tool that lets you call your controller endpoints as if you’re making an HTTP request.
    @Autowired
    private MockMvc mockMvc;

    //@MockBean replaces the real ProductService bean with a Mockito mock.
    @MockBean
    private ProductService service;

    @Test
    void shouldReturnAllProducts() throws Exception {
    	
    	//your test doesn’t hit the real database or business logic -service.
    	//You tell it exactly what to return when the controller calls it:
        //when(...).thenReturn(...) is Mockito syntax.
    	
    	when(service.findAll()).thenReturn(List.of(new Product("1", "Laptop", "Desc", "Cat", "tag", 1000, 5)));
       
    	//Simulates calling your controller endpoint GET /products.
    	//Runs it through the Spring MVC stack (routing, JSON serialization, etc.), just like a real request.
    	
    	//Checks the HTTP response status. isOk() = 200 OK.
    	//If the controller doesn’t return 200, the test fails.
    	
    	//Uses JsonPath to check the JSON response.
    	//$[0].name → means “the first element’s name field” in the JSON array.
    	//Verifies it equals "Laptop".
        mockMvc.perform(get("/products"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].name").value("OPE"));
    }
}
