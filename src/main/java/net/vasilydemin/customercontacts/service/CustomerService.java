package net.vasilydemin.customercontacts.service;

import net.vasilydemin.customercontacts.constant.UserMessages;
import net.vasilydemin.customercontacts.dto.CustomerDto;
import net.vasilydemin.customercontacts.entity.Customer;
import net.vasilydemin.customercontacts.exception.CustomerWithSuchIdNotFoundException;
import net.vasilydemin.customercontacts.exception.CustomerWithSuchNameNotFoundException;
import net.vasilydemin.customercontacts.mapper.CustomerMapper;
import net.vasilydemin.customercontacts.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    /**
     * Create customer with selected name in the database
     * @param name String with customer name
     * @return CustomerDto object. If customer with such name already exists in the database then returns existing
     * customer data, if not then create new customer and return his data. Database search ignores letters case
     */
    public CustomerDto createCustomer(String name){
        Optional<Customer> customerFound = customerRepository.findCustomerByNameIgnoreCase(name);
        if(customerFound.isEmpty()) {
            Customer customer = new Customer(0L, name);
            return customerMapper.entityToDto(customerRepository.save(customer));
        } else {
            return customerMapper.entityToDto(customerFound.get());
        }
    }

    public CustomerDto readCustomerById(Long id){
        Optional<Customer> customerFound = customerRepository.findCustomerById(id);
        if(customerFound.isEmpty()) {
            String msg = UserMessages.CUSTOMER_WITH_SUCH_ID_NOT_FOUND.getUserMessage()
                    .replace("%id%", id.toString());
            logger.error(msg);
            throw new CustomerWithSuchIdNotFoundException(msg);
        } else {
            return customerMapper.entityToDto(customerFound.get());
        }
    }

    public CustomerDto readCustomerByName(String name) {
        Optional<Customer> customerFound = customerRepository.findCustomerByNameIgnoreCase(name);
        if(customerFound.isPresent()) {
            return customerMapper.entityToDto(customerFound.get());
        } else {
            String msg = UserMessages.CUSTOMER_WITH_SUCH_NAME_NOT_FOUND.getUserMessage().replace("%name%", name);
            logger.error(msg);
            throw new CustomerWithSuchNameNotFoundException(msg);
        }
    }

    public List<CustomerDto> readAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::entityToDto)
                .toList();
    }

    public CustomerDto updateCustomer(CustomerDto customerDto) {
        Optional<Customer> customerFound = customerRepository.findCustomerById(customerDto.getId());
        if(customerFound.isEmpty()) {
            String msg = UserMessages.CUSTOMER_WITH_SUCH_ID_NOT_FOUND.getUserMessage()
                    .replace("%id%", customerDto.getId().toString());
            logger.error(msg);
            throw new CustomerWithSuchIdNotFoundException(msg);
        } else {
            return customerMapper.entityToDto(customerRepository.save(customerMapper.dtoToEntity(customerDto)));
        }
    }

    public CustomerDto deleteCustomer(CustomerDto customerDto){
        Optional<Customer> customerFound = customerRepository.findCustomerById(customerDto.getId());
        if(customerFound.isEmpty()) {
            String msg = UserMessages.CUSTOMER_WITH_SUCH_ID_NOT_FOUND.getUserMessage()
                    .replace("%id%", customerDto.getId().toString());
            logger.error(msg);
            throw new CustomerWithSuchIdNotFoundException(msg);
        } else {
            customerRepository.delete(customerFound.get());
            return customerMapper.entityToDto(customerFound.get());
        }
    }
}
