package com.ceng316.ceng316_oims_backend.PasswordResetToken;

import com.ceng316.ceng316_oims_backend.Company.Company;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Calendar;
import java.util.Date;

@Entity
@Data
public class PasswordResetToken {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = Company.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "company_id")
    private Company company;

    private Date expiryDate;

    public PasswordResetToken() {

    }
    public PasswordResetToken(final String token, final Company company) {
        this.token = token;
        this.company = company;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    private Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
}