package net.vasilydemin.customercontacts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.vasilydemin.customercontacts.dto.EmailDto;
import net.vasilydemin.customercontacts.entity.Customer;
import net.vasilydemin.customercontacts.entity.Email;
import net.vasilydemin.customercontacts.mapper.EmailMapper;
import net.vasilydemin.customercontacts.repository.CustomerRepository;
import net.vasilydemin.customercontacts.repository.EmailRepository;
import net.vasilydemin.customercontacts.service.EmailService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EmailController.class)
public class EmailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailRepository emailRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @SpyBean
    private EmailService emailService;

    @SpyBean
    private EmailMapper emailMapper;

    @InjectMocks
    private EmailController emailController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createEmailTest() throws Exception {
        Email email1 = new Email(1L, 1L, "vasily.demin@mail.org");
        Customer customer1 = new Customer(1L, "Vasily Demin");

        JSONObject emailObject = new JSONObject();
        emailObject.put("id", 0L);
        emailObject.put("customerId", 1L);
        emailObject.put("email", "vasily.demin@mail.org");

        when(emailRepository.save(any(Email.class))).thenReturn(email1);
        when(customerRepository.findCustomerById(any(Long.class))).thenReturn(Optional.of(customer1));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/emails")
                .content(emailObject.toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.customerId").value(1L))
                .andExpect(jsonPath("$.email").value("vasily.demin@mail.org"));

    }

    @Test
    public void createEmailWhenCustomerNotFoundTest() throws Exception {
        Email email1 = new Email(1L, 1L, "vasily.demin@mail.org");

        JSONObject emailObject = new JSONObject();
        emailObject.put("id", 0L);
        emailObject.put("customerId", 2L);
        emailObject.put("email", "vasily.demin@mail.org");

        when(emailRepository.save(any(Email.class))).thenReturn(email1);
        when(customerRepository.findCustomerById(any(Long.class))).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/emails")
                        .content(emailObject.toString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void readEmailByIdTest() throws Exception {
        Email email1 = new Email(1L, 1L, "vasily.demin@mail.org");

        JSONObject emailObject = new JSONObject();
        emailObject.put("id", 1L);
        emailObject.put("customerId", 1L);
        emailObject.put("email", "vasily.demin@mail.org");

        when(emailRepository.findById(any(Long.class))).thenReturn(Optional.of(email1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/emails/{id}", 1L)
                        .content(emailObject.toString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.customerId").value(1L))
                .andExpect(jsonPath("$.email").value("vasily.demin@mail.org"));
    }

    @Test
    public void updateEmailTest() throws Exception {
        Email email1 = new Email(1L, 1L, "vasily.demin@mail.org");
        Customer customer1 = new Customer(1L, "Vasily Demin");
        Email email2 = new Email(1L, 1L, "vvdemin@mail.org");

        JSONObject emailObject = new JSONObject();
        emailObject.put("id", 1L);
        emailObject.put("customerId", 1L);
        emailObject.put("email", "vvdemin@mail.org");

        when(emailRepository.findById(any(Long.class))).thenReturn(Optional.of(email1));
        when(customerRepository.findCustomerById(any(Long.class))).thenReturn(Optional.of(customer1));
        when(emailRepository.save(any(Email.class))).thenReturn(email2);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/emails")
                        .content(emailObject.toString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.customerId").value(1L))
                .andExpect(jsonPath("$.email").value("vvdemin@mail.org"));
    }

    @Test
    public void deleteEmailTest() throws Exception {
        Email email1 = new Email(1L, 1L, "vasily.demin@mail.org");
        EmailDto emailDto1 = new EmailDto(1L, 1L, "vasily.demin@mail.org");
        Customer customer1 = new Customer(1L, "Vasily Demin");

        JSONObject emailObject = new JSONObject();
        emailObject.put("id", 1L);
        emailObject.put("customerId", 1L);
        emailObject.put("email", "vasily.demin@mail.org");

        when(emailRepository.findById(any(Long.class))).thenReturn(Optional.of(email1));
        when(customerRepository.findCustomerById(any(Long.class))).thenReturn(Optional.of(customer1));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/emails")
                        .content(emailObject.toString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.customerId").value(1L))
                .andExpect(jsonPath("$.email").value("vasily.demin@mail.org"));
    }

}
