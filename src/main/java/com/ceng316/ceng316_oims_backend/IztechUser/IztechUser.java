package com.ceng316.ceng316_oims_backend.IztechUser;

import com.ceng316.ceng316_oims_backend.User.Role;
import com.ceng316.ceng316_oims_backend.User.User;

public class IztechUser extends User{

    private String fullName;

    public IztechUser() {
    }

    public IztechUser(String mail, String password, Role role, String fullName) {
        super(mail, password, role);
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
