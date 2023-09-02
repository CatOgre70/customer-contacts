package net.vasilydemin.customercontacts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailDto {

    private Long id;

    private Long customerId;

    private String email;

}
