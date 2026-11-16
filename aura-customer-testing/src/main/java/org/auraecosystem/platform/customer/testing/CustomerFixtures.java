package org.auraecosystem.platform.customer.testing;

import org.auraecosystem.platform.customer.model.Customer;

/** Reusable deterministic customer fixtures for tests in consuming applications. */
public final class CustomerFixtures {

    private CustomerFixtures() {
    }

    public static Customer customer(Long id, String firstName, String lastName) {
        return new Customer(id, firstName, lastName);
    }

    public static Customer johnDoe() {
        return customer(1L, "John", "Doe");
    }

    public static Customer janeSmith() {
        return customer(2L, "Jane", "Smith");
    }
}
