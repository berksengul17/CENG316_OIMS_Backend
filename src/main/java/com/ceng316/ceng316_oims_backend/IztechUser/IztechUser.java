package com.example.demo.IztechUser;

import com.example.demo.User.User;

public class IztechUser extends User {

    private String fullName;

    public IztechUser() {
    }

    public IztechUser(String mail, String password, String role, String fullName) {
        super(mail, password, role);
        this.fullName = fullName;
    }
}
