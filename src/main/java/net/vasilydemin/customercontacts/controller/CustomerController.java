package net.vasilydemin.customercontacts.controller;

import jakarta.validation.constraints.NotNull;
import net.vasilydemin.customercontacts.dto.CustomerDto;
import net.vasilydemin.customercontacts.service.CustomerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public CustomerDto createCustomer(@NotNull @RequestParam(value = "name") String name){
        return customerService.createCustomer(name);
    }

}
