package net.vasilydemin.customercontacts.mapper;

import net.vasilydemin.customercontacts.dto.PhoneDto;
import net.vasilydemin.customercontacts.entity.Phone;
import org.springframework.stereotype.Component;

@Component
public class PhoneMapper {

    public PhoneDto entityToDto(Phone phone){
        return new PhoneDto(phone.getId(), phone.getCustomerId(), phone.getPhone());
    }

    public Phone dtoToEntity(PhoneDto phoneDto) {
        return new Phone(phoneDto.getId(), phoneDto.getCustomerId(), phoneDto.getPhone());
    }

}
