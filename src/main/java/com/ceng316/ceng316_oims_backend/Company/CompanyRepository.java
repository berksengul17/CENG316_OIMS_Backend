package com.ceng316.ceng316_oims_backend.Company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByEmail(String email);
    List<Company> findByRegistrationStatus(RegistrationStatus registrationStatus);
}
