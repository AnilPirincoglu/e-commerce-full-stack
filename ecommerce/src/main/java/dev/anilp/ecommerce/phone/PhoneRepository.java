package dev.anilp.ecommerce.phone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Phone p WHERE p.phoneNumber = :phoneNumber")
    Boolean existsByPhoneNumber(String phoneNumber);
}
