package net.vasilydemin.customercontacts.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotNull;
import net.vasilydemin.customercontacts.dto.CustomerDto;
import net.vasilydemin.customercontacts.service.CustomerService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
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
    public List<CustomerDto> readCustomerByName(@RequestParam(name = "name", required = false) String name) {
        if(!(name == null || name.isEmpty() || name.isBlank())) {
            return Collections.singletonList(customerService.readCustomerByName(name));
        } else {
            return customerService.readAllCustomers();
        }
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
