package com.be.booker.business.repository;

import com.be.booker.business.entity.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, String> {
}
