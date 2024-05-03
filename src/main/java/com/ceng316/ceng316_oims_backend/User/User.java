package com.ceng316.ceng316_oims_backend.User;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private Role role;

    public User(String mail, String password, Role role) {
        this.email = mail;
        this.password = password;
        this.role = role;
    }
}
