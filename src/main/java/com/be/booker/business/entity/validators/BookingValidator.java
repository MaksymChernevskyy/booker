package com.be.booker.business.entity.validators;

import com.be.booker.business.entity.BookingRoom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookingValidator extends Validator {

  public static List<String> validate(BookingRoom bookingRooms, boolean isIdRequired) {
    if (bookingRooms == null) {
      return Collections.singletonList("Booking room cannot be null");
    }
    List<String> result = new ArrayList<>();
    if (isIdRequired) {
      String resultOfIdValidation = validateId(bookingRooms.getId());
      addResultOfValidation(result, resultOfIdValidation);
    }
    String resultOfBookedTimeValidation = validateDate(bookingRooms.getBookedFrom(), bookingRooms.getBookedTo());
    addResultOfValidation(result, resultOfBookedTimeValidation);
    String resultOfUserValidation = validateName(bookingRooms.getUserName());
    addResultOfValidation(result, resultOfUserValidation);
    String resultOfRoomsValidation = validateRoomName(bookingRooms.getRoomName());
    addResultOfValidation(result, resultOfRoomsValidation);
    return result;
  }

  private static String validateId(Long id) {
    return IdValidator.validateId(id);
  }

  private static String validateRoomName(String roomName) {
    return RoomNameValidator.validateRoomName(roomName);
  }

  private static String validateName(String name) {
    return UserNameValidator.validateName(name);
  }

  private static String validateDate(LocalDateTime bookedFrom, LocalDateTime bookedTo) {
    if (bookedFrom == null) {
      return "Booked from date cannot be null";
    }
    if (bookedTo == null) {
      return "Booked to date cannot be null";
    }
    if (bookedFrom.isAfter(bookedTo)) {
      return "Booked from date cannot be after booked to date";
    }
    return null;
  }
}
