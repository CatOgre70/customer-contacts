package net.vasilydemin.customercontacts.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotNull;
import net.vasilydemin.customercontacts.dto.EmailDto;
import net.vasilydemin.customercontacts.service.EmailService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emails")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @Operation(
            summary = "Adding new email in the database",
            operationId = "addNewEmail",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = EmailDto.class),
                            examples = {@ExampleObject(name = "JSON object EmailDto",
                                    value = """
                                    {"id" : 0,
                                    "customerId" : 2,
                                    "email" : "ivan.ivanov@mail.org"
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
                    )},
            tags = "Emails"
    )
    @PostMapping
    public EmailDto createEmail(@RequestBody EmailDto emailDto) {
        return emailService.createEmail(emailDto);
    }


    @Operation(
            summary = "Read email from the database by his id",
            operationId = "readEmailById",
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
            tags = "Emails"
    )
    @GetMapping("/{id}")
    public EmailDto readEmailById(@NotNull @PathVariable Long id) {
        return emailService.readEmailById(id);
    }

    @Operation(
            summary = "Updating email in the database with data from EmailDto",
            operationId = "updateEmail",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = EmailDto.class),
                            examples = {@ExampleObject(name = "JSON object EmailDto",
                                    value = """
                                    {"id" : 1,
                                    "customerId" : 2,
                                    "email" : "ivan.ivanov@mail.org"
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
                    )},
            tags = "Emails"
    )
    @PutMapping
    public EmailDto updateEmail(@NotNull @RequestBody EmailDto emailDto) {
        return emailService.updateEmail(emailDto);
    }

    @Operation(
            summary = "Deleting email from the database",
            operationId = "deleteEmail",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = EmailDto.class),
                            examples = {@ExampleObject(name = "JSON object EmailDto",
                                    value = """
                                    {"id" : 1,
                                    "customerId" : 2,
                                    "email" : "ivan.ivanov@mail.org"
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
                    )},
            tags = "Emails"
    )
    @DeleteMapping
    public EmailDto deleteEmail(@NotNull @RequestBody EmailDto emailDto) {
        return emailService.deleteEmail(emailDto);
    }

}
