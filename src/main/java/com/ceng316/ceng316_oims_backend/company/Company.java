package com.example.demo.company;

import com.example.demo.User.User;

enum RegistrationStatus {
    PENDING,
    REJECTED,
    APPROVED
}
public class Company extends User {
    private String companyName;
    private Integer contactNumber;
    private RegistrationStatus registrationStatus;

    public Company() {
    }

    public Company(String mail, String password, String role, String companyName, Integer contactNumber) {
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
