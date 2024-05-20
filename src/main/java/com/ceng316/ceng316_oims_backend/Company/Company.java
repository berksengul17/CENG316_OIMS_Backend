package com.ceng316.ceng316_oims_backend.Company;

import com.ceng316.ceng316_oims_backend.Announcements.Announcement;
import com.ceng316.ceng316_oims_backend.Feedback.CompanyFeedback.CompanyFeedback;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString

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
    @ToString.Exclude
    private List<Announcement> announcements;
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private List<CompanyFeedback> feedbacks;

    @OneToMany(mappedBy = "company")
    private List<IztechUser> iztechUser;

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

    public Company(Long id, String email, String companyName, RegistrationStatus registrationStatus) {
        this.id = id;
        this.email = email;
        this.companyName = companyName;
        this.registrationStatus = registrationStatus;
    }

    public Company(String email, String password, String companyName, RegistrationStatus registrationStatus) {
        this.email = email;
        this.password = password;
        this.companyName = companyName;
        this.registrationStatus = registrationStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Company company = (Company) o;
        return id != null && Objects.equals(id, company.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
