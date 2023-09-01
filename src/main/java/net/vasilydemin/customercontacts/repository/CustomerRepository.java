package net.vasilydemin.customercontacts.repository;

import net.vasilydemin.customercontacts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findCustomerByNameIgnoreCase(String name);

    Optional<Customer> findCustomerById(Long id);
}
