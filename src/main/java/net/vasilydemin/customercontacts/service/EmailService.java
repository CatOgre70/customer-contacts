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
