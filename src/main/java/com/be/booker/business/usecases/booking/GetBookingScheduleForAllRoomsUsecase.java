package com.be.booker.business.usecases.booking;

import com.be.booker.business.entity.Booking;
import com.be.booker.business.entitydto.RoomBookingNameAndSurnameDto;
import com.be.booker.business.repository.BookingRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GetBookingScheduleForAllRoomsUsecase {

  private BookingRepository bookingRepository;
  private DateChecker dateChecker;
  private MapBookings mapBookings;
  private LocalDateTime bookedFrom;
  private LocalDateTime bookedTo;

  public GetBookingScheduleForAllRoomsUsecase withBookingRepository(BookingRepository bookingRepository) {
    this.bookingRepository = bookingRepository;
    return this;
  }

  public GetBookingScheduleForAllRoomsUsecase withDateChecker(DateChecker dateChecker) {
    this.dateChecker = dateChecker;
    return this;
  }

  public GetBookingScheduleForAllRoomsUsecase withBookFromDate(LocalDateTime bookedFrom) {
    this.bookedFrom = bookedFrom;
    return this;
  }

  public GetBookingScheduleForAllRoomsUsecase withBookToDate(LocalDateTime bookedTo) {
    this.bookedTo = bookedTo;
    return this;
  }

  public GetBookingScheduleForAllRoomsUsecase withMapBookings(MapBookings mapBookings) {
    this.mapBookings = mapBookings;
    return this;
  }

  public List<RoomBookingNameAndSurnameDto> run() {
    return getBookingScheduleForAlRooms();

  }

  private List<RoomBookingNameAndSurnameDto> getBookingScheduleForAlRooms() {
    dateChecker.dateCheckerForSearching(bookedFrom, bookedTo);
    List<Booking> bookingList = new ArrayList<>();
    if (bookedFrom != null && bookedTo != null) {
      bookingList = bookingRepository.getAllBookingsWithIn(bookedFrom, bookedTo);
    }
    if (bookedFrom == null && bookedTo != null) {
      bookingList = bookingRepository.getAllBookingsInPast(bookedTo);
    }
    if (bookedFrom != null && bookedTo == null) {
      bookingList = bookingRepository.getAllBookingsInFuture(bookedFrom);
    }
    if (bookedFrom == null && bookedTo == null) {
      bookingList = bookingRepository.findAll();
    }
    return mapBookings.mapBookings(bookingList);
  }
}
