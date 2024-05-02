package com.ceng316.ceng316_oims_backend.Student;

import com.ceng316.ceng316_oims_backend.IztechUser.IztechUser;

public class Student extends IztechUser {
    private Integer id;

    public Student(String mail, String password, Role role, String fullName, Integer id) {
        super(mail, password, role, fullName);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
