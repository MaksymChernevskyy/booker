package com.be.booker.business.usecases.booking.findbydates;

import com.be.booker.business.entity.Booking;
import com.be.booker.business.entity.entitydto.RoomBookingNameAndSurnameDto;
import com.be.booker.business.repository.BookingRepository;
import com.be.booker.business.usecases.booking.DateChecker;
import com.be.booker.business.usecases.booking.MapBookings;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class GetBookingScheduleForAllBookingsInGivenTimeUsecase {

    private BookingRepository bookingRepository;
    private DateChecker dateChecker;
    private MapBookings mapBookings;
    private LocalDateTime bookedFrom;
    private LocalDateTime bookedTo;

    public GetBookingScheduleForAllBookingsInGivenTimeUsecase withBookingRepository(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
        return this;
    }

    public GetBookingScheduleForAllBookingsInGivenTimeUsecase withDateChecker(DateChecker dateChecker) {
        this.dateChecker = dateChecker;
        return this;
    }

    public GetBookingScheduleForAllBookingsInGivenTimeUsecase withBookFromDate(LocalDateTime bookedFrom) {
        this.bookedFrom = bookedFrom;
        return this;
    }

    public GetBookingScheduleForAllBookingsInGivenTimeUsecase withBookToDate(LocalDateTime bookedTo) {
        this.bookedTo = bookedTo;
        return this;
    }

    public GetBookingScheduleForAllBookingsInGivenTimeUsecase withMapBookings(MapBookings mapBookings) {
        this.mapBookings = mapBookings;
        return this;
    }

    public List<RoomBookingNameAndSurnameDto> run() {
        return getBookingScheduleForAllBookingsInGivenTimeRange();

    }

    private List<RoomBookingNameAndSurnameDto> getBookingScheduleForAllBookingsInGivenTimeRange() {
        dateChecker.dateCheckerForSearching(bookedFrom, bookedTo);
        List<Booking> bookingList;
        bookingList = bookingRepository.getAllBookingsWithIn(bookedFrom, bookedTo);
        return mapBookings.mapBookings(bookingList);
    }
}
