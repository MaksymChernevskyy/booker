package com.be.booker.business.database.user;

import com.be.booker.business.entity.User;
import com.be.booker.business.database.DatabaseOperationException;
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
public class InMemoryUserRepository implements UserDatabase {
  private Map<Long, User> users = new HashMap<>();
  private AtomicLong counter = new AtomicLong(0);
  private final Object lock = new Object();

  public InMemoryUserRepository(){
    DefaultUsers defaultUsers = new DefaultUsers();
    save(defaultUsers.createJohn());
    save(defaultUsers.createJane());
  }

  @Override
  public Optional<User> save(User user) {
    synchronized (lock) {
      if (user == null) {
        throw new IllegalArgumentException("User cannot be null");
      }
      if (isUserExist(user.getId())) {
        throw new IllegalArgumentException("User already exist");
      }
      long id = counter.incrementAndGet();
      user.setId(id);
      users.put(id, user);
      return Optional.of(user);
    }
  }

  @Override
  public Optional<User> findById(Long id) {
    synchronized (lock) {
      if (id == null) {
        throw new IllegalArgumentException("Id cannot be null");
      }
      return Optional.ofNullable(users.get(id));
    }
  }

  @Override
  public boolean existsById(Long id) {
    synchronized (lock) {
      if (id == null) {
        throw new IllegalArgumentException("Id cannot be null");
      }
      return isUserExist(id);
    }
  }

  @Override
  public Optional<List<User>> findAll() {
    synchronized (lock) {
      return Optional.of(new ArrayList<>(users.values()));
    }
  }

  @Override
  public long count() {
    synchronized (lock) {
      return users.size();
    }
  }

  @Override
  public void deleteById(Long id) {
    synchronized (lock) {
      if (id == null) {
        throw new IllegalArgumentException("Id cannot be null");
      }
      if (!isUserExist(id)) {
        throw new DatabaseOperationException("User does not exist");
      }
      users.remove(id);
    }
  }

  @Override
  public void deleteAll() {
    synchronized (lock) {
      users.clear();
    }
  }

  private boolean isUserExist(long id) {
    return users.containsKey(id);
  }
}
