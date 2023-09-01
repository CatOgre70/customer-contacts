package net.vasilydemin.customercontacts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CustomerWithContactsDto {

    private Long id;
    private String name;
    private List<String> emails;
    private List<String> phones;

}
