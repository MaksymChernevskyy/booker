package com.be.booker.rest.controlers;

import com.be.booker.business.entity.Booking;
import com.be.booker.business.entitydto.RoomBookingDto;
import com.be.booker.business.entitydto.RoomBookingNameAndSurnameDto;
import com.be.booker.business.services.BookingService;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BookingController.BASE_URL)
public class BookingController {
  private BookingService bookingService;

  public static final String BASE_URL = "bookRoom";

  public BookingController(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  @PostMapping
  public ResponseEntity<?> newBooking(@Valid @RequestBody RoomBookingDto roomBookingDto) {
    Booking bookedRoom = bookingService.bookTheRoom(roomBookingDto);
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(URI.create(String.format("/room/%d", bookedRoom.getId())));
    return new ResponseEntity<>(bookedRoom, responseHeaders, HttpStatus.CREATED);
  }

  @GetMapping
  public List<RoomBookingNameAndSurnameDto> getBookingScheduleForAllRooms(@RequestParam(required = false) String bookedFrom, @RequestParam(required = false) String bookedTo) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    if (bookedTo.equals("") && bookedFrom.equals("")) {
      return bookingService.getBookingScheduleForAllRooms(null, null);
    }
    if (bookedFrom.equals("")) {
      return bookingService.getBookingScheduleForAllRooms(null, LocalDateTime.parse(bookedTo, dateTimeFormatter));
    }
    if (bookedTo.equals("")) {
      return bookingService.getBookingScheduleForAllRooms(LocalDateTime.parse(bookedFrom, dateTimeFormatter), null);
    }
    return bookingService.getBookingScheduleForAllRooms(LocalDateTime.parse(bookedFrom, dateTimeFormatter), LocalDateTime.parse(bookedTo, dateTimeFormatter));
  }

  @GetMapping( {"/givenRoom"})
  public List<RoomBookingNameAndSurnameDto> getBookingScheduleForGivenRoom(@RequestParam(required = false) String bookedFrom, @RequestParam(required = false) String bookedTo, @RequestParam(required = false) String roomName) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    if (bookedTo.equals("") && bookedFrom.equals("")) {
      return bookingService.getBookingScheduleForGivenRoom(null, null, roomName);
    }
    if (bookedFrom.equals("")) {
      return bookingService.getBookingScheduleForGivenRoom(null, LocalDateTime.parse(bookedTo, dateTimeFormatter), roomName);
    }
    if (bookedTo.equals("")) {
      return bookingService.getBookingScheduleForGivenRoom(LocalDateTime.parse(bookedFrom, dateTimeFormatter), null, roomName);
    }
    return bookingService.getBookingScheduleForGivenRoom(LocalDateTime.parse(bookedFrom, dateTimeFormatter), LocalDateTime.parse(bookedTo, dateTimeFormatter), roomName);
  }

  @GetMapping( {"/givenUser"})
  public List<RoomBookingNameAndSurnameDto> getBookingScheduleForGivenUser(@RequestParam(required = false) String bookedFrom, @RequestParam(required = false) String bookedTo, @RequestParam(required = false) String userLogin) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    if (bookedTo.equals("") && bookedFrom.equals("")) {
      return bookingService.getBookingScheduleForGivenUser(null, null, userLogin);
    }
    if (bookedFrom.equals("")) {
      return bookingService.getBookingScheduleForGivenUser(null, LocalDateTime.parse(bookedTo, dateTimeFormatter), userLogin);
    }
    if (bookedTo.equals("")) {
      return bookingService.getBookingScheduleForGivenUser(LocalDateTime.parse(bookedFrom, dateTimeFormatter), null, userLogin);
    }
    return bookingService.getBookingScheduleForGivenUser(LocalDateTime.parse(bookedFrom, dateTimeFormatter), LocalDateTime.parse(bookedTo, dateTimeFormatter), userLogin);
  }
}
