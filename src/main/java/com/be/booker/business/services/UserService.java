package com.be.booker.business.services;

import com.be.booker.business.configs.security.RegistrationForm;
import com.be.booker.business.entity.User;
import com.be.booker.business.entity.entitydto.UserDto;
import com.be.booker.business.entity.entitydto.UserWithoutPasswordDto;
import com.be.booker.business.repository.UserRepository;
import com.be.booker.business.usecases.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private DeleteUserUsecase deleteUserUsecase;
    private SaveUserUsecase saveUserUsecase;
    private UpdateUserUsecase updateUserUsecase;
    private GetAllUsersUsecase getAllUsersUsecase;
    private GetUserUsecase getUserUsecase;
    private UserRepository userRepository;

    @Autowired
    public UserService(DeleteUserUsecase deleteUserUsecase, SaveUserUsecase saveUserUsecase, UpdateUserUsecase updateUserUsecase, GetAllUsersUsecase getAllUsersUsecase, GetUserUsecase getUserUsecase, UserRepository userRepository) {
        this.deleteUserUsecase = deleteUserUsecase;
        this.saveUserUsecase = saveUserUsecase;
        this.updateUserUsecase = updateUserUsecase;
        this.getAllUsersUsecase = getAllUsersUsecase;
        this.getUserUsecase = getUserUsecase;
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

    public User updateUser(String userLogin, RegistrationForm registrationForm) {
        return updateUserUsecase
                .withUserRepository(userRepository)
                .withUserLogin(userLogin)
                .forRegistrationForm(registrationForm)
                .run();
    }

    public UserDto getUser(String userLogin) {
        return getUserUsecase
                .withUserRepository(userRepository)
                .withLogin(userLogin)
                .run();
    }
}
