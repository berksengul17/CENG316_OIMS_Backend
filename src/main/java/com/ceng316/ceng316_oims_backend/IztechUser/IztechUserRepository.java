package com.ceng316.ceng316_oims_backend.IztechUser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IztechUserRepository extends JpaRepository<IztechUser, Long> {
    Optional<IztechUser> findByEmail(String email);
}
