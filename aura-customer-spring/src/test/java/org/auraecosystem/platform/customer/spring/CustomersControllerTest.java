package org.auraecosystem.platform.customer.spring;

import org.auraecosystem.platform.customer.model.Customer;
import org.auraecosystem.platform.customer.service.CustomerService;
import org.auraecosystem.platform.customer.testing.CustomerFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomersController.class)
class CustomersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    @Test
    @DisplayName("GET /customers returns all customers")
    void getAllCustomersReturnsList() throws Exception {
        when(customerService.getAllCustomers())
                .thenReturn(List.of(CustomerFixtures.johnDoe()));

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"));

        verify(customerService).getAllCustomers();
    }

    @Test
    @DisplayName("GET /customers/{id} returns customer when found")
    void getCustomerByIdFound() throws Exception {
        Customer customer = CustomerFixtures.janeSmith();
        when(customerService.getCustomerById(2L)).thenReturn(Optional.of(customer));

        mockMvc.perform(get("/customers/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Smith"));

        verify(customerService).getCustomerById(2L);
    }

    @Test
    @DisplayName("GET /customers/{id} returns 404 when not found")
    void getCustomerByIdNotFound() throws Exception {
        when(customerService.getCustomerById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/customers/99"))
                .andExpect(status().isNotFound());

        verify(customerService).getCustomerById(99L);
    }

    @Test
    @DisplayName("POST /customers creates a customer")
    void createCustomer() throws Exception {
        Customer created = CustomerFixtures.customer(3L, "Alice", "Wonderland");
        when(customerService.createCustomer(any(Customer.class))).thenReturn(created);

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"firstName":"Alice","lastName":"Wonderland"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/customers/3"))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.firstName").value("Alice"));

        verify(customerService).createCustomer(any(Customer.class));
    }

    @Test
    @DisplayName("POST /customers rejects blank names")
    void createCustomerRejectsBlankNames() throws Exception {
        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"firstName":" ","lastName":"Wonderland"}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /customers/{id} updates a customer")
    void updateCustomer() throws Exception {
        Customer updated = CustomerFixtures.customer(1L, "Bob", "Builder");
        when(customerService.updateCustomer(eq(1L), any(Customer.class)))
                .thenReturn(updated);

        mockMvc.perform(put("/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"firstName":"Bob","lastName":"Builder"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Bob"));

        verify(customerService).updateCustomer(eq(1L), any(Customer.class));
    }

    @Test
    @DisplayName("DELETE /customers/{id} deletes a customer")
    void deleteCustomer() throws Exception {
        mockMvc.perform(delete("/customers/1"))
                .andExpect(status().isNoContent());

        verify(customerService).deleteCustomer(1L);
    }
}
