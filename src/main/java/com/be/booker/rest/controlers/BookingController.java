package com.be.booker.rest.controlers;

import com.be.booker.business.entity.Booking;
import com.be.booker.business.entity.entitydto.RoomBookingDto;
import com.be.booker.business.entity.entitydto.RoomBookingNameAndSurnameDto;
import com.be.booker.business.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<?> newBooking(@Valid @RequestBody RoomBookingDto roomBookingDto) {
        Booking bookedRoom = bookingService.bookTheRoom(roomBookingDto);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(URI.create(String.format("/room/%d", bookedRoom.getId())));
        return responseForSuccess(bookedRoom, responseHeaders);
    }

    @GetMapping
    public ResponseEntity<?> getBookingScheduleForAllRoomsInGivenTimeRange(@RequestParam String bookedFrom, @RequestParam String bookedTo) {
        List<RoomBookingNameAndSurnameDto> list = bookingService.getBookingScheduleForAllBookingsInGivenTimeRange(LocalDateTime.parse(bookedFrom, dateTimeFormatter), LocalDateTime.parse(bookedTo, dateTimeFormatter));
        return responseForSuccess(list);
    }

    @GetMapping("{/givenRoomByTimeRange}")
    public ResponseEntity<?> getAllBookingsForRoomInGivenTimeRange(@RequestParam String bookedFrom, @RequestParam String bookedTo, @RequestParam String roomName) {
        List<RoomBookingNameAndSurnameDto> list = bookingService.getAllBookingsForRoomInGivenTimeRange(LocalDateTime.parse(bookedFrom, dateTimeFormatter), LocalDateTime.parse(bookedTo, dateTimeFormatter), roomName);
        return responseForSuccess(list);
    }

    @GetMapping({"/givenUser"})
    public ResponseEntity<?> getBookingScheduleForGivenUser(@RequestParam String bookedFrom, @RequestParam String bookedTo, @RequestParam String userLogin) {
        List<RoomBookingNameAndSurnameDto> list = bookingService.getBookingScheduleForGivenUserInGivenTimeRange(LocalDateTime.parse(bookedFrom, dateTimeFormatter), LocalDateTime.parse(bookedTo, dateTimeFormatter), userLogin);
        return responseForSuccess(list);
    }

    @GetMapping({"/allBookings"})
    public ResponseEntity<?> getAllBookings() {
        List<RoomBookingNameAndSurnameDto> list = bookingService.getAllBookings();
        return responseForSuccess(list);
    }

    @GetMapping("/allBookingsFuture")
    public ResponseEntity<?> getAllBookingsInFuture(@RequestParam String bookedFrom) {
        List<RoomBookingNameAndSurnameDto> list = bookingService.getAllBookingsInFuture(LocalDateTime.parse(bookedFrom, dateTimeFormatter));
        return responseForSuccess(list);
    }

    @GetMapping("/allBookingsPast")
    public ResponseEntity<?> getAllBookingsInPast(@RequestParam String bookedTo) {
        List<RoomBookingNameAndSurnameDto> list = bookingService.getAllBookingsInPast(LocalDateTime.parse(bookedTo, dateTimeFormatter));
        return responseForSuccess(list);
    }

    @GetMapping("{/roomName}")
    public ResponseEntity<?> getAllBookingsByRoomName(@RequestParam String roomName) {
        List<RoomBookingNameAndSurnameDto> list = bookingService.getAllBookingsByRoomName(roomName);
        return responseForSuccess(list);
    }

    @GetMapping("/roomNameFuture")
    public ResponseEntity<?> getAllBookingsByRoomNameInFuture(@RequestParam String roomName, @RequestParam String bookedFrom) {
        List<RoomBookingNameAndSurnameDto> list = bookingService.getAllBookingsByRoomNameInFuture(roomName, LocalDateTime.parse(bookedFrom, dateTimeFormatter));
        return responseForSuccess(list);
    }

    @GetMapping("/roomNamePast")
    public ResponseEntity<?> getAllBookingsByRoomNameInPast(@RequestParam String roomName, @RequestParam String bookedTo) {
        List<RoomBookingNameAndSurnameDto> list = bookingService.getAllBookingsByRoomNameInPast(roomName, LocalDateTime.parse(bookedTo, dateTimeFormatter));
        return responseForSuccess(list);
    }

    @GetMapping("/{userLogin}")
    public ResponseEntity<?> getAllBookingsByUserLogin(@RequestParam String userLogin) {
        List<RoomBookingNameAndSurnameDto> list = bookingService.getBookingsForUserByUserLogin(userLogin);
        return responseForSuccess(list);
    }

    @GetMapping("/userNamePast")
    public ResponseEntity<?> getAllBookingsByUserLoginInPast(@RequestParam String userLogin, @RequestParam String bookedTo) {
        List<RoomBookingNameAndSurnameDto> list = bookingService.getBookingsInPastByUser(userLogin, LocalDateTime.parse(bookedTo, dateTimeFormatter));
        return responseForSuccess(list);
    }

    @GetMapping("/userNameFuture")
    public ResponseEntity<?> getAllBookingsByUserLoginInFuture(@RequestParam String userLogin, @RequestParam String bookedFrom) {
        List<RoomBookingNameAndSurnameDto> list = bookingService.getBookingsInFutureByUser(userLogin, LocalDateTime.parse(bookedFrom, dateTimeFormatter));
        return responseForSuccess(list);
    }

    private ResponseEntity<List<RoomBookingNameAndSurnameDto>> responseForSuccess(List<RoomBookingNameAndSurnameDto> list) {
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    private ResponseEntity<?> responseForSuccess(Booking bookedRoom, HttpHeaders responseHeaders) {
        return new ResponseEntity<>(bookedRoom, responseHeaders, HttpStatus.CREATED);
    }
}
