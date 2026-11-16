# Aura Customer Platform

A reusable customer-management foundation for Aura Ecosystem applications.

**Java namespace:** `org.auraecosystem.platform.customer`  
**Build:** Maven multi-module  
**Java baseline:** 21

## Modules

| Module | Responsibility |
|---|---|
| `aura-customer-core` | Framework-independent customer model and `CustomerService` port |
| `aura-customer-testing` | Reusable deterministic customer fixtures for consumer tests |
| `aura-customer-spring` | Optional Spring MVC REST adapter and controller tests |

The core module has no Spring dependency. Applications can depend on the core contract and provide their own service implementation and persistence adapter. The Spring adapter is optional.

## REST endpoints

The Spring adapter exposes:

| Method | Path | Behaviour |
|---|---|---|
| GET | `/customers` | List customers |
| GET | `/customers/{id}` | Retrieve one customer; 404 if absent |
| POST | `/customers` | Create a customer; 201 with Location header |
| PUT | `/customers/{id}` | Update a customer |
| DELETE | `/customers/{id}` | Delete a customer; 204 |

Create and update requests require non-blank `firstName` and `lastName`.

## Build and test

Requirements: JDK 21 and Maven 3.9+.

From the repository root:

```bash
mvn clean verify
```

Run only the Spring adapter tests:

```bash
mvn -pl aura-customer-spring -am test
```

## Integration contract

Implement `org.auraecosystem.platform.customer.service.CustomerService` in the consuming application and register that implementation with its dependency-injection container. The repository intentionally does not prescribe a database, persistence vendor, hosting provider, or external API.

The controller module is an adapter, not a complete deployed server. A consuming application supplies the service implementation and its Spring Boot application entry point.

## Dependency and ownership policy

- Domain code is independent of Spring and infrastructure.
- No third-party hosted runtime service or external API is required by the source code.
- Maven resolves the declared open-source build dependencies from configured artifact repositories unless dependencies are already cached or supplied through an internal mirror.
- Pin and audit dependency versions as part of release management.
- The repository's source code, package namespace, and application architecture remain under Aura Ecosystem project control.

## Current scope

This is the initial reusable foundation. It includes the domain contract, optional REST adapter, request validation, controller tests, and reusable fixtures. It does not yet include a database implementation, authentication policy, authorization rules, production deployment configuration, or a web/blog frontend.
