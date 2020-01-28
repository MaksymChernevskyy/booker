package com.be.booker.business.usecases.booking;

import com.be.booker.business.entity.Booking;
import com.be.booker.business.entitydto.RoomBookingDto;
import com.be.booker.business.exceptions.BadRequestException;
import com.be.booker.business.repository.BookingRepository;
import com.be.booker.business.repository.RoomRepository;
import com.be.booker.business.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BookingRoomUsecase {
  private BookingRepository bookingRepository;
  private UserRepository userRepository;
  private RoomRepository roomRepository;
  private RoomBookingDto roomBookingDto;
  private DateChecker dateChecker;

  public BookingRoomUsecase withBookingRepository(BookingRepository bookingRepository) {
    this.bookingRepository = bookingRepository;
    return this;
  }

  public BookingRoomUsecase withUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
    return this;
  }

  public BookingRoomUsecase withRoomRepository(RoomRepository roomRepository) {
    this.roomRepository = roomRepository;
    return this;
  }

  public BookingRoomUsecase withRoomBookingDto(RoomBookingDto roomBookingDto) {
    this.roomBookingDto = roomBookingDto;
    return this;
  }

  public BookingRoomUsecase withDateChecker(DateChecker dateChecker) {
    this.dateChecker = dateChecker;
    return this;
  }

  public Booking run() {
    return bookingRoom();
  }

  private Booking bookingRoom() {
    dateChecker.dateCheck(roomBookingDto.getBookedFrom(), roomBookingDto.getBookedTo());
    roomRepository.findById(roomBookingDto.getRoomName()).orElseThrow(() -> new BadRequestException("No such room."));
    userRepository.findById(roomBookingDto.getUserLogin()).orElseThrow(() -> new BadRequestException("No such user."));
    List<Booking> booking = bookingRepository.getAllBookingsWithInDateFrameAndRoom(roomBookingDto.getBookedFrom(),
        roomBookingDto.getBookedTo(), roomBookingDto.getRoomName());
    if (!booking.isEmpty()) {
      throw new BadRequestException("Room is occupied at this time.");
    }
    Booking roomBooking = new Booking();
    roomBooking.setRoomName(roomBookingDto.getRoomName());
    roomBooking.setUserLogin(roomBookingDto.getUserLogin());
    roomBooking.setBookedFrom(roomBookingDto.getBookedFrom());
    roomBooking.setBookedTo(roomBookingDto.getBookedTo());
    return bookingRepository.save(roomBooking);
  }
}
