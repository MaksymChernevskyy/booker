package com.be.booker.business.entity.validators;

class IdValidator {

  static String validateId(Long id) {
    if (id == null) {
      return "Id cannot be null";
    }
    if (id <= 0) {
      return "Id cannot be less than or equal to 0";
    }
    return null;
  }
}
