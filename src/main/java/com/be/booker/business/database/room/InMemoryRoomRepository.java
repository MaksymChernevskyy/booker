package com.be.booker.business.database.room;

import com.be.booker.business.database.DatabaseOperationException;
import com.be.booker.business.entity.Room;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

@ConditionalOnProperty(name = "com.be.booker.business.database", havingValue = "in-memory")
@Repository
public class InMemoryRoomRepository implements RoomDatabase {
  private Map<Long, Room> rooms = new HashMap<>();
  private AtomicLong counter = new AtomicLong(0);
  private final Object lock = new Object();

  public InMemoryRoomRepository() {
    DefaultRooms startData = new DefaultRooms();
    save(startData.createLargeRoom());
    save(startData.createMediumRoom());
    save(startData.createSmallRoom());
  }

  @Override
  public Optional<Room> save(Room room) {
    synchronized (lock) {
      if (room == null) {
        throw new IllegalArgumentException("Room cannot be null");
      }
      if (isRoomExist(room.getId())) {
        throw new IllegalArgumentException("Room already exist");
      }
      long id = counter.incrementAndGet();
      room.setId(id);
      rooms.put(id, room);
      return Optional.of(room);
    }
  }

  @Override
  public Optional<Room> findById(Long id) {
    synchronized (lock) {
      if (id == null) {
        throw new IllegalArgumentException("Id cannot be null");
      }
      return Optional.ofNullable(rooms.get(id));
    }
  }

  @Override
  public boolean existsById(Long id) {
    synchronized (lock) {
      if (id == null) {
        throw new IllegalArgumentException("Id cannot be null");
      }
      return isRoomExist(id);
    }
  }

  @Override
  public Optional<List<Room>> findAll() {
    synchronized (lock) {
      return Optional.of(new ArrayList<>(rooms.values()));
    }
  }

  @Override
  public long count() {
    synchronized (lock) {
      return rooms.size();
    }
  }

  @Override
  public void deleteById(Long id) {
    synchronized (lock) {
      if (id == null) {
        throw new IllegalArgumentException("Id cannot be null");
      }
      if (!isRoomExist(id)) {
        throw new DatabaseOperationException("Room does not exist");
      }
      rooms.remove(id);
    }
  }

  @Override
  public void deleteAll() {
    synchronized (lock) {
      rooms.clear();
    }
  }

  private boolean isRoomExist(long id) {
    return rooms.containsKey(id);
  }
}
