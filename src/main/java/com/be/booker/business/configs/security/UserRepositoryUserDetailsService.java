package com.be.booker.business.configs.security;

import com.be.booker.business.entity.entitydto.UserDto;
import com.be.booker.business.exceptions.BadRequestException;
import com.be.booker.business.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {
    private UserService userService;

    @Autowired
    public UserRepositoryUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDto user = userService.getUser(username);
        if (user != null) {
            return user;
        }
        throw new BadRequestException("Such user does not exist");
    }
}
