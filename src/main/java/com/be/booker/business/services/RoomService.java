package com.be.booker.business.services;

import com.be.booker.business.entity.Room;
import com.be.booker.business.entity.entitydto.RoomDto;
import com.be.booker.business.repository.RoomRepository;
import com.be.booker.business.usecases.room.DeleteRoomUsecase;
import com.be.booker.business.usecases.room.GetAllRoomsUsecase;
import com.be.booker.business.usecases.room.SaveRoomUsecase;
import com.be.booker.business.usecases.room.UpdateRoomUsecase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    private SaveRoomUsecase saveRoomUsecase;
    private GetAllRoomsUsecase getAllRoomsUsecase;
    private DeleteRoomUsecase deleteRoomUsecase;
    private UpdateRoomUsecase updateRoomUsecase;
    private RoomRepository roomRepository;

    @Autowired
    public RoomService(SaveRoomUsecase saveRoomUsecase, GetAllRoomsUsecase getAllRoomsUsecase, DeleteRoomUsecase deleteRoomUsecase, UpdateRoomUsecase updateRoomUsecase, RoomRepository roomRepository) {
        this.saveRoomUsecase = saveRoomUsecase;
        this.getAllRoomsUsecase = getAllRoomsUsecase;
        this.deleteRoomUsecase = deleteRoomUsecase;
        this.updateRoomUsecase = updateRoomUsecase;
        this.roomRepository = roomRepository;
    }

    public Room saveRoom(RoomDto roomDto) {
        return saveRoomUsecase
                .withRoomRepository(roomRepository)
                .forRoom(roomDto)
                .run();
    }

    public List<RoomDto> getAllRooms() {
        return getAllRoomsUsecase
                .withRoomRepository(roomRepository)
                .run();
    }

    public void deleteRoom(String roomName) {
        deleteRoomUsecase
                .forRoomId(roomName)
                .run();
    }

    public Room updateRoom(String roomName, RoomDto roomDto) {
        return updateRoomUsecase
                .withRoomRepository(roomRepository)
                .forRoomDto(roomDto)
                .withRoomName(roomName)
                .run();
    }
}
