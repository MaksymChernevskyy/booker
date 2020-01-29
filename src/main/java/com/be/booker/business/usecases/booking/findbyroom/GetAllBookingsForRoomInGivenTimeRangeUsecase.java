package com.be.booker.business.usecases.booking.findbyroom;

import com.be.booker.business.entity.Booking;
import com.be.booker.business.entity.entitydto.RoomBookingNameAndSurnameDto;
import com.be.booker.business.exceptions.BadRequestException;
import com.be.booker.business.repository.BookingRepository;
import com.be.booker.business.repository.RoomRepository;
import com.be.booker.business.usecases.booking.DateChecker;
import com.be.booker.business.usecases.booking.MapBookings;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class GetAllBookingsForRoomInGivenTimeRangeUsecase {
    private BookingRepository bookingRepository;
    private RoomRepository roomRepository;
    private LocalDateTime bookedFrom;
    private LocalDateTime bookedTo;
    private String roomName;
    private MapBookings mapBookings;
    private DateChecker dateChecker;

    public GetAllBookingsForRoomInGivenTimeRangeUsecase withBookingRepository(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
        return this;
    }

    public GetAllBookingsForRoomInGivenTimeRangeUsecase withRoomRepository(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
        return this;
    }

    public GetAllBookingsForRoomInGivenTimeRangeUsecase withBookedFromDate(LocalDateTime bookedFrom) {
        this.bookedFrom = bookedFrom;
        return this;
    }

    public GetAllBookingsForRoomInGivenTimeRangeUsecase withBookedTo(LocalDateTime bookedTo) {
        this.bookedTo = bookedTo;
        return this;
    }

    public GetAllBookingsForRoomInGivenTimeRangeUsecase withRoomName(String roomName) {
        this.roomName = roomName;
        return this;
    }

    public GetAllBookingsForRoomInGivenTimeRangeUsecase withMapBookings(MapBookings mapBookings) {
        this.mapBookings = mapBookings;
        return this;
    }

    public GetAllBookingsForRoomInGivenTimeRangeUsecase withDateChecker(DateChecker dateChecker) {
        this.dateChecker = dateChecker;
        return this;
    }

    public List<RoomBookingNameAndSurnameDto> run() {
        return getAllBookingsForAllRooms();
    }

    private List<RoomBookingNameAndSurnameDto> getAllBookingsForAllRooms() {
        dateChecker.dateCheckerForSearching(bookedFrom, bookedTo);
        roomRepository.findById(roomName).orElseThrow(() -> new BadRequestException("No such room"));
        List<Booking> bookingList;
        bookingList = bookingRepository.getAllBookingsWithInDateFrameAndRoom(bookedFrom, bookedTo, roomName);
        return mapBookings.mapBookings(bookingList);
    }
}
