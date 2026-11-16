package org.auraecosystem.platform.customer.spring;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.auraecosystem.platform.customer.model.Customer;
import org.auraecosystem.platform.customer.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/customers")
@Validated
public class CustomersController {

    private final CustomerService customerService;

    public CustomersController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(
            @Valid @RequestBody CustomerRequest request) {
        Customer created = customerService.createCustomer(
                new Customer(null, request.firstName(), request.lastName()));
        URI location = URI.create("/customers/" + created.id());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerRequest request) {
        Customer updated = customerService.updateCustomer(
                id, new Customer(id, request.firstName(), request.lastName()));
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    public record CustomerRequest(
            @NotBlank String firstName,
            @NotBlank String lastName) {
    }
}
