package com.be.booker.business.usecases.booking;

import com.be.booker.business.entity.Booking;
import com.be.booker.business.entitydto.RoomBookingNameAndSurnameDto;
import com.be.booker.business.exceptions.BadRequestException;
import com.be.booker.business.repository.BookingRepository;
import com.be.booker.business.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GetBookingScheduleForGivenUserUsecase {
  private BookingRepository bookingRepository;
  private UserRepository userRepository;
  private String userLogin;
  private LocalDateTime bookedFrom;
  private LocalDateTime bookedTo;
  private MapBookings mapBookings;
  private DateChecker dateChecker;

  public GetBookingScheduleForGivenUserUsecase withBookingRepository(BookingRepository bookingRepository) {
    this.bookingRepository = bookingRepository;
    return this;
  }

  public GetBookingScheduleForGivenUserUsecase withUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
    return this;
  }

  public GetBookingScheduleForGivenUserUsecase withBookedFrom(LocalDateTime bookedFrom) {
    this.bookedFrom = bookedFrom;
    return this;
  }

  public GetBookingScheduleForGivenUserUsecase withBookedTo(LocalDateTime bookedTo) {
    this.bookedTo = bookedTo;
    return this;
  }

  public GetBookingScheduleForGivenUserUsecase withUserLogin(String userLogin) {
    this.userLogin = userLogin;
    return this;
  }

  public GetBookingScheduleForGivenUserUsecase withMapBookings(MapBookings mapBookings) {
    this.mapBookings = mapBookings;
    return this;
  }

  public GetBookingScheduleForGivenUserUsecase withDateChecker(DateChecker dateChecker) {
    this.dateChecker = dateChecker;
    return this;
  }

  public List<RoomBookingNameAndSurnameDto> run() {
    return getBookingForGivenUser();
  }

  private List<RoomBookingNameAndSurnameDto> getBookingForGivenUser() {
    dateChecker.dateCheckerForSearching(bookedFrom, bookedTo);
    userRepository.findById(userLogin).orElseThrow(() -> new BadRequestException("No such user"));
    List<Booking> roomBookingEntityList = new ArrayList<>();

    if (bookedFrom != null && bookedTo != null) {
      roomBookingEntityList = bookingRepository.getAllBookingsWithInDateFrameAndUser(bookedFrom, bookedTo, userLogin);
    }
    if (bookedFrom == null && bookedTo != null) {
      roomBookingEntityList = bookingRepository.getAllBookingsInPastAndUser(bookedFrom, userLogin);
    }
    if (bookedFrom != null && bookedTo == null) {
      roomBookingEntityList = bookingRepository.getAllBookingsInFutureAndUser(bookedTo, userLogin);
    }
    if (bookedFrom == null && bookedTo == null) {
      roomBookingEntityList = bookingRepository.getAllBookingsForUser(userLogin);
    }
    return mapBookings.mapBookings(roomBookingEntityList);
  }
}
