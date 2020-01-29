package com.be.booker.business.usecases.booking.findbyuser;

import com.be.booker.business.entity.Booking;
import com.be.booker.business.entity.entitydto.RoomBookingNameAndSurnameDto;
import com.be.booker.business.exceptions.BadRequestException;
import com.be.booker.business.repository.BookingRepository;
import com.be.booker.business.repository.UserRepository;
import com.be.booker.business.usecases.booking.MapBookings;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class GetAllBookingsByUserLoginInPastUsecase {
    private BookingRepository bookingRepository;
    private UserRepository userRepository;
    private String userLogin;
    private LocalDateTime bookedTo;
    private MapBookings mapBookings;

    public GetAllBookingsByUserLoginInPastUsecase withBookingRepository(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
        return this;
    }

    public GetAllBookingsByUserLoginInPastUsecase withUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
        return this;
    }

    public GetAllBookingsByUserLoginInPastUsecase withBookedTo(LocalDateTime bookedTo) {
        this.bookedTo = bookedTo;
        return this;
    }

    public GetAllBookingsByUserLoginInPastUsecase withUserLogin(String userLogin) {
        this.userLogin = userLogin;
        return this;
    }

    public GetAllBookingsByUserLoginInPastUsecase withMapBookings(MapBookings mapBookings) {
        this.mapBookings = mapBookings;
        return this;
    }

    public List<RoomBookingNameAndSurnameDto> run() {
        return getBookingForGivenUser();
    }

    private List<RoomBookingNameAndSurnameDto> getBookingForGivenUser() {
        userRepository.findById(userLogin).orElseThrow(() -> new BadRequestException("No such user"));
        List<Booking> roomBookingEntityList;
        roomBookingEntityList = bookingRepository.getAllBookingsInPastAndUser(bookedTo, userLogin);
        return mapBookings.mapBookings(roomBookingEntityList);
    }
}
