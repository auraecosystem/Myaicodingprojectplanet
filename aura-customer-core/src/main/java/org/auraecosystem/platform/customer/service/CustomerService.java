package org.auraecosystem.platform.customer.service;

import org.auraecosystem.platform.customer.model.Customer;

import java.util.List;
import java.util.Optional;

/**
 * Application-owned port for customer operations.
 * Implementations may use a database, file store, remote adapter, or in-memory store.
 */
public interface CustomerService {

    List<Customer> getAllCustomers();

    Optional<Customer> getCustomerById(Long id);

    Customer createCustomer(Customer customer);

    Customer updateCustomer(Long id, Customer customer);

    void deleteCustomer(Long id);
}
