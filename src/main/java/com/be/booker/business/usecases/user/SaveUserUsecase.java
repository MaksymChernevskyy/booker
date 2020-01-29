package com.be.booker.business.usecases.user;

import com.be.booker.business.entity.User;
import com.be.booker.business.entity.entitydto.UserDto;
import com.be.booker.business.exceptions.AlreadyExistsException;
import com.be.booker.business.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SaveUserUsecase {
    private UserRepository userRepository;
    private UserDto userDto;

    @Autowired
    public SaveUserUsecase withUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
        return this;
    }

    public SaveUserUsecase forUser(UserDto userDto) {
        this.userDto = userDto;
        return this;
    }

    public User run() {
        return createUser();
    }

    private User createUser() {
        User user = new User();
        user.setLogin(userDto.getLogin());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setPassword(userDto.getPassword());
        if (userRepository.existsById(userDto.getLogin())) {
            throw new AlreadyExistsException(String.format("User with login %s already exists", userDto.getLogin()));
        }
        return userRepository.save(user);
    }
}
