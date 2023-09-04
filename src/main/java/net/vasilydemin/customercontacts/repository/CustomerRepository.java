package net.vasilydemin.customercontacts.repository;

import net.vasilydemin.customercontacts.entity.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

/**
 * Customer repository with Paging and Sorting capabilities
 */
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

    Optional<Customer> findCustomerByNameIgnoreCase(String name);

    Optional<Customer> findCustomerById(Long id);

    Customer save(Customer customer);
    void delete(Customer customer);
}
