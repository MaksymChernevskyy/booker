package com.be.booker.business.repository;

import com.be.booker.business.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
    @Query ("select user from User user where  user.login = ?1")
    User findByLogin(String login);
}
