package com.be.booker.business.usecases.booking.findbydates;

import com.be.booker.business.entity.Booking;
import com.be.booker.business.entity.entitydto.RoomBookingNameAndSurnameDto;
import com.be.booker.business.repository.BookingRepository;
import com.be.booker.business.usecases.booking.MapBookings;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllBookingsUsecase {
    private BookingRepository bookingRepository;
    private MapBookings mapBookings;

    public GetAllBookingsUsecase withBookingRepository(com.be.booker.business.repository.BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
        return this;
    }

    public GetAllBookingsUsecase withMapBookings(MapBookings mapBookings) {
        this.mapBookings = mapBookings;
        return this;
    }

    public List<RoomBookingNameAndSurnameDto> run() {
        return getBookingScheduleForAlRooms();
    }

    private List<RoomBookingNameAndSurnameDto> getBookingScheduleForAlRooms() {
        List<Booking> bookingList;
        bookingList = bookingRepository.findAll();
        return mapBookings.mapBookings(bookingList);
    }
}
