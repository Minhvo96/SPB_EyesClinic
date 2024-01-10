package com.codegym.spb_eyesclinic_project.service.userService;


import com.codegym.spb_eyesclinic_project.domain.User;
import com.codegym.spb_eyesclinic_project.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface IUserService extends IGeneralService<User, String>, UserDetailsService {
    Optional<User> findByPhoneNumber(String phoneNumber);

    Boolean existsByPhoneNumber(String username);

}
