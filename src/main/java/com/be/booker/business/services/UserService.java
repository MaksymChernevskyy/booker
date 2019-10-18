package com.be.booker.business.services;

import com.be.booker.business.database.DatabaseOperationException;
import com.be.booker.business.database.user.UserDatabase;
import com.be.booker.business.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private UserDatabase userDatabase;

  @Autowired
  public UserService(UserDatabase userDatabase) {
    this.userDatabase = userDatabase;
  }

  public Optional<User> createUser(User user) {
    if (user == null) {
      throw new IllegalArgumentException("User cannot be null.");
    }
    try {
      Long id = user.getId();
      if (id != null && userDatabase.existsById(id)) {
        throw new ServiceOperationException(String.format("User with id %s already existsById", id));
      }
      return userDatabase.save(user);
    } catch (DatabaseOperationException e) {
      throw new ServiceOperationException("An error while adding user.", e);
    }
  }

  public Optional<List<User>> getAllUsers() {
    try {
      return userDatabase.findAll();
    } catch (DatabaseOperationException e) {
      throw new ServiceOperationException("An error while getting all users", e);
    }
  }

  public Optional<User> getUser(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null.");
    }
    try {
      return userDatabase.findById(id);
    } catch (DatabaseOperationException e) {
      throw new ServiceOperationException("An error while getting user.", e);
    }
  }

  public void updateUser(User user) {
    if (user == null) {
      throw new IllegalArgumentException("User cannot be null.");
    }
    try {
      Long id = user.getId();
      if (id == null || !userDatabase.existsById(id)) {
        throw new ServiceOperationException(String.format("User with id %s does not exist", id));
      }
      userDatabase.save(user);
    } catch (DatabaseOperationException e) {
      throw new ServiceOperationException("An error while updating user.", e);
    }
  }

  public void deleteUser(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null.");
    }
    try {
      if (!userDatabase.existsById(id)) {
        throw new ServiceOperationException(String.format("User with id %s does not exist", id));
      }
      userDatabase.deleteById(id);
    } catch (DatabaseOperationException e) {
      throw new ServiceOperationException("An error while deleting user.", e);
    }
  }

  public boolean userExistingById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null.");
    }
    try {
      return userDatabase.existsById(id);
    } catch (DatabaseOperationException e) {
      throw new ServiceOperationException("An error while checking if user exist.", e);
    }
  }
}
