package com.be.booker.boundary.application;

import com.be.booker.boundary.application.ErrorMessage;
import com.be.booker.business.entity.User;
import com.be.booker.business.entity.validators.UserValidator;
import com.be.booker.business.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "/users", description = "Available operations for users in booking application", tags = {"Users"})
@RestController
@RequestMapping("/users")
public class UserController {
  private @Value("${admin.password}")
  String adminPassword = "${admin.password}";
  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  @ApiOperation(
      value = "Returns all users",
      response = User.class,
      responseContainer = "List")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = User.class),
      @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)})
  public ResponseEntity<?> getAll() {
    try {
      Optional<List<User>> optionalUsersList = userService.getAllUsers();
      return optionalUsersList.<ResponseEntity<?>>map(users -> new ResponseEntity<>(users, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new ArrayList<User>(), HttpStatus.OK));
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorMessage("Internal server error while getting users."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{id}")
  @ApiOperation(
      value = "Get existing user.",
      response = User.class)
  @ApiImplicitParam(name = "id", value = "Only digits possible, e.g. 12", example = "12", dataType = "Long")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = User.class),
      @ApiResponse(code = 404, message = "User not found for passed id.", response = ErrorMessage.class),
      @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)})
  public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
    try {
      Optional<User> optionalUser = userService.getUser(id);
      return optionalUser.<ResponseEntity<?>>map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new ErrorMessage(String.format("User not found for passed id: %d", id)), HttpStatus.NOT_FOUND));
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorMessage(String.format("Internal server error while getting user by id: %d", id)), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/byName")
  @ApiOperation(
      value = "Returns all filtered by user name.",
      response = User.class)
  @ApiImplicitParam(name = "name", value = "Possible letters, numbers and sign '/'  e.g. 'Name'", example = "name")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = User.class),
      @ApiResponse(code = 404, message = "Users not found for passed name.", response = ErrorMessage.class),
      @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)})
  public ResponseEntity<?> findByUserName(@RequestParam("name") String name) {
    try {
      Optional<List<User>> optionalUserList = userService.getAllUsers();
      if (optionalUserList.isPresent()) {
        Optional<User> optionalUser = optionalUserList.get()
            .stream()
            .filter(userToFind -> userToFind.getName().equals(name))
            .findFirst();
        return optionalUser.<ResponseEntity<?>>map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new ErrorMessage(String.format("User not found for passed name: %s", name)), HttpStatus.NOT_FOUND));
      }
      return new ResponseEntity<>(new ErrorMessage(String.format("User not found for passed name: %s", name)), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorMessage(String.format("Internal server error while getting user by name: %s", name)), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping
  @ApiOperation(
      value = "Creates new user.",
      response = User.class)
  @ResponseStatus(HttpStatus.CREATED)
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Created", response = User.class),
      @ApiResponse(code = 400, message = "Passed data is invalid.", response = ErrorMessage.class),
      @ApiResponse(code = 409, message = "User already existsById", response = ErrorMessage.class),
      @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)})
  public ResponseEntity<?> createUser(@RequestBody(required = false) User user) {
    try {
      List<String> resultOfValidation = UserValidator.validate(user, true);
      if (resultOfValidation.size() > 0) {
        return new ResponseEntity<>(new ErrorMessage("Passed user data is invalid.", resultOfValidation), HttpStatus.BAD_REQUEST);
      }
      if (user.getId() == null || !userService.userExistingById(user.getId())) {
        Optional<User> addedUser = userService.createUser(user);
        HttpHeaders responseHeaders = new HttpHeaders();
        if (addedUser.isPresent()) {
          responseHeaders.setLocation(URI.create(String.format("/user/%d", addedUser.get().getId())));
          return new ResponseEntity<>(addedUser.get(), responseHeaders, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new ErrorMessage("Internal server error while adding user."), HttpStatus.INTERNAL_SERVER_ERROR);
      }
      return new ResponseEntity<>(new ErrorMessage("User already exist."), HttpStatus.CONFLICT);
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorMessage("Internal server error while adding user."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/{id}/{password}")
  @ApiOperation(
      value = "Update existing user.",
      response = User.class)
  @ApiImplicitParam(name = "id", value = "Only digits possible, e.g. 12", example = "12", dataType = "Long")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = User.class),
      @ApiResponse(code = 404, message = "User not found for passed id.", response = ErrorMessage.class),
      @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)})
  public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody(required = false) User user, @PathVariable String password) {
    try {
      List<String> resultOfValidation = UserValidator.validate(user, true);
      if (resultOfValidation.size() > 0) {
        return new ResponseEntity<>(new ErrorMessage("Passed user data is invalid.", resultOfValidation), HttpStatus.BAD_REQUEST);
      }
      if (!id.equals(user.getId())) {
        return new ResponseEntity<>(new ErrorMessage(String.format("User to update has different id than %d.", id)), HttpStatus.BAD_REQUEST);
      }
      if (!userService.userExistingById(id)) {
        return new ResponseEntity<>(new ErrorMessage(String.format("User with %d id does not exist.", id)), HttpStatus.NOT_FOUND);
      }
      if (password.equals(adminPassword)) {
        userService.updateUser(user);
      }
      return new ResponseEntity<>(user, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorMessage("Internal server error while updating user."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/{id}/{password}")
  @ApiOperation(
      value = "Removes existing user.",
      response = User.class)
  @ApiImplicitParam(name = "id", value = "Only digits possible, e.g. 12", example = "12", dataType = "Long")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = User.class),
      @ApiResponse(code = 404, message = "User not found for passed id.", response = ErrorMessage.class),
      @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)})
  public ResponseEntity<?> removeUser(@PathVariable("id") Long id, @PathVariable String password) {
    try {
      Optional<User> optionalUser = userService.getUser(id);
      if (!optionalUser.isPresent()) {
        return new ResponseEntity<>(new ErrorMessage(String.format("User with %d id does not exist.", id)), HttpStatus.NOT_FOUND);
      }
      if (password.equals(adminPassword)) {
        userService.deleteUser(id);
      }
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorMessage("Internal server error while removing user."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
