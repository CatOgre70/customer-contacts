package net.vasilydemin.customercontacts.repository;

import net.vasilydemin.customercontacts.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Email repository
 */
public interface PhoneRepository extends JpaRepository<Phone, Long> {
    Optional<Phone> findPhoneByPhoneIgnoreCase(String phone);

    List<Phone> findAllByCustomerId(Long customerId);
}
