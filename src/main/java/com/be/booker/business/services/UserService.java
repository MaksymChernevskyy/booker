package com.be.booker.business.services;

import com.be.booker.business.entity.User;
import com.be.booker.business.entity.entitydto.UserDto;
import com.be.booker.business.entity.entitydto.UserWithoutPasswordDto;
import com.be.booker.business.repository.UserRepository;
import com.be.booker.business.usecases.user.DeleteUserUsecase;
import com.be.booker.business.usecases.user.GetAllUsersUsecase;
import com.be.booker.business.usecases.user.SaveUserUsecase;
import com.be.booker.business.usecases.user.UpdateUserUsecase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private DeleteUserUsecase deleteUserUsecase;
    private SaveUserUsecase saveUserUsecase;
    private UpdateUserUsecase updateUserUsecase;
    private GetAllUsersUsecase getAllUsersUsecase;
    private UserRepository userRepository;

    @Autowired
    public UserService(DeleteUserUsecase deleteRoomUsecase, SaveUserUsecase saveRoomUsecase, UpdateUserUsecase updateUserUsecase,
                       GetAllUsersUsecase getAllUsersUsecase, UserRepository userRepository) {
        this.deleteUserUsecase = deleteRoomUsecase;
        this.saveUserUsecase = saveRoomUsecase;
        this.updateUserUsecase = updateUserUsecase;
        this.getAllUsersUsecase = getAllUsersUsecase;
        this.userRepository = userRepository;
    }

    public User saveUser(UserDto userDto) {
        return saveUserUsecase
                .withUserRepository(userRepository)
                .forUser(userDto)
                .run();
    }

    public List<UserWithoutPasswordDto> getAllUsers() {
        return getAllUsersUsecase
                .withUserRepository(userRepository)
                .run();
    }

    public void deleteUser(String userLogin) {
        deleteUserUsecase
                .withUserRepository(userRepository)
                .forUserId(userLogin)
                .run();
    }

    public void updateUser(String userLogin, UserDto userDto) {
        updateUserUsecase
                .withUserRepository(userRepository)
                .withUserLogin(userLogin)
                .forUserDto(userDto)
                .run();
    }
}
