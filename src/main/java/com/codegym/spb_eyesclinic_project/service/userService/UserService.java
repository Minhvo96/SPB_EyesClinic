package com.codegym.spb_eyesclinic_project.service.userService;


import com.codegym.spb_eyesclinic_project.domain.User;
import com.codegym.spb_eyesclinic_project.domain.UserPrinciple;
import com.codegym.spb_eyesclinic_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findById(String id) {
        Optional<User> optionalUser = userRepository.findById(Long.valueOf(id));
        if(optionalUser.isEmpty()){
            return null;
        }
        return optionalUser;
    }

    @Override
    public Optional<User> findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public Boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(phoneNumber);
        }
        return UserPrinciple.build(userOptional.get());
    }

//    public boolean checkUsernameOrPhoneNumberOrEmail(RegisterRequest request, BindingResult result){
//        boolean check = false;
//        if(userRepository.existsByUsernameIgnoreCase(request.getUsername())){
//            result.rejectValue("username", "username", "Tên người dùng đã tồn tại!");
//            check = true;
//        }
//        if(userRepository.existsByEmailIgnoreCase(request.getEmail())){
//            result.rejectValue("email", "email", "Email đã tồn tại!");
//            check = true;
//        }
//        if(userRepository.existsByPhoneNumber(request.getPhoneNumber())){
//            result.rejectValue("phoneNumber","phoneNumber","Số điện thoại đã tồn tại!");
//            check = true;
//        }
//        return check;
//    }
}
