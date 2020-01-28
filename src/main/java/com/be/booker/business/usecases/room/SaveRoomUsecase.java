package com.be.booker.business.usecases.room;

import com.be.booker.business.entity.Room;
import com.be.booker.business.entitydto.RoomDto;
import com.be.booker.business.exceptions.AlreadyExistsException;
import com.be.booker.business.repository.RoomRepository;
import org.springframework.stereotype.Component;

@Component
public class SaveRoomUsecase {

  private RoomRepository roomRepository;
  private RoomDto roomDto;

  public SaveRoomUsecase withRoomRepository(RoomRepository roomRepository) {
    this.roomRepository = roomRepository;
    return this;
  }

  public SaveRoomUsecase forRoom(RoomDto roomDto) {
    this.roomDto = roomDto;
    return this;
  }

  public Room run() {
    return createRoom();
  }

  private Room createRoom() {
    Room room = new Room();
    room.setRoomName(roomDto.getName());
    room.setNumberOfSeats(roomDto.getNumberOfSeats());
    if (roomDto.getLocationDescription() != null) {
      room.setLocationDescription(roomDto.getLocationDescription());
    }
    if (roomDto.getContainProjector() != null) {
      room.setContainProjector(roomDto.getContainProjector());
    }
    if (roomDto.getPhoneNumber() != null) {
      room.setPhoneNumber(roomDto.getPhoneNumber());
    }
    if (roomRepository.existsById(roomDto.getName())) {
      throw new AlreadyExistsException(String.format("Room with name %s already exists", roomDto.getName()));
    }
    return roomRepository.save(room);
  }
}
