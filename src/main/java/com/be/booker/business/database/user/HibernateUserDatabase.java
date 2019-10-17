package com.be.booker.business.database.user;

import com.be.booker.business.entity.User;
import com.be.booker.business.database.DatabaseOperationException;
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
public class HibernateUserDatabase implements UserDatabase {
  private HibernateUserRepository hibernateUserRepository;

  @Autowired
  public HibernateUserDatabase(HibernateUserRepository hibernateUserRepository) {
    this.hibernateUserRepository = hibernateUserRepository;
  }

  @Override
  public Optional<User> save(User user) {
    if (user == null) {
      throw new IllegalArgumentException("User cannot be null");
    }
    try {
      return Optional.of(hibernateUserRepository.save(user));
    } catch (NonTransientDataAccessException e) {
      throw new DatabaseOperationException("An error while saving user.", e);
    }
  }

  @Override
  public Optional<User> findById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    try {
      return hibernateUserRepository.findById(id);
    } catch (NoSuchElementException e) {
      throw new DatabaseOperationException("An error while searching for user.", e);
    }
  }

  @Override
  public boolean existsById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    try {
      return hibernateUserRepository.existsById(id);
    } catch (NonTransientDataAccessException e) {
      throw new DatabaseOperationException("An error while searching for user.", e);
    }
  }

  @Override
  public Optional<List<User>> findAll() {
    try {
      return Optional.of(hibernateUserRepository.findAll());
    } catch (NonTransientDataAccessException e) {
      throw new DatabaseOperationException("An error while searching for user.", e);
    }
  }

  @Override
  public long count() {
    try {
      return hibernateUserRepository.count();
    } catch (NonTransientDataAccessException e) {
      throw new DatabaseOperationException("An error while counting users.", e);
    }
  }

  @Override
  public void deleteById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    try {
      hibernateUserRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new DatabaseOperationException("There was no user in database.", e);
    }
  }

  @Override
  public void deleteAll() {
    try {
      hibernateUserRepository.deleteAll();
    } catch (NonTransientDataAccessException e) {
      throw new DatabaseOperationException("An error while deleting all users.", e);
    }
  }
}
