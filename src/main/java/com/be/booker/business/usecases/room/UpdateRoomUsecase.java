package com.be.booker.business.usecases.room;

import com.be.booker.business.entity.Room;
import com.be.booker.business.entity.entitydto.RoomDto;
import com.be.booker.business.exceptions.BadRequestException;
import com.be.booker.business.repository.RoomRepository;
import org.springframework.stereotype.Component;

@Component
public class UpdateRoomUsecase {
    private RoomRepository roomRepository;
    private RoomDto roomDto;
    private String roomName;

    public UpdateRoomUsecase withRoomRepository(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
        return this;
    }

    public UpdateRoomUsecase forRoomDto(RoomDto roomDto) {
        this.roomDto = roomDto;
        return this;
    }

    public UpdateRoomUsecase withRoomName(String roomName) {
        this.roomName = roomName;
        return this;
    }

    public void run() {
        roomForUpdate();
    }

    private void roomForUpdate() {
        Room updatedRoom = roomRepository.findById(roomName).orElseThrow(() -> new BadRequestException("No such room."));
        if (roomDto.getLocationDescription() != null && !roomDto.getLocationDescription().isEmpty() && !roomDto.getLocationDescription().equals(updatedRoom.getLocationDescription())) {
            updatedRoom.setLocationDescription(roomDto.getLocationDescription());
        }
        if (roomDto.getNumberOfSeats() != updatedRoom.getNumberOfSeats()) {
            updatedRoom.setNumberOfSeats(roomDto.getNumberOfSeats());
        }
        if (roomDto.getContainProjector() != null && !roomDto.getContainProjector().equals(updatedRoom.isContainProjector())) {
            updatedRoom.setContainProjector(roomDto.getContainProjector());
        }
        if (roomDto.getPhoneNumber() != null && !roomDto.getPhoneNumber().equals(updatedRoom.getPhoneNumber())) {
            updatedRoom.setPhoneNumber(roomDto.getPhoneNumber());
        }
        roomRepository.save(updatedRoom);

    }
}
