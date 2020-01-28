package com.be.booker.rest.controlers;

import com.be.booker.business.entity.Room;
import com.be.booker.business.entitydto.RoomDto;
import com.be.booker.business.services.RoomService;
import io.swagger.annotations.Api;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
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
import org.springframework.web.bind.annotation.RestController;

@Api(value = "/rooms", description = "Available operations for rooms in booking application", tags = {"Rooms"})
@RestController
@RequestMapping(RoomController.BASE_URL)
public class RoomController {
  public static final String BASE_URL = "room";
  private RoomService roomService;

  public RoomController(RoomService roomService) {
    this.roomService = roomService;
  }

  @PostMapping()
  public ResponseEntity<?> createNewRoom(@Valid @RequestBody RoomDto roomDto) {
    Room addedRoom = roomService.saveRoom(roomDto);
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(URI.create(String.format("/room/%s", addedRoom.getRoomName())));
    return new ResponseEntity<>(addedRoom, responseHeaders, HttpStatus.CREATED);
  }

  @PutMapping("/{roomName}")
  public void update(@PathVariable String roomName, @Valid @RequestBody RoomDto roomDto) {
    roomService.updateRoom(roomName, roomDto);
  }

  @DeleteMapping( {"/{roomName}"})
  public void delete(@PathVariable String roomName) {
    roomService.deleteRoom(roomName);
  }

  @GetMapping( {"", "/"})
  public List<RoomDto> getAllRooms() {
    return roomService.getAllRooms();
  }
}
