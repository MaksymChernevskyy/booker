package com.be.booker.boundary.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.be.booker.business.entity.Room;
import com.be.booker.business.services.RoomService;
import com.be.booker.business.services.ServiceOperationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RoomController.class)
@WithMockUser(username = "admin", roles = "ADMIN")
class RoomControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private RoomService roomService;

  @Test
  void getAllRooms() throws Exception {
    List<Room> rooms = Arrays.asList(createLargeRoom());
    when(roomService.getAllRooms()).thenReturn(Optional.ofNullable(rooms));

    MvcResult result = mockMvc
        .perform(get("/rooms/all"))
        .andReturn();

    int actualHttpStatus = result.getResponse().getStatus();
    assertEquals(HttpStatus.OK.value(), actualHttpStatus);
    verify(roomService).getAllRooms();
    String contentAsString = result.getResponse().getContentAsString();
    String jobJson = jsonAsString(rooms);
    assertEquals(contentAsString, jobJson);
  }

  @Test
  void shouldReturnEmptyListOfRoomsWhenThereAreNoRoomsInTheDatabase() throws Exception {
    when(roomService.getAllRooms()).thenReturn(Optional.empty());

    MvcResult result = mockMvc
        .perform(get("/rooms/all"))
        .andReturn();

    int actualHttpStatus = result.getResponse().getStatus();
    assertEquals(HttpStatus.OK.value(), actualHttpStatus);
    verify(roomService).getAllRooms();
  }

  @Test
  void shouldReturnInternalServerErrorDuringFindingAllRoomsWhenSomethingWentWrongOnServer() throws Exception {
    doThrow(ServiceOperationException.class).when(roomService).getAllRooms();
    ErrorMessage expectedResponse = new ErrorMessage("Internal server error while getting rooms.");

    MvcResult result = mockMvc
        .perform(get("/rooms/all")
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    int actualHttpStatus = result.getResponse().getStatus();

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), actualHttpStatus);
    verify(roomService).getAllRooms();
  }

  private static String jsonAsString(final Object job) {
    try {
      return new ObjectMapper().writeValueAsString(job);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private Room createLargeRoom() {
    Room room = new Room();
    room.setId(1L);
    room.setRoomName("Large room");
    room.setLocationDescription("1st floor");
    room.setNumberOfSeats(10);
    room.setContainProjector(true);
    room.setPhoneNumber("22-22-22-22");
    return room;
  }
}
