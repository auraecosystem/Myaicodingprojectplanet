package org.auraecosystem.platform.customer.model;

/**
 * Framework-independent customer domain value.
 *
 * @param id customer identifier; null is permitted before persistence assigns an ID
 * @param firstName customer's first name
 * @param lastName customer's last name
 */
public record Customer(Long id, String firstName, String lastName) {
}
