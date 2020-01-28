package com.be.booker.business.usecases.user;

import com.be.booker.business.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class DeleteUserUsecase {
    private UserRepository userRepository;
    private String userLogin;

    public DeleteUserUsecase withUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
        return this;
    }

    public DeleteUserUsecase forUserId(String userLogin) {
        this.userLogin = userLogin;
        return this;
    }

    public void run() {
        userRepository.deleteById(userLogin);
    }
}
