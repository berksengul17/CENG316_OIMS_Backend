package com.ceng316.ceng316_oims_backend.Student;

import com.ceng316.ceng316_oims_backend.IztechUser.IztechUser;
import com.ceng316.ceng316_oims_backend.User.Role;

public class Student extends IztechUser {
    private Integer studentId;

    public Student(String mail, String password, Role role, String fullName, Integer studentId) {
        super(mail, password, role, fullName);
        this.studentId = studentId;
    }

}
