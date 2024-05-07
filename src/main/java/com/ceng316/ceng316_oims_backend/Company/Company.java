package com.ceng316.ceng316_oims_backend.Company;

import com.ceng316.ceng316_oims_backend.Announcements.Announcement;
import com.ceng316.ceng316_oims_backend.Feedback.CompanyFeedback.CompanyFeedback;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Announcement> announcements;
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<CompanyFeedback> feedbacks;

    public Company(String email, String password, String companyName) {
        this.email = email;
        this.password = password;
        this.companyName = companyName;
        this.registrationStatus = RegistrationStatus.PENDING;
    }

    public Company(Long id, String email, String companyName) {
        this.id = id;
        this.email = email;
        this.companyName = companyName;

    }
}
