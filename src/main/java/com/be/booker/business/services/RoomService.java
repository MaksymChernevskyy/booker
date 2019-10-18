package com.be.booker.business.services;

import com.be.booker.business.database.DatabaseOperationException;
import com.be.booker.business.database.room.RoomDatabase;
import com.be.booker.business.entity.Room;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
 private RoomDatabase roomDatabase;

 @Autowired
  public RoomService(RoomDatabase roomDatabase) {
    this.roomDatabase = roomDatabase;
  }

  public Optional<Room> createRoom(Room room) {
    if (room == null) {
      throw new IllegalArgumentException("Room cannot be null.");
    }
    try {
      Long id = room.getId();
      if (id != null && roomDatabase.existsById(id)) {
        throw new ServiceOperationException(String.format("Room with id %s already existsById", id));
      }
      return roomDatabase.save(room);
    } catch (DatabaseOperationException e) {
      throw new ServiceOperationException("An error while adding room.", e);
    }
  }

  public Optional<List<Room>> getAllRooms() {
    try {
      return roomDatabase.findAll();
    } catch (DatabaseOperationException e) {
      throw new ServiceOperationException("An error while getting all rooms", e);
    }
  }

  public Optional<Room> getRoom(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null.");
    }
    try {
      return roomDatabase.findById(id);
    } catch (DatabaseOperationException e) {
      throw new ServiceOperationException("An error while getting room.", e);
    }
  }

  public void updateRoom(Room room) {
    if (room == null) {
      throw new IllegalArgumentException("Room cannot be null.");
    }
    try {
      Long id = room.getId();
      if (id == null || !roomDatabase.existsById(id)) {
        throw new ServiceOperationException(String.format("Room with id %s does not exist", id));
      }
      roomDatabase.save(room);
    } catch (DatabaseOperationException e) {
      throw new ServiceOperationException("An error while updating room.", e);
    }
  }

  public void deleteRoom(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null.");
    }
    try {
      if (!roomDatabase.existsById(id)) {
        throw new ServiceOperationException(String.format("Room with id %s does not exist", id));
      }
      roomDatabase.deleteById(id);
    } catch (DatabaseOperationException e) {
      throw new ServiceOperationException("An error while deleting room.", e);
    }
  }

  public boolean roomExistingById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null.");
    }
    try {
      return roomDatabase.existsById(id);
    } catch (DatabaseOperationException e) {
      throw new ServiceOperationException("An error while checking if room exist.", e);
    }
  }
}
