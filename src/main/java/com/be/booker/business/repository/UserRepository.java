package com.be.booker.business.repository;

import com.be.booker.business.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
    User findByLogin(String login);
}
