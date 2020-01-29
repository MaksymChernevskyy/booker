package com.be.booker.business.usecases.booking;

import com.be.booker.business.entity.Booking;
import com.be.booker.business.entity.User;
import com.be.booker.business.entity.entitydto.RoomBookingNameAndSurnameDto;
import com.be.booker.business.exceptions.BadRequestException;
import com.be.booker.business.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapBookings {
  @Autowired
  private UserRepository userRepository;

  public List<RoomBookingNameAndSurnameDto> mapBookings(List<Booking> roomBookingEntityList) {
    List<RoomBookingNameAndSurnameDto> roomBookingNameSurnameList = new ArrayList<>();
    for (Booking booking : roomBookingEntityList) {
      RoomBookingNameAndSurnameDto roomBookingNameSurname = new RoomBookingNameAndSurnameDto();
      findUser(roomBookingNameSurnameList, booking, roomBookingNameSurname);
    }
    return roomBookingNameSurnameList;
  }

  private void findUser(List<RoomBookingNameAndSurnameDto> roomBookingNameSurnameList, Booking booking, RoomBookingNameAndSurnameDto roomBookingNameSurname) {
    User user = userRepository.findById(booking.getUserLogin()).orElseThrow(() -> new BadRequestException("There is no such a user."));
    roomBookingNameSurname.setUserName(user.getName());
    roomBookingNameSurname.setSurname(user.getSurname());
    roomBookingNameSurname.setBookedFrom(booking.getBookedFrom());
    roomBookingNameSurname.setBookedTo(booking.getBookedTo());
    roomBookingNameSurname.setRoomName(booking.getRoomName());
    roomBookingNameSurnameList.add(roomBookingNameSurname);
  }
}
