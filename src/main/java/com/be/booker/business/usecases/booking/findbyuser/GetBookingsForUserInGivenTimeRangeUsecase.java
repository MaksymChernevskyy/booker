package com.be.booker.business.usecases.booking.findbyuser;

import com.be.booker.business.entity.Booking;
import com.be.booker.business.entity.entitydto.RoomBookingNameAndSurnameDto;
import com.be.booker.business.exceptions.BadRequestException;
import com.be.booker.business.repository.BookingRepository;
import com.be.booker.business.repository.UserRepository;
import com.be.booker.business.usecases.booking.DateChecker;
import com.be.booker.business.usecases.booking.MapBookings;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class GetBookingsForUserInGivenTimeRangeUsecase {
    private BookingRepository bookingRepository;
    private UserRepository userRepository;
    private String userLogin;
    private LocalDateTime bookedFrom;
    private LocalDateTime bookedTo;
    private MapBookings mapBookings;
    private DateChecker dateChecker;

    public GetBookingsForUserInGivenTimeRangeUsecase withBookingRepository(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
        return this;
    }

    public GetBookingsForUserInGivenTimeRangeUsecase withUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
        return this;
    }

    public GetBookingsForUserInGivenTimeRangeUsecase withBookedFrom(LocalDateTime bookedFrom) {
        this.bookedFrom = bookedFrom;
        return this;
    }

    public GetBookingsForUserInGivenTimeRangeUsecase withBookedTo(LocalDateTime bookedTo) {
        this.bookedTo = bookedTo;
        return this;
    }

    public GetBookingsForUserInGivenTimeRangeUsecase withUserLogin(String userLogin) {
        this.userLogin = userLogin;
        return this;
    }

    public GetBookingsForUserInGivenTimeRangeUsecase withMapBookings(MapBookings mapBookings) {
        this.mapBookings = mapBookings;
        return this;
    }

    public GetBookingsForUserInGivenTimeRangeUsecase withDateChecker(DateChecker dateChecker) {
        this.dateChecker = dateChecker;
        return this;
    }

    public List<RoomBookingNameAndSurnameDto> run() {
        return getBookingForGivenUser();
    }

    private List<RoomBookingNameAndSurnameDto> getBookingForGivenUser() {
        dateChecker.dateCheckerForSearching(bookedFrom, bookedTo);
        userRepository.findById(userLogin).orElseThrow(() -> new BadRequestException("No such user"));
        List<Booking> roomBookingEntityList;
        roomBookingEntityList = bookingRepository.getAllBookingsWithInDateFrameAndUser(bookedFrom, bookedTo, userLogin);

        return mapBookings.mapBookings(roomBookingEntityList);
    }
}
