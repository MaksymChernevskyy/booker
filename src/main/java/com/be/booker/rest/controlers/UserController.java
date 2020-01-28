package com.be.booker.rest.controlers;

import com.be.booker.business.entity.User;
import com.be.booker.business.entitydto.UserDto;
import com.be.booker.business.entitydto.UserWithoutPasswordDto;
import com.be.booker.business.services.UserService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    return new ResponseEntity<>(addedUser, responseHeaders, HttpStatus.CREATED);
  }

  @PutMapping("/{userLogin}")
  public void update(@PathVariable String userLogin, @Valid @RequestBody UserDto userDto) {
    userService.updateUser(userLogin, userDto);
  }

  @DeleteMapping( {"/{userLogin}"})
  public void delete(@PathVariable String userLogin) {
    userService.deleteUser(userLogin);
  }

  @GetMapping( {"", "/"})
  public List<UserWithoutPasswordDto> getAllUsers() {
    return userService.getAllUsers();
  }

}
