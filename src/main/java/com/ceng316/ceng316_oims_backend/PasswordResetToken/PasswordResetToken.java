package com.ceng316.ceng316_oims_backend.PasswordResetToken;

import com.ceng316.ceng316_oims_backend.Company.Company;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class PasswordResetToken {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = Company.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private Company user;

    private Date expiryDate;
}