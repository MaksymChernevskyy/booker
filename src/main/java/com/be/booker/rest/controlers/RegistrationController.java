package com.be.booker.rest.controlers;

import com.be.booker.business.configs.security.RegistrationForm;
import com.be.booker.business.entity.User;
import com.be.booker.business.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<?> processRegistration(@RequestBody RegistrationForm form) {
        User savedUser = userService.saveUser(form.toUser(passwordEncoder));
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(URI.create(String.format("/user/%s", savedUser.getLogin())));
        return new ResponseEntity<>(savedUser, responseHeaders, HttpStatus.CREATED);
    }
}
