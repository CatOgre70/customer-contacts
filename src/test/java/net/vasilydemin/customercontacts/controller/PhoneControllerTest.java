package net.vasilydemin.customercontacts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.vasilydemin.customercontacts.entity.Customer;
import net.vasilydemin.customercontacts.entity.Phone;
import net.vasilydemin.customercontacts.mapper.PhoneMapper;
import net.vasilydemin.customercontacts.repository.CustomerRepository;
import net.vasilydemin.customercontacts.repository.PhoneRepository;
import net.vasilydemin.customercontacts.service.PhoneService;
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

@WebMvcTest(controllers = PhoneController.class)
public class PhoneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhoneRepository phoneRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @SpyBean
    private PhoneService phoneService;

    @SpyBean
    private PhoneMapper phoneMapper;

    @InjectMocks
    private PhoneController phoneController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createPhoneTest() throws Exception {
        Phone phone1 = new Phone(1L, 1L, "+79012345678");
        Customer customer1 = new Customer(1L, "Vasily Demin");

        JSONObject phoneObject = new JSONObject();
        phoneObject.put("id", 0L);
        phoneObject.put("customerId", 1L);
        phoneObject.put("phone", "+79012345678");

        when(phoneRepository.save(any(Phone.class))).thenReturn(phone1);
        when(customerRepository.findCustomerById(any(Long.class))).thenReturn(Optional.of(customer1));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/phones")
                .content(phoneObject.toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.customerId").value(1L))
                .andExpect(jsonPath("$.phone").value("+79012345678"));

    }

    @Test
    public void createPhoneWhenCustomerNotFoundTest() throws Exception {
        Phone phone1 = new Phone(1L, 1L, "+79012345678");

        JSONObject phoneObject = new JSONObject();
        phoneObject.put("id", 0L);
        phoneObject.put("customerId", 2L);
        phoneObject.put("phone", "+79012345678");

        when(phoneRepository.save(any(Phone.class))).thenReturn(phone1);
        when(customerRepository.findCustomerById(any(Long.class))).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/phones")
                        .content(phoneObject.toString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void readPhoneByIdTest() throws Exception {
        Phone phone1 = new Phone(1L, 1L, "+79012345678");

        JSONObject phoneObject = new JSONObject();
        phoneObject.put("id", 1L);
        phoneObject.put("customerId", 1L);
        phoneObject.put("phone", "+79012345678");

        when(phoneRepository.findById(any(Long.class))).thenReturn(Optional.of(phone1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/phones/{id}", 1L)
                        .content(phoneObject.toString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.customerId").value(1L))
                .andExpect(jsonPath("$.phone").value("+79012345678"));
    }

    @Test
    public void updatePhoneTest() throws Exception {
        Phone phone1 = new Phone(1L, 1L, "+79012345678");
        Customer customer1 = new Customer(1L, "Vasily Demin");
        Phone phone2 = new Phone(1L, 1L, "+79102345678");

        JSONObject phoneObject = new JSONObject();
        phoneObject.put("id", 1L);
        phoneObject.put("customerId", 1L);
        phoneObject.put("phone", "+79102345678");

        when(phoneRepository.findById(any(Long.class))).thenReturn(Optional.of(phone1));
        when(customerRepository.findCustomerById(any(Long.class))).thenReturn(Optional.of(customer1));
        when(phoneRepository.save(any(Phone.class))).thenReturn(phone2);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/phones")
                        .content(phoneObject.toString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.customerId").value(1L))
                .andExpect(jsonPath("$.phone").value("+79102345678"));
    }

    @Test
    public void deletePhoneTest() throws Exception {
        Phone phone1 = new Phone(1L, 1L, "+79012345678");
        Customer customer1 = new Customer(1L, "Vasily Demin");

        JSONObject phoneObject = new JSONObject();
        phoneObject.put("id", 1L);
        phoneObject.put("customerId", 1L);
        phoneObject.put("phone", "+79012345678");

        when(phoneRepository.findById(any(Long.class))).thenReturn(Optional.of(phone1));
        when(customerRepository.findCustomerById(any(Long.class))).thenReturn(Optional.of(customer1));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/phones")
                        .content(phoneObject.toString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.customerId").value(1L))
                .andExpect(jsonPath("$.phone").value("+79012345678"));
    }

}
