package com.be.booker.business.configs.security;

import com.be.booker.business.entity.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm {

    private String login;
    private String name;
    private String surname;
    private String password;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(login, passwordEncoder.encode(password), name, surname);
    }
}
