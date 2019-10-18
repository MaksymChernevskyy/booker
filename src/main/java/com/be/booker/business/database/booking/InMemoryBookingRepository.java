package com.be.booker.business.database.booking;

import com.be.booker.business.database.DatabaseOperationException;
import com.be.booker.business.entity.Booking;
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
public class InMemoryBookingRepository implements BookingDatabase{

    private Map<Long, Booking> bookedRooms = new HashMap<>();
    private AtomicLong counter = new AtomicLong();
    private final Object lock = new Object();

    @Override
    public Optional<Booking> save(Booking booking) {
      synchronized (lock) {
        if (booking == null) {
          throw new IllegalArgumentException("Room cannot be null");
        }
        if (isRoomExist(booking.getId())) {
          throw new IllegalArgumentException("Room already exist");
        }
        long id = counter.incrementAndGet();
        booking.setId(id);
        bookedRooms.put(id, booking);
        return Optional.of(booking);
      }
    }

    @Override
    public Optional<Booking> findById(Long id) {
      synchronized (lock) {
        if (id == null) {
          throw new IllegalArgumentException("Id cannot be null");
        }
        return Optional.ofNullable(bookedRooms.get(id));
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
    public Optional<List<Booking>> findAll() {
      synchronized (lock) {
        return Optional.of(new ArrayList<>(bookedRooms.values()));
      }
    }

    @Override
    public long count() {
      synchronized (lock) {
        return bookedRooms.size();
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
        bookedRooms.remove(id);
      }
    }

    @Override
    public void deleteAll() {
      synchronized (lock) {
        bookedRooms.clear();
      }
    }

    private boolean isRoomExist(long id) {
      return bookedRooms.containsKey(id);
    }
}
