package com.be.booker.business.database;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Database<T, ID extends Serializable> {

  Optional<T> save(T entity);

  Optional<T> findById(ID id);

  boolean existsById(ID id);

  Optional<List<T>> findAll();

  long count();

  void deleteById(ID id);

  void deleteAll();
}
