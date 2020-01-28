package com.be.booker.business.services;

import com.be.booker.business.entity.Booking;
import com.be.booker.business.entitydto.RoomBookingDto;
import com.be.booker.business.entitydto.RoomBookingNameAndSurnameDto;
import com.be.booker.business.repository.BookingRepository;
import com.be.booker.business.repository.RoomRepository;
import com.be.booker.business.repository.UserRepository;
import com.be.booker.business.usecases.booking.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {
    private BookingRoomUsecase bookingRoomUsecase;
    private DateChecker dateChecker;
    private GetBookingScheduleForAllRoomsUsecase getBookingScheduleForAllRoomsUsecase;
    private GetBookingScheduleForGivenRoomUsecase getBookingScheduleForGivenRoomUsecase;
    private GetBookingScheduleForGivenUserUsecase getBookingScheduleForGivenUserUsecase;
    private MapBookings mapBookings;
    private BookingRepository bookingRepository;
    private UserRepository userRepository;
    private RoomRepository roomRepository;

    public BookingService(BookingRoomUsecase bookingRoomUsecase, DateChecker dateChecker, GetBookingScheduleForAllRoomsUsecase getBookingScheduleForAllRoomsUsecase,
                          GetBookingScheduleForGivenRoomUsecase getBookingScheduleForGivenRoomUsecase, GetBookingScheduleForGivenUserUsecase getBookingScheduleForGivenUserUsecase, MapBookings mapBookings,
                          BookingRepository bookingRepository, UserRepository userRepository, RoomRepository roomRepository) {
        this.bookingRoomUsecase = bookingRoomUsecase;
        this.dateChecker = dateChecker;
        this.getBookingScheduleForAllRoomsUsecase = getBookingScheduleForAllRoomsUsecase;
        this.getBookingScheduleForGivenRoomUsecase = getBookingScheduleForGivenRoomUsecase;
        this.getBookingScheduleForGivenUserUsecase = getBookingScheduleForGivenUserUsecase;
        this.mapBookings = mapBookings;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;

    }

    public Booking bookTheRoom(RoomBookingDto roomBookingDto) {
        return bookingRoomUsecase
                .withBookingRepository(bookingRepository)
                .withRoomRepository(roomRepository)
                .withUserRepository(userRepository)
                .withRoomBookingDto(roomBookingDto)
                .withDateChecker(dateChecker)
                .run();

    }

    public List<RoomBookingNameAndSurnameDto> getBookingScheduleForAllRooms(LocalDateTime bookedFrom, LocalDateTime bookedTo) {
        return getBookingScheduleForAllRoomsUsecase
                .withBookingRepository(bookingRepository)
                .withDateChecker(dateChecker)
                .withMapBookings(mapBookings)
                .withBookFromDate(bookedFrom)
                .withBookToDate(bookedTo)
                .run();
    }

    public List<RoomBookingNameAndSurnameDto> getBookingScheduleForGivenRoom(LocalDateTime bookedFrom, LocalDateTime bookedTo, String roomName) {
        return getBookingScheduleForGivenRoomUsecase
                .withBookingRepository(bookingRepository)
                .withBookingRepository(roomRepository)
                .withBookedFromDate(bookedFrom)
                .withBookedTo(bookedTo)
                .withRoomName(roomName)
                .withMapBookings(mapBookings)
                .run();
    }

    public List<RoomBookingNameAndSurnameDto> getBookingScheduleForGivenUser(LocalDateTime bookedFrom, LocalDateTime bookedTo, String userLogin) {
        return getBookingScheduleForGivenUserUsecase
                .withBookingRepository(bookingRepository)
                .withUserRepository(userRepository)
                .withBookedFrom(bookedFrom)
                .withBookedTo(bookedTo)
                .withUserLogin(userLogin)
                .withMapBookings(mapBookings)
                .run();
    }
}
