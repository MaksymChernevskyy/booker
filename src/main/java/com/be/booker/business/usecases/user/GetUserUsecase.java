package com.be.booker.business.usecases.user;

import com.be.booker.business.entity.User;
import com.be.booker.business.entity.entitydto.UserDto;
import com.be.booker.business.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetUserUsecase {

    private UserRepository userRepository;
    private String login;

    @Autowired
    public GetUserUsecase withUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
        return this;
    }

    public GetUserUsecase withLogin(String login) {
        this.login = login;
        return this;
    }

    public UserDto run() {
        return getUserWithoutPasswordDto();
    }

    private UserDto getUserWithoutPasswordDto() {
        User user = userRepository.findByLogin(login);
        UserDto userDto = new UserDto();
        userDto.setLogin(user.getLogin());
        userDto.setPassword(user.getPassword());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        return userDto;
    }
}
