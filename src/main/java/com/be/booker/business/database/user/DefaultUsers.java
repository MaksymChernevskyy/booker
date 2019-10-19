package com.be.booker.business.database.user;

import com.be.booker.business.entity.User;

public class DefaultUsers {
  User createJohn() {
    User user = new User();
    user.setId(1L);
    user.setName("John");
    user.setSurname("Smith");
    user.setLogin("jsmith");
    user.setPassword("qwerty");
    return user;
  }

  User createJane() {
    User user = new User();
    user.setId(2L);
    user.setName("Jane");
    user.setSurname("Doe");
    user.setLogin("jdoe");
    user.setPassword("mysecret");
    return user;
  }
}
