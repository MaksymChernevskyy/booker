package com.be.booker.business.usecases.room;

import com.be.booker.business.repository.RoomRepository;
import org.springframework.stereotype.Component;

@Component
public class DeleteRoomUsecase {
    private RoomRepository roomRepository;
    private String roomName;

    public DeleteRoomUsecase withJobRepository(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
        return this;
    }

    public DeleteRoomUsecase forRoomId(String roomName) {
        this.roomName = roomName;
        return this;
    }

    public void run() {
        roomRepository.deleteById(roomName);
    }
}
