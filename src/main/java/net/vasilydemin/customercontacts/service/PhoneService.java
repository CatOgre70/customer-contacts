package net.vasilydemin.customercontacts.service;

import net.vasilydemin.customercontacts.constant.UserMessages;
import net.vasilydemin.customercontacts.dto.PhoneDto;
import net.vasilydemin.customercontacts.entity.Customer;
import net.vasilydemin.customercontacts.entity.Phone;
import net.vasilydemin.customercontacts.exception.*;
import net.vasilydemin.customercontacts.mapper.PhoneMapper;
import net.vasilydemin.customercontacts.repository.CustomerRepository;
import net.vasilydemin.customercontacts.repository.PhoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhoneService {

    private final Logger logger = LoggerFactory.getLogger(PhoneService.class);
    private final PhoneRepository phoneRepository;
    private final PhoneMapper phoneMapper;
    private final CustomerRepository customerRepository;

    public PhoneService(PhoneRepository phoneRepository, PhoneMapper phoneMapper, CustomerRepository customerRepository) {
        this.phoneRepository = phoneRepository;
        this.phoneMapper = phoneMapper;
        this.customerRepository = customerRepository;
    }

    /**
     * Method creates new phone record in the database based on information from PhoneDto
     * @param phoneDto phone DTO
     * @return created or existing phone record. If specified phone was not found in the database, method creates
     * new phone record and returns DTO with created phone record data. If specified phone already exists in the
     * database and owned by same customer, then method returns DTO object with existing phone record data. If
     * specified phone already exists in the database and owned by another customer, then method generates exception
     * PhoneIsInTheDatabaseAlreadyException
     * @throws PhoneIsInTheDatabaseAlreadyException If specified phone already exists in the database and owned by
     * another customer
     * @throws CustomerMustNotBeNullException if PhoneDto object customerId = null
     * @throws CustomerWithSuchIdNotFoundException if customer with specified customerId was not found in the database
     */
    public PhoneDto createPhone(PhoneDto phoneDto) {
        if(phoneDto.getCustomerId() == null) {
            logger.error(UserMessages.CUSTOMER_ID_MUST_NOT_BE_NULL.getUserMessage());
            throw new CustomerMustNotBeNullException(UserMessages.CUSTOMER_ID_MUST_NOT_BE_NULL.getUserMessage());
        }
        Optional<Customer> customerFound = customerRepository.findCustomerById(phoneDto.getCustomerId());
        if(customerFound.isEmpty()) {
            String msg = UserMessages.CUSTOMER_WITH_SUCH_ID_NOT_FOUND.getUserMessage()
                    .replace("%id%", phoneDto.getCustomerId().toString());
            logger.error(msg);
            throw new CustomerWithSuchIdNotFoundException(msg);
        }
        Optional<Phone> phoneFound = phoneRepository.findPhoneByPhoneIgnoreCase(phoneDto.getPhone());
        if(phoneFound.isPresent() && phoneFound.get().getCustomerId().equals(phoneDto.getCustomerId())) {
            return phoneMapper.entityToDto(phoneFound.get());
        } else if(phoneFound.isPresent() && !phoneFound.get().getCustomerId().equals(phoneDto.getCustomerId())) {
            String msg = UserMessages.PHONE_NUMBER_IS_IN_THE_DATABASE_ALREADY1.getUserMessage()
                    .replace("%phone%", phoneDto.getPhone());
            logger.error(msg);
            throw new PhoneIsInTheDatabaseAlreadyException(msg);
        } else {
            return phoneMapper.entityToDto(phoneRepository.save(phoneMapper.dtoToEntity(phoneDto)));
        }
    }

    /**
     * Method looks for phone record with specified id
     * @param id phone id
     * @return PhoneDto object with phone found record data
     * @throws PhoneWithSuchIdNotFoundException if phone record with specified id was not found in the database
     */
    public PhoneDto readPhoneById(Long id) {
        Optional<Phone> phoneFound = phoneRepository.findById(id);
        if(phoneFound.isPresent()) {
            return phoneMapper.entityToDto(phoneFound.get());
        } else {
            String msg = UserMessages.PHONE_WITH_SUCH_ID_NOT_FOUND.getUserMessage()
                    .replace("%id%", id.toString());
            logger.error(msg);
            throw new PhoneWithSuchIdNotFoundException(msg);
        }
    }

    /**
     * Method looks for all phones owned by customer with specified id
     * @param customerId - customer id (surprise, surprise!)
     * @return List (array) of phoneDto objects found
     * @throws CustomerWithSuchIdNotFoundException if customer with specified id was not found in the database
     */
    public List<PhoneDto> findAllPhonesByCustomerId(Long customerId) {
        Optional<Customer> customerFound = customerRepository.findCustomerById(customerId);
        if(customerFound.isEmpty()) {
            String msg = UserMessages.CUSTOMER_WITH_SUCH_ID_NOT_FOUND.getUserMessage()
                    .replace("%id%", customerId.toString());
            logger.error(msg);
            throw new CustomerWithSuchIdNotFoundException(msg);
        }
        return phoneRepository.findAllByCustomerId(customerId).stream()
                .map(phoneMapper::entityToDto).toList();

    }

    /**
     * Method updates existing phone record with data from PhoneDto object
     * @param phoneDto PhoneDto object with data for updating
     * @return PhoneDto object with updated data
     * @throws CustomerWithSuchIdNotFoundException if customer with specified customerId was not found in the database
     * @throws PhoneWithSuchIdNotFoundException if phone record with specified id was not found in the database
     */
    public PhoneDto updatePhone(PhoneDto phoneDto) {
        Optional<Phone> phoneFound = phoneRepository.findById(phoneDto.getId());
        if(phoneFound.isEmpty()) {
            String msg = UserMessages.PHONE_WITH_SUCH_ID_NOT_FOUND.getUserMessage()
                    .replace("%id%", phoneDto.getId().toString());
            logger.error(msg);
            throw new PhoneWithSuchIdNotFoundException(msg);
        }
        Optional<Customer> customerFound = customerRepository.findCustomerById(phoneDto.getCustomerId());
        if(customerFound.isEmpty()) {
            String msg = UserMessages.CUSTOMER_WITH_SUCH_ID_NOT_FOUND.getUserMessage()
                    .replace("%id%", phoneDto.getCustomerId().toString());
            logger.error(msg);
            throw new CustomerWithSuchIdNotFoundException(msg);
        }
        return phoneMapper.entityToDto(phoneRepository.save(phoneMapper.dtoToEntity(phoneDto)));
    }

    /**
     * Method deletes specified phone record from the database
     * @param phoneDto PhoneDto object to delete from the database
     * @return phoneDto object with deleted data
     * @throws PhoneWithSuchIdNotFoundException if phone record with specified id (phoneDto.getId()) was not found
     * in the database
     */
     public PhoneDto deletePhone(PhoneDto phoneDto){
        Optional<Phone> phoneFound = phoneRepository.findById(phoneDto.getId());
        if(phoneFound.isEmpty()) {
            String msg = UserMessages.PHONE_WITH_SUCH_ID_NOT_FOUND.getUserMessage()
                    .replace("%id%", phoneDto.getId().toString());
            logger.error(msg);
            throw new PhoneWithSuchIdNotFoundException(msg);
        }
        phoneRepository.delete(phoneFound.get());
        return phoneMapper.entityToDto(phoneFound.get());
    }

}
