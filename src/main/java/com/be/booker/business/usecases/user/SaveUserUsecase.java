package com.be.booker.business.usecases.user;

import com.be.booker.business.entity.User;
import com.be.booker.business.entitydto.UserDto;
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
            return userRepository.save(user);
    }
}
