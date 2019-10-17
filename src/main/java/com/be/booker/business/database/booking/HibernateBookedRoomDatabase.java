package com.be.booker.business.database.booking;

import com.be.booker.business.database.DatabaseOperationException;
import com.be.booker.business.entity.BookingRoom;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.stereotype.Repository;

@ConditionalOnProperty(name = "com.be.booker.business.database", havingValue = "hibernate")
@Repository
public class HibernateBookedRoomDatabase implements BookingDatabase {

  private HibernateBookingRoomRepository hibernateBookingRoomRepository;

  @Autowired
  public HibernateBookedRoomDatabase(HibernateBookingRoomRepository hibernateBookingRoomRepository) {
    this.hibernateBookingRoomRepository = hibernateBookingRoomRepository;
  }

  public Optional<BookingRoom> save(BookingRoom bookingRoom) {
    if (bookingRoom == null) {
      throw new IllegalArgumentException("Room cannot be null");
    }
    try {
      return Optional.of(hibernateBookingRoomRepository.save(bookingRoom));
    } catch (NonTransientDataAccessException e) {
      throw new DatabaseOperationException("An error while saving room.", e);
    }
  }

  @Override
  public Optional<BookingRoom> findById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    try {
      return hibernateBookingRoomRepository.findById(id);
    } catch (NoSuchElementException e) {
      throw new DatabaseOperationException("An error while searching for room.", e);
    }
  }

  @Override
  public boolean existsById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    try {
      return hibernateBookingRoomRepository.existsById(id);
    } catch (NonTransientDataAccessException e) {
      throw new DatabaseOperationException("An error while searching for room.", e);
    }
  }

  @Override
  public Optional<List<BookingRoom>> findAll() {
    try {
      return Optional.of(hibernateBookingRoomRepository.findAll());
    } catch (NonTransientDataAccessException e) {
      throw new DatabaseOperationException("An error while searching for room.", e);
    }
  }

  @Override
  public long count() {
    try {
      return hibernateBookingRoomRepository.count();
    } catch (NonTransientDataAccessException e) {
      throw new DatabaseOperationException("An error while counting rooms.", e);
    }
  }

  @Override
  public void deleteById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    try {
      hibernateBookingRoomRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new DatabaseOperationException("There was no room in database.", e);
    }
  }

  @Override
  public void deleteAll() {
    try {
      hibernateBookingRoomRepository.deleteAll();
    } catch (NonTransientDataAccessException e) {
      throw new DatabaseOperationException("An error while deleting all rooms.", e);
    }
  }
}
