package com.be.booker.rest.controlers;

import com.be.booker.business.entity.User;
import com.be.booker.business.entity.entitydto.UserDto;
import com.be.booker.business.entity.entitydto.UserWithoutPasswordDto;
import com.be.booker.business.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(UserController.BASE_URL)
public class UserController {

    public static final String BASE_URL = "users";
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<?> createNewUser(@Valid @RequestBody UserDto userDto) {
        User addedUser = userService.saveUser(userDto);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(URI.create(String.format("/user/%s", addedUser.getLogin())));
        return getResponseForSuccess(addedUser, responseHeaders);
    }


    @PutMapping("/{userLogin}")
    public ResponseEntity<?> update(@PathVariable String userLogin, @Valid @RequestBody UserDto userDto) {
        userService.updateUser(userLogin, userDto);
        return getResponseForSuccess(userDto);
    }

    @DeleteMapping({"/{userLogin}"})
    public ResponseEntity<?> delete(@PathVariable String userLogin) {
        userService.deleteUser(userLogin);
        return getResponseForSuccess();
    }

    @GetMapping({"", "/"})
    public ResponseEntity<?> getAllUsers() {
        List<UserWithoutPasswordDto> list = userService.getAllUsers();
        return getResponseForSuccess(list);
    }

    private ResponseEntity<?> getResponseForSuccess(List<UserWithoutPasswordDto> list) {
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    private ResponseEntity<?> getResponseForSuccess(User addedUser, HttpHeaders responseHeaders) {
        return new ResponseEntity<>(addedUser, responseHeaders, HttpStatus.CREATED);
    }

    private ResponseEntity<?> getResponseForSuccess() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity<?> getResponseForSuccess(@RequestBody @Valid UserDto userDto) {
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
