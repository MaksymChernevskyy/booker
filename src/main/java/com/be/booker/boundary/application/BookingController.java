package com.be.booker.boundary.application;

import com.be.booker.business.entity.Booking;
import com.be.booker.business.entity.validators.BookingValidator;
import com.be.booker.business.services.BookingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "/booked", description = "Available operations for booking", tags = {"Booking"})
@RestController
@RequestMapping("/booked")
public class BookingController {

  private BookingService bookingService;

  @Autowired
  public BookingController(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  @GetMapping
  @ApiOperation(
      value = "Returns all bookings",
      response = com.be.booker.business.entity.Booking.class,
      responseContainer = "List")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = com.be.booker.business.entity.Booking.class),
      @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)})
  public ResponseEntity<?> getAll() {
    try {
      Optional<List<Booking>> optionalBookingList = bookingService.getAllBookings();
      return optionalBookingList.<ResponseEntity<?>>map(bookings -> new ResponseEntity<>(bookings, HttpStatus.OK)).orElseGet(() ->
          new ResponseEntity<>(new ArrayList<Booking>(), HttpStatus.OK));
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorMessage("Internal server error while getting bookings."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{from}/{to}")
  @ApiOperation(
      value = "Returns all bookings in given range",
      response = com.be.booker.business.entity.Booking.class,
      responseContainer = "List")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = com.be.booker.business.entity.Booking.class),
      @ApiResponse(code = 404, message = "Not found", response = com.be.booker.business.entity.Booking.class),
      @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)})
  public ResponseEntity<?> getBookingsInGivenRange(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime from,
                                                   @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime to) {
    try {
      Optional<List<com.be.booker.business.entity.Booking>> optionalBookingList = bookingService.getAllBookingsInGivenRange(from, to);
      return optionalBookingList.<ResponseEntity<?>>map(bookings -> new ResponseEntity<>(bookings, HttpStatus.OK)).orElseGet(() ->
          new ResponseEntity<>(new ArrayList<com.be.booker.business.entity.Booking>(), HttpStatus.NOT_FOUND));
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorMessage("Internal server error while getting bookings."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{user}")
  @ApiOperation(
      value = "Returns all bookings in given range",
      response = com.be.booker.business.entity.Booking.class,
      responseContainer = "List")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = com.be.booker.business.entity.Booking.class),
      @ApiResponse(code = 404, message = "Bookings not found for passed name.", response = ErrorMessage.class),
      @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)})
  public ResponseEntity<?> getBookingsByUser(@PathVariable String user) {
    try {
      Optional<List<com.be.booker.business.entity.Booking>> optionalBookingList = bookingService.getBookingsByUser(user);
      return optionalBookingList.<ResponseEntity<?>>map(bookings -> new ResponseEntity<>(bookings, HttpStatus.OK)).orElseGet(() ->
          new ResponseEntity<>(new ArrayList<com.be.booker.business.entity.Booking>(), HttpStatus.NOT_FOUND));
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorMessage("Internal server error while getting bookings."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{user}/{from}/{to}")
  @ApiOperation(
      value = "Returns all bookings by user in given range",
      response = com.be.booker.business.entity.Booking.class,
      responseContainer = "List")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = com.be.booker.business.entity.Booking.class),
      @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)})
  public ResponseEntity<?> getBookingsByUserInGivenRange(@PathVariable String user, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime from,
                                                         @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime to) {
    try {
      Optional<List<com.be.booker.business.entity.Booking>> optionalBookingList = bookingService.getAllBookedRoomsByUserInGivenRange(user, from, to);
      return optionalBookingList.<ResponseEntity<?>>map(bookings -> new ResponseEntity<>(bookings, HttpStatus.OK)).orElseGet(() ->
          new ResponseEntity<>(new ArrayList<com.be.booker.business.entity.Booking>(), HttpStatus.OK));
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorMessage("Internal server error while getting bookings."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping
  @ApiOperation(
      value = "Creates new booking.",
      response = Booking.class)
  @ResponseStatus(HttpStatus.CREATED)
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Created", response = Booking.class),
      @ApiResponse(code = 400, message = "Passed data is invalid.", response = ErrorMessage.class),
      @ApiResponse(code = 409, message = "Booking already exist", response = ErrorMessage.class),
      @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)})
  public ResponseEntity<?> book(@RequestBody(required = false) com.be.booker.business.entity.Booking booking) {
    List<String> resultOfValidation = BookingValidator.validate(booking, false);
    if (resultOfValidation.size() > 0) {
      return new ResponseEntity<>(new ErrorMessage("Passed booking is invalid.", resultOfValidation), HttpStatus.BAD_REQUEST);
    }
    try {
      if (booking.getId() == null || !bookingService.bookingExistingById(booking.getId())) {
        Optional<com.be.booker.business.entity.Booking> addedBooking = bookingService.createBooking(booking);
        HttpHeaders responseHeaders = new HttpHeaders();
        if (addedBooking.isPresent()) {
          responseHeaders.setLocation(URI.create(String.format("/booking/%d", addedBooking.get().getId())));
          return new ResponseEntity<>(addedBooking.get(), responseHeaders, HttpStatus.CREATED);
        }
      }
      return new ResponseEntity<>(new ErrorMessage("Booking already exist."), HttpStatus.CONFLICT);
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorMessage("Internal server error while book."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{id}")
  @ApiOperation(
      value = "Get existing booking.",
      response = com.be.booker.business.entity.Booking.class)
  @ApiImplicitParam(name = "id", value = "Only digits possible, e.g. 12", example = "12", dataType = "Long")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = com.be.booker.business.entity.Booking.class),
      @ApiResponse(code = 404, message = "Booking not found for passed id.", response = ErrorMessage.class),
      @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)})
  public ResponseEntity<?> getBookingById(@PathVariable("id") Long id) {
    try {
      Optional<com.be.booker.business.entity.Booking> optionalBooking = bookingService.getBooking(id);
      return optionalBooking.<ResponseEntity<?>>map(booking -> new ResponseEntity<>(booking, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new ErrorMessage(String.format("Booking not found for passed id: %d", id)), HttpStatus.NOT_FOUND));
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorMessage(String.format("Internal server error while getting booking by id: %d", id)), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/{id}")
  @ApiOperation(
      value = "Update booking.",
      response = com.be.booker.business.entity.Booking.class)
  @ApiImplicitParam(name = "id", value = "Only digits possible, e.g. 7865", example = "7865", dataType = "Long")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = com.be.booker.business.entity.Booking.class),
      @ApiResponse(code = 400, message = "Passed data is invalid.", response = ErrorMessage.class),
      @ApiResponse(code = 404, message = "Booking not found for passed id.", response = ErrorMessage.class),
      @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)})
  public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody(required = false) com.be.booker.business.entity.Booking booking) {
    try {
      List<String> resultOfValidation = BookingValidator.validate(booking, true);
      if (resultOfValidation.size() > 0) {
        return new ResponseEntity<>(new ErrorMessage("Passed booking is invalid.", resultOfValidation), HttpStatus.BAD_REQUEST);
      }
      if (!id.equals(booking.getId())) {
        return new ResponseEntity<>(new ErrorMessage(String.format("Booking to update has different id than %d.", id)), HttpStatus.BAD_REQUEST);
      }
      if (!bookingService.bookingExistingById(id)) {
        return new ResponseEntity<>(new ErrorMessage(String.format("Booking with %d id does not exist.", id)), HttpStatus.NOT_FOUND);
      }
      bookingService.updateBooking(booking);
      return new ResponseEntity<>(booking, HttpStatus.OK);
    } catch (Exception e) {
      String message = String.format("Internal server error while updating booking %d id, %s booking", id, booking);
      return new ResponseEntity<>(new ErrorMessage(message), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/{id}")
  @ApiOperation(
      value = "Removes existing booking.",
      response = Booking.class)
  @ApiImplicitParam(name = "id", value = "Only digits possible, e.g. 12", example = "12", dataType = "Long")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = Booking.class),
      @ApiResponse(code = 404, message = "Booking not found for passed id.", response = ErrorMessage.class),
      @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)})
  public ResponseEntity<?> removeBooking(@PathVariable("id") Long id) {
    try {
      Optional<com.be.booker.business.entity.Booking> optionalBooking = bookingService.getBooking(id);
      if (!optionalBooking.isPresent()) {
        return new ResponseEntity<>(new ErrorMessage(String.format("Booking with %d id does not exist.", id)), HttpStatus.NOT_FOUND);
      }
      bookingService.deleteBooking(id);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorMessage("Internal server error while removing booking."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
