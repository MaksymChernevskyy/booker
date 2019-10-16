package com.be.booker.business.database.room;

import com.be.booker.business.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HibernateRoomRepository extends JpaRepository<Room, Long> {
}
