package com.be.booker.rest.controlers;

import com.be.booker.business.configs.security.RegistrationForm;
import com.be.booker.business.entity.User;
import com.be.booker.business.entity.entitydto.UserDto;
import com.be.booker.business.entity.entitydto.UserWithoutPasswordDto;
import com.be.booker.business.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(UserController.BASE_URL)
public class UserController {

    public static final String BASE_URL = "users";
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PutMapping("/{userLogin}")
    public ResponseEntity<?> update(@PathVariable String userLogin, @Valid @RequestBody RegistrationForm registrationForm) {
        User updatedUser = userService.updateUser(userLogin, registrationForm.toUser(passwordEncoder));
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping({"/{userLogin}"})
    public ResponseEntity<?> getByLogin(@PathVariable String userLogin) {
        UserDto userDto = userService.getUser(userLogin);
        return getResponseForSuccess(userDto);
    }


    @DeleteMapping({"/{userLogin}"})
    public ResponseEntity<?> delete(@PathVariable String userLogin) {
        userService.deleteUser(userLogin);
        return getResponseForSuccess();
    }

    @GetMapping({"/"})
    public ResponseEntity<?> getAllUsers() {
        List<UserWithoutPasswordDto> list = userService.getAllUsers();
        return getResponseForSuccess(list);
    }

    private ResponseEntity<?> getResponseForSuccess(List<UserWithoutPasswordDto> list) {
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    private ResponseEntity<?> getResponseForSuccess() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity<?> getResponseForSuccess(UserDto userDto) {
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
