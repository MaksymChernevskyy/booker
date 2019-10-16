package com.be.booker.business.database.user;

import com.be.booker.business.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HibernateUserRepository extends JpaRepository<User, Long> {
}
