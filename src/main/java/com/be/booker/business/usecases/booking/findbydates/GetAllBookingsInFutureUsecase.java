package com.be.booker.business.usecases.booking.findbydates;

import com.be.booker.business.entity.Booking;
import com.be.booker.business.entity.entitydto.RoomBookingNameAndSurnameDto;
import com.be.booker.business.repository.BookingRepository;
import com.be.booker.business.usecases.booking.MapBookings;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class GetAllBookingsInFutureUsecase {
    private BookingRepository bookingRepository;
    private MapBookings mapBookings;
    private LocalDateTime bookedFrom;

    public GetAllBookingsInFutureUsecase withBookingRepository(com.be.booker.business.repository.BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
        return this;
    }

    public GetAllBookingsInFutureUsecase bookedFrom(LocalDateTime bookedFrom) {
        this.bookedFrom = bookedFrom;
        return this;
    }

    public GetAllBookingsInFutureUsecase withMapBookings(MapBookings mapBookings) {
        this.mapBookings = mapBookings;
        return this;
    }

    public List<RoomBookingNameAndSurnameDto> run() {
        return getBookingScheduleForAllRoomsInFuture();

    }

    private List<RoomBookingNameAndSurnameDto> getBookingScheduleForAllRoomsInFuture() {
        List<Booking> bookingList;
        bookingList = bookingRepository.getAllBookingsInFuture(bookedFrom);
        return mapBookings.mapBookings(bookingList);
    }
}
