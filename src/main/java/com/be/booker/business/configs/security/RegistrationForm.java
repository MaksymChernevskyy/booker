package com.be.booker.business.configs.security;

import com.be.booker.business.entity.entitydto.UserDto;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm {

    private String login;
    private String name;
    private String surname;
    private String password;

    public UserDto toUser(PasswordEncoder passwordEncoder) {
        return new UserDto(login, passwordEncoder.encode(password), name, surname);
    }
}
