package com.example.demo.IztechUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class IztechUserController {
    private final IztechUserService iztechUserService;

    @Autowired
    public IztechUserController(IztechUserService iztechUserService) {
        this.iztechUserService = iztechUserService;
    }

    @GetMapping
    public List<IztechUser> getStudents() {
        return iztechUserService.getStudents();
    }
}
