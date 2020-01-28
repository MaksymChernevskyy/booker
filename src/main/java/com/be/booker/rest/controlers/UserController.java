package com.be.booker.rest.controlers;

import com.be.booker.business.entitydto.UserDto;
import com.be.booker.business.entitydto.UserWithoutPasswordDto;
import com.be.booker.business.services.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//@Api(value = "/users", description = "Available operations for users in booking application", tags = {"Users"})
@RestController
@RequestMapping(UserController.BASE_URL)
public class UserController {

    public static final String BASE_URL = "users";
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping()
    public void createNewUser(@Valid @RequestBody UserDto userDto) {
        userService.saveUser(userDto);
    }

    @PostMapping("/{userLogin}")
    public void update(@PathVariable String userLogin, @Valid @RequestBody UserDto userDto) {
        userService.updateUser(userLogin, userDto);
    }

    @DeleteMapping({"/{userLogin}"})
    public void delete(@PathVariable String userLogin) {
        userService.deleteUser(userLogin);
    }

    @GetMapping({"", "/"})
    public List<UserWithoutPasswordDto> getAllUsers() {
        return userService.getAllUsers();
    }

}
