package com.codegym.spb_eyesclinic_project.service.user;


import com.codegym.spb_eyesclinic_project.domain.Avatar;
import com.codegym.spb_eyesclinic_project.domain.Customer;
import com.codegym.spb_eyesclinic_project.domain.Enum.EDegree;
import com.codegym.spb_eyesclinic_project.domain.Enum.EStatus;
import com.codegym.spb_eyesclinic_project.domain.Staff;
import com.codegym.spb_eyesclinic_project.domain.User;
import com.codegym.spb_eyesclinic_project.repository.AvatarRepository;
import com.codegym.spb_eyesclinic_project.repository.CustomerRepository;
import com.codegym.spb_eyesclinic_project.repository.StaffRepository;
import com.codegym.spb_eyesclinic_project.repository.UserRepository;
import com.codegym.spb_eyesclinic_project.service.user.request.UserSaveRequest;
import com.codegym.spb_eyesclinic_project.service.user.response.UserListResponse;
import com.codegym.spb_eyesclinic_project.utils.AppUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServices {


    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final CustomerRepository customerRepository;
    private final AvatarRepository avatarRepository;
    private final PasswordEncoder passwordEncoder;

//    public User findByPhoneNumber(String phoneNumber){
//        return userRepository.findUserByPhoneNumber(phoneNumber).orElseThrow(()-> new RuntimeException("Not found!"));
//    }

    public List<UserListResponse> findAllUsers() {
        return userRepository.findAll().stream().map(item -> new UserListResponse(item.getId(),
                item.getFullName(),
                item.getPhoneNumber(),
                item.getRole().toString())).collect(Collectors.toList());
    }

    public String createUser(UserSaveRequest request) {

        var user = AppUtils.mapper.map(request, User.class);


        if (request.getRole().equals("ROLE_CUSTOMER")) {
            var users = userRepository.findAll();
            var listPhoneNumbers = users.stream().map(User::getPhoneNumber).toList();
            for (int i = 0; i < listPhoneNumbers.size(); i++) {
                if (listPhoneNumbers.get(i).equals(request.getPhoneNumber())) {
                    var customer = customerRepository.findCustomerByPhone(request.getPhoneNumber());
                    if(customer != null) {
                        return customer.getId().toString();
                    } else {
                        return "error";
                    }
                }
            }
            var userFinal = userRepository.save(user);
            var customer = new Customer(request.getAge(), userFinal);
            var newCustomer = customerRepository.save(customer);
            return newCustomer.getId().toString();
        }
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var userFinal = userRepository.save(user);

        var avatar = new Avatar(request.getAvatar());
        var avatarFinal = avatarRepository.save(avatar);

        var staff = new Staff(request.getExperience(), EDegree.valueOf(request.getDegree()), avatarFinal, userFinal);
        staff.setStatus(EStatus.WORKING);
        var newStaff = staffRepository.save(staff);

        return newStaff.getId().toString();
    }








        // lấy role trong request > kiểm tra nếu mà Staff thì call StaffRepo.save, nếu mà Cus thì call CusRepo.save
        // staff(userId, phoneNumber) cus(userId, phoneNumber)

//        if ("Staff".equals(request.getRole())) {
//            var staff = AppUtils.mapper.map(request, Staff.class);
//            staffRepository.save(staff);
//        } else if ("Cus".equals(request.getRole())) {
//            var customer = AppUtils.mapper.map(request, Customer.class);
//            customerRepository.save(customer);
//        }

//        ERole role = ERole.valueOf(request.getRole());
//        switch (role) {
//            case ROLE_USER:
//                var customer = AppUtils.mapper.map(request, Customer.class);
//                customer.setUser(user);
//                customerRepository.save(customer);
//                break;
//            case ROLE_ADMIN:
//            case ROLE_RECEPTIONIST:
//            case ROLE_DOCTOR:
//                var staff = AppUtils.mapper.map(request, Staff.class);
//                staff.setUser(user);
//                staffRepository.save(staff);
//                break;
//            default:
//
//                break;
//        }
//    }
//
//    public String getUserRoleByPhoneNumber(String phoneNumber) {
//        User user = userRepository.findUserByPhoneNumber(phoneNumber)
//                .orElseThrow(() -> new RuntimeException("Not found!"));
//
//        // Lấy vai trò từ đối tượng User
//        var role = String.valueOf(user.getRole());
//
//        return role;
//    }

}
