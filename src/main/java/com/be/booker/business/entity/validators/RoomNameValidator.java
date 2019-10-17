package com.be.booker.business.entity.validators;

import java.util.regex.Matcher;

class RoomNameValidator {

  static String validateRoomName(String roomName) {
    if (roomName == null) {
      return "Room name cannot be null";
    }
    if (roomName.trim().isEmpty()) {
      return "Room name cannot be empty";
    }
    Matcher matcher = RegExpPatterns.namePattern.matcher(roomName);
    if (!matcher.matches()) {
      return "Incorrect room name";
    }
    return null;
  }
}
