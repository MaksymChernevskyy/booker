package com.be.booker.business.entity.validators;

import com.be.booker.business.entity.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;

public class UserValidator extends Validator {

  public static List<String> validate(User user, boolean isIdRequired) {
    if (user == null) {
      return Collections.singletonList("User cannot be null");
    }
    List<String> result = new ArrayList<>();
    if (isIdRequired) {
      String resultOfIdValidation = validateId(user.getId());
      addResultOfValidation(result, resultOfIdValidation);
    }
    String resultOfNameValidation = validateName(user.getName());
    addResultOfValidation(result, resultOfNameValidation);
    String resultOfSurnameValidation = validateSurname(user.getSurname());
    addResultOfValidation(result, resultOfSurnameValidation);
    String resultOfLoginValidation = validateLogin(user.getLogin());
    addResultOfValidation(result, resultOfLoginValidation);
    String resultOfPasswordValidation = validatePassword(user.getPassword());
    addResultOfValidation(result, resultOfPasswordValidation);
    return result;
  }

  private static String validateId(Long id) {
    return IdValidator.validateId(id);
  }

  private static String validateName(String name) {
    return UserNameValidator.validateName(name);
  }

  private static String validateSurname(String surname) {
    if (surname == null) {
      return "Surname cannot be null";
    }
    if (surname.trim().isEmpty()) {
      return "Surname cannot be empty";
    }
    Matcher matcher = RegExpPatterns.surnamePattern.matcher(surname);
    if (!matcher.matches()) {
      return "Incorrect surname";
    }
    return null;
  }

  private static String validateLogin(String login) {
    if (login == null) {
      return "Login cannot be null";
    }
    if (login.trim().isEmpty()) {
      return "Login cannot be empty";
    }
    Matcher matcher = RegExpPatterns.loginPattern.matcher(login);
    if (!matcher.matches()) {
      return "Incorrect login";
    }
    return null;
  }

  private static String validatePassword(String password) {
    if (password == null) {
      return "Password cannot be null";
    }
    if (password.trim().isEmpty()) {
      return "Password cannot be empty";
    }
    Matcher matcher = RegExpPatterns.passwordPattern.matcher(password);
    if (!matcher.matches()) {
      return "Incorrect password";
    }
    return null;
  }
}
