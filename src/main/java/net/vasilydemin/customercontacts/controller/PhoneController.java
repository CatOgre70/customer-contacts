package net.vasilydemin.customercontacts.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotNull;
import net.vasilydemin.customercontacts.dto.EmailDto;
import net.vasilydemin.customercontacts.dto.PhoneDto;
import net.vasilydemin.customercontacts.service.PhoneService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/phones")
public class PhoneController {

    private final PhoneService phoneService;

    public PhoneController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @Operation(
            summary = "Adding new phone in the database",
            operationId = "addNewPhone",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PhoneDto.class),
                            examples = {@ExampleObject(name = "JSON object PhoneDto",
                                    value = """
                                    {"id" : 0,
                                    "customerId" : 2,
                                    "phone" : "+79012345678"
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
                    )},
            tags = "Phones"
    )
    @PostMapping
    public PhoneDto createPhone(@RequestBody PhoneDto phoneDto) {
        return phoneService.createPhone(phoneDto);
    }


    @Operation(
            summary = "Read phone from the database by his id",
            operationId = "readPhoneById",
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
            tags = "Phones"
    )
    @GetMapping("/{id}")
    public PhoneDto readPhoneById(@NotNull @PathVariable Long id) {
        return phoneService.readPhoneById(id);
    }

    @Operation(
            summary = "Updating phone in the database with data from PhoneDto",
            operationId = "updatePhone",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PhoneDto.class),
                            examples = {@ExampleObject(name = "JSON object PhoneDto",
                                    value = """
                                    {"id" : 1,
                                    "customerId" : 2,
                                    "phone" : "+79023456789"
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
                    )},
            tags = "Phones"
    )
    @PutMapping
    public PhoneDto updatePhone(@NotNull @RequestBody PhoneDto phoneDto) {
        return phoneService.updatePhone(phoneDto);
    }

    @Operation(
            summary = "Deleting phone from the database",
            operationId = "deletePhone",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PhoneDto.class),
                            examples = {@ExampleObject(name = "JSON object PhoneDto",
                                    value = """
                                    {"id" : 1,
                                    "customerId" : 2,
                                    "phone" : "+79023456789"
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
                    )},
            tags = "Phones"
    )
    @DeleteMapping
    public PhoneDto deletePhone(@NotNull @RequestBody PhoneDto phoneDto) {
        return phoneService.deletePhone(phoneDto);
    }

}
