package com.be.booker.business.usecases.user;

import com.be.booker.business.configs.security.RegistrationForm;
import com.be.booker.business.entity.User;
import com.be.booker.business.exceptions.BadRequestException;
import com.be.booker.business.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UpdateUserUsecase {
    private UserRepository userRepository;
    private RegistrationForm registrationForm;
    private String userLogin;

    public UpdateUserUsecase withUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
        return this;
    }

    public UpdateUserUsecase forRegistrationForm(RegistrationForm registrationForm) {
        this.registrationForm = registrationForm;
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
        userForUpdate.setName(registrationForm.getName());
        userForUpdate.setPassword(registrationForm.getPassword());
        userForUpdate.setSurname(registrationForm.getSurname());
        userRepository.save(userForUpdate);
        return userForUpdate;
    }
}
