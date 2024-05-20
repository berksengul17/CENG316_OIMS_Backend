package com.ceng316.ceng316_oims_backend.IztechUser;

import com.ceng316.ceng316_oims_backend.Company.Company;
import com.ceng316.ceng316_oims_backend.Feedback.IztechUserFeedback.IztechUserFeedback;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Objects;


@Getter
@Setter
@ToString

@Entity
// TODO niye no args constructor istiyor öğren
@NoArgsConstructor
public class IztechUser{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "iztechUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private List<IztechUserFeedback> feedbacks;
    private String identityNumber;
    private String schoolId;
    private String grade;
    private String contactNumber;
    private Integer isEligible = 0;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public IztechUser(String fullName, String email, String password,
                      Role role, String identityNumber, String contactNumber) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
//        this.feedbacks = feedbacks;
        this.identityNumber = identityNumber;
        this.contactNumber = contactNumber;
    }

    public IztechUser(String fullName, String email, String password,
                      Role role, String identityNumber, String contactNumber, int isEligible) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
//        this.feedbacks = feedbacks;
        this.identityNumber = identityNumber;
        this.contactNumber = contactNumber;
        this.isEligible = isEligible;
    }

    public IztechUser(String fullName, String email, String password,
                      Role role, List<IztechUserFeedback> feedbacks,
                      String identityNumber, String schoolId, String grade,
                      String contactNumber) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
//        this.feedbacks = feedbacks;
        this.identityNumber = identityNumber;
        this.schoolId = schoolId;
        this.grade = grade;
        this.contactNumber = contactNumber;
    }
    public IztechUser(Long id, String fullName, String email,
                       Role role, String identityNumber,
                       String schoolId, String grade, String contactNumber) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
//        this.feedbacks = feedbacks;
        this.identityNumber = identityNumber;
        this.schoolId = schoolId;
        this.grade = grade;
        this.contactNumber = contactNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        IztechUser that = (IztechUser) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
