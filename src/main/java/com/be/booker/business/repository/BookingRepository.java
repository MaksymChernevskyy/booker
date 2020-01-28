package com.be.booker.business.repository;

import com.be.booker.business.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("select booking from Booking booking where ?1  between booking.bookedFrom and booking.bookedTo and ?2  between booking.bookedFrom and booking.bookedTo")
    List<Booking> getAllBookingsWithIn(@Param("bookedFrom") LocalDateTime bookedFrom, @Param("bookedTo") LocalDateTime bookedTo);

    @Query("select booking from Booking booking where ?1  < booking.bookedFrom")
    List<Booking> getAllBookingsInPast(@Param("bookedTo") LocalDateTime bookedTo);

    @Query("select booking from Booking booking where ?1  > booking.bookedFrom")
    List<Booking> getAllBookingsInFuture(@Param("bookedFrom") LocalDateTime bookedFrom);

    @Query("select booking from Booking booking where ?1  between booking.bookedFrom and booking.bookedTo and ?2  between booking.bookedFrom and booking.bookedTo and booking.roomName = ?3")
    List<Booking> getAllBookingsWithInDateFrameAndRoom(@Param("bookedFrom") LocalDateTime bookedFrom, @Param("bookedTo") LocalDateTime bookedTo, @Param("roomName") String roomName);

    @Query("select booking from Booking booking where ?1  < booking.bookedFrom and booking.roomName = ?2 ")
    List<Booking> getAllBookingsInPastAndRoom(LocalDateTime bookedTo, String roomName);

    @Query("select booking from Booking booking where ?1  > booking.bookedFrom and booking.bookedTo = ?2")
    List<Booking> getAllBookingsInFutureAndRoom(LocalDateTime bookedFrom, String roomName);

    @Query("select booking from Booking booking where  booking.roomName = ?1")
    List<Booking> getAllBookingsInRoom(String roomName);

    @Query("select booking from Booking booking where  ?1  between booking.bookedFrom and booking.bookedTo and ?2  between booking.bookedFrom and booking.bookedTo and booking.userLogin = ?3")
    List<Booking> getAllBookingsWithInDateFrameAndUser(@Param("bookedFrom") LocalDateTime bookedFrom, @Param("bookedTo") LocalDateTime bookedTo, @Param("roomName") String roomName);

    @Query("select booking from Booking booking where  ?1  < booking.bookedFrom and booking.userLogin = ?2 ")
    List<Booking> getAllBookingsInPastAndUser(LocalDateTime bookedFrom, String login);

    @Query("select booking from Booking booking where  ?1  > booking.bookedFrom and booking.userLogin = ?2")
    List<Booking> getAllBookingsInFutureAndUser(LocalDateTime bookedFrom, String login);

    @Query("select booking from Booking booking where  booking.userLogin = ?1")
    List<Booking> getAllBookingsForUser(String login);
}
