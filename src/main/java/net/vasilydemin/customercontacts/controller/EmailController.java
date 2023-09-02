package net.vasilydemin.customercontacts.controller;

import jakarta.validation.constraints.NotNull;
import net.vasilydemin.customercontacts.dto.EmailDto;
import net.vasilydemin.customercontacts.service.EmailService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emails")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public EmailDto createEmail(@RequestBody EmailDto emailDto) {
        return emailService.createEmail(emailDto);
    }

    @GetMapping("/{id}")
    public EmailDto readEmailById(@NotNull @PathVariable Long id) {
        return emailService.readEmailById(id);
    }

}
