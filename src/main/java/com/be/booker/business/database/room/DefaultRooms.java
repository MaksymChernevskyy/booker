package com.be.booker.business.database.room;

import com.be.booker.business.entity.Room;

class DefaultRooms {

  Room createLargeRoom() {
    Room room = new Room();
    room.setId(1L);
    room.setRoomName("Large room");
    room.setLocationDescription("1st floor");
    room.setNumberOfSeats(10);
    room.setContainProjector(true);
    room.setPhoneNumber("22-22-22-22");
    return room;
  }

  Room createMediumRoom() {
    Room room = new Room();
    room.setId(2L);
    room.setRoomName("Medium room");
    room.setLocationDescription("2nd floor");
    room.setNumberOfSeats(6);
    room.setContainProjector(true);
    room.setPhoneNumber("");
    return room;
  }

  Room createSmallRoom() {
    Room room = new Room();
    room.setId(3L);
    room.setRoomName("Small room");
    room.setLocationDescription("3rd floor");
    room.setNumberOfSeats(4);
    room.setContainProjector(false);
    room.setPhoneNumber("");
    return room;
  }
}
