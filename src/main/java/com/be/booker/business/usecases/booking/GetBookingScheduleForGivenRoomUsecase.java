package com.be.booker.business.usecases.booking;

import com.be.booker.business.entity.Booking;
import com.be.booker.business.entitydto.RoomBookingNameAndSurnameDto;
import com.be.booker.business.exceptions.BadRequestException;
import com.be.booker.business.repository.BookingRepository;
import com.be.booker.business.repository.RoomRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class GetBookingScheduleForGivenRoomUsecase {
    private BookingRepository bookingRepository;
    private RoomRepository roomRepository;
    private LocalDateTime bookedFrom;
    private LocalDateTime bookedTo;
    private String roomName;
    private MapBookings mapBookings;

    public GetBookingScheduleForGivenRoomUsecase withBookingRepository(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
        return this;
    }

    public GetBookingScheduleForGivenRoomUsecase withBookingRepository(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
        return this;
    }

    public GetBookingScheduleForGivenRoomUsecase withBookedFromDate(LocalDateTime bookedFrom) {
        this.bookedFrom = bookedFrom;
        return this;
    }

    public GetBookingScheduleForGivenRoomUsecase withBookedTo(LocalDateTime bookedTo) {
        this.bookedTo = bookedTo;
        return this;
    }

    public GetBookingScheduleForGivenRoomUsecase withRoomName(String roomName) {
        this.roomName = roomName;
        return this;
    }

    public GetBookingScheduleForGivenRoomUsecase withMapBookings(MapBookings mapBookings) {
        this.mapBookings = mapBookings;
        return this;
    }

    public List<RoomBookingNameAndSurnameDto> run() {
        return getBookingScheduleForGivenRoom();
    }

    private List<RoomBookingNameAndSurnameDto> getBookingScheduleForGivenRoom() {
        roomRepository.findById(roomName).orElseThrow(() -> new BadRequestException("No such room"));
        List<Booking> bookingListt = new ArrayList<>();
        if (bookedFrom != null && bookedTo != null) {
            bookingListt = bookingRepository.getAllBookingsWithInDateFrameAndRoom(bookedFrom, bookedTo, roomName);
        }
        if (bookedFrom == null && bookedTo != null) {
            bookingListt = bookingRepository.getAllBookingsInPastAndRoom(bookedTo, roomName);
        }
        if (bookedFrom != null && bookedTo == null) {
            bookingListt = bookingRepository.getAllBookingsInFutureAndRoom(bookedFrom, roomName);
        }
        if (bookedFrom == null && bookedTo == null) {
            bookingListt = bookingRepository.getAllBookingsInRoom(roomName);
        }
        return mapBookings.mapBookings(bookingListt);
    }
}
