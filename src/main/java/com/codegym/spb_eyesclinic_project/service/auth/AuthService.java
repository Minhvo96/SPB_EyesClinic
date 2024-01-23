package com.codegym.spb_eyesclinic_project.service.auth;


import com.codegym.spb_eyesclinic_project.domain.Enum.ERole;
import com.codegym.spb_eyesclinic_project.domain.User;
import com.codegym.spb_eyesclinic_project.domain.dto.authDTO.LoginRequest;
import com.codegym.spb_eyesclinic_project.domain.dto.authDTO.RegisterRequest;
import com.codegym.spb_eyesclinic_project.repository.UserRepository;
import com.codegym.spb_eyesclinic_project.utils.AppUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.*;

@Service
@AllArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public void register(RegisterRequest request) {
        var user = AppUtils.mapper.map(request, User.class);
        user.setRole(ERole.ROLE_RECEPTIONIST);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public boolean checkPhoneNumber(RegisterRequest request, BindingResult result) {
        boolean check = false;
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            result.rejectValue("phoneNumber", "phoneNumber", "Số điện thoại đã tồn tại!");
            check = true;
        }
        return check;
    }

    public boolean checkPhoneNumberAndPassword(LoginRequest request, BindingResult result) {
        boolean check = false;
        Optional<User> userOptional = userRepository.findByPhoneNumber(request.getPhoneNumber());
        if (userOptional.isEmpty()
                || passwordEncoder.matches(userOptional.get().getPassword(),request.getPassword())) {
            result.rejectValue("password", "password",
                    "Số điện thoại hoặc mật khẩu không đúng");
            check = true;
        }
        return check;
    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("User not Exist"));
        var role = new ArrayList<SimpleGrantedAuthority>();
        role.add(new SimpleGrantedAuthority(user.getRole().toString()));
        return new org.springframework.security.core.userdetails.User(user.getPhoneNumber(), user.getPassword(), role);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return authorities;
    }
}
