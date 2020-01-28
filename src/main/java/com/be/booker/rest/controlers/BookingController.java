package com.be.booker.rest.controlers;

import com.be.booker.business.entitydto.RoomBookingDto;
import com.be.booker.business.entitydto.RoomBookingNameAndSurnameDto;
import com.be.booker.business.services.BookingService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

//@Api(value = "/booked", description = "Available operations for booking", tags = {"Booking"})
@RestController
@RequestMapping(BookingController.BASE_URL)
public class BookingController {
    private BookingService bookingService;

    public static final String BASE_URL = "bookRoom";

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PutMapping
    public void newBooking(@Valid @RequestBody RoomBookingDto roomBookingDto) {
        bookingService.bookTheRoom(roomBookingDto);
    }

    @GetMapping
    public List<RoomBookingNameAndSurnameDto> getBookingScheduleForAllRooms(@RequestParam String bookedFrom, @RequestParam String bookedTo) {
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

    @GetMapping({"/givenRoom"})
    public List<RoomBookingNameAndSurnameDto> getBookingScheduleForGivenRoom(@RequestParam String bookedFrom, @RequestParam String bookedTo, @RequestParam String roomName) {

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

    @GetMapping({"/givenUser"})
    public List<RoomBookingNameAndSurnameDto> getBookingScheduleForGivenUser(@RequestParam String bookedFrom, @RequestParam String bookedTo, @RequestParam String userLogin) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        if (bookedTo.equals("") && bookedFrom.equals(""))
            return bookingService.getBookingScheduleForGivenUser(null, null, userLogin);
        if (bookedFrom.equals(""))
            return bookingService.getBookingScheduleForGivenUser(null, LocalDateTime.parse(bookedTo, dateTimeFormatter), userLogin);
        if (bookedTo.equals(""))
            return bookingService.getBookingScheduleForGivenUser(LocalDateTime.parse(bookedFrom, dateTimeFormatter), null, userLogin);
        return bookingService.getBookingScheduleForGivenUser(LocalDateTime.parse(bookedFrom, dateTimeFormatter), LocalDateTime.parse(bookedTo, dateTimeFormatter), userLogin);

    }
}
