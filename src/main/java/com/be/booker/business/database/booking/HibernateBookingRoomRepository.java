package com.be.booker.business.database.booking;

import com.be.booker.business.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HibernateBookingRoomRepository extends JpaRepository<Booking, Long> {
}
