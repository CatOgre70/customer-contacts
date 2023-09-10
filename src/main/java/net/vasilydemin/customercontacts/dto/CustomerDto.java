package net.vasilydemin.customercontacts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * DTO for Customer entity
 */
@Data
@AllArgsConstructor
public class CustomerDto {

    @Schema(description = "Customer id", name = "id", type = "Long", example = "1")
    private Long id;
    @Schema(description = "Name of the customer", name = "name", type = "string", example = "Vasily Demin")
    private String name;

}
