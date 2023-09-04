package net.vasilydemin.customercontacts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * DTO for CustomerController call /customer/{id}/allcontacts
 * combines all customer and contacts (all emails and phones) information in one object
 */
@Data
@AllArgsConstructor
public class CustomerWithContactsDto {

    private Long id;
    private String name;
    private List<String> emails;
    private List<String> phones;

}
