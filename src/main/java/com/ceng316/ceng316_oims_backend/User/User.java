package com.ceng316.ceng316_oims_backend.User;

public class User {

    public enum Role{
        DEPARTMENT_SECRETARY,
        SYSTEM_ADMIN,
        COMPANY,
        STUDENT,
        SUMMER_PRACTICE_COORDINATOR
    }

    private String email;
    private String password;
    private Role role;

    public User() {

    }

    public User(String mail, String password, Role role) {
        this.email = mail;
        this.password = password;
        this.role = role;
    }

    public String getMail() {
        return email;
    }

    public void setMail(String mail) {
        this.email = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
