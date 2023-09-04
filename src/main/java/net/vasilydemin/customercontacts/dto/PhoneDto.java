package net.vasilydemin.customercontacts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO for Phone entity
 */
@Data
@AllArgsConstructor
public class PhoneDto {

    private Long id;

    private Long customerId;

    private String phone;

}
