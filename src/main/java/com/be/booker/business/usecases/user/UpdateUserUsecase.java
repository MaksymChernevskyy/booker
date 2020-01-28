package com.be.booker.business.usecases.user;

import com.be.booker.business.entity.User;
import com.be.booker.business.entitydto.UserDto;
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

  public void run() {
    updateUser();
  }

  private void updateUser() {
    User userForUpdate = userRepository.findById(userLogin).orElseThrow(() -> new BadRequestException("No such user."));
    if (userDto.getName() != null && !userDto.getName().isEmpty() && !userDto.getName().equals(userForUpdate.getName())) {
      userForUpdate.setName(userDto.getName());
    }
    if (userDto.getPassword() != null && !userDto.getPassword().isEmpty() && !userDto.getPassword().equals(userForUpdate.getPassword())) {
      userForUpdate.setPassword(userDto.getPassword());
    }
    if (userDto.getSurname() != null && !userDto.getSurname().isEmpty() && !userDto.getSurname().equals(userForUpdate.getSurname())) {
      userForUpdate.setSurname(userDto.getSurname());
    }
  }
}
