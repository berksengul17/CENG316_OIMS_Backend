package com.ceng316.ceng316_oims_backend.Company;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String companyName;
    @Enumerated(EnumType.STRING)
    private RegistrationStatus registrationStatus;

    public Company(String email, String password, String companyName) {
        this.email = email;
        this.password = password;
        this.companyName = companyName;
        this.registrationStatus = RegistrationStatus.PENDING;
    }

    public Company(String email, String companyName) {
        this.email = email;
        this.companyName = companyName;

    }
}
