package net.vasilydemin.customercontacts.service;

import net.vasilydemin.customercontacts.constant.ContactType;
import net.vasilydemin.customercontacts.constant.UserMessages;
import net.vasilydemin.customercontacts.dto.CustomerDto;
import net.vasilydemin.customercontacts.dto.CustomerWithContactsDto;
import net.vasilydemin.customercontacts.entity.Customer;
import net.vasilydemin.customercontacts.entity.Email;
import net.vasilydemin.customercontacts.entity.Phone;
import net.vasilydemin.customercontacts.exception.ContactTypeIsWrongException;
import net.vasilydemin.customercontacts.exception.CustomerWithSuchIdNotFoundException;
import net.vasilydemin.customercontacts.exception.CustomerWithSuchNameNotFoundException;
import net.vasilydemin.customercontacts.mapper.CustomerMapper;
import net.vasilydemin.customercontacts.repository.CustomerRepository;
import net.vasilydemin.customercontacts.repository.EmailRepository;
import net.vasilydemin.customercontacts.repository.PhoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final EmailRepository emailRepository;
    private final PhoneRepository phoneRepository;
    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper,
                           EmailRepository emailRepository,
                           PhoneRepository phoneRepository) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.emailRepository = emailRepository;
        this.phoneRepository = phoneRepository;
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

    /**
     * Read customer with selected id from the database
     * @param id Customer id
     * @return CustomerDto object. If customer with such name doesn't exist in the database then throw
     * CustomerWithSuchIdNotFoundException
     */
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

    /**
     * Read customer with selected name from the database
     * @param name customer name
     * @return CustomerDto object. If customer with such name doesn't exist in the database then throw
     * CustomerWithSuchNameNotFoundException
     */
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

    /**
     * Read all customers from the database. This is a bad style if written without pagination
     * @return List (array) of CustomerDto objects, unsorted with pagination (default or obtained from the http request)
     */
    public List<CustomerDto> readAllCustomers(int pageNumber, int itemsPerPage) {
        Sort sorting = Sort.unsorted();
        Pageable pageRequest = PageRequest.of(pageNumber, itemsPerPage, sorting);
        return customerRepository.findAll(pageRequest).stream()
                .map(customerMapper::entityToDto)
                .toList();
    }

    /**
     * Update selected customer with data from CustomerDto
     * @param customerDto customer DTO obtained from frontend
     * @return CustomerDto with updated fields
     * @throws CustomerWithSuchIdNotFoundException if customer with id from DTO was not found in the database
     */
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

    /**
     * Delete specified customer from the database
     * @param customerDto customer to delete (only id field is being used to look for specified customer)
     * @return CustomerDto with deleted customer record data
     * @throws CustomerWithSuchIdNotFoundException if customer with id specified in the customerDto was not found
     * in the database
     */
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

    /**
     * Method to read all customer information from the database (customer, email, phone repositories)
     * @param id customer id
     * @return CustomerWithContactsDto object
     * @throws CustomerWithSuchIdNotFoundException if customer with specified id was not found in the database
     */
    public CustomerWithContactsDto readAllContactsByCustomerId(Long id) {
        Optional<Customer> customerFound = customerRepository.findCustomerById(id);
        if(customerFound.isEmpty()) {
            String msg = UserMessages.CUSTOMER_WITH_SUCH_ID_NOT_FOUND.getUserMessage()
                    .replace("%id%", id.toString());
            logger.error(msg);
            throw new CustomerWithSuchIdNotFoundException(msg);
        }
        List<String> emails = emailRepository.findAllByCustomerId(id).stream().map(Email::getEmail).toList();
        List<String> phones = phoneRepository.findAllByCustomerId(id).stream().map(Phone::getPhone).toList();
        return new CustomerWithContactsDto(customerFound.get().getId(),
                customerFound.get().getName(), emails, phones);
    }

    /**
     * Method to read all customer contacts from the database by customer id and type of contacts (email or phone)
     * @param id customer id
     * @param type contacts type (email or phone)
     * @return List (array) of strings containing contacts of specific type
     * @throws CustomerWithSuchIdNotFoundException if customer with specified id was not found in the database
     */
    public List<String> readAllContactsByCustomerIdAndByType(Long id, String type) {
        Optional<Customer> customerFound = customerRepository.findCustomerById(id);
        if(customerFound.isEmpty()) {
            String msg = UserMessages.CUSTOMER_WITH_SUCH_ID_NOT_FOUND.getUserMessage()
                    .replace("%id%", id.toString());
            logger.error(msg);
            throw new CustomerWithSuchIdNotFoundException(msg);
        }
        ContactType contactType = ContactType.getContactTypeByName(type.toLowerCase());
        switch(contactType) {
            case EMAIL -> {
                return emailRepository.findAllByCustomerId(id).stream().map(Email::getEmail).toList();
            }
            case PHONE -> {
                return phoneRepository.findAllByCustomerId(id).stream().map(Phone::getPhone).toList();
            }
            default -> {
                String msg = UserMessages.CONTACT_TYPE_IS_WRONG.getUserMessage().replace("%type%", type);
                logger.error(msg);
                throw new ContactTypeIsWrongException(msg);
            }
        }
    }

}
