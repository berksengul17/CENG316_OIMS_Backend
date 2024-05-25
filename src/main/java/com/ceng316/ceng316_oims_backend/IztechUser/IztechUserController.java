package com.ceng316.ceng316_oims_backend.IztechUser;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/iztech-user")
@AllArgsConstructor
public class IztechUserController {

    private final IztechUserService iztechUserService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody IztechUser request) {
        IztechUser loggedInIztechUser = iztechUserService.login(request);
        if(loggedInIztechUser != null) {
            return ResponseEntity.ok(loggedInIztechUser);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Email or password is wrong");
        }
    }
}
