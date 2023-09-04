package net.vasilydemin.customercontacts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * DTO for Customer entity
 */
@Data
@AllArgsConstructor
public class CustomerDto {

    private Long id;
    private String name;

}
