package com.be.booker.business.entity.validators;

import com.be.booker.business.entity.Room;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;

class RoomValidator extends Validator {

   static List<String> validate(Room room, boolean isIdRequired) {
    if (room == null) {
      return Collections.singletonList("Room cannot be null");
    }
    List<String> result = new ArrayList<>();
    if (isIdRequired) {
      String resultOfIdValidation = validateId(room.getId());
      addResultOfValidation(result, resultOfIdValidation);
    }
      String resultOfNameValidation = validateRoomName(room.getRoomName());
      addResultOfValidation(result, resultOfNameValidation);
      String resultOfLocationDescriptionValidation = validateLocationDescription(room.getLocationDescription());
      addResultOfValidation(result, resultOfLocationDescriptionValidation);
      String resultOfNumberOfSeatsValidation = validateNumberOfSeats(room.getNumberOfSeats());
      addResultOfValidation(result, resultOfNumberOfSeatsValidation);
      String resultOfPhoneNumberValidation = phoneNumberValidator(room.getPhoneNumber());
      addResultOfValidation(result, resultOfPhoneNumberValidation);
      return result;
  }

  private static String validateId(Long id) {
    return IdValidator.validateId(id);
  }

  private static String validateRoomName(String roomName) {
    return RoomNameValidator.validateRoomName(roomName);
  }

  private static String validateLocationDescription(String location) {
    if (location == null) {
      return "Location cannot be null";
    }
    if (location.trim().isEmpty()) {
      return "Location cannot be empty";
    }
    Matcher matcher = RegExpPatterns.locationPattern.matcher(location);
    if (!matcher.matches()) {
      return "Incorrect location";
    }
    return null;
  }

  private static String validateNumberOfSeats(Integer numberOfSeats) {
    if(numberOfSeats == null){
      return "Number of seats cannot be null";
    }
    if(numberOfSeats <= 0){
      return "Number of seats cannot be less than 1";
    }
    if(numberOfSeats > 100){
      return "Number of seats cannot be more than 100";
    }
    return null;
  }

  private static String phoneNumberValidator(String phoneNumber) {
    if (phoneNumber == null) {
      return "Phone number cannot be null";
    }
    if (phoneNumber.trim().isEmpty()) {
      return "Phone number cannot be empty";
    }
    Matcher matcher = RegExpPatterns.phoneNumber.matcher(phoneNumber);
    if (!matcher.matches()) {
      return "Incorrect phone number";
    }
    return null;
  }
}
