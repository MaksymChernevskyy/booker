package com.be.booker.business.usecases.user;

import com.be.booker.business.entity.User;
import com.be.booker.business.entity.entitydto.UserDto;
import com.be.booker.business.exceptions.BadRequestException;
import com.be.booker.business.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UpdateUserUsecase {
    private UserRepository userRepository;
    private UserDto userDto;
    private String userLogin;

    public UpdateUserUsecase withUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
        return this;
    }

    public UpdateUserUsecase forUserDto(UserDto userDto) {
        this.userDto = userDto;
        return this;
    }

    public UpdateUserUsecase withUserLogin(String userLogin) {
        this.userLogin = userLogin;
        return this;
    }

    public User run() {
        return updateUser();
    }

    private User updateUser() {
        User userForUpdate = userRepository.findById(userLogin).orElseThrow(() -> new BadRequestException("No such user."));
        userForUpdate.setName(userDto.getName());
        userForUpdate.setPassword(userDto.getPassword());
        userForUpdate.setSurname(userDto.getSurname());
        return userForUpdate;
    }
}
