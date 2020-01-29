package com.be.booker.rest.controlers;

import com.be.booker.business.entity.Booking;
import com.be.booker.business.entity.entitydto.RoomBookingDto;
import com.be.booker.business.entity.entitydto.RoomBookingNameAndSurnameDto;
import com.be.booker.business.services.BookingService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(BookingController.BASE_URL)
public class BookingController {
    private BookingService bookingService;

    public static final String BASE_URL = "bookRoom";
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

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
    public List<RoomBookingNameAndSurnameDto> getBookingScheduleForAllRoomsInGivenTimeRange(@RequestParam(required = false) String bookedFrom, @RequestParam(required = false) String bookedTo) {
        return bookingService.getBookingScheduleForAllBookingsInGivenTimeRange(LocalDateTime.parse(bookedFrom, dateTimeFormatter), LocalDateTime.parse(bookedTo, dateTimeFormatter));
    }

    @GetMapping("/givenRoomByTimeRange")
    public List<RoomBookingNameAndSurnameDto> getAllBookingsForRoomInGivenTimeRange(@RequestParam(required = false) String bookedFrom, @RequestParam(required = false) String bookedTo, @RequestParam(required = false) String roomName) {
        return bookingService.getAllBookingsForRoomInGivenTimeRange(LocalDateTime.parse(bookedFrom, dateTimeFormatter), LocalDateTime.parse(bookedTo, dateTimeFormatter), roomName);
    }

    @GetMapping({"/givenUser"})
    public List<RoomBookingNameAndSurnameDto> getBookingScheduleForGivenUser(@RequestParam(required = false) String bookedFrom, @RequestParam(required = false) String bookedTo, @RequestParam(required = false) String userLogin) {
        return bookingService.getBookingScheduleForGivenUserInGivenTimeRange(LocalDateTime.parse(bookedFrom, dateTimeFormatter), LocalDateTime.parse(bookedTo, dateTimeFormatter), userLogin);
    }

    @GetMapping({"/allBookings"})
    public List<RoomBookingNameAndSurnameDto> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/allBookingsFuture")
    public List<RoomBookingNameAndSurnameDto> getAllBookingsInFuture(@RequestParam(required = false) String bookedFrom) {
        return bookingService.getAllBookingsInFuture(LocalDateTime.parse(bookedFrom, dateTimeFormatter));
    }

    @GetMapping("/allBookingsPast")
    public List<RoomBookingNameAndSurnameDto> getAllBookingsInPast(@RequestParam(required = false) String bookedTo) {
        return bookingService.getAllBookingsInPast(LocalDateTime.parse(bookedTo, dateTimeFormatter));
    }

    @GetMapping("{/roomName}")
    public List<RoomBookingNameAndSurnameDto> getAllBookingsByRoomName(@RequestParam String roomName) {
        return bookingService.getAllBookingsByRoomName(roomName);
    }

    @GetMapping("/roomNameFuture")
    public List<RoomBookingNameAndSurnameDto> getAllBookingsByRoomNameInFuture(@RequestParam String roomName, @RequestParam String bookedFrom) {
        return bookingService.getAllBookingsByRoomNameInFuture(roomName, LocalDateTime.parse(bookedFrom, dateTimeFormatter));
    }

    @GetMapping("/roomNamePast")
    public List<RoomBookingNameAndSurnameDto> getAllBookingsByRoomNameInPast(@RequestParam String roomName, @RequestParam String bookedTo) {
        return bookingService.getAllBookingsByRoomNameInPast(roomName, LocalDateTime.parse(bookedTo, dateTimeFormatter));
    }

    @GetMapping("/{userLogin}")
    public List<RoomBookingNameAndSurnameDto> getAllBookingsByUserLogin(@RequestParam String userLogin) {
        return bookingService.getBookingsForUserByUserLogin(userLogin);
    }

    @GetMapping("/userNamePast")
    public List<RoomBookingNameAndSurnameDto> getAllBookingsByUserLoginInPast(@RequestParam String userLogin, @RequestParam String bookedTo) {
        return bookingService.getBookingsInPastByUser(userLogin, LocalDateTime.parse(bookedTo, dateTimeFormatter));
    }

    @GetMapping("/userNameFuture")
    public List<RoomBookingNameAndSurnameDto> getAllBookingsByUserLoginInFuture(@RequestParam String userLogin, @RequestParam String bookedFrom) {
        return bookingService.getBookingsInFutureByUser(userLogin, LocalDateTime.parse(bookedFrom, dateTimeFormatter));
    }
}
