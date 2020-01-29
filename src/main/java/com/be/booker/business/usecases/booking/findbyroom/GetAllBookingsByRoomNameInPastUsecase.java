package com.be.booker.business.usecases.booking.findbyroom;

import com.be.booker.business.entity.Booking;
import com.be.booker.business.entity.entitydto.RoomBookingNameAndSurnameDto;
import com.be.booker.business.exceptions.BadRequestException;
import com.be.booker.business.repository.BookingRepository;
import com.be.booker.business.repository.RoomRepository;
import com.be.booker.business.usecases.booking.MapBookings;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class GetAllBookingsByRoomNameInPastUsecase {
    private BookingRepository bookingRepository;
    private RoomRepository roomRepository;
    private String roomName;
    private LocalDateTime bookedTo;
    private MapBookings mapBookings;

    public GetAllBookingsByRoomNameInPastUsecase withBookingRepository(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
        return this;
    }

    public GetAllBookingsByRoomNameInPastUsecase withRoomRepository(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
        return this;
    }

    public GetAllBookingsByRoomNameInPastUsecase withRoomName(String roomName) {
        this.roomName = roomName;
        return this;
    }

    public GetAllBookingsByRoomNameInPastUsecase bookedTo(LocalDateTime bookedTo) {
        this.bookedTo = bookedTo;
        return this;
    }

    public GetAllBookingsByRoomNameInPastUsecase withMapBook(MapBookings mapBookings) {
        this.mapBookings = mapBookings;
        return this;
    }

    public List<RoomBookingNameAndSurnameDto> run() {
        return getBookingScheduleByRoomNameInPast();
    }

    private List<RoomBookingNameAndSurnameDto> getBookingScheduleByRoomNameInPast() {
        roomRepository.findById(roomName).orElseThrow(() -> new BadRequestException("No such room"));
        List<Booking> bookingList;
        bookingList = bookingRepository.getAllBookingsInPastAndRoom(bookedTo, roomName);
        return mapBookings.mapBookings(bookingList);
    }
}
