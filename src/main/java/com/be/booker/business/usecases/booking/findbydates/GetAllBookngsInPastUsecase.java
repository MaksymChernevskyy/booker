package com.be.booker.business.usecases.booking.findbydates;

import com.be.booker.business.entity.Booking;
import com.be.booker.business.entity.entitydto.RoomBookingNameAndSurnameDto;
import com.be.booker.business.repository.BookingRepository;
import com.be.booker.business.usecases.booking.MapBookings;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class GetAllBookngsInPastUsecase {

    private BookingRepository bookingRepository;
    private MapBookings mapBookings;
    private LocalDateTime bookedTo;

    public GetAllBookngsInPastUsecase withBookingRepository(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
        return this;
    }

    public GetAllBookngsInPastUsecase withMapBookings(MapBookings mapBookings) {
        this.mapBookings = mapBookings;
        return this;
    }

    public GetAllBookngsInPastUsecase withBookedTo(LocalDateTime bookedTo) {
        this.bookedTo = bookedTo;
        return this;
    }

    public List<RoomBookingNameAndSurnameDto> run() {
        return getBookingScheduleForAllRoomsInPast();
    }

    private List<RoomBookingNameAndSurnameDto> getBookingScheduleForAllRoomsInPast() {
        List<Booking> bookingList;
        bookingList = bookingRepository.getAllBookingsInPast(bookedTo);
        return mapBookings.mapBookings(bookingList);
    }
}
