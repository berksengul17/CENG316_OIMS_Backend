package com.ceng316.ceng316_oims_backend.IztechUser;

import com.ceng316.ceng316_oims_backend.User.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IztechUserService {
    public List<IztechUser> getStudents() {
        return (List.of(new IztechUser("ewe",
                "şşş", User.Role.COMPANY,"xxd")));
    }
}
