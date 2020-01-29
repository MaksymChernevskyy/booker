package com.be.booker.business.services;

import com.be.booker.business.entity.Booking;
import com.be.booker.business.entity.entitydto.RoomBookingDto;
import com.be.booker.business.entity.entitydto.RoomBookingNameAndSurnameDto;
import com.be.booker.business.repository.BookingRepository;
import com.be.booker.business.repository.RoomRepository;
import com.be.booker.business.repository.UserRepository;
import com.be.booker.business.usecases.booking.BookingRoomUsecase;
import com.be.booker.business.usecases.booking.DateChecker;
import com.be.booker.business.usecases.booking.MapBookings;
import com.be.booker.business.usecases.booking.findbydates.GetAllBookingsInFutureUsecase;
import com.be.booker.business.usecases.booking.findbydates.GetAllBookingsUsecase;
import com.be.booker.business.usecases.booking.findbydates.GetAllBookngsInPastUsecase;
import com.be.booker.business.usecases.booking.findbydates.GetBookingScheduleForAllBookingsInGivenTimeUsecase;
import com.be.booker.business.usecases.booking.findbyroom.GetAllBookingsByRoomNameInFutureUsecase;
import com.be.booker.business.usecases.booking.findbyroom.GetAllBookingsByRoomNameInPastUsecase;
import com.be.booker.business.usecases.booking.findbyroom.GetAllBookingsByRoomUsecase;
import com.be.booker.business.usecases.booking.findbyroom.GetAllBookingsForRoomInGivenTimeRangeUsecase;
import com.be.booker.business.usecases.booking.findbyuser.GetAllBookingsByUserLoginInFutureUsecase;
import com.be.booker.business.usecases.booking.findbyuser.GetAllBookingsByUserLoginInPastUsecase;
import com.be.booker.business.usecases.booking.findbyuser.GetAllBookingsByUserUsecase;
import com.be.booker.business.usecases.booking.findbyuser.GetBookingsForUserInGivenTimeRangeUsecase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {
    private BookingRoomUsecase bookingRoomUsecase;
    private DateChecker dateChecker;
    private GetBookingScheduleForAllBookingsInGivenTimeUsecase getBookingScheduleForAllRoomsUsecase;
    private GetAllBookingsUsecase getAllBookingsUsecase;
    private MapBookings mapBookings;
    private BookingRepository bookingRepository;
    private UserRepository userRepository;
    private RoomRepository roomRepository;
    private GetAllBookingsInFutureUsecase getAllBookingsInFutureUsecase;
    private GetAllBookngsInPastUsecase getAllBookngsInPastUsecase;
    private GetAllBookingsByRoomUsecase getAllBookingsByRoomUsecase;
    private GetAllBookingsByRoomNameInFutureUsecase getAllBookingsByRoomNameInFutureUsecase;
    private GetAllBookingsByRoomNameInPastUsecase getAllBookingsByRoomNameInPastUsecase;
    private GetBookingsForUserInGivenTimeRangeUsecase getBookingScheduleForGivenUserUsecase;
    private GetAllBookingsByUserLoginInFutureUsecase getAllBookingsByUserLoginInFutureUsecase;
    private GetAllBookingsByUserLoginInPastUsecase getAllBookingsByUserLoginInPastUsecase;
    private GetAllBookingsByUserUsecase getAllBookingsByUserUsecase;
    private GetAllBookingsForRoomInGivenTimeRangeUsecase getAllBookingsForRoomInGivenTimeRangeUsecase;

    @Autowired
    public BookingService(BookingRoomUsecase bookingRoomUsecase,
                          DateChecker dateChecker,
                          GetBookingScheduleForAllBookingsInGivenTimeUsecase getBookingScheduleForAllRoomsUsecase,
                          GetBookingsForUserInGivenTimeRangeUsecase getBookingScheduleForGivenUserUsecase,
                          GetAllBookingsUsecase getAllBookingsUsecase,
                          GetAllBookingsInFutureUsecase getAllBookingsInFutureUsecase,
                          GetAllBookngsInPastUsecase getAllBookngsInPastUsecase,
                          GetAllBookingsByRoomUsecase getAllBookingsByRoomUsecase,
                          GetAllBookingsByRoomNameInFutureUsecase getAllBookingsByRoomNameInFutureUsecase,
                          GetAllBookingsByRoomNameInPastUsecase getAllBookingsByRoomNameInPastUsecase,
                          GetAllBookingsByUserLoginInFutureUsecase getAllBookingsByUserLoginInFutureUsecase,
                          GetAllBookingsByUserLoginInPastUsecase getAllBookingsByUserLoginInPastUsecase,
                          GetAllBookingsByUserUsecase getAllBookingsByUserUsecase,
                          GetAllBookingsForRoomInGivenTimeRangeUsecase getAllBookingsForRoomInGivenTimeRangeUsecase,
                          MapBookings mapBookings,
                          BookingRepository bookingRepository,
                          UserRepository userRepository,
                          RoomRepository roomRepository) {
        this.bookingRoomUsecase = bookingRoomUsecase;
        this.dateChecker = dateChecker;
        this.getBookingScheduleForAllRoomsUsecase = getBookingScheduleForAllRoomsUsecase;
        this.getBookingScheduleForGivenUserUsecase = getBookingScheduleForGivenUserUsecase;
        this.getAllBookingsUsecase = getAllBookingsUsecase;
        this.getAllBookingsInFutureUsecase = getAllBookingsInFutureUsecase;
        this.getAllBookngsInPastUsecase = getAllBookngsInPastUsecase;
        this.getAllBookingsByRoomUsecase = getAllBookingsByRoomUsecase;
        this.getAllBookingsByRoomNameInFutureUsecase = getAllBookingsByRoomNameInFutureUsecase;
        this.getAllBookingsByRoomNameInPastUsecase = getAllBookingsByRoomNameInPastUsecase;
        this.getAllBookingsByUserLoginInFutureUsecase = getAllBookingsByUserLoginInFutureUsecase;
        this.getAllBookingsByUserLoginInPastUsecase = getAllBookingsByUserLoginInPastUsecase;
        this.getAllBookingsByUserUsecase = getAllBookingsByUserUsecase;
        this.getAllBookingsForRoomInGivenTimeRangeUsecase = getAllBookingsForRoomInGivenTimeRangeUsecase;
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

    public List<RoomBookingNameAndSurnameDto> getBookingScheduleForAllBookingsInGivenTimeRange(LocalDateTime bookedFrom, LocalDateTime bookedTo) {
        return getBookingScheduleForAllRoomsUsecase
                .withBookingRepository(bookingRepository)
                .withDateChecker(dateChecker)
                .withMapBookings(mapBookings)
                .withBookFromDate(bookedFrom)
                .withBookToDate(bookedTo)
                .run();
    }

    public List<RoomBookingNameAndSurnameDto> getAllBookingsForRoomInGivenTimeRange(LocalDateTime bookedFrom, LocalDateTime bookedTo, String roomName) {
        return getAllBookingsForRoomInGivenTimeRangeUsecase
                .withRoomRepository(bookingRepository)
                .withRoomRepository(roomRepository)
                .withDateChecker(dateChecker)
                .withBookedFromDate(bookedFrom)
                .withBookedTo(bookedTo)
                .withRoomName(roomName)
                .withMapBookings(mapBookings)
                .run();
    }

    public List<RoomBookingNameAndSurnameDto> getBookingScheduleForGivenUserInGivenTimeRange(LocalDateTime bookedFrom, LocalDateTime bookedTo, String userLogin) {
        return getBookingScheduleForGivenUserUsecase
                .withBookingRepository(bookingRepository)
                .withUserRepository(userRepository)
                .withDateChecker(dateChecker)
                .withBookedFrom(bookedFrom)
                .withBookedTo(bookedTo)
                .withUserLogin(userLogin)
                .withMapBookings(mapBookings)
                .run();
    }

    public List<RoomBookingNameAndSurnameDto> getAllBookings() {
        return getAllBookingsUsecase
                .withBookingRepository(bookingRepository)
                .withMapBookings(mapBookings)
                .run();
    }

    public List<RoomBookingNameAndSurnameDto> getAllBookingsInFuture(LocalDateTime bookedFrom) {
        return getAllBookingsInFutureUsecase
                .withBookingRepository(bookingRepository)
                .bookedFrom(bookedFrom)
                .withMapBookings(mapBookings)
                .run();
    }

    public List<RoomBookingNameAndSurnameDto> getAllBookingsInPast(LocalDateTime bookedTo) {
        return getAllBookngsInPastUsecase
                .withBookingRepository(bookingRepository)
                .withBookedTo(bookedTo)
                .withMapBookings(mapBookings)
                .run();
    }

    public List<RoomBookingNameAndSurnameDto> getAllBookingsByRoomName(String roomName) {
        return getAllBookingsByRoomUsecase
                .withBookingRepository(bookingRepository)
                .withRoomRepository(roomRepository)
                .withRoomName(roomName)
                .withMapBook(mapBookings)
                .run();
    }

    public List<RoomBookingNameAndSurnameDto> getAllBookingsByRoomNameInFuture(String roomName, LocalDateTime bookedFrom) {
        return getAllBookingsByRoomNameInFutureUsecase
                .withBookingRepository(bookingRepository)
                .withRoomRepository(roomRepository)
                .withRoomName(roomName)
                .bookedFrom(bookedFrom)
                .withMapBook(mapBookings)
                .run();
    }

    public List<RoomBookingNameAndSurnameDto> getAllBookingsByRoomNameInPast(String roomName, LocalDateTime bookedTo) {
        return getAllBookingsByRoomNameInPastUsecase
                .withBookingRepository(bookingRepository)
                .withRoomRepository(roomRepository)
                .withRoomName(roomName)
                .bookedTo(bookedTo)
                .withMapBook(mapBookings)
                .run();
    }

    public List<RoomBookingNameAndSurnameDto> getBookingsForUserByUserLogin(String userLogin) {
        return getAllBookingsByUserUsecase
                .withBookingRepository(bookingRepository)
                .withUserRepository(userRepository)
                .withUserLogin(userLogin)
                .withMapBookings(mapBookings)
                .run();
    }

    public List<RoomBookingNameAndSurnameDto> getBookingsInFutureByUser(String userLogin, LocalDateTime bookedFrom) {
        return getAllBookingsByUserLoginInFutureUsecase
                .withBookingRepository(bookingRepository)
                .withUserRepository(userRepository)
                .withUserLogin(userLogin)
                .bookedFrom(bookedFrom)
                .withMapBookings(mapBookings)
                .run();
    }

    public List<RoomBookingNameAndSurnameDto> getBookingsInPastByUser(String userLogin, LocalDateTime bookedTo) {
        return getAllBookingsByUserLoginInPastUsecase
                .withBookingRepository(bookingRepository)
                .withUserRepository(userRepository)
                .withUserLogin(userLogin)
                .withBookedTo(bookedTo)
                .withMapBookings(mapBookings)
                .run();
    }
}
