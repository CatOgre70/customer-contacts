package net.vasilydemin.customercontacts.mapper;

import net.vasilydemin.customercontacts.dto.EmailDto;
import net.vasilydemin.customercontacts.entity.Email;
import org.springframework.stereotype.Component;

/**
 * Email entity to DTO and DTO to entity mapper. No more, but no less
 */
@Component
public class EmailMapper {

    public EmailDto entityToDto(Email email) {
        return new EmailDto(email.getId(), email.getCustomerId(), email.getEmail());
    }

    public Email dtoToEntity(EmailDto emailDto) {
        return new Email(emailDto.getId(), emailDto.getCustomerId(), emailDto.getEmail());
    }

}
