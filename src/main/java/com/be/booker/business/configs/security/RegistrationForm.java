package com.be.booker.business.configs.security;

import com.be.booker.business.entity.entitydto.UserDto;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Size;

@Data
public class RegistrationForm {

    @Size(max = 100, message = "Max size is 100")
    private String login;

    @Size(min = 6, max = 100, message = "Size must be between 5 and 100")
    private String name;

    @Size(max = 50, message = "Max size is 50")
    private String surname;

    @Size(max = 100,  message = "Max size is 100")
    private String password;

    public UserDto toUser(PasswordEncoder passwordEncoder) {
        return new UserDto(login, passwordEncoder.encode(password), name, surname);
    }
}
