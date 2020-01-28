package com.be.booker.business.bootstrap;

import com.be.booker.business.entity.Room;
import com.be.booker.business.entity.User;
import com.be.booker.business.repository.RoomRepository;
import com.be.booker.business.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private RoomRepository roomRepository;
    private UserRepository userRepository;

    public Bootstrap(RoomRepository roomRepository, UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        User user = new User();
        user.setName("John");
        user.setSurname("Smith");
        user.setLogin("jsmith");
        user.setPassword("qwerty");

        User user2 = new User();
        user2.setName("Jane");
        user2.setSurname("Doe");
        user2.setLogin("jdoe");
        user2.setPassword("mySecret");

        userRepository.save(user);
        userRepository.save(user2);

        Room room = new Room();
        room.setRoomName("Large Room");
        room.setLocationDescription("1st floor");
        room.setNumberOfSeats(10);
        room.setContainProjector(true);
        room.setPhoneNumber("22-22-22-22");

        Room room2 = new Room();
        room2.setRoomName("Medium Room");
        room2.setLocationDescription("1st floor");
        room2.setNumberOfSeats(6);
        room2.setContainProjector(true);

        Room room3 = new Room();
        room3.setRoomName("Small Room");
        room3.setLocationDescription("2nd floor");
        room3.setNumberOfSeats(4);
        room3.setContainProjector(false);

        roomRepository.save(room);
        roomRepository.save(room2);
        roomRepository.save(room3);

    }
}
