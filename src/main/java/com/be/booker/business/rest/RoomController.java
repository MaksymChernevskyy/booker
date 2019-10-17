package com.be.booker.business.rest;

import com.be.booker.business.entity.Room;
import com.be.booker.business.services.RoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "/rooms", description = "Available operations for rooms in booking application", tags = {"Rooms"})
@RestController
@RequestMapping("/rooms")
public class RoomController {

  private @Value("${admin.password}")
  String administrarorPassword;
  private RoomService roomService;

  @Autowired
  public RoomController(RoomService roomService) {
    this.roomService = roomService;
  }

  @GetMapping
  @ApiOperation(
      value = "Returns all rooms",
      response = Room.class,
      responseContainer = "List")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = Room.class),
      @ApiResponse(code = 200, message = "OK", response = Room.class),
      @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)})
  public ResponseEntity<?> getAll() {
    try {
      Optional<List<Room>> optionalRoomList = roomService.getAllRooms();
      return optionalRoomList.<ResponseEntity<?>>map(rooms -> new ResponseEntity<>(rooms, HttpStatus.OK)).orElseGet(() ->
          new ResponseEntity<>(new ArrayList<Room>(), HttpStatus.OK));
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorMessage("Internal server error while getting rooms."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/byRoomName")
  @ApiOperation(
      value = "Returns all filtered by room name.",
      response = Room.class)
  @ApiImplicitParam(name = "name", value = "Possible letters, numbers and sign '/'  e.g. 'Name'", example = "name")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = Room.class),
      @ApiResponse(code = 404, message = "Rooms not found for passed name.", response = ErrorMessage.class),
      @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)})
  public ResponseEntity<?> findByRoomName(@RequestParam("name") String name) {
    try {
      Optional<List<Room>> optionalRoomList = roomService.getAllRooms();
      if (optionalRoomList.isPresent()) {
        Optional<Room> optionalRoom = optionalRoomList.get()
            .stream()
            .filter(roomToFind -> roomToFind.getRoomName().equals(name))
            .findFirst();
        return optionalRoom.<ResponseEntity<?>>map(room -> new ResponseEntity<>(room, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new ErrorMessage(String.format("Room not found for passed name: %s", name)), HttpStatus.NOT_FOUND));
      }
      return new ResponseEntity<>(new ErrorMessage(String.format("Room not found for passed name: %s", name)), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorMessage(String.format("Internal server error while getting room by name: %s", name)), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping
  @ApiOperation(
      value = "Creates new room.",
      response = Room.class)
  @ResponseStatus(HttpStatus.CREATED)
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Created", response = Room.class),
      @ApiResponse(code = 409, message = "Room already existsById", response = ErrorMessage.class),
      @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)})
  public ResponseEntity<?> createRoom(@RequestBody(required = false) Room room) {
    try {
      if (room.getId() == null || !roomService.roomExistingById(room.getId())) {
        Optional<Room> addedRoom = roomService.createRoom(room);
        HttpHeaders responseHeaders = new HttpHeaders();
        if (addedRoom.isPresent()) {
          responseHeaders.setLocation(URI.create(String.format("/room/%d", addedRoom.get().getId())));
          return new ResponseEntity<>(addedRoom.get(), responseHeaders, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new ErrorMessage("Internal server error while adding room."), HttpStatus.INTERNAL_SERVER_ERROR);
      }
      return new ResponseEntity<>(new ErrorMessage("Room already exist."), HttpStatus.CONFLICT);
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorMessage("Internal server error while adding room."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/{id}/{password}")
  @ApiOperation(
      value = "Update existing room.",
      response = Room.class)
  @ApiImplicitParam(name = "id", value = "Only digits possible, e.g. 12", example = "12", dataType = "Long")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = Room.class),
      @ApiResponse(code = 404, message = "Room not found for passed id.", response = ErrorMessage.class),
      @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)})
  public ResponseEntity<?> updateRoom(@PathVariable("id") Long id, @RequestBody(required = false) Room room, @PathVariable String password) {
    try {
      if (!id.equals(room.getId())) {
        return new ResponseEntity<>(new ErrorMessage(String.format("Room to update has different id than %d.", id)), HttpStatus.BAD_REQUEST);
      }
      if (!roomService.roomExistingById(id)) {
        return new ResponseEntity<>(new ErrorMessage(String.format("Room with %d id does not exist.", id)), HttpStatus.NOT_FOUND);
      }
      if (password.equals(administrarorPassword)) {
        roomService.updateRoom(room);
      }
      return new ResponseEntity<>(room, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorMessage("Internal server error while updating room."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/{id}/{password}")
  @ApiOperation(
      value = "Removes existing room.",
      response = Room.class)
  @ApiImplicitParam(name = "id", value = "Only digits possible, e.g. 12", example = "12", dataType = "Long")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = Room.class),
      @ApiResponse(code = 404, message = "Room not found for passed id.", response = ErrorMessage.class),
      @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)})
  public ResponseEntity<?> removeRoom(@PathVariable("id") Long id, @PathVariable String password) {
    try {
      Optional<Room> optionalRoom = roomService.getRoom(id);
      if (!optionalRoom.isPresent()) {
        return new ResponseEntity<>(new ErrorMessage(String.format("Room with %d id does not exist.", id)), HttpStatus.NOT_FOUND);
      }
      if (password.equals(administrarorPassword)) {
        roomService.deleteRoom(id);
      }
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorMessage("Internal server error while removing room."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
