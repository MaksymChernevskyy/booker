package com.be.booker.rest.controlers;

import com.be.booker.business.entitydto.RoomDto;
import com.be.booker.business.services.RoomService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//@Api(value = "/rooms", description = "Available operations for rooms in booking application", tags = {"Rooms"})
@RestController
@RequestMapping(RoomController.BASE_URL)
public class RoomController {
    public static final String BASE_URL = "room";
    private RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PutMapping()
    public void createNewUser(@Valid @RequestBody RoomDto roomDto) {
        roomService.saveRoom(roomDto);
    }

    @PostMapping("/{roomName}")
    public void update(@PathVariable String roomName, @Valid @RequestBody RoomDto roomDto) {
        roomService.updateRoom(roomName, roomDto);
    }

    @DeleteMapping({"/{roomName}"})
    public void delete(@PathVariable String roomName) {
        roomService.deleteRoom(roomName);
    }

    @GetMapping({"", "/"})
    public List<RoomDto> getAllRooms() {
        return roomService.getAllRooms();
    }
}
