package com.be.booker.business.services;

import com.be.booker.business.database.DatabaseOperationException;
import com.be.booker.business.database.booking.BookingDatabase;
import com.be.booker.business.entity.Booking;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
  private BookingDatabase bookingDatabase;

  @Autowired
  public BookingService(BookingDatabase bookingDatabase) {
    this.bookingDatabase = bookingDatabase;
  }

  public Optional<Booking> createBooking(Booking booking) {
    if (booking == null) {
      throw new IllegalArgumentException("BookingController room cannot be null.");
    }
    try {
      Long id = booking.getId();
      if (id != null && bookingDatabase.existsById(id)) {
        throw new ServiceOperationException(String.format("Room with id %s already existsById", id));
      }
      return bookingDatabase.save(booking);
    } catch (DatabaseOperationException e) {
      throw new ServiceOperationException("An error while booking room.", e);
    }
  }

  public Optional<List<Booking>> getAllBookingsInGivenRange(LocalDateTime fromDate, LocalDateTime toDate) {
    if (fromDate == null) {
      throw new IllegalArgumentException("fromDate cannot be null");
    }
    if (toDate == null) {
      throw new IllegalArgumentException("toDate cannot be null");
    }
    if (toDate.isBefore(fromDate)) {
      throw new IllegalArgumentException("toDate cannot be before fromDate.");
    }
    Optional<List<Booking>> allRoomsOptional = getAllBookings();
    List<Booking> roomsInDataRange = new ArrayList<>();
    if (allRoomsOptional.isPresent()) {
      roomsInDataRange = allRoomsOptional
          .get()
          .stream()
          .filter(bookingRoom -> bookingRoom.getBookedFrom().compareTo(fromDate) >= 0 && bookingRoom.getBookedTo().compareTo(toDate) <= 0)
          .collect(Collectors.toList());
    }
    return Optional.of(roomsInDataRange);
  }

  public Optional<List<Booking>> getBookingsByUser(String user) {
    if (user == null) {
      throw new IllegalArgumentException("user cannot be null");
    }
    Optional<List<Booking>> allRoomsOptional = getAllBookings();
    List<Booking> bookedRoomsByUser = new ArrayList<>();
    if (allRoomsOptional.isPresent()) {
      bookedRoomsByUser = allRoomsOptional
          .get()
          .stream()
          .filter(bookingRoom -> bookingRoom.getUserName().contains(user))
          .collect(Collectors.toList());
    }
    return Optional.of(bookedRoomsByUser);
  }

  public Optional<List<Booking>> getAllBookedRoomsByUserInGivenRange(String user, LocalDateTime fromDate, LocalDateTime toDate) {
    if (user == null) {
      throw new IllegalArgumentException("user cannot be null");
    }
    Optional<List<Booking>> allRoomsOptional = getAllBookings();
    List<Booking> bookedRoomsByUser = new ArrayList<>();
    if (allRoomsOptional.isPresent()) {
      bookedRoomsByUser = allRoomsOptional
          .get()
          .stream()
          .filter(bookingRoom -> bookingRoom.getUserName().contains(user) && bookingRoom.getBookedFrom().compareTo(fromDate) >= 0 && bookingRoom.getBookedTo().compareTo(toDate) <= 0)
          .collect(Collectors.toList());
    }
    return Optional.of(bookedRoomsByUser);
  }

  public Optional<List<Booking>> getAllBookings() {
    try {
      return bookingDatabase.findAll();
    } catch (DatabaseOperationException e) {
      throw new ServiceOperationException("An error while getting all rooms", e);
    }
  }

  public Optional<Booking> getBooking(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null.");
    }
    try {
      return bookingDatabase.findById(id);
    } catch (DatabaseOperationException e) {
      throw new ServiceOperationException("An error while getting room.", e);
    }
  }

  public void updateBooking(Booking room) {
    if (room == null) {
      throw new IllegalArgumentException("Room cannot be null.");
    }
    try {
      Long id = room.getId();
      if (id == null || !bookingDatabase.existsById(id)) {
        throw new ServiceOperationException(String.format("Room with id %s does not exist", id));
      }
      bookingDatabase.save(room);
    } catch (DatabaseOperationException e) {
      throw new ServiceOperationException("An error while updating room.", e);
    }
  }

  public void deleteBooking(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null.");
    }
    try {
      if (!bookingDatabase.existsById(id)) {
        throw new ServiceOperationException(String.format("Room with id %s does not exist", id));
      }
      bookingDatabase.deleteById(id);
    } catch (DatabaseOperationException e) {
      throw new ServiceOperationException("An error while deleting room.", e);
    }
  }

  public boolean bookingExistingById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null.");
    }
    try {
      return bookingDatabase.existsById(id);
    } catch (DatabaseOperationException e) {
      throw new ServiceOperationException("An error while checking if room exist.", e);
    }
  }
}
