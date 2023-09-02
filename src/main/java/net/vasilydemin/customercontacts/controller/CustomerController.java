package net.vasilydemin.customercontacts.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import net.vasilydemin.customercontacts.dto.CustomerDto;
import net.vasilydemin.customercontacts.dto.CustomerWithContactsDto;
import net.vasilydemin.customercontacts.dto.EmailDto;
import net.vasilydemin.customercontacts.dto.PhoneDto;
import net.vasilydemin.customercontacts.service.CustomerService;
import net.vasilydemin.customercontacts.service.EmailService;
import net.vasilydemin.customercontacts.service.PhoneService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/customer")
@Tag(name = "Customers", description = "Customers API")
public class CustomerController {

    @Value("${application.default.page}")
    private int defaultPage;

    @Value("${application.default.itemsperpage}")
    private int defaultItemsPerPage;


    private final CustomerService customerService;
    private final EmailService emailService;
    private final PhoneService phoneService;

    public CustomerController(CustomerService customerService, EmailService emailService, PhoneService phoneService) {
        this.customerService = customerService;
        this.emailService = emailService;
        this.phoneService = phoneService;
    }

    @Operation(
            summary = "Adding new customer in the database",
            operationId = "addNewCustomer",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CustomerDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
            },
            tags = "Customers"
    )
    @PostMapping
    public CustomerDto createCustomer(@NotNull @RequestParam(value = "name") String name){
        return customerService.createCustomer(name);
    }

    @Operation(
            summary = "Read customer from the database by id",
            operationId = "readCustomerById",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CustomerDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
            },
            tags = "Customers"
    )
    @GetMapping("/{id}")
    public CustomerDto readCustomerById(@NotNull @PathVariable Long id) {
        return customerService.readCustomerById(id);
    }

    @Operation(
            summary = "Read customer from the database by name (strings must be equal, case insensitive) or read all " +
                    "customers if name = null or blank",
            operationId = "readCustomerByName",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CustomerDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
            },
            tags = "Customers"
    )
    @GetMapping
    public List<CustomerDto> readCustomerByName(@RequestParam(name = "name", required = false) String name,
                                                @RequestParam(name = "page", required = false) Integer page,
                                                @RequestParam(name = "items", required = false) Integer items) {
        if(!(name == null || name.isEmpty() || name.isBlank())) {
            return Collections.singletonList(customerService.readCustomerByName(name));
        } else {
            if(page == null || page < 0) {
                page = defaultPage;
            }
            if(items == null || items < 1) {
                items = defaultItemsPerPage;
            }
            return customerService.readAllCustomers(page, items);
        }
    }

    @Operation(
            summary = "Read all emails owned by customer from the database by customer id",
            operationId = "readAllCustomerEmails",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = EmailDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
            },
            tags = "Customers"
    )
    @GetMapping("/{id}/allemails")
    public List<EmailDto> readAllEmailsByCustomerId(@PathVariable Long id) {
        return emailService.findAllEmailsByCustomerId(id);
    }

    @Operation(
            summary = "Read all phones owned by customer from the database by customer id",
            operationId = "readAllCustomerPhones",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PhoneDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
            },
            tags = "Customers"
    )
    @GetMapping("/{id}/allphones")
    public List<PhoneDto> readAllPhonesByCustomerId(@PathVariable Long id) {
        return phoneService.findAllPhonesByCustomerId(id);
    }

    @Operation(
            summary = "Read all customer contact information from the database by customer id",
            operationId = "readAllCustomerContacts",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CustomerWithContactsDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
            },
            tags = "Customers"
    )
    @GetMapping("/{id}/allcontacts")
    public CustomerWithContactsDto readAllContactsByCustomerId(@PathVariable Long id) {
        return customerService.readAllContactsByCustomerId(id);
    }

    @Operation(
            summary = "Read all customer contact information from the database by customer id an by type (email or phone)",
            operationId = "readAllCustomerContactsByType",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
            },
            tags = "Customers"
    )
    @GetMapping("/{id}/allcontactsbytype")
    public List<String> readAllContactsByCustomerIdAndByType(@NotNull @PathVariable Long id,
                                                             @NotNull @RequestParam(name = "type") String type) {
        return customerService.readAllContactsByCustomerIdAndByType(id, type);
    }

    @Operation(
            summary = "Update customer name in the database",
            operationId = "updateCustomer",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CustomerDto.class),
                            examples = {@ExampleObject(name = "JSON object CustomerDto",
                                    value = """
                                    {"id" : 1,
                                    "name" : "Ivan Ivanov"
                                    }"""
                            )}
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CustomerDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    )
            },
            tags = "Customers"
    )
    @PutMapping
    public CustomerDto updateCustomer(@NotNull @RequestBody CustomerDto customerDto) {
        return customerService.updateCustomer(customerDto);
    }

    @Operation(
            summary = "Delete customer from the database",
            operationId = "deleteCustomer",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CustomerDto.class),
                            examples = {@ExampleObject(name = "JSON object CustomerDto",
                                    value = """
                                    {"id" : 1,
                                    "name" : "Ivan Ivanov"
                                    }"""
                            )}
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CustomerDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    )
            },
            tags = "Customers"
    )
    @DeleteMapping
    public CustomerDto deleteCustomer(@NotNull @RequestBody CustomerDto customerDto) {
        return customerService.deleteCustomer(customerDto);
    }

}
