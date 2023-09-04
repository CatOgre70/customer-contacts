package net.vasilydemin.customercontacts.repository;

import net.vasilydemin.customercontacts.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Email repository
 */
public interface EmailRepository extends JpaRepository<Email, Long> {
    Optional<Email> findEmailByEmailIgnoreCase(String email);
    List<Email> findAllByCustomerId(Long customerId);
}
