package com.be.booker.business.entity.validators;

import java.util.regex.Matcher;

class UserNameValidator {

  static String validateName(String name) {
    if (name == null) {
      return "Name cannot be null";
    }
    if (name.trim().isEmpty()) {
      return "Name cannot be empty";
    }
    Matcher matcher = RegExpPatterns.namePattern.matcher(name);
    if (!matcher.matches()) {
      return "Incorrect name";
    }
    return null;
  }
}
