package com.be.booker.business.usecases.user;

import com.be.booker.business.entity.User;
import com.be.booker.business.entity.entitydto.UserWithoutPasswordDto;
import com.be.booker.business.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GetAllUsersUsecase {
    private UserRepository userRepository;

    public GetAllUsersUsecase withUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
        return this;
    }

    public List<UserWithoutPasswordDto> run() {
        return getUserWithoutPasswordDto();
    }

    private List<UserWithoutPasswordDto> getUserWithoutPasswordDto() {
        List<UserWithoutPasswordDto> list = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            UserWithoutPasswordDto userWithoutPasswordDto = new UserWithoutPasswordDto();
            userWithoutPasswordDto.setLogin(user.getLogin());
            userWithoutPasswordDto.setName(user.getName());
            userWithoutPasswordDto.setSurname(user.getSurname());
            list.add(userWithoutPasswordDto);
        }
        return list;
    }
}
