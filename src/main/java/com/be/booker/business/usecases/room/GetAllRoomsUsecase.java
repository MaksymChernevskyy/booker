package com.be.booker.business.usecases.room;

import com.be.booker.business.entity.Room;
import com.be.booker.business.entity.entitydto.RoomDto;
import com.be.booker.business.repository.RoomRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GetAllRoomsUsecase {
    private RoomRepository roomRepository;

    public GetAllRoomsUsecase withRoomRepository(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
        return this;
    }

    public List<RoomDto> run() {
        return getAllRooms();
    }

    private List<RoomDto> getAllRooms() {
        List<RoomDto> list = new ArrayList<>();
        for (Room room : roomRepository.findAll()) {
            RoomDto roomDto = new RoomDto();
            roomDto.setName(room.getRoomName());
            roomDto.setLocationDescription(room.getLocationDescription());
            roomDto.setNumberOfSeats(room.getNumberOfSeats());
            roomDto.setContainProjector(room.isContainProjector());
            roomDto.setPhoneNumber(room.getPhoneNumber());
            list.add(roomDto);
        }
        return list;
    }
}
