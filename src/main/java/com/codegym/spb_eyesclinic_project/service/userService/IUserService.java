package com.example.furnitureweb.service.userService;

import com.example.furnitureweb.model.User;
import com.example.furnitureweb.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface IUserService extends IGeneralService<User, String>, UserDetailsService {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

}
