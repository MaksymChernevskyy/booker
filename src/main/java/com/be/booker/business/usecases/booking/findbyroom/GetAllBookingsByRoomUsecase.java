package com.be.booker.business.usecases.booking.findbyroom;

import com.be.booker.business.entity.Booking;
import com.be.booker.business.entity.entitydto.RoomBookingNameAndSurnameDto;
import com.be.booker.business.exceptions.BadRequestException;
import com.be.booker.business.repository.BookingRepository;
import com.be.booker.business.repository.RoomRepository;
import com.be.booker.business.usecases.booking.MapBookings;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllBookingsByRoomUsecase {
    private BookingRepository bookingRepository;
    private RoomRepository roomRepository;
    private String roomName;
    private MapBookings mapBookings;

    public GetAllBookingsByRoomUsecase withBookingRepository(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
        return this;
    }

    public GetAllBookingsByRoomUsecase withRoomRepository(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
        return this;
    }

    public GetAllBookingsByRoomUsecase withRoomName(String roomName) {
        this.roomName = roomName;
        return this;
    }

    public GetAllBookingsByRoomUsecase withMapBook(MapBookings mapBookings) {
        this.mapBookings = mapBookings;
        return this;
    }

    public List<RoomBookingNameAndSurnameDto> run(){
        return getBookingScheduleForGivenRoom();
    }

    private List<RoomBookingNameAndSurnameDto> getBookingScheduleForGivenRoom() {
        roomRepository.findById(roomName).orElseThrow(() -> new BadRequestException("No such room"));
        List<Booking> bookingList;
        bookingList = bookingRepository.getAllBookingsInRoom (roomName);
        return mapBookings.mapBookings(bookingList);
    }

}
