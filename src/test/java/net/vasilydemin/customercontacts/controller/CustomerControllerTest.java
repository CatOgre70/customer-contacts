package net.vasilydemin.customercontacts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.vasilydemin.customercontacts.dto.EmailDto;
import net.vasilydemin.customercontacts.dto.PhoneDto;
import net.vasilydemin.customercontacts.entity.Customer;
import net.vasilydemin.customercontacts.entity.Email;
import net.vasilydemin.customercontacts.entity.Phone;
import net.vasilydemin.customercontacts.mapper.CustomerMapper;
import net.vasilydemin.customercontacts.mapper.EmailMapper;
import net.vasilydemin.customercontacts.mapper.PhoneMapper;
import net.vasilydemin.customercontacts.repository.CustomerRepository;
import net.vasilydemin.customercontacts.repository.EmailRepository;
import net.vasilydemin.customercontacts.repository.PhoneRepository;
import net.vasilydemin.customercontacts.service.CustomerService;
import net.vasilydemin.customercontacts.service.EmailService;
import net.vasilydemin.customercontacts.service.PhoneService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@WebMvcTest(controllers = CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhoneRepository phoneRepository;

    @MockBean
    private EmailRepository emailRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @SpyBean
    private EmailService emailService;

    @SpyBean
    private EmailMapper emailMapper;

    @SpyBean
    private PhoneService phoneService;

    @SpyBean
    private PhoneMapper phoneMapper;

    @SpyBean
    private CustomerService customerService;

    @SpyBean
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerController customerController;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    public void createCustomerTest() throws Exception {
        Customer customer = new Customer(1L, "Vasily Demin");

        when(customerRepository.findCustomerByNameIgnoreCase(any(String.class))).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/customer")
                        .param("name", "Vasily Demin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Vasily Demin"));

    }

    @Test
    public void createCustomerWhenCustomerAlreadyExistsInTheDatabaseTest() throws Exception {
        Customer customer = new Customer(1L, "Vasily Demin");

        when(customerRepository.findCustomerByNameIgnoreCase(any(String.class))).thenReturn(Optional.of(customer));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/customer")
                        .param("name", "Vasily Demin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Vasily Demin"));
    }

    @Test
    public void readCustomerByIdTest() throws Exception {
        Customer customer = new Customer(1L, "Vasily Demin");

        when(customerRepository.findCustomerById(any(Long.class))).thenReturn(Optional.of(customer));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/customer/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Vasily Demin"));
    }

    @Test
    public void readCustomerByIdAndNoSuchIdTest() throws Exception {
        when(customerRepository.findCustomerById(any(Long.class))).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/customer/{id}", 2L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void readCustomerByNameTest() throws Exception {
        Customer customer = new Customer(1L, "Vasily Demin");

        when(customerRepository.findCustomerByNameIgnoreCase(any(String.class))).thenReturn(Optional.of(customer));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/customer")
                        .param("name", "Vasily Demin"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':1,'name':'Vasily Demin'}]"));
    }

    @Test
    public void readAllEmailsByCustomerIdTest() throws Exception {
        Customer customer = new Customer(1L, "Vasily Demin");
        EmailDto email1 = new EmailDto(1L, 1L, "vvdemin@t2.ru");
        EmailDto email2 = new EmailDto(2L, 1L, "vasily.demin@t3.com");

        when(customerRepository.findCustomerById(any(Long.class))).thenReturn(Optional.of(customer));
        when(emailService.findAllEmailsByCustomerId(any(Long.class))).thenReturn(List.of(email1, email2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/customer/{id}/allemails", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':1,'customerId':1,'email':'vvdemin@t2.ru'}," +
                        "{'id':2,'customerId':1,'email':'vasily.demin@t3.com'}]"));

    }

    @Test
    public void readAllPhonesByCustomerIdTest() throws Exception {
        Customer customer = new Customer(1L, "Vasily Demin");
        PhoneDto email1 = new PhoneDto(1L, 1L, "vvdemin@t2.ru");
        PhoneDto email2 = new PhoneDto(2L, 1L, "vasily.demin@t3.com");

        when(customerRepository.findCustomerById(any(Long.class))).thenReturn(Optional.of(customer));
        when(phoneService.findAllPhonesByCustomerId(any(Long.class))).thenReturn(List.of(email1, email2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/customer/{id}/allphones", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':1,'customerId':1,'phone':'vvdemin@t2.ru'}," +
                        "{'id':2,'customerId':1,'phone':'vasily.demin@t3.com'}]"));

    }

    @Test
    public void readAllContactsByCustomerIdTest() throws Exception {
        Customer customer = new Customer(1L, "Vasily Demin");
        Phone phone1 = new Phone(1L, 1L, "+79012345678");
        Phone phone2 = new Phone(2L, 1L, "+79102345678");
        Email email1 = new Email(1L, 1L, "vvdemin@t2.ru");
        Email email2 = new Email(2L, 1L, "vasily.demin@t3.com");

        when(customerRepository.findCustomerById(any(Long.class))).thenReturn(Optional.of(customer));
        when(emailRepository.findAllByCustomerId(any(Long.class))).thenReturn(List.of(email1, email2));
        when(phoneRepository.findAllByCustomerId(any(Long.class))).thenReturn(List.of(phone1, phone2));


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/customer/{id}/allcontacts", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id':1,'name':'Vasily Demin'," +
                        "'emails':['vvdemin@t2.ru','vasily.demin@t3.com'],'phones':['+79012345678','+79102345678']}"));
    }

    @Test
    public void readAllContactsByCustomerIdAndByTypeEmailTest() throws Exception {
        Customer customer = new Customer(1L, "Vasily Demin");
        Phone phone1 = new Phone(1L, 1L, "+79012345678");
        Phone phone2 = new Phone(2L, 1L, "+79102345678");
        Email email1 = new Email(1L, 1L, "vvdemin@t2.ru");
        Email email2 = new Email(2L, 1L, "vasily.demin@t3.com");

        when(customerRepository.findCustomerById(any(Long.class))).thenReturn(Optional.of(customer));
        when(emailRepository.findAllByCustomerId(any(Long.class))).thenReturn(List.of(email1, email2));
        when(phoneRepository.findAllByCustomerId(any(Long.class))).thenReturn(List.of(phone1, phone2));


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/customer/{id}/allcontactsbytype", 1L)
                        .param("type", "email"))
                .andExpect(status().isOk())
                .andExpect(content().json("['vvdemin@t2.ru','vasily.demin@t3.com']"));
    }

    @Test
    public void readAllContactsByCustomerIdAndByTypePhoneTest() throws Exception {
        Customer customer = new Customer(1L, "Vasily Demin");
        Phone phone1 = new Phone(1L, 1L, "+79012345678");
        Phone phone2 = new Phone(2L, 1L, "+79102345678");
        Email email1 = new Email(1L, 1L, "vvdemin@t2.ru");
        Email email2 = new Email(2L, 1L, "vasily.demin@t3.com");

        when(customerRepository.findCustomerById(any(Long.class))).thenReturn(Optional.of(customer));
        when(emailRepository.findAllByCustomerId(any(Long.class))).thenReturn(List.of(email1, email2));
        when(phoneRepository.findAllByCustomerId(any(Long.class))).thenReturn(List.of(phone1, phone2));


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/customer/{id}/allcontactsbytype", 1L)
                        .param("type", "phone"))
                .andExpect(status().isOk())
                .andExpect(content().json("['+79012345678','+79102345678']"));
    }

    @Test
    public void updateCustomerTest() throws Exception {
        Customer customer = new Customer(1L, "Vasily Demin");
        Customer customer1 = new Customer(1L, "Vasily Demin1");

        JSONObject customerObject = new JSONObject();
        customerObject.put("id", 1L);
        customerObject.put("name", "Vasily Demin1");


        when(customerRepository.findCustomerById(any(Long.class))).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer1);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/customer")
                        .content(customerObject.toString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Vasily Demin1"));
    }

    @Test
    public void deleteCustomerTest() throws Exception {
        Customer customer = new Customer(1L, "Vasily Demin");

        JSONObject customerObject = new JSONObject();
        customerObject.put("id", 1L);
        customerObject.put("name", "Vasily Demin");


        when(customerRepository.findCustomerById(any(Long.class))).thenReturn(Optional.of(customer));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/customer")
                        .content(customerObject.toString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Vasily Demin"));
    }


}
