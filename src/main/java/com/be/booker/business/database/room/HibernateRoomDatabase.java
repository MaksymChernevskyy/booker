package com.be.booker.business.database.room;

import com.be.booker.business.database.DatabaseOperationException;
import com.be.booker.business.entity.Room;
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
public class HibernateRoomDatabase implements RoomDatabase {
  private HibernateRoomRepository hibernateRoomRepository;

  @Autowired
  public HibernateRoomDatabase(HibernateRoomRepository hibernateRoomRepository) {
    this.hibernateRoomRepository = hibernateRoomRepository;
  }

  public Optional<Room> save(Room room) {
    if (room == null) {
      throw new IllegalArgumentException("Room cannot be null");
    }
    try {
      return Optional.of(hibernateRoomRepository.save(room));
    } catch (NonTransientDataAccessException e) {
      throw new DatabaseOperationException("An error while saving room.", e);
    }
  }

  @Override
  public Optional<Room> findById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    try {
      return hibernateRoomRepository.findById(id);
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
      return hibernateRoomRepository.existsById(id);
    } catch (NonTransientDataAccessException e) {
      throw new DatabaseOperationException("An error while searching for room.", e);
    }
  }

  @Override
  public Optional<List<Room>> findAll() {
    try {
      return Optional.of(hibernateRoomRepository.findAll());
    } catch (NonTransientDataAccessException e) {
      throw new DatabaseOperationException("An error while searching for room.", e);
    }
  }

  @Override
  public long count() {
    try {
      return hibernateRoomRepository.count();
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
      hibernateRoomRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new DatabaseOperationException("There was no room in database.", e);
    }
  }

  @Override
  public void deleteAll() {
    try {
      hibernateRoomRepository.deleteAll();
    } catch (NonTransientDataAccessException e) {
      throw new DatabaseOperationException("An error while deleting all rooms.", e);
    }
  }
}
