package com.example.demo.User;

public class User {

    private String email;
    private String password;
    private String role;

    public User() {

    }

    public User(String mail, String password, String role) {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
