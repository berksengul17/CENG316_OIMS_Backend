package com.ceng316.ceng316_oims_backend.company;

import com.ceng316.ceng316_oims_backend.User.Role;
import com.ceng316.ceng316_oims_backend.User.User;
import jakarta.persistence.Entity;
import org.springframework.stereotype.Repository;

@Entity
public class Company extends User {

    private String companyName;
    private Integer contactNumber;
    private RegistrationStatus registrationStatus;

    public Company() {
    }

    public Company(String mail, String password, Role role, String companyName, Integer contactNumber) {
        super(mail, password, role);
        this.companyName = companyName;
        this.contactNumber = contactNumber;
        this.registrationStatus = RegistrationStatus.PENDING;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Integer contactNumber) {
        this.contactNumber = contactNumber;
    }

    public RegistrationStatus getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(RegistrationStatus registrationStatus) {
        this.registrationStatus = registrationStatus;
    }
}
