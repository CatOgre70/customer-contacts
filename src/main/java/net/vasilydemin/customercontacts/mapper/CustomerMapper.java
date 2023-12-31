package net.vasilydemin.customercontacts.mapper;

import net.vasilydemin.customercontacts.dto.CustomerDto;
import net.vasilydemin.customercontacts.entity.Customer;
import org.springframework.stereotype.Component;

/**
 * Customer entity to DTO and DTO to entity mapper. No more, but no less
 */
@Component
public class CustomerMapper {

    public Customer dtoToEntity(CustomerDto customerDto) {
        return new Customer(customerDto.getId(), customerDto.getName());
    }

    public CustomerDto entityToDto(Customer customer) {
        return new CustomerDto(customer.getId(), customer.getName());
    }

}
