package net.vasilydemin.customercontacts.service;

import net.vasilydemin.customercontacts.constant.UserMessages;
import net.vasilydemin.customercontacts.dto.EmailDto;
import net.vasilydemin.customercontacts.entity.Customer;
import net.vasilydemin.customercontacts.entity.Email;
import net.vasilydemin.customercontacts.exception.CustomerMustNotBeNullException;
import net.vasilydemin.customercontacts.exception.CustomerWithSuchIdNotFoundException;
import net.vasilydemin.customercontacts.exception.EmailIsInTheDatabaseAlreadyException;
import net.vasilydemin.customercontacts.exception.EmailWithSuchIdNotFoundException;
import net.vasilydemin.customercontacts.mapper.EmailMapper;
import net.vasilydemin.customercontacts.repository.CustomerRepository;
import net.vasilydemin.customercontacts.repository.EmailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmailService {

    private final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final EmailRepository emailRepository;
    private final EmailMapper emailMapper;
    private final CustomerRepository customerRepository;

    public EmailService(EmailRepository emailRepository, EmailMapper emailMapper, CustomerRepository customerRepository) {
        this.emailRepository = emailRepository;
        this.emailMapper = emailMapper;
        this.customerRepository = customerRepository;
    }

    /**
     * Method creates new email record in the database based on information from EmailDto
     * @param emailDto email DTO
     * @return created or existing email record. If specified email was not found in the database, method creates
     * new email record and returns DTO with created email record data. If specified email already exists in the
     * database and owned by same customer, then method returns DTO object with existing email record data. If
     * specified email already exists in the database and owned by another customer, then method generates exception
     * EmailIsInTheDatabaseAlreadyException
     * @throws EmailIsInTheDatabaseAlreadyException If specified email already exists in the database and owned by
     * another customer
     * @throws CustomerMustNotBeNullException if EmailDto object id = null
     * @throws CustomerWithSuchIdNotFoundException if customer with specified customerId was not found in the database
     */
    public EmailDto createEmail(EmailDto emailDto) {
        if(emailDto.getCustomerId() == null) {
            logger.error(UserMessages.CUSTOMER_ID_MUST_NOT_BE_NULL.getUserMessage());
            throw new CustomerMustNotBeNullException(UserMessages.CUSTOMER_ID_MUST_NOT_BE_NULL.getUserMessage());
        }
        Optional<Customer> customerFound = customerRepository.findCustomerById(emailDto.getCustomerId());
        if(customerFound.isEmpty()) {
            String msg = UserMessages.CUSTOMER_WITH_SUCH_ID_NOT_FOUND.getUserMessage()
                    .replace("%id%", emailDto.getCustomerId().toString());
            logger.error(msg);
            throw new CustomerWithSuchIdNotFoundException(msg);
        }
        Optional<Email> emailFound = emailRepository.findEmailByEmailIgnoreCase(emailDto.getEmail());
        if(emailFound.isPresent() && emailFound.get().getCustomerId().equals(emailDto.getCustomerId())) {
            return emailMapper.entityToDto(emailFound.get());
        } else if(emailFound.isPresent() && !emailFound.get().getCustomerId().equals(emailDto.getCustomerId())) {
            String msg = UserMessages.EMAIL_ADDRESS_IS_IN_THE_DATABASE_ALREADY1.getUserMessage()
                    .replace("%email%", emailDto.getEmail());
            logger.error(msg);
            throw new EmailIsInTheDatabaseAlreadyException(msg);
        } else {
            return emailMapper.entityToDto(emailRepository.save(emailMapper.dtoToEntity(emailDto)));
        }
    }

    /**
     * Method looks for email record with specified id
     * @param id email id
     * @return EmailDto object with email record data
     * @throws EmailWithSuchIdNotFoundException if email record with specified id was not found in the database
     */
    public EmailDto readEmailById(Long id) {
        Optional<Email> emailFound = emailRepository.findById(id);
        if(emailFound.isPresent()) {
            return emailMapper.entityToDto(emailFound.get());
        } else {
            String msg = UserMessages.EMAIL_WITH_SUCH_ID_NOT_FOUND.getUserMessage()
                    .replace("%id%", id.toString());
            logger.error(msg);
            throw new EmailWithSuchIdNotFoundException(msg);
        }
    }

    /**
     * Method looks for all emails owned by customer with specified id
     * @param customerId - customer id (surprise, surprise!)
     * @return List (array) of EmailDto objects found
     * @throws CustomerWithSuchIdNotFoundException if customer with specified id was not found in the database
     */
    public List<EmailDto> findAllEmailsByCustomerId(Long customerId) {
        Optional<Customer> customerFound = customerRepository.findCustomerById(customerId);
        if(customerFound.isEmpty()) {
            String msg = UserMessages.CUSTOMER_WITH_SUCH_ID_NOT_FOUND.getUserMessage()
                    .replace("%id%", customerId.toString());
            logger.error(msg);
            throw new CustomerWithSuchIdNotFoundException(msg);
        }
        return emailRepository.findAllByCustomerId(customerId).stream()
                .map(emailMapper::entityToDto).toList();

    }

    /**
     * Method updates existing email record with data from EmailDto object
     * @param emailDto EmailDto object with data for updating
     * @return EmailDto object with updated data
     * @throws CustomerWithSuchIdNotFoundException if customer with specified customerId was not found in the database
     * @throws EmailWithSuchIdNotFoundException if email record with specified id was not found in the database
     */
    public EmailDto updateEmail(EmailDto emailDto) {
        Optional<Email> emailFound = emailRepository.findById(emailDto.getId());
        if(emailFound.isEmpty()) {
            String msg = UserMessages.EMAIL_WITH_SUCH_ID_NOT_FOUND.getUserMessage()
                    .replace("%id%", emailDto.getId().toString());
            logger.error(msg);
            throw new EmailWithSuchIdNotFoundException(msg);
        }
        Optional<Customer> customerFound = customerRepository.findCustomerById(emailDto.getCustomerId());
        if(customerFound.isEmpty()) {
            String msg = UserMessages.CUSTOMER_WITH_SUCH_ID_NOT_FOUND.getUserMessage()
                    .replace("%id%", emailDto.getCustomerId().toString());
            logger.error(msg);
            throw new CustomerWithSuchIdNotFoundException(msg);
        }
        return emailMapper.entityToDto(emailRepository.save(emailMapper.dtoToEntity(emailDto)));
    }

    /**
     * Method deletes specified email record from the database
     * @param emailDto EmailDto object to delete from the database
     * @return EmailDto object with deleted data
     * @throws EmailWithSuchIdNotFoundException if email record with specified id (emailDto.getId()) was not found
     * in the database
     */
    public EmailDto deleteEmail(EmailDto emailDto){
        Optional<Email> emailFound = emailRepository.findById(emailDto.getId());
        if(emailFound.isEmpty()) {
            String msg = UserMessages.EMAIL_WITH_SUCH_ID_NOT_FOUND.getUserMessage()
                    .replace("%id%", emailDto.getId().toString());
            logger.error(msg);
            throw new EmailWithSuchIdNotFoundException(msg);
        }
        emailRepository.delete(emailFound.get());
        return emailMapper.entityToDto(emailFound.get());
    }

}
